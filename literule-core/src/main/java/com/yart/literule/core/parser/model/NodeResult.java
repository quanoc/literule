package com.yart.literule.core.parser.model;

import java.util.Objects;

/**
 * 因子的解析执行结果, 用于记录执行流程.
 */
public class NodeResult {
    /**
     * 因子编号.
     */
    private String id;
    private String name;
    /**
     * 因子ID. 通过id获取具体的因子配置数据.
     */
    private String nodeId;
    private boolean hit;
    private Throwable throwable;
    private long cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public boolean hasError() {
        return Objects.nonNull(throwable);
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
