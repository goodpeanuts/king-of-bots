package com.kingofboss.botrunningsystem.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

public interface GetRankListService {
    JSONObject getList(Integer page);
}
