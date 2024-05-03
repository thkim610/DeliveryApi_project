package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.user.business.UserBusiness;
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
}
