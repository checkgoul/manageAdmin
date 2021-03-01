package com.nynu.goule.service.impl;

import com.alibaba.fastjson.JSON;
import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.LoginUserService;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

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
        String addr = request.getRemoteAddr();
        System.err.println("addr : " + addr);
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
            //查询账号是否已经锁定
            int lockNum = loginUserMapper.checkLock(username);
            if(lockNum == 1){
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("该账号已锁定,请联系管理员");
                return result;
            }
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
        String accountName = ValidateUtil.isBlankParam(paramMap,"accountName","人员姓名");
        String telPhone = ValidateUtil.isBlankParam(paramMap,"telPhone","手机号");
        String username = ValidateUtil.isBlankParam(paramMap,"username","主账号");
        String mail = (String) paramMap.get("mail");
        String prsnIdNum = (String) paramMap.get("prsnIdNum");
        if("人员姓名不能为空".equals(accountName)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg(accountName);
            return result;
        }
        if("手机号不能为空".equals(telPhone)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg(telPhone);
            return result;
        }
        if(!RegExUtil.isMobile(telPhone)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("手机号格式错误");
            return result;
        }
        int phoneNum = loginUserMapper.queryPhoneNum(paramMap);
        if(phoneNum >= 1) {
            result.setMsg("该手机号已注册");
            result.setStatus(Result.RTN_CODE.ERROR);
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
        // password 默认为 nynu + @ + 姓名拼音前两位 + 手机号后四位 例: nynu@wa9321
        String password = "nynu@"+username.substring(0,2)+telPhone.substring(7);
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
            afterCntt.put("telPhone",telPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            if("".equals(prsnIdNum) || null == prsnIdNum){
                afterCntt.put("prsnIdNum","");
            }else {
                afterCntt.put("prsnIdNum", prsnIdNum.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2"));
            }
            afterCntt.put("addTime",time);
            String msg = JsonUtil.convertObject2Json(afterCntt);
            String opCntt = "新增用户“"+ accountName +"”";
            operateMap.put("acctId",map.get("username"));
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

    @Override
    public Result toGetAcctId(Map<String, Object> map) {
        Result result = new Result();
        String accountName = ValidateUtil.isBlankParam(map,"accountName","人员姓名");
        if("人员姓名不能为空".equals(accountName)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("请先输入人员姓名");
            return result;
        }
        //登录的主账号为填写人姓名的拼音 例: 王瑜 -> wangyu 如果拼音相同,则依次递增 -> wangyu1
        String username = ToPinYin.converterToAllSpell(accountName);
        List<Map<String, Object>> usernameList = loginUserMapper.queryUsernameCount(username);
        String order = "";
        if(usernameList != null && !usernameList.isEmpty()) {
            for (Map<String, Object> realMap : usernameList) {
                String finalUsername = (String) realMap.get("username");
                if (finalUsername.length() > username.length()) {
                    finalUsername = finalUsername.substring(username.length());
                }else {
                    continue;
                }
                boolean flag = finalUsername.matches("^[0-9]*$");
                if(flag){
                    if(order == ""){
                        order = finalUsername;
                    } else{
                        order = order + "," + finalUsername;
                    }
                }
            }
            if(StringUtil.isNotEmpty(order)){
                String[] arrs = order.split(",");
                int[] ints = new int[arrs.length];
                for(int i=0; i< arrs.length; i++){
                    ints[i] = Integer.parseInt(arrs[i]);
                }
                Arrays.sort(ints);
                username = username + Integer.toString(ints[ints.length - 1] + 1);
            }else{
                username = username + "1";
            }
        }
        result.setData(username);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    /**
     * 获取所有用户的信息
     * @return
     */
    @Override
    public Result getAllUserInfo() {
        Result result = new Result();
        List<Map<String, Object>> userInfoList = loginUserMapper.getAllUserInfo();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("users",userInfoList);
        returnMap.put("roles","");
        result.setData(returnMap);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    @Override
    public Result checkPhoneNum(Map<String, Object> map) {
        Result result = new Result();
        String telPhone = (String) map.get("telPhone");
        if(null != telPhone) {
            if (!RegExUtil.isMobile(telPhone)) {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("手机号格式错误");
                return result;
            }
            int phoneNum = loginUserMapper.queryPhoneNum(map);
            if (phoneNum >= 1) {
                result.setMsg("该手机号已注册");
                result.setStatus(Result.RTN_CODE.ERROR);
            } else {
                result.setStatus(Result.RTN_CODE.SUCCESS);
            }
        }
        return result;
    }

    @Override
    public Result checkPwd(Map<String, Object> map) {
        Result result = new Result();
        //默认密码操作都为正确的
        result.setStatus(Result.RTN_CODE.SUCCESS);
        if(map.size() != 0) {
            if (null != map.get("username")) {
                LoginUser loginUser = loginUserMapper.getOldPwd(map);
                //数据库中的原密码
                String oldPwd = loginUser.getPassword();
                //前端传入原密码
                String oldPwd1 = (String) map.get("oldPwd");
                if (null != oldPwd1) {
                    if ((oldPwd.length() != oldPwd1.length() || !oldPwd.equals(oldPwd1))) {
                        result.setMsg("原密码输入错误");
                        result.setStatus(Result.RTN_CODE.ERROR);
                    }
                }
            } else {
                String newWord = (String) map.get("newPwd");
                String confirmPwd = (String) map.get("confirmPwd");
                if(null == newWord || null == confirmPwd){
                    result.setMsg("请输入新密码");
                    result.setStatus(Result.RTN_CODE.ERROR);
                    return result;
                }
                if (isContainSpaces("newPwd", map)) {
                    result.setMsg("密码中不允许含有空格");
                    result.setStatus(Result.RTN_CODE.ERROR);
                    return result;
                }
                if (newWord.length() != confirmPwd.length() || !newWord.equals(confirmPwd)) {
                    result.setMsg("两次密码不一致");
                    result.setStatus(Result.RTN_CODE.ERROR);
                    return result;
                }
            }
        }
        return result;
    }

    @Override
    public Result updatePwd(Map<String, Object> map) {
        Result result = new Result();
        String oldPwd = (String) map.get("oldPwd");
        String newPwd = (String) map.get("newPwd");
        String username = (String) map.get("username");
        int i = loginUserMapper.updatePwd(map);
        if(1 == i){
            result.setMsg("密码修改成功");
            result.setStatus(Result.RTN_CODE.SUCCESS);
        }else{
            result.setMsg("密码修改失败");
            result.setStatus(Result.RTN_CODE.ERROR);
        }
        return result;
    }

    @Override
    public Result delUser(Map<String, Object> map) {
        Result result = new Result();
        Map<String, Object> param = new HashMap<>();
        //被操作账号ids
        List<Integer> idList = (List<Integer>) map.get("ids");
        //被操作账号主账号
        List<String> accountList = (List<String>) map.get("account");
        String account = "";
        //操作人姓名
        String username = (String) map.get("username");
        param.put("list",idList);
        int delNum = loginUserMapper.delUser(param);
        if(delNum >= 1){
            result.setStatus(Result.RTN_CODE.SUCCESS);
            result.setMsg("删除成功");
            for(int i = 0; i < accountList.size(); i++){
                account += accountList.get(i) + ",";
            }
            //被操作账号的String
            account = account.substring(0, account.length()-1);

            //记录日志
            Map<String, Object> operateMap = new HashMap<>();
            Map<String, Object> beforeMap = new HashMap<>();
            String msg = "删除主账号操作";
            beforeMap.put("ids", account);
            String beforeMsg = JsonUtil.convertObject2Json(beforeMap);
            operateMap.put("acctId", username);
            operateMap.put("opType", OperateLog.OP_TYPE.DELETE);
            operateMap.put("opMenu", OperateLog.OP_PATH.USER_MANAGEMENT);
            operateMap.put("beforeCntt", beforeMsg);
            operateMap.put("logCntt", msg);
            operateLogService.addOperateLog(operateMap);
        }else{
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("删除失败");
        }
        return result;
    }

    @Override
    public Result lockUser(Map<String, Object> map) {
        Result result = new Result();
        List<Integer> ids = (List<Integer>) map.get("ids");
        List<String> account = (List<String>) map.get("account");
        String username = (String) map.get("username");
        String lockReason = (String) map.get("lockReason");
        Map<String, Object> param = new HashMap<>();
        param.put("ids",ids);
        param.put("lockReason",lockReason);
        if(StringUtil.isNotEmpty(lockReason)){
            int lockNum = loginUserMapper.lockUser(param);
            if(lockNum >= 1){
                result.setMsg("锁定成功");
                result.setStatus(Result.RTN_CODE.SUCCESS);
            }else{
                result.setMsg("锁定失败");
                result.setStatus(Result.RTN_CODE.ERROR);
            }
        }else{
            result.setMsg("锁定原因不能为空");
            result.setStatus(Result.RTN_CODE.ERROR);
            return result;
        }
        return result;
    }

    @Override
    public Result breakLock(Map<String, Object> map) {
        Result result = new Result();
        List<Integer> ids = (List<Integer>) map.get("ids");
        List<String> account = (List<String>) map.get("account");
        String username = (String) map.get("username");
        Map<String, Object> param = new HashMap<>();
        param.put("ids",ids);
        int breakLockNum = loginUserMapper.breakLock(param);
        if(breakLockNum >= 1){
            result.setMsg("解锁成功");
            result.setStatus(Result.RTN_CODE.SUCCESS);
        }else{
            result.setMsg("解锁失败");
            result.setStatus(Result.RTN_CODE.ERROR);
        }
        return result;
    }

    /**
     * 检验该字段中是否含有空格
     * @param key
     * @param map
     * @return boolean
     */
    boolean isContainSpaces(String key, Map<String, Object> map){
        String value = (String) map.get(key);
        value.replace(" ","");
        String value1 = value.replaceAll(" ","");
        if(value.length() != value1.length()){
            return true;
        }else{
            return false;
        }
    }
}
