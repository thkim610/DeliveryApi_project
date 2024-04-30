package org.delivery.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggerFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //요청이 들어오면 형 변환
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(req, res); // 직접 생성한 요청과 응답을 넘김.

        // 요청 정보 수집
        Enumeration<String> headerNames = req.getHeaderNames();
        StringBuilder headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            String headerValue = req.getHeader(headerKey);

            //예시 : authorization-token : ??? , user-agent : ???
            headerValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue).append("] ");

        });

        String requestBody = new String(req.getContentAsByteArray());

        String uri = req.getRequestURI(); //URI 정보
        String method = req.getMethod(); //요청 메소드 정보


        log.info(">>>>> uri : {}, method : {}, header : {}, body : {}", uri, method, headerValues, requestBody);

        //응답 정보 수집
        StringBuilder responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerKey -> {
            String headerValue = res.getHeader(headerKey);

            //예시 : authorization-token : ??? , user-agent : ???
            responseHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");

        });

        String responseBody = new String(res.getContentAsByteArray());

        log.info("<<<<< uri : {}, method : {}, header : {}, body : {}", uri, method, responseHeaderValues, responseBody);

        res.copyBodyToResponse();

    }
}
