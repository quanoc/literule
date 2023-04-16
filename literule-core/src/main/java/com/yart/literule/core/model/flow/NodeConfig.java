package com.yart.literule.core.model.flow;

import com.yart.literule.core.model.Node;

import java.util.List;

/**
 * 决策流节点信息.
 */
public class NodeConfig implements Node {
    // @see com.yart.literule.core.model.flow.NodeType
    private NodeType nodeType;
    private String id;
    private String nodeId; // TODO ???
    private String name;
    private List<NodeTrace> yesNext;
    private List<NodeTrace> noNext;
    private List<NodeTrace> runNext;
    private NodeLoopData loopData;


    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

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

    public List<NodeTrace> getYesNext() {
        return yesNext;
    }

    public void setYesNext(List<NodeTrace> yesNext) {
        this.yesNext = yesNext;
    }

    public List<NodeTrace> getNoNext() {
        return noNext;
    }

    public void setNoNext(List<NodeTrace> noNext) {
        this.noNext = noNext;
    }

    public List<NodeTrace> getRunNext() {
        return runNext;
    }

    public void setRunNext(List<NodeTrace> runNext) {
        this.runNext = runNext;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeLoopData getLoopData() {
        return loopData;
    }

    public void setLoopData(NodeLoopData loopData) {
        this.loopData = loopData;
    }
}
