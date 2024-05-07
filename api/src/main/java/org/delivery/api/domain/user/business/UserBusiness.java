package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.db.user.UserEntity;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /**
     * 사용자에 대한 회원가입 로직
     * 1. request를 엔터티로 변환
     * 2. 엔터티 저장
     * 3. 저장한 엔터티를 UserResponse로 변환
     * 4. UserResponse 반환
     * @param request
     * @return UserResponse
     */
    public UserResponse register(UserRegisterRequest request) {

        UserEntity entity = userConverter.toEntity(request);
        UserEntity registeredEntity = userService.register(entity);
        UserResponse response = userConverter.toResponse(registeredEntity);

        return response;

        //람다형 방식
        /* return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request Null"));*/
    }

    /**
     * 로그인 로직
     * 1. email, password를 가지고 사용자 유효성 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token 응답 반환.
     * @param request
     */
    public TokenResponse login(UserLoginRequest request) {

        UserEntity userEntity = userService.login(request.getEmail(), request.getPassword());

        //토큰 생성
        TokenResponse tokenResponse = tokenBusiness.issueToken(userEntity);

        return tokenResponse;

    }

    public UserResponse me(Long userId) {
        UserEntity userEntity = userService.getUserWithThrow(userId);
        UserResponse response = userConverter.toResponse(userEntity);

        return response;
    }
}
