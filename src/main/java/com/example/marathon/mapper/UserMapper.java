package com.example.marathon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.marathon.dataobject.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select name from user where user_id = #{userId}")
    String getUsername(String userId);
}
