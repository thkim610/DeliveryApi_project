package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public Api<UserResponse> me(@UserSession User user){ //UserSessionResolver 동작.

        UserResponse response = userBusiness.me(user);

        return Api.OK(response);
    }
}
