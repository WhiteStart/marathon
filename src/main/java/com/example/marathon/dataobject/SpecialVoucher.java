package com.example.marathon.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@TableName("special_voucher")
@Data
public class SpecialVoucher {
    @TableId("voucher_id")
    private Long voucherId;

    @TableField("stock")
    private Integer stock;

    @TableField(value = "begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp beginTime;

    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp endTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;
}

/*
{
  "stock": 1,
  "beginTime": "2023-06-20 09:00:00",
  "endTime": "2023-06-21 18:00:00"
}
@DateTimeFormat 用于解析前端传递的日期时间字符串，将其转换为对应的 Java 类型；
@JsonFormat 则用于在将日期时间类型转换为 JSON 字符串时，按照指定的格式进行格式化。
 */