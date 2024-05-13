package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    //특정 사용자의 모든 주문 리스트 조회
    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }


    //특정 사용자의 특정 주문 조회
    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId){

        Optional<UserOrderEntity> userOrderEntity = userOrderRepository.findFirstByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId);

        return userOrderEntity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserOrderEntity Null"));

    }

    //특정 사용자의 주문 상태에 따른 모든 주문 리스트 조회
    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    //주문 생성 (create)
    public UserOrderEntity order(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it ->{
                    //주문
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now()); //주문한 현재 시간

                    return it;
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserOrderEntity Null"));
    }

    //주문 확인
    public UserOrderEntity orderAccept(UserOrderEntity userOrderEntity){
        userOrderEntity.setAcceptedAt(LocalDateTime.now());

        return setStatus(userOrderEntity, UserOrderStatus.ACCEPT);
    }


    //조리 시작
    public UserOrderEntity cookingStart(UserOrderEntity userOrderEntity){
        userOrderEntity.setCookingStartedAt(LocalDateTime.now());

        return setStatus(userOrderEntity, UserOrderStatus.COOKING);
    }

    //배달 시작
    public UserOrderEntity deliveryStart(UserOrderEntity userOrderEntity){
        userOrderEntity.setDeliveryStartedAt(LocalDateTime.now());

        return setStatus(userOrderEntity, UserOrderStatus.DELIVERY);
    }

    //배달 완료
    public UserOrderEntity receive(UserOrderEntity userOrderEntity){
        userOrderEntity.setReceivedAt(LocalDateTime.now());

        return setStatus(userOrderEntity, UserOrderStatus.RECEIVE);
    }

    //상태 변경
    public UserOrderEntity setStatus(UserOrderEntity userOrderEntity, UserOrderStatus status){
        userOrderEntity.setStatus(status);
        return userOrderEntity;
    }

    //현재 진행중인 주문 내역
    public List<UserOrderEntity> current(Long userId){
        return getUserOrderList(
                userId,
                List.of(
                UserOrderStatus.ORDER,
                UserOrderStatus.ACCEPT,
                UserOrderStatus.COOKING,
                UserOrderStatus.DELIVERY
        )
        );
    }

    //과거 주문한 내역
    public List<UserOrderEntity> history(Long userId){
        return getUserOrderList(
                userId,
                List.of(
                UserOrderStatus.RECEIVE
        )
        );
    }
}
