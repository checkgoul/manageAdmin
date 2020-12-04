package com.nynu.goule.service.impl;

import com.alibaba.fastjson.JSON;
import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.LoginUserService;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Resource
    private LoginUserMapper loginUserMapper;
    @Resource
    private OperateLogService operateLogService;

    /**
     * 用户登录接口
     * @param paramMap
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result login(Map<String, Object> paramMap,HttpServletRequest request) throws Exception {
        Result result = new Result();
        String username = ValidateUtil.isBlankParam(paramMap, "username", "用户名");
        String password = ValidateUtil.isBlankParam(paramMap, "password", "密码");
        String verification1 = ValidateUtil.isBlankParam(paramMap, "verification", "验证码");
        // 从session中取验证码
        String checkCode1 = (String) request.getSession().getAttribute("verifyCode");
        if(StringUtil.isEmpty(checkCode1)) {
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("请重新获取验证码!");
            return result;
        }
        //统一对字母转小写处理,防止对比失败
        String verification = verification1.toLowerCase();
        String checkCode = checkCode1.toLowerCase();
        if (!(checkCode.equals(verification))) {
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("验证码错误!");
            return result;
        } else {
            Map<String, Object> rtnMap = loginUserMapper.login(username);
            String realPwd = null;
            if (!CollectionUtils.isEmpty(rtnMap) && rtnMap.size() > 0) {
                realPwd = (String) rtnMap.get("password");
            }
            if (realPwd == null || "".equals(realPwd) || !password.equals(realPwd)) {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("账号或密码错误");
                return result;
            }
            result.setStatus(Result.RTN_CODE.SUCCESS);
            result.setData(rtnMap);
            return result;
        }
    }

    /**
     * 添加主账号
     * @param map
     * @return
     */
    @Override
    public Result addUser(Map<String, Object> map) {
        Result result = new Result();
        Map<String, Object> paramMap = (Map<String, Object>) map.get("user");
        Map<String, Object> userInfoMap = new HashMap<>();
        String accountName = ValidateUtil.isBlankParam(paramMap,"accountName","主账号");
        String telPhone = ValidateUtil.isBlankParam(paramMap,"telPhone","手机号");
        if("主账号不能为空".equals(accountName)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg(accountName);
            return result;
        }
        if("手机号不能为空".equals(telPhone)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg(telPhone);
            return result;
        }
        String mail = (String) paramMap.get("mail");
        String prsnIdNum = (String) paramMap.get("prsnIdNum");
        if(!RegExUtil.isMobile(telPhone)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("手机号填写错误");
            return result;
        }
        if (StringUtil.isNotEmpty(mail)){
            if(RegExUtil.isEmail(mail)){
                userInfoMap.put("mail",mail);
            }else{
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("邮箱格式错误");
                return result;
            }
        }
        if (StringUtil.isNotEmpty(prsnIdNum)){
            if(RegExUtil.isCredNum(prsnIdNum)) {
                String birthDay = prsnIdNum.substring(6, 10) + "-" + prsnIdNum.substring(10, 12) + "-" + prsnIdNum.substring(12, 14);
                userInfoMap.put("birthDay", birthDay);
            }else{
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("身份证格式错误");
                return result;
            }
        }else {
            userInfoMap.put("birthDay","");
        }
        String username = ToPinYin.converterToAllSpell(accountName); //登录的主账号为填写人姓名的拼音 例: 王瑜 -> wangyu 如果拼音相同,则依次递增 -> wangyu1
        //todo sql
        String password = "nynu@"+username.substring(0,2)+telPhone.substring(7); // password 为 nynu+@+姓名拼音前两位+手机号后四位 例: nynu@wa9321
        Timestamp time = DateUtil.getCurrentTimestamp();
        userInfoMap.put("accountName",accountName);
        userInfoMap.put("telPhone",telPhone);
        userInfoMap.put("prsnIdNum",prsnIdNum);
        userInfoMap.put("username",username);
        userInfoMap.put("password",password);
        userInfoMap.put("addTime",time);
        int i = loginUserMapper.addUser(userInfoMap);
        if(i >= 0){
            result.setStatus(Result.RTN_CODE.SUCCESS);
            result.setMsg("添加用户信息成功");

            //记录操作日志
            Map<String, Object> operateMap = new HashMap<>();
            Map<String, Object> afterCntt = new HashMap<>();
            afterCntt.put("acctId",accountName);
            afterCntt.put("telPhone",telPhone);
            afterCntt.put("prsnIdNum",prsnIdNum);
            afterCntt.put("addTime",time);
            String msg = JSON.toJSONString(afterCntt);
            String opCntt = "新增用户“"+ accountName +"”";
            operateMap.put("acctId",map.get("account_name"));
            operateMap.put("opType", OperateLog.OP_TYPE.ADD);
            operateMap.put("opMenu",OperateLog.OP_PATH.USER_MANAGEMENT);
            operateMap.put("afterCntt",msg);
            operateMap.put("logCntt",opCntt);
            operateLogService.addOperateLog(operateMap);
        } else {
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("添加用户信息失败");
        }
        return result;
    }
}
