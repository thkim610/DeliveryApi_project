package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.db.user.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 특정 메소드의 파라미터 값이 다음과 같으면 동작한다.
 * - @UserSession 어노테이션 존재.
 * - org.delivery.api.domain.user.model.User 객체 인자를 받음.
 */
@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //지원하는 파라미터 , 어노테이션을 체크하는 메서드

        //1. 어노테이션(UserSession)이 있는지 체크
        boolean annotation = parameter.hasParameterAnnotation(UserSession.class); //해당 어노테이션(UserSession)이 존재하는지
        boolean parameterType = parameter.getParameterType().equals(User.class); //들어오는 파라미터 값(User클래스)이 동일한지

        return (annotation && parameterType);
    }

    //supportsParameter()가 true로 반환 시, 해당 메서드가 실행된다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        //request context holder에서 userId 찾아오기
        //request 헤더에 담긴 userId 반환.
        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        //userId로 user 엔터티 반환.
        UserEntity userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

        //사용자 정보 세팅
        //이 user 객체 값이 특정 메소드의 argument로 들어감.
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .password(userEntity.getPassword())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt())
                .unregisteredAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();
    }
}
