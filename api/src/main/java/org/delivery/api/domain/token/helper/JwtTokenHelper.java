package org.delivery.api.domain.token.helper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;
    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;
    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    //토큰 생성
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {

        //만료시간 설정
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                ZoneId.systemDefault()
                ).toInstant()
        );

        //서명 키 설정
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        //JWT 토큰 생성
        String jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();

    }

    //리프레시 토큰 생성
    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {

        //만료시간 설정
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        //서명 키 설정
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        //JWT 토큰 생성
        String jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    //토큰 유효성 검증 로직
    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        //키 생성
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        //키를 통해 파싱
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();


        /*파싱을 진행할 때 예외 발생할 수 있음.
            - SignatureException -> 토큰이 유효하지 않을 때
            - ExpiredJwtException -> 토큰이 만료되었을 때
            - 알 수 없는 그외의 에러
         */
        try{

            Jws<Claims> result = parser.parseClaimsJws(token);
            return new HashMap<String, Object>(result.getBody()); //Map의 형태로 결과가 반환.

        }catch (Exception e){

            if(e instanceof SignatureException){
                //토큰이 유효하지 않을 때
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);

            }else if(e instanceof ExpiredJwtException) {
                //토큰이 만료되었을 때
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);

            }else { //그외 에러
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }

        }

    }
}
