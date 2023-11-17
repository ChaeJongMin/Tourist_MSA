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
public class AuthServiceImpl implements AuthService {
    private final Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${token.access_expiration_time}")
    private String access_exp_time;
    @Value("${token.refresh_expiration_time}")
    private String refresh_exp_time;

    @Autowired
    public AuthServiceImpl(@Qualifier("myTokenKey") Key key, RefreshTokenRepository refreshTokenRepository) {
        this.key = key;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    public String generateAccess(String userId, HttpServletResponse response) {

        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(access_exp_time)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateRefresh() {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(refresh_exp_time)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String saveRefreshToken(String userId, HttpServletResponse response) {
        String tokenValue = generateRefresh();
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenValue).getBody();
        long expTime=claims.getExpiration().getTime();
        log.info("expTime : "+expTime+" "+tokenValue);
        //현재 해당 사용자 토큰이 존재하는지 확인
        Optional<RefreshTokenEntity> refreshToken=refreshTokenRepository.findByUserId(userId);

        if(refreshToken.isPresent()){
            //만료기간, 값 변경
            RefreshTokenEntity ExistrefreshTokenEntity = refreshToken.get();
            log.info(ExistrefreshTokenEntity.getId()+" "+ExistrefreshTokenEntity.getUserId());
            ExistrefreshTokenEntity.update(tokenValue, expTime);

        } else {
            //새로 생성
            //리프레쉬 토큰 생성
            RefreshTokenEntity newRefreshTokenEntity = RefreshTokenEntity.builder()
                    .userId(userId)
                    .value(tokenValue)
                    .expTime(expTime)
                    .build();
            refreshTokenRepository.save(newRefreshTokenEntity);
        }


        return tokenValue;
    }

    @Override
    public ResponseTokenDto generateAllTokens(String userId, HttpServletResponse response) {
        String access = generateAccess(userId,response);
        String refresh = saveRefreshToken(userId,response);
        return new ResponseTokenDto(access,refresh);
    }

    @Override
    public String findUserIdByToken(String value) {
        return refreshTokenRepository.findByValue(value)
                .orElseThrow(()-> new JwtException("리프레쉬 토큰 에러!!")).getUserId();
    }


}