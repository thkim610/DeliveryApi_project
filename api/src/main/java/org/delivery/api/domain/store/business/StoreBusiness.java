package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    //매장 등록
    public StoreResponse register(StoreRegisterRequest request){
        //req -> entity (save) -> response
        StoreEntity entity = storeConverter.toEntity(request);
        StoreEntity registerEntity = storeService.register(entity);//save

        return storeConverter.toResponse(registerEntity);
    }

    //카테고리 별 매장리스트 조회
    public List<StoreResponse> searchCategory(StoreCategory storeCategory){
        // entity list -> to response list
        List<StoreEntity> storeList = storeService.searchByCategory(storeCategory);

        return storeList.stream()
                .map(it->{
                    return storeConverter.toResponse(it);
                })
                .collect(Collectors.toList());
    }
}
