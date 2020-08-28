package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private String server;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public HttpClient(String server, String contentType) {
        this.server = server;
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", contentType);
        headers.add("Accept", "*/*");
    }

    public HttpClient(String server) {
        this.server = server;
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        headers.add("Accept", "*/*");
    }

    public HttpClient(String server, MediaType mediaType) {
        this.server = server;
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.add("Accept", "*/*");
    }

    public String get(String uri) {
        logger.info("for PDF URL:" + server + uri);
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public String post(String uri, String req) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(req, headers);

        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setContentType(String contentType) {
        headers.remove("Content-Type");
        headers.add("Content-Type", contentType);
    }

    public void setAuthorization(String authorization) {
        headers.remove("Authorization");
        headers.add("Authorization", authorization);
    }
}
