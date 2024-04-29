package org.delivery.api.config.objectMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링부트가 실행이 되어 올라올 때, ObjectMapper에 대한 따로 설정이 없으면,
 * 자신이 Default로 ObjectMapper를 생성한다.
 * 그러나, 아래와 같이 설정을 해두면, 커스텀한 ObjectMapper를 사용하게 된다.
 */
@Configuration
public class ObjectMapperConfig {

    //스네이크 표기법을 사용하기 위한 ObjectMapper 커스텀화
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module()); //Java 8버전 이후의 클래스를 파싱, 시리얼라이즈
        objectMapper.registerModule(new JavaTimeModule()); //LocalDate 시리즈

        //모르는 JSON field에 대해서 무시한다.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //비어있을 때 빈 생성 옵션 -> false(사용X)
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //날짜 관련 직렬화 설정
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //스네이크 표기법 설정
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;


    }
}
