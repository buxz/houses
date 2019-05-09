package com.buxz.imooc.houses.mapper;


import com.buxz.imooc.houses.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> selectUsers();

}
