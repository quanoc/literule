package com.yart.literule.core.admin;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.model.data.DataValue;
import com.yart.literule.core.model.rule.ConditionConfig;
import com.yart.literule.core.rule.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.yart.literule.core.model.rule.ConditionConfig.*;

/**
 * 数据总线.
 * 负责变量数据的查询、缓存、变量的加工计算.
 */
public class DataBus {
    private static final Logger LOG = LoggerFactory.getLogger(DataBus.class);
    private static final DataBus instance = new DataBus();
    private DataBus(){}
    public static DataBus getInstance(){
        return instance;
    }

    /**
     * @return 返回非空.
     */
    public static DataValue getDataValue(ParameterConfig param, WorkingMemory memory){
        // TODO.
        return new DataValue();
    }


    // gid. rules.
    private static final Map<String, Rules> rulesMap = new ConcurrentHashMap<>();

    // 1. 获取规则集
    // 2. 规则载入

    // 获取一组规则.
    public static Rules getRuleSet(String gid){
        return rulesMap.get(gid);
    }

    public static Collection<Rules> getRuleSet(){
        return rulesMap.values();
    }


    public static void addRules(Rules rules){
        if (Objects.isNull(rules.getGid())) {
            throw new IllegalArgumentException("rules id must not be empty.");
        }
        rulesMap.put(rules.getGid(), rules);
    }

    public static void registerRule(String gid, Object... rules){
        if (Objects.isNull(gid)) {
            throw new IllegalArgumentException("rules id must not be empty.");
        }
        Rules ruleSet = rulesMap.get(gid);
        if (Objects.isNull(ruleSet)) {
            throw new IllegalArgumentException("ruleSet not found.");
         }
        ruleSet.register(rules);
    }

    public static boolean needInit(){
        return CollectionUtil.isEmpty(rulesMap);
    }

}
