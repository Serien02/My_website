package com.example.demo.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloControl{
    @GetMapping("/path")
     public String hello() {
        return "Hello, 这是我的第一个Java后端接口";
    }
}