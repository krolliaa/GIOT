package com.kk.service.sms.controller;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kk.common.result.ResultData;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.FormUtils;
import com.kk.common.util.RandomUtils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(description = "短信管理")
@CrossOrigin
@RestController
@RequestMapping(value = "/api/sms")
@Slf4j
public class ApiSmsController {
    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/send/{mobile}")
    public ResultData getCode(@PathVariable(value = "mobile") String mobile) throws ClientException {
        System.out.println("没进来？");
        //校验手机号码是否合法
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)) {
            log.error("请输入正确的手机号码 ");
            throw new GiotException(ResultEnum.LOGIN_PHONE_ERROR);
        }
        //生成验证码然后发送验证码，然后将验证码存入 Redis
        String checkCode = RandomUtils.getFourBitRandom();
        smsService.send(mobile, checkCode);
        redisTemplate.opsForValue().set(mobile, checkCode);
        return ResultData.ok().message("短信发送成功！");
    }
}
