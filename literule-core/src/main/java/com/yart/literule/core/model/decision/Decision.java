package com.yart.literule.core.model.decision;

/**
 * @author zhangquanquan
 */
public interface Decision {
    /**
     * 决策ID.
     */
    String getId();
    /**
     * 版本.
     */
    String getVer();
    /**
     * 决策类型:决策流、决策树、规则集、评分卡.
     */
    DecisionType type();

    /**
     * 是否生效.
     */
    boolean isAlive();
}