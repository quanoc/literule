package com.yart.literule.support.storage;

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
    String type();
}
