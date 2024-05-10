package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreMenuService {
    private final StoreMenuRepository storeMenuRepository;

    //유효한 메뉴 가져오기
    public StoreMenuEntity getStoreMenuWithThrow(Long menuId){
        Optional<StoreMenuEntity> entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(menuId, StoreMenuStatus.REGISTERED);

        return entity.orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "StoreMenuEntity Null"));
    }

    //store_id를 통해 메뉴 가져오기
    //TODO 연관관계 수정 후 변경하기
    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED);
    }

    //메뉴 등록
    public StoreMenuEntity register(StoreMenuEntity storeMenuEntity){
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> {
                    //필요한 값 세팅
                    it.setStatus(StoreMenuStatus.REGISTERED);

                    return storeMenuRepository.save(it);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "StoreMenuEntity Null"));
    }


}
