package org.delivery.db.storemenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storemenu.enums.StoreMenuStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "store_menu")
public class StoreMenuEntity extends BaseEntity {
    @Column(nullable = false)
    private Long storeId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreMenuStatus status;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    private int likeCount; //int인 이유 : 기본값이 0이기 때문 -> 만약 기본값이 null이면 Integer 사용

    private int sequence;
}
