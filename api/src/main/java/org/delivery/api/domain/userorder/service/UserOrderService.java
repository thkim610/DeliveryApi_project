package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

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

    //주문 생성

    //주문 확인

    //조리 시작

    //배달 시작

    //배달 완료
}
