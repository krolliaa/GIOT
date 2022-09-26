package com.kk.service.ucenter.controller;

import com.kk.common.result.ResultData;
import com.kk.service.ucenter.pojo.vo.RegisterVo;
import com.kk.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
