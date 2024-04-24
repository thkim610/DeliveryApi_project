package org.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 오토와이어링을 할 때, 자신의 패키지 하위 빈들만을 등록하여 사용할 수 있음.
 * 따라서, 다른 위치의 패키지 하위의 빈을 등록하여 사용하려면 추가로 설정을 해줘야 함.
 */
@Configuration
@EntityScan(basePackages = "org.delivery.db") //찾고자하는 빈의 패키지 경로
@EnableJpaRepositories(basePackages = "org.delivery.db") //해당 패키지의 JPA 레포지토리를 사용한다는 의미.
public class JpaConfig {
}
