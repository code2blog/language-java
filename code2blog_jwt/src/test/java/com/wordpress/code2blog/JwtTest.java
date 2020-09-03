package com.wordpress.code2blog;

import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Log4j2
public class JwtTest {
    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hmacSha256(String data, String secret) throws Exception {
        //MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);//digest.digest(secret.getBytes(StandardCharsets.UTF_8));

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);

        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return encode(signedBytes);
    }

    private String base64(String input) throws Exception {
        return encode(input.getBytes("utf-8"));
    }

    @Test
    public void should_generate_jwt() throws Exception {
        String secret = "shhh..dont tell anyone";

        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

        JSONObject payload = new JSONObject();
        payload.put("sub", "sub123");
        payload.put("aud", "aud123");
        payload.put("exp", 3600);

        String signature = hmacSha256(base64(header) + "." + base64(payload.toJSONString()), secret);
        String jwtToken = base64(header) + "." + base64(payload.toJSONString()) + "." + signature;

        log.info("Here is my jwt " + jwtToken);
    }
}
