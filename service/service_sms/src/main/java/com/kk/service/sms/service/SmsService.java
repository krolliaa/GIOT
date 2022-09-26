package com.kk.service.sms.service;

import com.aliyuncs.exceptions.ClientException;

public interface SmsService {
    public abstract void send(String mobile, String checkCode) throws ClientException;
}
