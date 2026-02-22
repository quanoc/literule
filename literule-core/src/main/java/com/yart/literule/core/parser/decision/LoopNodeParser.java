package com.yart.literule.core.parser.decision;


import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.model.flow.NodeConfig;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.parser.NodeParser;
import com.yart.literule.core.parser.model.NodeResult;

public class LoopNodeParser implements NodeParser {


    @Override
    public NodeType type() {
        return NodeType.Loop;
    }

    @Override
    public NodeResult parse(NodeConfig currNode, WorkingMemory memory) {
        return null;
    }
}
