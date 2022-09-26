package com.kk.service.sms.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.kk.common.result.ResultEnum;
import com.kk.service.base.exception.GiotException;
import com.kk.service.sms.service.SmsService;
import com.kk.service.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsProperties smsProperties;

    @Override
    public void send(String mobile, String checkCode) throws ClientException {
        String regionId = smsProperties.getRegionId();
        String accessKeyId = smsProperties.getAccessKeyId();
        String accessKeySecret = smsProperties.getAccessKeySecret();
        String signName = smsProperties.getSignName();
        String templateCode = smsProperties.getTemplateCode();

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);

        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setPhoneNumbers(mobile);
        request.setTemplateParam("{\"code\":\"" + checkCode + "\"}");

        SendSmsResponse response = client.getAcsResponse(request);
        System.out.println(new Gson().toJson(response));
        String code = response.getCode();
        String message = response.getMessage();
        //若 code 不是 `ok` 则表示短信发送失败
        if ("isv.BUSINESS_LIMIT_CONTROL".equals(code)) {
            log.error("短信发送过于频繁 " + "【code】" + code + ", 【message】" + message);
            throw new GiotException(ResultEnum.SMS_SEND_ERROR_BUSINESS_LIMIT_CONTROL);
        }
        if (!"OK".equals(code)) {
            log.error("短信发送失败 " + " - code: " + code + ", message: " + message);
            throw new GiotException(ResultEnum.SMS_SEND_ERROR);
        }
    }
}