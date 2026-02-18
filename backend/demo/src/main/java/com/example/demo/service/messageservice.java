package com.example.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Message;

public interface messageservice extends IService<Message> {
    boolean saveMessage(Message message);
    
    List<Message> getAllMessages();
    
    boolean clearAllMessages();
}
