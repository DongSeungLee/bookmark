package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Value("${bookmark.disableAuthenticate}")
    private Boolean disableAuthenticate;
    @Autowired
    @Qualifier("BookmarkAuthenticationProvider")
    private BookmarkAuthenticationProvider bookmarkAuthenticationProvider;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl();

        //http.addFilterBefore(new SessionFilter(env), UsernamePasswordAuthenticationFilter.class);

        if (disableAuthenticate.booleanValue()) {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        } else {
            http

                    .addFilterBefore(new BookmarkFilter("/login", authenticationManager(),tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                    .csrf().disable()
                    .authorizeRequests()
                    .anyRequest().permitAll();

        }
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.bookmarkAuthenticationProvider);
    }
}
