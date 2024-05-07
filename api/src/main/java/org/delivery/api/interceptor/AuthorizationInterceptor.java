package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

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

        /* header 검증 */
        //헤더에서 토큰 값 가져오기
        String accessToken = request.getHeader("authorization-token");
        if(accessToken == null){
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        //액세스 토큰을 통해 userId 반환.
        Long userId = tokenBusiness.validationAccessToken(accessToken);

        if(userId != null){
            //request컨텍스트(범위 : 현재 request)에 userId 저장.
            RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            //setAttribute(name, object, scope) => RequestAttributes.SCOPE_REQUEST : 요청(request) 동안에만 유지
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);

            return true; //인증 성공.
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패"); //인증 실패.

    }
}
