package org.delivery.api.domain.storemenu.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreMenuEntity.builder()
                            .storeId(request.getStoreId())
                            .name(request.getName())
                            .amount(request.getAmount())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StoreRegisterRequest Null"));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity){
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> {
                    return StoreMenuResponse.builder()
                            .id(storeMenuEntity.getId())
                            .storeId(storeMenuEntity.getStoreId())
                            .name(storeMenuEntity.getName())
                            .amount(storeMenuEntity.getAmount())
                            .status(storeMenuEntity.getStatus())
                            .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                            .sequence(storeMenuEntity.getSequence())
                            .likeCount(storeMenuEntity.getLikeCount())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "storeMenuEntity Null"));
    }

    //오버로딩 (응답객체 리스트 반환)
    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> storeMenuEntityList){
        return storeMenuEntityList.stream()
                .map(it -> toResponse(it))
                .collect(Collectors.toList());
    }
}
