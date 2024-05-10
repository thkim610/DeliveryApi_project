package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    //메뉴 등록
    @PostMapping("/register")
    public Api<StoreMenuResponse> register(@Valid @RequestBody Api<StoreMenuRegisterRequest> request){
        StoreMenuResponse menuResponse = storeMenuBusiness.register(request.getBody());

        return Api.OK(menuResponse);
    }
}
