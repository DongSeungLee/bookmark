package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.book.model.Widget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
@Component("tokenAuthenticationService")
@Slf4j
public class TokenAuthenticationService {
    private Integer sessionTimeout = 87400;
    private String secret = "FashiongoVendorAdmin6301";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";
    @Value("${server.servlet.cookie.bookmarkCookieName}")
    private String cookieName;
    private static final String SECRET = "Bookmark";
    static final long EXPIRATIONTIME = 86400000; // default, 24 hours
   public void addAuthentication(HttpServletResponse response, Authentication authentication) throws UnsupportedEncodingException {
       log.debug("session timeout: {}", sessionTimeout);
       HashMap<String,Object> claims = new HashMap<String, Object>();
       UserInfo userInfo = ((BookmarkAuthenticationToken)authentication).getUserInfo();
       String username = userInfo.getUsername();
       String password = userInfo.getPassword();
       claims.put("username",username);
       claims.put("password",password);
       claims.put("attr","attr");
       claims.put("widget", Widget.builder().id(100).name("hoho").build());
       claims.put("authorities",authentication.getAuthorities());
       Algorithm algorithm = Algorithm.HMAC512(SECRET);
       String token = JWT.create().withClaim("username",username)
               .withClaim("password",password)
               .withClaim("attr","attr")
               .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
               .sign(algorithm);

       response.addHeader(headerString, tokenPrefix + " " + token);
       Cookie cookie = new Cookie(cookieName,token);
       cookie.setMaxAge(sessionTimeout);
       cookie.setValue(token);
       response.addCookie(cookie);
       try {
           OutputStream ostr = response.getOutputStream();
           String s = "{\"success\":true, \"data\": \"" + token + "\"}";
           ostr.write(s.getBytes());
           //ostr.flush();
       } catch (IOException e) {
            log.error("IO exception occurred ! :{}",e.getMessage());
       }

   }
    public String addAuthentication1() throws UnsupportedEncodingException {
        log.debug("session timeout: {}", sessionTimeout);
        HashMap<String,Object> claims = new HashMap<String, Object>();

        String username = "DS";
        String password = "HOHO";
        claims.put("username",username);
        claims.put("password",password);
        claims.put("attr","attr");
        Algorithm algorithm = Algorithm.HMAC512(SECRET);
        String token = JWT.create().withClaim("username",username)
                .withClaim("password",password)
                .withClaim("attr","attr")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .sign(algorithm);
        return token;
    }
}
