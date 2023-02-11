package com.yart.literule.core.parser;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.internal.util.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Json格式解析器
 *
 * @author zhangquanquan@kanzhun.com
 * @since 1.0.0
 */
public abstract class JsonRuleParser implements RuleParser {

    private final Logger LOG = LoggerFactory.getLogger(JsonRuleParser.class);

    public void parse(String content) throws Exception {
        parse(ListUtil.toList(content));
    }

    @Override
    public void parse(List<String> contentList) throws Exception {
        if (CollectionUtil.isEmpty(contentList)) {
            return;
        }

        List<JSONObject> jsonObjectList = ListUtil.toList();
        for (String content : contentList) {
            //把字符串原生转换为json对象，如果不加第二个参数OrderedField，会无序
            JSONObject flowJsonObject = JSONObject.parseObject(content, Feature.OrderedField);
            jsonObjectList.add(flowJsonObject);
        }

        parseJsonObject(jsonObjectList);
    }

    //json格式，解析过程
    public void parseJsonObject(List<JSONObject> flowJsonObjectList) throws Exception {

    }

    /**
     * 解析一个chain的过程
     */
    private void parseOneChain(JSONObject chainObject){

    }
}
