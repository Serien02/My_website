package com.example.demo.service.impl;

import com.example.demo.service.messageservice;

import com.example.demo.mapper.Messagemapper;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.demo.entity.Message;

import java.util.List;

@Service
public class messageservice_impl extends ServiceImpl<Messagemapper, Message> implements messageservice {

    @Override
    public boolean saveMessage(Message message) {
        return save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return baseMapper.selectAllOrderByCreateTimeDesc();
    }

    @Override
    public boolean clearAllMessages() {
        return remove(null);
    }
    
}
