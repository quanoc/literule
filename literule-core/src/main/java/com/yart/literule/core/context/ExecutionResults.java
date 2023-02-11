package com.yart.literule.core.context;

import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.entity.RulesResult;

import java.util.Collection;
import java.util.List;

public interface ExecutionResults {
    String RULES_RESULTS_KEY = "RULES_RESULTS";

    Collection<String> getIdentifiers();

    Object getValue(String identifier);

    Object getFactHandle(String identifier);

    List<RulesResult> getRulesResults();

    /**
     * 添加一个 组规则结果.
     */
    void addResult(RulesResult value);

    boolean isHit();

    Facts getEnv();

}
