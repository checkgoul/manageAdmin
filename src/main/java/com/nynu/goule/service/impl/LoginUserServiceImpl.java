package com.nynu.goule.service.impl;

import com.alibaba.fastjson.JSON;
import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.LoginUserService;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.misc.Cleaner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Service
public class LoginUserServiceImpl implements LoginUserService {
    private final static Logger logger = LoggerFactory.getLogger(LoginUserServiceImpl.class);

    @Resource
    private LoginUserMapper loginUserMapper;
    @Resource
    private OperateLogService operateLogService;

    Security security = new Security();

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
        logger.info("获取到的验证码为：{}" ,verification1);
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
        String sex = ValidateUtil.isBlankParam(paramMap,"sex","性别");
        String orgaId; // 组织机构id
        List<String> orgaList = (List<String>) paramMap.get("orgaName");
        if(orgaList.size() == 0){
            result.setMsg("请选择所属组织");
            result.setStatus(Result.RTN_CODE.ERROR);
            return result;
        }else{
            String orgaName = orgaList.get(0);
            orgaId = loginUserMapper.getOrgaIdByOrgaName(orgaName);
        }
        //通过主账号查询看是否为修改  因为前台生成的主账号一定是唯一的  如果重复则说明是修改操作
        int acctNum = loginUserMapper.queryAcctNum(paramMap);
        //修改操作
        if(1 == acctNum){
            //查询当前操作人权限 修改用户
            String mainAcctId = (String) map.get("username");
            String authType = CommonConstants.AUTHID.UPDATE + "," + CommonConstants.AUTHID.USER_UPDATE;
            boolean isAuth = security.checkAccountAuth(mainAcctId, authType);
            if(!isAuth){
                result.setMsg("您没有当前操作权限");
                result.setStatus(Result.RTN_CODE.ERROR);
                return result;
            }
            //通过主账号去查询该账号下的所有数据，进行比对然后修改
            List<Map<String, Object>> userInfo = loginUserMapper.queryUserInfoByAcct(paramMap);
            //目前能够修改的有 性别、手机号、组织机构、邮箱、身份证号
            String sexOld = (String) userInfo.get(0).get("sex");
            String phoneOld = (String) userInfo.get(0).get("telphone");
            String orgaIdOld = (String) userInfo.get(0).get("orgaId");
            String mailOld = (String) userInfo.get(0).get("mail");
            String prsnIdNumOld = (String) userInfo.get(0).get("prsnIdNum");
            String birthDayOld = (String) userInfo.get(0).get("birthDay");
            String acctStatus = (String) userInfo.get(0).get("acctStatus");
            if("锁定".equals(acctStatus)){
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("锁定账户不可修改，请先解锁");
                return result;
            }
            for (Map<String, Object> users : userInfo){
                //做判断时，先判断传入的值是否为空，其次判断新值与旧值是否一样，最后判断新值是否可以重复，若条件都满足则修改。
                if(!sex.equals(users.get("sex"))){
                    sexOld = sex;
                }
                if(!StringUtil.isEmpty(paramMap.get("telPhone")) && !telPhone.equals(users.get("telphone"))){
                    //判断新的手机号是否重复
                    int phoneNum = loginUserMapper.queryPhoneNum(paramMap);
                    if(phoneNum > 0){
                        result.setMsg("该手机号已注册");
                        result.setStatus(Result.RTN_CODE.ERROR);
                        return result;
                    }else{
                        phoneOld = (String) paramMap.get("telPhone");
                    }
                }
                if(!orgaId.equals(users.get("orgaId"))){
                    orgaIdOld = orgaId;
                }
                if(StringUtil.isEmpty(mail) || !mail.equals(users.get("mail"))){
                    mailOld = mail;
                }
                if(StringUtil.isEmpty(prsnIdNum) || !prsnIdNum.equals(users.get("prsnIdNum"))){
                    if(StringUtil.isEmpty(prsnIdNum)){
                        prsnIdNumOld = prsnIdNum;
                    } else if(RegExUtil.isCredNum(prsnIdNum)) {
                        birthDayOld = prsnIdNum.substring(6, 10) + "-" + prsnIdNum.substring(10, 12) + "-" + prsnIdNum.substring(12, 14);
                        prsnIdNumOld = prsnIdNum;
                    }else{
                        result.setStatus(Result.RTN_CODE.ERROR);
                        result.setMsg("身份证格式错误");
                        return result;
                    }
                }
            }
            Map<String, Object> userMapUpd = new HashMap<>();
            userMapUpd.put("telPhone",phoneOld);
            userMapUpd.put("sex",ChooseToUse("reverseSex",sexOld));
            userMapUpd.put("orgaId",orgaIdOld);
            userMapUpd.put("mail",mailOld);
            userMapUpd.put("prsnIdNum",prsnIdNumOld);
            userMapUpd.put("birthDay",birthDayOld);
            userMapUpd.put("username",username);
            int updNum = loginUserMapper.updateUserInfo(userMapUpd);
            if(updNum > 0){
                result.setStatus(Result.RTN_CODE.SUCCESS);
                result.setMsg("修改成功");
            }else{
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("修改失败");
            }

            //记录日志操作
            Map<String, Object> operateMap = new HashMap<>();
            Map<String, Object> afterCntt = new HashMap<>();
            Map<String, Object> beforeCntt = new HashMap<>();
            //操作前用户数据
            String telphone1 = (String) userInfo.get(0).get("telphone");
            beforeCntt.put("sex",userInfo.get(0).get("sex"));
            beforeCntt.put("telPhone",telphone1.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            beforeCntt.put("orgaId",userInfo.get(0).get("orgaId"));
            beforeCntt.put("mail",userInfo.get(0).get("mail"));
            beforeCntt.put("prsnIdNum",userInfo.get(0).get("prsnIdNum"));
            beforeCntt.put("birthDay",userInfo.get(0).get("birthDay"));
            //操作后用户数据
            afterCntt.put("sex",sexOld);
            afterCntt.put("telPhone",phoneOld.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
            afterCntt.put("orgaId",orgaIdOld);
            afterCntt.put("mail",mailOld);
            afterCntt.put("prsnIdNum",prsnIdNumOld);
            afterCntt.put("birthDay",birthDayOld);

            String beforeMsg = JsonUtil.convertObject2Json(beforeCntt);
            String afterMsg = JsonUtil.convertObject2Json(afterCntt);
            String opCntt = "修改用户“" + userMapUpd.get("username") + "”";
            operateMap.put("acctId",map.get("username"));
            operateMap.put("opType",OperateLog.OP_TYPE.MODIFY);
            operateMap.put("opMenu",OperateLog.OP_PATH.USER_MANAGEMENT);
            operateMap.put("beforeCntt",beforeMsg);
            operateMap.put("afterCntt",afterMsg);
            operateMap.put("logCntt",opCntt);
            operateLogService.addOperateLog(operateMap);
            return result;

        }
        //查询当前操作人权限 添加用户
        String mainAcctId = (String) map.get("username");
        String authType = CommonConstants.AUTHID.INSERT + "," + CommonConstants.AUTHID.USER_INSERT;
        boolean isAuth = security.checkAccountAuth(mainAcctId, authType);
        if(!isAuth){
            result.setMsg("您没有当前操作权限");
            result.setStatus(Result.RTN_CODE.ERROR);
            return result;
        }
        if("人员姓名不能为空".equals(accountName)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg(accountName);
            return result;
        }
        if("性别不能为空".equals(sex)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg(sex);
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
        userInfoMap.put("sex",sex);
        userInfoMap.put("orgaId",orgaId);
        userInfoMap.put("username",username);
        userInfoMap.put("password",password);
        userInfoMap.put("addTime",time);
        int i = loginUserMapper.addUser(userInfoMap);
        if(i >= 0){
            result.setStatus(Result.RTN_CODE.SUCCESS);
            result.setMsg("添加用户信息成功");

            //记录操作日志
            String sexs = ChooseToUse("sex",sex);
            //获取组织信息用于插入日志

            Map<String, Object> operateMap = new HashMap<>();
            Map<String, Object> afterCntt = new HashMap<>();
            afterCntt.put("acctId",accountName);
            afterCntt.put("sex",sexs);
            afterCntt.put("orgaId",orgaId);
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
        //检验传入的姓名是否为空
        String accountName = ValidateUtil.isBlankParam(map,"accountName","人员姓名");
        if("人员姓名不能为空".equals(accountName)){
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("请先输入人员姓名");
            return result;
        }
        //登录的主账号为填写人姓名的拼音 例: 王瑜 -> wangyu 如果拼音相同,则依次递增 -> wangyu1
        String username = ToPinYin.converterToAllSpell(accountName);
        //模糊查询数据库中相似的数据
        List<Map<String, Object>> usernameList = loginUserMapper.queryUsernameCount(username);
        String order = "";
        if(usernameList != null && !usernameList.isEmpty()) {
            //for循环遍历
            for (Map<String, Object> realMap : usernameList) {
                String finalUsername = (String) realMap.get("username");
                //数据库内数据长度大于用户名长度的数据会截取一位，用于递增，小于的数据将会被去除
                if (finalUsername.length() > username.length()) {
                    finalUsername = finalUsername.substring(username.length());
                }else {
                    continue;
                }
                //截取的一位使用正则表达式匹配，防止出现wangyu与wangyue匹配。
                boolean flag = finalUsername.matches("^[0-9]*$");
                if(flag){
                    //将截取到的数字添加到order
                    if("".equals(order)){
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
                //将拿到的数组进行排序，排序后+1赋值给用户名
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

        //查询当前操作人权限 删除用户
        String mainAcctId = (String) map.get("username");
        String authType = CommonConstants.AUTHID.DELETE + "," + CommonConstants.AUTHID.USER_DELETE;
        boolean isAuth = security.checkAccountAuth(mainAcctId, authType);
        if(!isAuth){
            result.setMsg("您没有当前操作权限");
            result.setStatus(Result.RTN_CODE.ERROR);
            return result;
        }
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

        //查询当前操作人权限 锁定用户
        String mainAcctId = (String) map.get("username");
        String authType = CommonConstants.AUTHID.UPDATE + "," + CommonConstants.AUTHID.USER_UPDATE;
        boolean isAuth = security.checkAccountAuth(mainAcctId, authType);
        if(!isAuth){
            result.setMsg("您没有当前操作权限");
            result.setStatus(Result.RTN_CODE.ERROR);
            return result;
        }

        List<Integer> ids = (List<Integer>) map.get("ids");
        List<String> account = (List<String>) map.get("account");
        String lockReason = (String) map.get("lockReason");
        Map<String, Object> param = new HashMap<>();
        param.put("ids",ids);
        param.put("lockReason",lockReason);
        if(StringUtil.isNotEmpty(lockReason)){
            int lockNum = loginUserMapper.lockUser(param);
            if(lockNum >= 1){
                result.setMsg("锁定成功");
                result.setStatus(Result.RTN_CODE.SUCCESS);

                //记录操作日志
                Map<String, Object> operateMap = new HashMap<>();
                Map<String, Object> beforeMap = new HashMap<>();
                Map<String, Object> afterMap = new HashMap<>();
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < account.size(); i++){
                    sb.append(account.get(i)).append(",");
                }
                String msg = "账号“" + account + "”被锁定";
                beforeMap.put("acctStatus","正常");
                afterMap.put("acctStatus","锁定");
                String beforeMsg = JsonUtil.convertObject2Json(beforeMap);
                String afterMsg = JsonUtil.convertObject2Json(afterMap);
                operateMap.put("acctId", mainAcctId);
                operateMap.put("opType", OperateLog.OP_TYPE.MODIFY);
                operateMap.put("opMenu", OperateLog.OP_PATH.USER_MANAGEMENT);
                operateMap.put("beforeCntt", beforeMsg);
                operateMap.put("afterCntt", afterMsg);
                operateMap.put("logCntt", msg);
                operateLogService.addOperateLog(operateMap);
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

    @Override
    public Result resetPwd(Map<String, Object> map) {
        Result result = new Result();
        Map<String, Object> inMap = new HashMap<>();
        List<Map<String, Object>> inList = new ArrayList<>();
        List<Map<String, Object>> userList = loginUserMapper.queryUserInfoById(map);
        Iterator<Map<String, Object>> iterator = userList.iterator();
        while (iterator.hasNext()){
            Map<String, Object> itMap = iterator.next();
            String username = (String) itMap.get("username");
            String telphone = (String) itMap.get("telphone");
            String password = "nynu@" + username.substring(0,2) + telphone.substring(7,11);
            itMap.put("password",password);
            inList.add(itMap);
            iterator.remove();
        }
        inMap.put("list",inList);
        try{
            int num = loginUserMapper.resetPwd(inMap);
            if(num >= 0) {
                result.setStatus(Result.RTN_CODE.SUCCESS);
                result.setMsg("密码重置成功");
            }else {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("密码重置失败");
            }
        }catch (Exception e){
            throw e;
        }
        return result;
    }

    @Override
    public Result getOrgaInfo() {
        Result result = new Result();
        List<Map<String, Object>> orgaList = loginUserMapper.getOrgaInfo();
        result.setData(orgaList);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    @Override
    public Result exportUserInfo(){
        Result result = new Result();
        List<LoginUser> userInfo = loginUserMapper.exportUserInfo();
        result.setData(userInfo);
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

    /**
     *
     * @param type
     * @param value
     * @return
     */
    private String ChooseToUse(String type, String value){
        String res = "";
        if("sex".equals(type)){
            switch (value){
                case "1":
                    res = "男";
                    break;
                case "2":
                    res = "女";
                    break;
                default:
                    res = "其他";
            }
        }
        if("reverseSex".equals(type)){
            switch (value){
                case "男":
                    res = "1";
                    break;
                case "女":
                    res = "2";
                    break;
                default:
                    res = "0";
            }
        }
        return res;
    }
}
