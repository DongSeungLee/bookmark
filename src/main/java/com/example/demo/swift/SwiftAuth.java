package com.example.demo.swift;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * token을 공유하는 것이니 Singleton으로 구성한다.
 */
@Component
public class SwiftAuth {
    private final SwiftProperties swiftConfig;
    private ZonedDateTime expireDateTime;
    private String accessToken;
    private RestTemplate restTemplate = new RestTemplate();
    public SwiftAuth(SwiftProperties swiftConfig) {
        this.swiftConfig = swiftConfig;
    }

    private void setAuth() throws RuntimeException {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=utf-8");

            Map<String, String> passwordCredentials = new HashMap<>();
            passwordCredentials.put("username", swiftConfig.getUsername());
            passwordCredentials.put("password", swiftConfig.getPassword());

            Map<String, Object> auth = new HashMap<>();
            auth.put("tenantId", swiftConfig.getTenantId());
            auth.put("passwordCredentials", passwordCredentials);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("auth", auth);

            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(swiftConfig.getAuthApiUrl(), new HttpEntity(requestBody, headers), String.class);


            JSONObject jsonResult = new JSONObject(stringResponseEntity.getBody());
            JSONObject accessTokenInfo = jsonResult.getJSONObject("access").getJSONObject("token");

            String expireStr = accessTokenInfo.getString("expires");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            expireDateTime = ZonedDateTime.parse(expireStr, formatter);
            accessToken = accessTokenInfo.getString("id");
            System.out.println(accessToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAccessToken() {
        //expirytime보다 30분 전에 token을 재설정!
        if (StringUtils.isEmpty(accessToken) || expireDateTime == null || ZonedDateTime.now(ZoneId.of("GMT")).plusMinutes(30).isAfter(expireDateTime)) {
            setAuth();
        }

        return accessToken;
    }
}
