package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    //사용자 주문 생성
    @PostMapping("")
    public Api<UserOrderResponse> userOrder(
            @Valid @RequestBody Api<UserOrderRequest> request, @Parameter(hidden = true) @UserSession User user){

        UserOrderResponse response = userOrderBusiness.userOrder(user, request.getBody());

        return Api.OK(response);

    }

    //현재 진행 중인 주문 조회
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> currentOrder(@Parameter(hidden = true) @UserSession User user ){

        List<UserOrderDetailResponse> response = userOrderBusiness.current(user);

        return Api.OK(response);
    }

    //과거에 주문한 내역 조회
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> historyOrder(@Parameter(hidden = true) @UserSession User user ){

        List<UserOrderDetailResponse> response = userOrderBusiness.history(user);

        return Api.OK(response);
    }

    //주문 1건에 대한 조회
    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> read(@Parameter(hidden = true) @UserSession User user, @PathVariable Long orderId ){

        UserOrderDetailResponse response = userOrderBusiness.read(user, orderId);

        return Api.OK(response);
    }
}
