package org.delivery.api.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Store 도메인 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    //유효한 스토어 가져오기
    public StoreEntity getStoreWithThrow(Long storeId){
        //디버깅을 편리하게 하기 위해 분리
        Optional<StoreEntity> entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeId, StoreStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    //스토어 등록
    public StoreEntity register(StoreEntity storeEntity){
        return Optional.ofNullable(storeEntity)
                .map(it ->{
                    //엔터티에 추가적인 값 세팅
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    //TODO 등록일시 추가하기

                    return storeRepository.save(it);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    //카테고리로 스토어 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory){
        List<StoreEntity> storeList = storeRepository.findByCategoryAndStatusOrderByStarDesc(storeCategory, StoreStatus.REGISTERED);

        return storeList;
    }

    //전체 스토어
    public List<StoreEntity> registerStore(){
        List<StoreEntity> storeListAll = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);

        return storeListAll;
    }

}
