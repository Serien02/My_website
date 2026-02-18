package com.example.demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("message")
public class Message {
    private Long id;

    private String username;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
