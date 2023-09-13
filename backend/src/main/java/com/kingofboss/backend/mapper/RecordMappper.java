package com.kingofboss.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingofboss.backend.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMappper extends BaseMapper<Record> {
}
