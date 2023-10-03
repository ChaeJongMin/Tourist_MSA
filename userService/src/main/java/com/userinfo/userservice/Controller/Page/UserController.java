package com.userinfo.userservice.Controller.Page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    //로그인 & 회원가입 페이지
    @GetMapping("/login")
    public String showLogin(){
        return "User/loginAndRegister";
    }

//    @GetMapping("/myInformation")
//    public String showMyInformation(){
//        return "user/MyInformation";
//    }

}
