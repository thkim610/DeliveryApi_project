package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    //매장 메뉴 등록
    public StoreMenuResponse register(StoreMenuRegisterRequest request){
        //req -> entity (save) -> response
        StoreMenuEntity entity = storeMenuConverter.toEntity(request);
        StoreMenuEntity newMenuEntity = storeMenuService.register(entity);
        return storeMenuConverter.toResponse(newMenuEntity);
    }

    //유효한 메뉴 가져오기
    public List<StoreMenuResponse> search(Long storeId){
        // entity list -> to response list
        List<StoreMenuEntity> storeMenuList = storeMenuService.getStoreMenuByStoreId(storeId);

        return storeMenuList.stream()
                .map(it -> {
                    return storeMenuConverter.toResponse(it);
                })
                .collect(Collectors.toList());
    }
}
