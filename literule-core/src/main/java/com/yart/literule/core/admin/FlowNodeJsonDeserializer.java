package com.yart.literule.core.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yart.literule.core.model.flow.FlowNode;
import com.yart.literule.core.model.flow.FlowNodeType;

import java.util.ArrayList;
import java.util.List;

public class FlowNodeJsonDeserializer {
    public List<FlowNode> deserialize(String json){
        List<FlowNode> result = new ArrayList<>();
        JSONArray jsonArr = JSON.parseArray(json);
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject obj = jsonArr.getJSONObject(i);
        }

        return result;
    }

    private FlowNode parse(JSONObject obj){
        FlowNodeType type = FlowNodeType.valueOf(obj.getString("type"));
        switch (type) {
            case Action:{

            }
        }
        return null;
    }
}
