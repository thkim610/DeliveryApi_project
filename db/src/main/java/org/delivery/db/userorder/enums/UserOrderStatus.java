package org.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

/**
 * ENUM으로 사용하는 이유
 * -오탈자 방지
 * -DB로만 값을 읽더라도 알아보기 쉬움
 * -잘못된 데이터가 삽입되는 (삽입이상) 것을 방지
 */
@AllArgsConstructor
public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;


    private final String description;
}
