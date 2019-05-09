package com.buxz.imooc.houses.service;

import com.buxz.imooc.houses.common.model.User;
import com.buxz.imooc.houses.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers(){
        return userMapper.selectUsers();
    }
}
