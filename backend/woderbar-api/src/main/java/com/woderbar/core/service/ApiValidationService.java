package com.woderbar.core.service;

import com.nimbusds.jwt.JWTClaimsSet;
import com.woderbar.core.config.CognitoConfiguration;
import com.woderbar.core.util.CognitoUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@AllArgsConstructor
@Service
public class ApiValidationService {

    private static final String TOKEN_USE_ACCESS = "access";

    private final CognitoUtil cognitoUtil;
    private final CognitoConfiguration cognitoConfiguration;

    public JWTClaimsSet getValidJWTClaimsSet(String token) {
        final JWTClaimsSet jwtClaimsSet;
        try {
            jwtClaimsSet = cognitoUtil.processAwsJwtToken(token);
        } catch (Exception e) {
            throw new WoderbarException(e, ErrorType.UNAUTHORIZED_ERROR, "Exception during JWT token claim set read");
        }
        validateClientId(jwtClaimsSet);
        validateTokenUse(jwtClaimsSet);
        validateTokenExpiration(jwtClaimsSet);
        validateUsername(jwtClaimsSet);
        return jwtClaimsSet;
    }

    private void validateClientId(JWTClaimsSet jwtClaimsSet) {
        final Object tokenClientId = cognitoUtil.getClientId(jwtClaimsSet);
        final String acceptedClientId = cognitoConfiguration.getClientId();
        if (!acceptedClientId.equals(tokenClientId)) {
            throw new WoderbarException(ErrorType.UNAUTHORIZED_ERROR, "Wrong AWS Cognito client id: {}; accepted: {}", tokenClientId, acceptedClientId);
        }
    }

    private void validateTokenUse(JWTClaimsSet jwtClaimsSet) {
        final Object tokenTokenUse = cognitoUtil.getTokenUse(jwtClaimsSet);
        if (!TOKEN_USE_ACCESS.equals(tokenTokenUse)) {
            throw new WoderbarException(ErrorType.UNAUTHORIZED_ERROR, "Not access token sent: {}", tokenTokenUse);
        }
    }

    private void validateTokenExpiration(JWTClaimsSet jwtClaimsSet) {
        final Date tokenExp = cognitoUtil.getExp(jwtClaimsSet);
        if (tokenExp.before(Date.from(Instant.now()))) {
            throw new WoderbarException(ErrorType.UNAUTHORIZED_ERROR, "Access token expired at {}", tokenExp);
        }
    }

    private void validateUsername(JWTClaimsSet jwtClaimsSet) {
        if (StringUtils.isBlank(cognitoUtil.getUsername(jwtClaimsSet))) {
            throw new WoderbarException(ErrorType.UNAUTHORIZED_ERROR, "Username is missing from token");
        }
    }

}