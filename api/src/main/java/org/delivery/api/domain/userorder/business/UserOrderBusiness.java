package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;
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
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreConverter storeConverter;


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

    //현재 진행 중인 주문 조회
    public List<UserOrderDetailResponse> current(User user) {
        //현재 사용자가 주문한 리스트
        List<UserOrderEntity> UserOrderEntityList = userOrderService.current(user.getId());

        //현재 주문 리스트에서 상세 주문 정보 리스트 생성
        List<UserOrderDetailResponse> userOrderDetailResponseList = UserOrderEntityList.stream()
                //주문 1건씩 처리
                .map(it -> {
                    //사용자 주문 id를 통해 사용자 주문 메뉴리스트 가져오기
                    List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenu(it.getId());
                    //사용자 주문 메뉴리스트에서 가게 메뉴 상세 리스트 가져오기
                    List<StoreMenuEntity> storeMenuList = userOrderMenuList.stream()
                            .map(userOrderMenuEntity -> {

                                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());

                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    //사용자가 주문한 가게 정보 가져오기
                    //TODO 리팩토링 필요 : get()에 대한 nullpoint 처리
                    StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuList.stream().findFirst().get().getStoreId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    //과거에 주문한 내역 조회
    public List<UserOrderDetailResponse> history(User user) {
        //과거 사용자가 주문한 리스트
        List<UserOrderEntity> UserOrderEntityList = userOrderService.history(user.getId());

        //과거 주문 리스트에서 상세 주문 정보 리스트 생성
        //TODO 리팩토링 필요 중복 코드 발생.
        List<UserOrderDetailResponse> userOrderDetailResponseList = UserOrderEntityList.stream()
                //주문 1건씩 처리
                .map(it -> {
                    //사용자 주문 id를 통해 사용자 주문 메뉴리스트 가져오기
                    List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenu(it.getId());
                    //사용자 주문 메뉴리스트에서 가게 메뉴 상세 리스트 가져오기
                    List<StoreMenuEntity> storeMenuList = userOrderMenuList.stream()
                            .map(userOrderMenuEntity -> {

                                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());

                                return storeMenuEntity;
                            })
                            .collect(Collectors.toList());

                    //사용자가 주문한 가게 정보 가져오기
                    //TODO 리팩토링 필요 : get()에 대한 nullpoint 처리
                    StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuList.stream().findFirst().get().getStoreId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    //주문 1건에 대한 조회
    public UserOrderDetailResponse read(User user, Long orderId) {
    }
}
