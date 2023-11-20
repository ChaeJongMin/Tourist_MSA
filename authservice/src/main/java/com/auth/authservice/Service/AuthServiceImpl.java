package com.auth.authservice.Service;

import com.auth.authservice.Domain.RefreshTokenEntity;
import com.auth.authservice.Dto.ResponseTokenDto;
import com.auth.authservice.Repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
//AuthService 인터페이스 구현체
public class AuthServiceImpl implements AuthService {
    private final Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${token.access_expiration_time}")
    private String access_exp_time;
    @Value("${token.refresh_expiration_time}")
    private String refresh_exp_time;

    //생성자로 필요한 객체를 의존성 주입으로 받음
    @Autowired
    public AuthServiceImpl(@Qualifier("myTokenKey") Key key, RefreshTokenRepository refreshTokenRepository) {
        this.key = key;
        this.refreshTokenRepository = refreshTokenRepository;
    }
    // 액세스 토큰 생성 메소드
    @Override
    public String generateAccess(String userId, HttpServletResponse response) {
        // Jwts 객체(액세스 토큰 값)를 생성하며 서명은 HS512 알고리즘을 사용
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(access_exp_time)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 리프레쉬 토큰 생성 메소드
    @Override
    public String generateRefresh() {
        // Jwts 객체(리프레쉬 토큰 값)를 생성하며 서명은 HS512 알고리즘을 사용
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(refresh_exp_time)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 리프레쉬 토큰을 DB에 저장 또는 수정하는 ㅔㅁ소드
    @Override
    public String saveRefreshToken(String userId, HttpServletResponse response) {
        String tokenValue = generateRefresh();
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenValue).getBody();
        long expTime=claims.getExpiration().getTime();
        log.info("expTime : "+expTime+" "+tokenValue);
        //현재 해당 사용자 토큰이 존재하는지 확인
        Optional<RefreshTokenEntity> refreshToken=refreshTokenRepository.findByUserId(userId);
        // 존재 시 만료일, 값만 변경
        if(refreshToken.isPresent()){
            //만료기간, 값 변경
            RefreshTokenEntity ExistrefreshTokenEntity = refreshToken.get();
            log.info(ExistrefreshTokenEntity.getId()+" "+ExistrefreshTokenEntity.getUserId());
            //수정
            ExistrefreshTokenEntity.update(tokenValue, expTime);
        // 없을 시 새로 생성
        } else {
            //리프레쉬 토큰 생성
            RefreshTokenEntity newRefreshTokenEntity = RefreshTokenEntity.builder()
                    .userId(userId)
                    .value(tokenValue)
                    .expTime(expTime)
                    .build();
            //저장
            refreshTokenRepository.save(newRefreshTokenEntity);
        }


        return tokenValue;
    }

    // 액세스 , 리프레쉬 토큰을 생성하는 메소드
    @Override
    public ResponseTokenDto generateAllTokens(String userId, HttpServletResponse response) {
        String access = generateAccess(userId,response);
        String refresh = saveRefreshToken(userId,response);
        return new ResponseTokenDto(access,refresh);
    }

    //토큰 값으로부터 유저 네임을 찾는 메소드
    @Override
    public String findUserIdByToken(String value) {
        return refreshTokenRepository.findByValue(value)
                .orElseThrow(()-> new JwtException("리프레쉬 토큰 에러!!")).getUserId();
    }


}