package com.kk.service.sms.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties {
    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
}
