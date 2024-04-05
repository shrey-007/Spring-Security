package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/home")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    // only user can open this page
    public String home(){
        return "home";
    }

    @RequestMapping("/about")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // only admin can open this page
    public String about(){
        return "about";
    }
}
