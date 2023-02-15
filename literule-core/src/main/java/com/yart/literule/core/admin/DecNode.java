package com.yart.literule.core.admin;

import com.yart.literule.core.model.flow.FlowNodeType;

import java.util.List;

/**
 * 决策流节点信息.
 */
public class DecNode {
    private FlowNodeType nType;
    private String id;
    private String name;
    private List<NodeTrace> yesNext;
    private List<NodeTrace> noNext;
    private List<NodeTrace> runNext;

    public static class NodeTrace{
        private String parentId;
        private String nodeId;
        private String edgeId;
        private String conditionId;

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
}
