package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    //카테고리 별 매장리스트 조회
    @GetMapping("/search")
    public Api<List<StoreResponse>> search(@RequestParam(required = false) StoreCategory storeCategory){
        List<StoreResponse> storeList = storeBusiness.searchCategory(storeCategory);

        return Api.OK(storeList);
    }

    //매장 등록
}
