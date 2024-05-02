package org.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 비지니스 로직을 담당하는 어노테이션
 * -비지니스 로직을 명시적으로 보여주기 위해 커스텀 어노테이션 설정.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) //실행 중 적용한다는 의미.
@Service
public @interface Business {

    @AliasFor(annotation = Service.class)
    String value() default "";


}
