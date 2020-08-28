package com.example.demo.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class BookmarkAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private UserInfo userInfo;

    public BookmarkAuthenticationToken(Object principal, Object credentials,
                                       Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
