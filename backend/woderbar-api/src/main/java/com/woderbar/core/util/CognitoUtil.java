package com.woderbar.core.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.woderbar.core.config.CognitoConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;


@AllArgsConstructor
@Component
@Lazy
public class CognitoUtil {

    private static final String CLAIM_CLIENT_ID = "client_id";
    private static final String CLAIM_TOKEN_USE = "token_use";
    private static final String CLAIM_EXP = "exp";
    private static final String CLAIM_USERNAME = "username";

    private final CognitoConfiguration cognitoConfiguration;

    public JWTClaimsSet processAwsJwtToken(final String token) throws BadJOSEException, ParseException, JOSEException, MalformedURLException {
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWKSource<SecurityContext> jwkSource = JWKSourceBuilder.create(new URL(cognitoConfiguration.getJsonWebKeyFileUrl())).build();
        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource);
        jwtProcessor.setJWSKeySelector(keySelector);
        return jwtProcessor.process(token, null);
    }


    public Object getClientId(JWTClaimsSet jwtClaimsSet) {
        return jwtClaimsSet.getClaims().get(CLAIM_CLIENT_ID);
    }

    public Object getTokenUse(JWTClaimsSet jwtClaimsSet) {
        return jwtClaimsSet.getClaims().get(CLAIM_TOKEN_USE);
    }

    public Date getExp(JWTClaimsSet jwtClaimsSet) {
        return (Date) jwtClaimsSet.getClaims().get(CLAIM_EXP);
    }

    public String getUsername(JWTClaimsSet jwtClaimsSet) {
        return jwtClaimsSet.getClaims().get(CLAIM_USERNAME).toString();
    }
}