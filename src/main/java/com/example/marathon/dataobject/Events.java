package com.example.marathon.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Events {
    @TableId(type = IdType.AUTO)
    @JsonIgnore
    private Integer id;
    private String eventId;
    private String name;
    private String location;
    private Date eventDate;
    private Date registrationDeadline;
    // 赛事类型
    // 0 短程
    // 1 半马
    // 2 全马
    private int type;
    // 已报名人数
    private int numbers;
    // 限制人数
    private int limits;
    private String details;
}
