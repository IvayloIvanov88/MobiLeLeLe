package com.example.demo.web.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDenied {

    @GetMapping("/accessDenied")
    public String accessDenied() {
    return "error/accessDenied";
    }
}
