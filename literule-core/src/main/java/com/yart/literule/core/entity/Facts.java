package com.yart.literule.core.entity;

/**
 * 事实定义.
 * @author zhangquanquan 2022.07.28.
 */
public class Facts extends BaseMapEntity<String, Object> {
    public Facts() {}
    public Facts(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Facts clone() {
        return (Facts) super.clone();
    }

}
