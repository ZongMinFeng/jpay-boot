package com.jpay.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.jpay.common.exception.JpayBootException;
import com.jpay.common.pojo.vo.Result;
import com.jpay.common.util.MD5Util;
import com.jpay.common.util.RedisUtil;
import com.jpay.system.pojo.bo.LoginCheckBo;
import com.jpay.system.pojo.vo.LoginVo;
import com.jpay.system.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@Slf4j
@Api(tags="用户登录")
public class LoginController {

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/getCheckCode")
    public Result<Map<String,String>> getCheckCode(){
        Result<Map<String,String>> result = new Result<Map<String,String>>();
        Map<String,String> map = new HashMap<String,String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES,4);
            String key = MD5Util.MD5Encode(code+System.currentTimeMillis(), "utf-8");
            redisUtil.set(key, code, 60);
            map.put("key", key);
            map.put("code",code);
            result.setResult(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @ApiOperation(value="用户检查")
    @RequestMapping(value="loginCheck", method = RequestMethod.POST)
    public Result<LoginVo> loginCheck(@RequestBody LoginCheckBo loginCheckBo){
        Result<LoginVo> result = new Result<LoginVo>();
        String userInfo=loginCheckBo.getUserInfo();
        System.out.println("LoginController login参数:"+userInfo);//debug
        if (userInfo.length()<65){
            //请求用户信息错误
            throw new JpayBootException("用户登录信息错误");
        }
        String randKey=userInfo.substring(0, 32);
        String passwd=userInfo.substring(32, 64);
        String code=userInfo.substring(64);
        LoginVo loginVo=loginService.loginCheck(randKey, passwd, code);
        result.setResult(loginVo);
        result.success("登录成功");
        return result;
    }
}
