package org.delivery.api.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 로그인 시 요청 정보
 * - 이메일
 * - 비밀번호
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
