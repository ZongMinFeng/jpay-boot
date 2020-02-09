package com.jpay.system.service;

import com.jpay.common.exception.JpayBootException;
import com.jpay.common.util.SnowFlake;
import com.jpay.system.mapper.AuthUserInfoMapper;
import com.jpay.system.mapper.SysInstMapper;
import com.jpay.system.pojo.po.AuthUserInfoPo;
import com.jpay.system.pojo.po.SysInstPo;
import com.jpay.system.pojo.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {
    @Autowired
    private AuthUserInfoMapper authUserInfoMapper;
    @Autowired
    private SysInstMapper uInstMapper;
    @Autowired
    private IssuerService issuerService;

    @Autowired
    private AcquirerService acquirerService;

    public LoginVo loginCheck(String randKey, String passwd, String code) {
        System.out.println("参数"+randKey+" "+passwd+" "+code);//debug
        //根据code获取用户信息
        AuthUserInfoPo authUserInfoPo = authUserInfoMapper.queryByCode(code);
        //用户名不对
        if (authUserInfoPo==null){
            throw new JpayBootException("无此用户或密码不正确");
        }
        //密码不对
        if (!authUserInfoPo.getPwd().equals(passwd)){
            throw new JpayBootException("无此用户或密码不正确");
        }
        LoginVo loginVo=new LoginVo();
        loginVo.setLoginId(authUserInfoPo.getId());

        //获取用户机构信息
        SysInstPo uInstPo = uInstMapper.selectByPrimaryKey(authUserInfoPo.getInstId());
        if (uInstPo ==null){
            //用户机构不存在
            throw new JpayBootException("机构不存在");
        }
        loginVo.setInstInfo(uInstPo);

        //获取用户发卡机构信息
        SysInstPo issuInstInfo=issuerService.getIssuUinst(authUserInfoPo.getInstId());
        loginVo.setIssuInstInfo(issuInstInfo);

        //获取用户收单机构信息
        SysInstPo acqInstInfo=acquirerService.getAcqUinst(authUserInfoPo.getInstId());
        loginVo.setAcqInstInfo(acqInstInfo);

        //获取transId
        loginVo.setTransId(SnowFlake.nextId()+"");

        return loginVo;
    }
}
