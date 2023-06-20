package com.example.marathon.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    @JsonIgnore
    private Integer id;
    private String userId;
    private String name;
    @JsonIgnore
    private String password;
    //  0 女
    //  1 男
    //  2 未知
    private int gender;
    private int age;
    private String phoneNumber;
    // 未中签次数
    private int fail_time;
}
