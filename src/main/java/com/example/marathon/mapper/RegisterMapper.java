package com.example.marathon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.marathon.dataobject.Register;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RegisterMapper extends BaseMapper<Register> {
    @Select("select participant_id from register where event_id = #{eventId}")
    List<String> getParticipants(String eventId);

    @Select("update register set selected = #{select} where participant_id = #{participantId} and event_id = #{eventId}")
    void updateSelect(int select, String participantId, String eventId);
}
