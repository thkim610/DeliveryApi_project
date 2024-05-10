package org.delivery.api.domain.store.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.store.enums.StoreCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreRegisterRequest {
    @NotBlank //"", " ", null 허용x
    private String name;
    @NotBlank
    private String address;
    @NotNull //enum 타입이기 때문에 문자열로 확인x
    private StoreCategory storeCategory;
    @NotNull
    private String thumbnailUrl;
    @NotNull
    private BigDecimal minimumAmount;
    @NotBlank
    private BigDecimal minimumDeliveryAmount;
    @NotBlank
    private String phoneNumber;
}
