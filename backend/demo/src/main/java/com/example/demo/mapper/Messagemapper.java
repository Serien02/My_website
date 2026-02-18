package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface Messagemapper extends BaseMapper<Message> {
    List<Message> selectAllOrderByCreateTimeDesc();
    
}
