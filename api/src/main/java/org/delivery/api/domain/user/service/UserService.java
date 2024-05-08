package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    //회원가입
    public UserEntity register(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {

                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());

                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity null"));
    }

    //로그인
    public UserEntity login(String email, String password){
        UserEntity entity = getUserWithThrow(email, password);

        return entity;
    }

    //이메일, 비밀번호를 통한 회원 엔티티 조회
    public UserEntity getUserWithThrow(String email, String password){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email, password, UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    //userId를 통한 회원 엔티티 조회 (오버로딩)
    public UserEntity getUserWithThrow(Long userId) {
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, UserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
