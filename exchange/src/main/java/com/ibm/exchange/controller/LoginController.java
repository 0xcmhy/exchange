package com.ibm.exchange.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public String logIn(String username, String password){

        return ;
    }
    @PostMapping("/logout")
    public String logOut(String username, String password){
        return ;
    }
}
