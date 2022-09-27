package com.kk.service.ucenter.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.open")
public class UCenterProperties {
    private String appId;
    private String appSecret;
    private String redirectUri;
}
