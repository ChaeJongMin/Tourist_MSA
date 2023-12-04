package com.userinfo.userservice.Controller.Page;

import io.micrometer.core.annotation.Timed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {
    //로그인 & 회원가입 페이지
    @GetMapping("/login")
    @Timed(value = "user-login-page" , longTask = true)
    public String showLogin(){
        return "User/loginAndRegister";
    }
//    @GetMapping("/myInformation")
//    public String showMyInformation(){
//        return "user/MyInformation";
//    }
    @GetMapping("/myPage")
    @Timed(value = "user-my-page" , longTask = true)
    public String showMypage(){
        return "User/MyPage";
    }
}
