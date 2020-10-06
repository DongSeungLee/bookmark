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
    /* 현재까지 파악한 바로는(20201006) 이렇게 custom errorController를 만들게 되면
     * excecption이 터졌을 때 이를 낚아채서 다시 여기로 요청을 보낸다.(GET)
     * 그래서 해당 요청을 받고 error page를 그려준다고 볼 수 있겠다.
     * ExceptionHandlerResolver를 활용해서 exception이 터질 때 api를 내리는게 아니라
     * 특정 page로 이동하게끔 구현할 수도 있을 거 같은데 전역으로 ControllerAdvice를 설정해서 이를 어떻게 해야
     * 할지 모르겠다. 만약 전역으로 하는 것은 RestController만 하고 Controller에 대해서는 ExceptionHandlerResolver를
     * 해서 Exception에 따른 error페이지를 보여줄 수도 있겠다.
     */
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
