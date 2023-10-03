package com.userinfo.userservice.Service;

import com.userinfo.userservice.Domain.UserEntity;
import com.userinfo.userservice.Dto.Request.SaveDto;
import com.userinfo.userservice.Dto.Request.UpdateDto;
import com.userinfo.userservice.Dto.Response.UserDto;
import com.userinfo.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Long save(SaveDto saveDto) {
        if(!userRepository.existsByEmail(saveDto.getEmail())){
            saveDto.setPasswd(passwordEncoder.encode(saveDto.getPasswd()));
            return userRepository.save(saveDto.toUserEntity()).getId();
        }
        log.info("save 메소드 예외 발생");
        throw new DuplicateKeyException("이미 이메일이 존재합니다.");

    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity =userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        return new UserDto(userEntity);
    }

    @Override
    public Long update(Long id, UpdateDto updateDto) {
        UserEntity userEntity =userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        userEntity.update(updateDto.getName(), updateDto.getPasswd());
        return userEntity.getId();
    }

    @Override
    public String getUserNameByEmail(String email) {
        UserEntity userEntity =userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        return userEntity.getName();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity =userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("해당 유저는 없습니다."));
        return new User(userEntity.getEmail(), userEntity.getPasswd(), true, true, true, true,
                new ArrayList<>());
    }
}
