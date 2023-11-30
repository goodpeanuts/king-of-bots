package com.kingofboss.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingofboss.backend.pojo.Feedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

    Feedback selectByPrimaryKey(int feedbackId);
}
