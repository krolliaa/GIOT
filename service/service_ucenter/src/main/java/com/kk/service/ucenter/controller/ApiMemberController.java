package com.kk.service.ucenter.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kk.common.result.ResultData;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.JWTInfo;
import com.kk.common.util.JWTUtils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.ucenter.pojo.vo.LoginVo;
import com.kk.service.ucenter.pojo.vo.RegisterVo;
import com.kk.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "会员管理")
@CrossOrigin
@RestController
@RequestMapping("/api/ucenter/member")
@Slf4j
public class ApiMemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping(value = "/register")
    public ResultData register(@ApiParam(value = "registerVo", required = true) @RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return ResultData.ok().message("注册成功");
    }

    @ApiOperation(value = "会员登录")
    @PostMapping(value = "/login")
    public ResultData login(@ApiParam(value = "loginVo", required = true) @RequestBody LoginVo loginVo) {
        String JWTToken = memberService.login(loginVo);
        System.out.println(JWTToken);
        if (!StringUtils.isBlank(JWTToken)) return ResultData.ok().message("登录成功").data("token", JWTToken);
        else return ResultData.error().message("登录失败");
    }

    @ApiOperation(value = "根据JWTToken获取登录信息")
    @GetMapping("get-login-info")
    public ResultData getLoginInfo(HttpServletRequest request) {
        try {
            JWTInfo jwtInfo = JWTUtils.getMember(request);
            if (jwtInfo != null) return ResultData.ok().data("userInfo", jwtInfo);
            else throw new GiotException(ResultEnum.LOGIN_AUTH);
        } catch (Exception e) {
            log.error("解析用户信息失败，" + e.getMessage());
            throw new GiotException(ResultEnum.FETCH_USERINFO_ERROR);
        }
    }
}
