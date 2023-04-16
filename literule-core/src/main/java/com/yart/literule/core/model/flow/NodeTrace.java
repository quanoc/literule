package com.yart.literule.core.model.flow;

public class NodeTrace{
    private String parentId;
    private String nodeId;
    private String edgeId;
    private String conditionId;

    // getter setter.
    public String getParentId() {
        return parentId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }
}