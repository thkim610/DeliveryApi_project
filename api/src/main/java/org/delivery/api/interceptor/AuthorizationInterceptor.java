package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        //WEB, chrome 의 경우, GET, POST와 같은 요청 메소드를 지원하는지 확인 할 수 있는 Option API가 존재. => 통과시킴.
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        //.js .html .png 등의 resource를 요청하는 경우 => 통과시킴.
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        //TODO header 검증

        return true; //일단 통과 처리


    }
}
