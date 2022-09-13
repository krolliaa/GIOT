package com.kk.service.edu.controller;

import com.kk.common.result.ResultData;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class LoginController {
    @PostMapping(value = "/login")
    public ResultData login() {
        return ResultData.ok().data("token", "admin");
    }

    @PostMapping(value = "/logout")
    public ResultData logout() {
        return ResultData.ok();
    }

    @GetMapping(value = "/info")
    public ResultData info() {
        return ResultData.ok().data("roles","[admin]").data("name","admin").data("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
    }
}
