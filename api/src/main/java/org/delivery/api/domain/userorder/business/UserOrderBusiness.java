package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;


    /**
     * 1. 사용자, 메뉴 id를 받음
     * 2. userOrder 생성
     * 3. userOrderMenu 생성
     * 4. 응답 생성
     */
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        //메뉴가 유효한지 체크
        List<StoreMenuEntity> storeMenuEntityList = body.getStoreMenuIdList().stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());

        //사용자 주문 생성
        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        //DB 저장
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        //매핑
        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    //menu + userOrder
                    UserOrderMenuEntity userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);

                    return userOrderMenuEntity;
                })
                .collect(Collectors.toList());

        //주문 내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        //응답 생성
        return userOrderConverter.toResponse(newUserOrderEntity);
    }
}
