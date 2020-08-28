package com.example.demo.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.ConnectException;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class ApiExceptionHandler {
    private ApiResponse createResponse(ResultCode resultCode) {
        return new ApiResponse(ApiResponseHeader.create(resultCode), null);
    }
    private ApiResponse createResponse(ResultCode resultCode, String message) {
        return new ApiResponse(ApiResponseHeader.create(false, resultCode.getCode(), message), null);
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    @ResponseBody
    public ApiResponse handleBadRequestException(HttpServletRequest request) {
        return createResponse(ResultCode.BAD_REQUEST);
    }

    /**
     * servlet에서 controller에서 error가 터지면 servlet container까지 전파가 되고
     * /error에 mapping이 된다.
     * @param request
     * @param response
     * @param e
     * @throws IOException
     */
    @ExceptionHandler({NotFoundException.class})
    public void handleCustomException(HttpServletRequest request, HttpServletResponse response, NotFoundException e) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler({ConnectException.class})
    @ResponseBody
    public ApiResponse handleConnectionException(HttpServletRequest request, ConnectException e) {
        return createResponse(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ApiResponse handleRuntimeException(HttpServletRequest request, ConnectException e) {
        return createResponse(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResponse handleException(HttpServletRequest request, ConnectException e) {
        return createResponse(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
