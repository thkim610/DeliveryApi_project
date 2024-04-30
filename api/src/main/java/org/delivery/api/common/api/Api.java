package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * JSON 형태의 데이터를 생성하는 공통 Api Spec
 * {
 *     "result" : {
 *         ...
 *     },
 *     "body" : {
 *
 *     }
 * }
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    //상태 : OK , 성공
    public static <T> Api<T> OK(T data){
        Api<T> api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }
}
