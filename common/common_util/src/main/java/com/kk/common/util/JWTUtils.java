package com.kk.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String generateJWT(JWTInfo jwtInfo, int expire) {
        //对 SECRET 进行进一步地加密
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", jwtInfo.getId());
        payload.put("nickname", jwtInfo.getNickname());
        payload.put("avatar", jwtInfo.getAvatar());
        String JWTToken = JWT.create().withSubject("giot-user").withIssuedAt(new Date()).withExpiresAt(DateTime.now().plusSeconds(expire).toDate()).withHeader(header).withPayload(payload).sign(algorithm);
        return JWTToken;
    }

    public static Boolean checkedJWTToken(String JWTToken) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            jwtVerifier.verify(JWTToken);
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }

    public static JWTInfo getMember(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest);
        String JWTToken = httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(JWTToken)) return null;
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(JWTToken);
            JWTInfo jwtInfo = new JWTInfo(verify.getClaim("id").asString(), verify.getClaim("nickname").asString(), verify.getClaim("avatar").asString());
            return jwtInfo;
        } catch (JWTVerificationException jwtVerificationException) {
            return null;
        }
    }
}
