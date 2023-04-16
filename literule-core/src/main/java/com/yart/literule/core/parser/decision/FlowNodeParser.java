package com.yart.literule.core.parser.decision;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.model.flow.NodeConfig;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.parser.NodeParser;
import com.yart.literule.core.parser.model.NodeResult;

/**
 * flow默认的节点解析器.
 *
 * @author zhangquanquan
 */
public class FlowNodeParser implements NodeParser {


    @Override
    public NodeType type() {
        // 默认的节点解析器，不设置类型. 支持一下节点解析.
        return null;
    }

    @Override
    public NodeResult parse(NodeConfig currNode, WorkingMemory memory) {
        return null;
    }
}
