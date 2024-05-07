package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

/**
 * 사용자 인증이 필요한 API
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    //요청 헤더의 토큰을 통한 사용자 인증
    @GetMapping("/me")
    public Api<UserResponse> me(){
        RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        UserResponse response = userBusiness.me(Long.parseLong(userId.toString()));

        return Api.OK(response);
    }
}
