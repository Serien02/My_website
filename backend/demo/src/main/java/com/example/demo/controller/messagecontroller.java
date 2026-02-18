package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Message;
import com.example.demo.service.messageservice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*") 
public class messagecontroller {
    
    @Autowired
    private messageservice messageService;

    @PostMapping("/submit")
        public String submitMessage(@RequestBody Message message) {
        if (message.getUsername() == null || message.getUsername().trim().isEmpty()) {
            return "请输入昵称！";
        }
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            return "请输入留言内容！";
        }
        boolean result = messageService.saveMessage(message);
        return result ? "提交成功！" : "提交失败！";
    }

    @GetMapping("/list")
    public List<Message> getMessageList() {
        return messageService.getAllMessages();
    }

    @DeleteMapping("/clear")
    public String clearAllMessages() {
        boolean result = messageService.clearAllMessages();
        return result ? "清空成功！" : "清空失败！";
    }

}
