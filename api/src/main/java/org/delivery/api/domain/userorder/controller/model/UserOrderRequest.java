package org.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 주문
 *   특정 사용자가, 특정 메뉴를 주문
 *   특정 사용자 = 로그인된 세션에 들어있는 사용자
 *   특정 메뉴 id 리스트 - 메뉴를 여러가지 담을 수 있기 때문에
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequest {

    @NotNull
    private List<Long> storeMenuIdList;


}
