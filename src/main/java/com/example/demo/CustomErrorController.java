package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {
    public CustomErrorController(
            @Qualifier("HohoErrorAttributes")ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
   //     errorViewResolvers.add(()->{return new ModelAndView("4xx",new Map<String,Object>())});
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        System.out.println("errorHtml : ");
        return super.errorHtml(request, response);
    }
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }
}
