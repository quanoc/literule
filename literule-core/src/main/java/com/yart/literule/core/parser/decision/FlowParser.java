package com.yart.literule.core.parser.decision;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.model.basic.DecisionResult;
import com.yart.literule.core.model.flow.NodeConfig;
import com.yart.literule.core.model.flow.NodeTrace;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.parser.NodeParser;
import com.yart.literule.core.parser.model.NodeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FlowParser {
    Logger log = LoggerFactory.getLogger(FlowParser.class);

    public void parser(NodeConfig currentFactor, Map<String, NodeConfig> nodeMap,
                       String gid, WorkingMemory memory) {
        DecisionResult decisionResult = new DecisionResult();
        decisionResult.setGid(gid);
        NodeType type = currentFactor.getNodeType();
        NodeParser parser = NodeParserFactory.get(type);
        NodeResult result = parser.parse(currentFactor, memory);
        decisionResult.addResultList(result);
        // 因子执行结果处理.
        if (result.hasError()) {
            // TODO 记录异常信息
            decisionResult.setError(true);
            return;
        }
        if (result.isHit()) {
            // 记录命中信息.
            decisionResult.setHit(true);
            parseResult(type, decisionResult);
            decisionResult.addHitNodes(currentFactor.getNodeId());
            // 1. 是(命中)后需要执行的节点.
            executeNextFactor(currentFactor.getYesNext(), nodeMap, gid, memory);
        } else {
            // 2. 否 需要执行的节点.
            executeNextFactor(currentFactor.getNoNext(), nodeMap, gid, memory);
        }
        // 3. 执行继续 的节点类型.
        executeNextFactor(currentFactor.getRunNext(), nodeMap, gid, memory);
    }

    private void parseResult(NodeType type, DecisionResult result){
        if (result.isHit()) {
            result.setRes(DecisionResult.Result.HIT);
            if (type == NodeType.Action) {
                result.setRes(DecisionResult.Result.ACT);
//            } else if (type == NodeType.Filter) {
//                result.setRes(DecisionResult.Result.BLOCK);
                // TODO 根据节点信息 判断是否是Filter.
            }
        }
    }


    public void executeNextFactor(
            List<NodeTrace> nextFactors,
            Map<String, NodeConfig> nodeMap,
            String gid,
            WorkingMemory memory
    ){
        if (CollectionUtil.isEmpty(nextFactors)) {
            return;
        }
        // TODO 节点路由问题：可以向上路由那？节点可以复用吗，复用的时候还是同一个对象？
        for (NodeTrace factor : nextFactors) {
            // 串行执行子路线, --》 多路并行??
            try {
                NodeConfig currFactor = nodeMap.get(factor.getNodeId());
                if (Objects.isNull(currFactor)) {
                    log.error("nodeConfig not found. factor:{}", factor);
                    continue;
                }
                parser(currFactor, nodeMap, gid, memory);
            } catch (Exception e) {
                log.error("parse nextFactor error. factor:{}", factor);
            }
        }
    }

//    hitStatusParse(NodeType type, ){
//
//    }
}
