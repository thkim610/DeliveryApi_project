package org.delivery.db.userordermenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_order_menu")
public class UserOrderMenuEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userOrderId; // 1:n 관계

    @Column(nullable = false)
    private Long storeMenuId; // 1:n 관계

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserOrderMenuStatus status;
}
