package org.delivery.api.common.exception;

import lombok.Getter;
import org.delivery.api.common.error.ErrorCodeIfs;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs{

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs){
        super(errorCodeIfs.getDescription()); //부모에게 에러메시지 전달.
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    //에러 메시지 직접 넘기기
    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription){
        super(errorDescription); //부모에게 에러메시지 전달.
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

    //에러코드와 예외를 던짐
    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx){
        super(tx); //부모에게 에러메시지 전달.
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    //모든 것을 다 받음.
    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx, String errorDescription){
        super(tx); //부모에게 에러메시지 전달.
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }
}
