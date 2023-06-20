package com.example.marathon.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Register {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String participantId;
    private String eventId;
    // 0 未选中
    // 1 选中
    // 2 未抽签
    private int selected;
}
