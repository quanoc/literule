package flow;

import com.alibaba.fastjson.JSON;
import com.yart.literule.core.model.flow.NodeConfig;
import com.yart.literule.core.parser.deserializer.FlowDeserializer;

import java.util.List;

public class TestFlowDeserializer {

    public static void main(String[] args) {
        //        DecisionFlowConfigEntity decisionFlowConfigEntity = new DecisionFlowConfigEntity();
        String json = "{\"nodes\":[{\"id\":\"1\",\"nodeType\":\"start\",\"conditionId\":\"\",\"trace\":false,\"name\":\"\"},{\"id\":\"2\",\"nodeType\":\"router\",\"conditionId\":\"6172aa83b4c2b2000a73e8db\",\"trace\":false,\"name\":\"boss是否企业认证通过\"},{\"id\":\"6\",\"nodeType\":\"action\",\"conditionId\":\"618a6bf9eeb45d000944c92d\",\"trace\":false,\"name\":\"boss是否企业认证通过\"},{\"id\":\"3\",\"nodeType\":\"risk\",\"conditionId\":\"6177c969326f5e442b212368\",\"trace\":true,\"name\":\"6177c969326f5e442b212368\"},{\"id\":\"4\",\"nodeType\":\"filter\",\"conditionId\":\"618cd202326f5ee4d45a7143\",\"trace\":true,\"name\":\"\"},{\"id\":\"5\",\"nodeType\":\"action\",\"conditionId\":\"618a6bf9eeb45d000944c92d\",\"trace\":false,\"name\":\"\"}],\"edges\":[{\"source\":\"1\",\"target\":\"2\",\"edgeValue\":\"yes\"},{\"source\":\"2\",\"target\":\"3\",\"edgeValue\":\"yes\"},{\"source\":\"3\",\"target\":\"4\",\"edgeValue\":\"yes\"},{\"source\":\"4\",\"target\":\"5\",\"edgeValue\":\"no\"},{\"source\":\"2\",\"target\":\"6\",\"edgeValue\":\"run\"}]}";
        System.out.println(JSON.toJSONString(JSON.parseObject(json), true));
        List<NodeConfig> result = FlowDeserializer.deserializerNodeList(json);
        System.out.println("args = [" + JSON.toJSONString(result, true) + "]");
    }
}
