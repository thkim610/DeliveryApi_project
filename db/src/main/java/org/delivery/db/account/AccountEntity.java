package org.delivery.db.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder //부모로부터 상속받은 변수도 포함하겠다는 의미.
@Data
@EqualsAndHashCode(callSuper = true) //객체 비교시 사용.(callSuper = true : 부모에 있는 값까지 같이 포함해서 비교)
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {
}
