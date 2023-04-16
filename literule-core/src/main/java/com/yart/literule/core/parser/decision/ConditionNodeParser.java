package com.yart.literule.core.parser.decision;

import com.yart.literule.core.admin.KnowledgeBase;
import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.model.flow.NodeConfig;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.parser.NodeParser;
import com.yart.literule.core.parser.model.NodeResult;
import com.yart.literule.core.rule.NormalRule;
import com.yart.literule.core.rule.Rule;
import com.yart.literule.core.rule.Rule.RuleType;

/**
 * ConditionNodeParser.
 * @author zhangquanquan
 */
public class ConditionNodeParser implements NodeParser {

    @Override
    public NodeType type() {
        return NodeType.Rule;
    }

    @Override
    public NodeResult parse(NodeConfig currNode, WorkingMemory memory) {
        Rule rule = KnowledgeBase.getRule(currNode.getNodeId());
        RuleType type = rule.type();
        NodeResult result = new NodeResult();
        result.setId(currNode.getId());
        result.setName(currNode.getName());
        try {
            ruleParse0(type, rule, memory, result);
        } catch (Exception e) {
            result.setThrowable(e);
        }
        return result;
    }

    private void ruleParse0(
            RuleType type, Rule rule,
            WorkingMemory memory, NodeResult result
    ){
        if (type.equals(RuleType.Normal)) {
            NormalRule normalRule = (NormalRule) rule;
            result.setHit(normalRule.evaluate(memory.facts()));
        }
    }
}
