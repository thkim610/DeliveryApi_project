package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.model.TokenDto;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. userId 추출.
     * 2. access, refresh Token 발행
     * 3. converter -> token response 변경
     * @param userEntity
     * @return TokenResponse
     */
    public TokenResponse issueToken(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    return userEntity.getId();
                })
                .map(userId -> {
                    TokenDto accessToken = tokenService.issueAccessToken(userId);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userId);

                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

    }
}
