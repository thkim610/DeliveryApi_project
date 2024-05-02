package org.delivery.db.user;

import org.delivery.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> { // <엔터티 타입, 기본키>

    //회원 조회
    //select * from user where id = ? and status = ? order by id desc limit 1
    Optional<UserEntity> findFirstByIdAAndStatusOrderByIdDesc(Long id, UserStatus status);

    //로그인 (이메일, 패스워드 조회)
    //select * from user where email = ? and password = ? and status = ? order by id desc limit 1
    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus status);
}
