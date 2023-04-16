package com.yart.literule.core.parser;


import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.model.flow.NodeConfig;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.parser.model.NodeResult;

public interface NodeParser {
    /**
     * support type.
     */
    NodeType type();

    NodeResult parse(NodeConfig currNode, WorkingMemory memory);
}
