package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
@Slf4j
public class ProxyRequestFilter extends OncePerRequestFilter {
    private final String requestIdHeaderName;

    public final String MDC_REQUEST_ID = "http.x-request-id";

    public ProxyRequestFilter(String requestIdHeaderName) {
        this.requestIdHeaderName = requestIdHeaderName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(requestIdHeaderName);
            if (StringUtils.isEmpty(requestId)) {
                requestId = Base64.encodeBase64String(UUID.randomUUID().toString().getBytes()).concat(".G");
                log.info("RequestId is Empty. generate new RequestId({})", requestId);
            }
            log.info("Put requestId(MDC) : {}", requestId);
            MDC.put(MDC_REQUEST_ID, requestId);
            MDC.put("hoho","HOHO");
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_REQUEST_ID);
        }

    }
}
