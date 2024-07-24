package com.woderbar.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aws-cognito")
public class CognitoConfiguration {

    private String accessKeyId;
    private String secretAccessKey;
    private String clientId;
    private String jsonWebKeyFileUrl;
    private String userPoolId;

}