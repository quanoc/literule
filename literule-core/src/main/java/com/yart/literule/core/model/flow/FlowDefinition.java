package com.yart.literule.core.model.flow;

import com.yart.literule.core.model.decision.BaseDecision;
import com.yart.literule.core.model.decision.DecisionType;

import java.util.List;

/**
 * 决策流定义.
 * @author zhangquanquan 2022.06.10.
 */
public class FlowDefinition extends BaseDecision {
    private String id;
    private String ver;
    private List<NodeConfig> nodeConfigList;
    /**
     * 处置建议列表(Action类型的节点列表).
     */
    private List<String> adviceIds;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getVer() {
        return ver;
    }

    @Override
    public DecisionType type() {
        return DecisionType.flow;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public void setNodeConfigList(List<NodeConfig> nodeConfigList) {
        this.nodeConfigList = nodeConfigList;
    }

    public List<NodeConfig> getNodeConfigList() {
        return nodeConfigList;
    }

    public List<String> getAdviceIds() {
        return adviceIds;
    }

    public void setAdviceIds(List<String> adviceIds) {
        this.adviceIds = adviceIds;
    }
}
