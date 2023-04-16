package com.yart.literule.core.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yart.literule.core.internal.exception.ConfigErrorException;
import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.core.model.decision.DecisionType;
import com.yart.literule.core.model.flow.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.yart.literule.core.model.flow.NodeType.*;

public class FlowDeserializer implements DecisionDeserializer{

    @Override
    public FlowDefinition deserializer(String json) {
        List<NodeConfig> configList  = deserializerNodeList(json);
        return buildFlowDefinition(configList);
    }

    @Override
    public DecisionType type() {
        return DecisionType.flow;
    }

    /**
     * 通过节点list构建 决策流定义.
     * 1. 合法性check.
     * 2.
     * @param nodeList 节点list.
     */
    private FlowDefinition buildFlowDefinition(List<NodeConfig> nodeList){
        if (CollectionUtil.isEmpty(nodeList)) {
            throw new ConfigErrorException("节点列表不能为空.");
        }
        for (NodeConfig node: nodeList) {
            checkNode(node);
        }
        List<NodeConfig> riskAdviceNodeList = nodeList.stream()
                .filter(node -> node.getNodeType().equals(Action))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(riskAdviceNodeList)) {
            throw new ConfigErrorException("处置节点配置有误「不可为空等」.");
        }
        FlowDefinition definition = new FlowDefinition();
        definition.setNodeConfigList(nodeList);
        Set<String> set = riskAdviceNodeList.stream()
                .map(NodeConfig::getNodeId)
                .collect(Collectors.toSet());
        definition.setAdviceIds(new ArrayList<>(set));
        return definition;
    }

    private void checkNode(NodeConfig node){
        if (Start.equals(node.getNodeType())){
            if (CollectionUtil.isNotEmpty(node.getNoNext())) {
                throw new ConfigErrorException("起始节点的边，只能选为「是」");
            }
            if (Objects.isNull(node.getYesNext())
                    || node.getYesNext().size() > 1) {
                throw new ConfigErrorException("起始节点仅支持一条边");
            }
            return;
        }
        if (LoopEnd.equals(node.getNodeType())) {
            if (CollectionUtil.isNotEmpty(node.getNoNext())
                    || CollectionUtil.isNotEmpty(node.getRunNext())) {
                throw new ConfigErrorException("循环结束节点，往下边逻辑仅为「是」和「循环」");
            }
            return;
        }
        if (Action.equals(node.getNodeType())) {
            if (CollectionUtil.isNotEmpty(node.getNoNext())
                    || CollectionUtil.isNotEmpty(node.getRunNext())) {
                throw new ConfigErrorException("处置节点，往下边逻辑仅为「是」");
            }
        }
        if (StringUtil.isBlank(node.getNodeId())) {
            throw new ConfigErrorException("节点配置信息「nodeId」缺失.");
        }
    }


    public static List<NodeConfig> deserializerNodeList(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (!jsonObject.containsKey("nodes")) {
            return null;
        }
        List<NodeConfig> nodeConfigs = new ArrayList<>();
        JSONArray nodeArray = jsonObject.getJSONArray("nodes");
        JSONArray edgeArray = jsonObject.getJSONArray("edges");

        Map<String, JSONObject> nodeMaps = new HashMap<>();
        Map<String, String> nodeConditionIdMap = new HashMap<>();
        JSONObject startNode = null;

        // 一、节点解析.
        for (int i = 0; i < nodeArray.size(); i++) {
            JSONObject node = nodeArray.getJSONObject(i);
            if (Objects.isNull(node)) {
                continue;
            }
            // 1. 获取start节点.
            if (node.containsKey("nodeType")) {
                String nodeType = node.getString("nodeType");
                if ("start".equals(nodeType)) {
                    startNode = node;
                }
            }
            // 2. nodeConditionIdMap构造.
            String id = node.getString("id");
            nodeConditionIdMap.put(id, node.getString("conditionId"));
            // 3. nodeMaps构造.
            nodeMaps.put(id, node);
        }
        if (Objects.isNull(startNode)) {
            throw new IllegalArgumentException("startNode not found.");
        }
        // 获取节点id
        String id = startNode.getString("id");
        NodeTrace nodeEdgeDTO = new NodeTrace();
        nodeEdgeDTO.setNodeId(id);
        nodeEdgeDTO.setEdgeId(null);
        return nodeConfigsBuildForTest(nodeConfigs, edgeArray, nodeEdgeDTO, nodeMaps, nodeConditionIdMap);
    }


    private static List<NodeTrace> findTargetNodeIdBySourceId(JSONArray jsonArray, String edgeType,
                                                              String sourceId,
                                                              Map<String, String> nodeCondition) {
        //log.info("findTargetNodeIdBySourceId edgeType:{},{},{}", edgeType, sourceId, targets);
        return jsonArray.stream().map(edge -> {
            JSONObject edgeJson = (JSONObject) edge;
            if (edgeJson.containsKey("source")) {
                String source = edgeJson.getString("source");
                if (source.equals(sourceId)) {
                    String edgeValue = edgeJson.getString("edgeValue");
                    if (edgeValue.equals(edgeType)) {
                        NodeTrace nodeEdgeDTO = new NodeTrace();
                        nodeEdgeDTO.setParentId(sourceId);
                        String target = edgeJson.getString("target");
                        nodeEdgeDTO.setConditionId(nodeCondition.get(target));
                        nodeEdgeDTO.setNodeId(target);
                        nodeEdgeDTO.setEdgeId(edgeJson.getString("id"));
                        return nodeEdgeDTO;
                    }
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    private static List<NodeConfig> nodeConfigsBuildForTest(List<NodeConfig> nodeConfigs,
                                                            JSONArray edgeArray, NodeTrace id,
                                                            Map<String, JSONObject> nodeMap, Map<String, String> nodeCondition) {

        /*
          边解析.
         */
        List<NodeTrace> yesOutPuts = findTargetNodeIdBySourceId(edgeArray, "yes", id.getNodeId(),
                nodeCondition);
        List<NodeTrace> noOutPuts = findTargetNodeIdBySourceId(edgeArray, "no", id.getNodeId(),
                nodeCondition);
        List<NodeTrace> runOutPuts = findTargetNodeIdBySourceId(edgeArray, "run", id.getNodeId(),
                nodeCondition);
        NodeConfig nodeConfig = new NodeConfig();
        nodeConfig.setId(id.getNodeId());
        JSONObject nodeData = nodeMap.get(id.getNodeId());
        NodeType nodeType = NodeType.valueOf(nodeData.getString("nodeType"));
        // 额外数据处理 TODO. 参数校验.
        nodeConfig.setNodeType(nodeType);
        nodeConfig.setNodeId(nodeData.getString("conditionId"));
        if (nodeType.equals(NodeType.Loop)) {
            NodeLoopData loopData = new NodeLoopData();
            if (nodeData.containsKey("jump")) {
                loopData.setJump(nodeData.getBooleanValue("jump"));
            }
            if (nodeData.containsKey("loopKey")) {
                loopData.setLoopKey(nodeData.getString("loopKey"));
            }
            if (nodeData.containsKey("rateCalculate")) {
                loopData.setRateCalculate(nodeData.getBooleanValue("rateCalculate"));
            }
            if (nodeData.containsKey("rateKey")) {
                loopData.setRateKey(nodeData.getString("rateKey"));
            }
            if (nodeData.containsKey("times")) {
                loopData.setTimes(nodeData.getIntValue("times"));
            }
            if (nodeData.containsKey("valueType")) {
                loopData.setValueType(nodeData.getString("valueType"));
            }
            nodeConfig.setLoopData(loopData);
        }
        if (nodeType.equals(LoopEnd)) {
            nodeConfig.setNodeId(nodeData.getString("id"));
        }
        // nodeConfig.setTrace(nodeData.getBool("trace"));
        // nodeConfig.setName(nodeData.getString(AegisConstInfo.NAME));
        nodeConfig.setYesNext(yesOutPuts);
        nodeConfig.setNoNext(noOutPuts);
        nodeConfig.setRunNext(runOutPuts);
        nodeConfigs.add(nodeConfig);
        if (CollectionUtil.isNotEmpty(yesOutPuts)) {
            yesOutPuts.forEach(yesOutPut -> {
                nodeConfigsBuildForTest(nodeConfigs, edgeArray, yesOutPut, nodeMap, nodeCondition);
            });
        }
        if (CollectionUtil.isNotEmpty(noOutPuts)) {
            noOutPuts.forEach(noOutPut -> {
                nodeConfigsBuildForTest(nodeConfigs, edgeArray, noOutPut, nodeMap, nodeCondition);
            });
        }
        if (CollectionUtil.isNotEmpty(runOutPuts)) {
            runOutPuts.forEach(runOutPut -> {
                nodeConfigsBuildForTest(nodeConfigs, edgeArray, runOutPut, nodeMap, nodeCondition);
            });
        }
        return nodeConfigs;
    }

    public static void main(String[] args) {
//        DecisionFlowConfigEntity decisionFlowConfigEntity = new DecisionFlowConfigEntity();
        String json = "{\"nodes\":[{\"id\":\"1\",\"nodeType\":\"start\",\"conditionId\":\"\",\"trace\":false,\"name\":\"\"},{\"id\":\"2\",\"nodeType\":\"router\",\"conditionId\":\"6172aa83b4c2b2000a73e8db\",\"trace\":false,\"name\":\"boss是否企业认证通过\"},{\"id\":\"6\",\"nodeType\":\"action\",\"conditionId\":\"618a6bf9eeb45d000944c92d\",\"trace\":false,\"name\":\"boss是否企业认证通过\"},{\"id\":\"3\",\"nodeType\":\"risk\",\"conditionId\":\"6177c969326f5e442b212368\",\"trace\":true,\"name\":\"6177c969326f5e442b212368\"},{\"id\":\"4\",\"nodeType\":\"filter\",\"conditionId\":\"618cd202326f5ee4d45a7143\",\"trace\":true,\"name\":\"\"},{\"id\":\"5\",\"nodeType\":\"action\",\"conditionId\":\"618a6bf9eeb45d000944c92d\",\"trace\":false,\"name\":\"\"}],\"edges\":[{\"source\":\"1\",\"target\":\"2\",\"edgeValue\":\"yes\"},{\"source\":\"2\",\"target\":\"3\",\"edgeValue\":\"yes\"},{\"source\":\"3\",\"target\":\"4\",\"edgeValue\":\"yes\"},{\"source\":\"4\",\"target\":\"5\",\"edgeValue\":\"no\"},{\"source\":\"2\",\"target\":\"6\",\"edgeValue\":\"run\"}]}";
        System.out.println(JSON.toJSONString(JSON.parseObject(json), true));
        List<NodeConfig> result = FlowDeserializer.deserializerNodeList(json);
        System.out.println("args = [" + JSON.toJSONString(result, true) + "]");
    }
}
