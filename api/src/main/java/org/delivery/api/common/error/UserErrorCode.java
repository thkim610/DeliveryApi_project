package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User의 경우 1000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs{

    USER_NOT_FOUND(400, 1404 , "사용자를 찾을 수 없음."),

    ;

    private final Integer httpStatusCode; // 내부 에러코드에 상응하는 HTTP 상태코드
    private final Integer errorCode; // 내부 에러코드
    private final String description; //세부 메시지
}
