package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component("BookmarkAuthenticationProvider")
public class BookmarkAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        BookmarkAuthenticationToken auth = new BookmarkAuthenticationToken(
                username,password, Arrays.asList(new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("Vendor"),
                new SimpleGrantedAuthority("WholeSaler"))
        );
        auth.setUserInfo(new UserInfo(username,password));
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
