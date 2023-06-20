package com.example.marathon.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@TableName("voucher")
@Data
public class Voucher {
    @TableId(value = "id")
    private Long id;

    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(value = "title")
    private String title;

    @TableField(value = "subtitle")
    private String subtitle;

    @TableField(value = "rules")
    private String rules;

    @TableField(value = "pay_value")
    private Long payValue;

    @TableField(value = "actual_value")
    private Long actualValue;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp updateTime;

    @TableField(exist = false)
    private Integer stock;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp beginTime;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp endTime;
}
