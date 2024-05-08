package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request){

        //Optional 체크
        return Optional.ofNullable(request)
                .map(it -> {
                    // to entity
                    return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .address(request.getAddress())
                            .password(request.getPassword())
                            .build();
                })//request가 null 이면 예외 던짐.
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    //to response
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .status(userEntity.getStatus())
                            .email(userEntity.getEmail())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }

    public UserResponse toResponse(User user) {
        return Optional.ofNullable(user)
                .map(it -> {
                    //to response
                    return UserResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .status(user.getStatus())
                            .email(user.getEmail())
                            .address(user.getAddress())
                            .registeredAt(user.getRegisteredAt())
                            .unregisteredAt(user.getUnregisteredAt())
                            .lastLoginAt(user.getLastLoginAt())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }
}
