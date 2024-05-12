package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    //특정 사용자의 모든 주문 리스트 조회
    //select * from user_order where user_id = ? and status = ? order by id desc
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);


    //특정 사용자의 특정 주문 조회
    //select * from user_order where id = ? and status = ? and user_id = ?
    Optional<UserOrderEntity> findFirstByIdAndStatusAndUserId(Long userOrderId, UserOrderStatus status, Long userId);
}