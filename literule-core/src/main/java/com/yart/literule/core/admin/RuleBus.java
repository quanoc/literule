package com.yart.literule.core.admin;

import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.rule.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 规则总线.
 * 管理执行集(Rules)的注册/注销
 */
public class RuleBus {

    private static final Logger LOG = LoggerFactory.getLogger(RuleBus.class);

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
