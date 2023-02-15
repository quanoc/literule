package com.yart.literule.core.context;

import com.yart.literule.core.entity.Facts;

/**
 * 存放要匹配规则的事实
 * @author zhangquanquan 2022.10.17.
 */
public interface WorkingMemory {
    void addPropagation(PropagationEntry propagationEntry);

    //void addPropagation(Facts env);

    Facts facts();

    ExecutionResults results();
}
