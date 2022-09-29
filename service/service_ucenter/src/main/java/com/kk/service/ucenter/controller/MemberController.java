package com.kk.service.ucenter.controller;

import com.kk.common.result.ResultData;
import com.kk.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-25
 */
@Api(description = "会员管理")
@RestController
@RequestMapping("/admin/ucenter/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "根据日期统计注册人数")
    @GetMapping(value = "/count-register-num/{day}")
    public Long countRegisterNum(@ApiParam(name = "day", value = "统计日期") @PathVariable(value = "day") String day) {
        return memberService.countRegisterNum(day);
    }
}