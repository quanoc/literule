package com.yart.literule.core.parser.decision;

import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.parser.NodeParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * 节点解析器工厂.
 *
 * @author zhangquanquan
 */
public class NodeParserFactory {
    private static final Map<NodeType, NodeParser> parserMap = new HashMap<>();
    private static FlowNodeParser defaultNodeParser;

    public static NodeParser get(NodeType type) {
        if (needInit()) {
            init();
        }
        NodeParser parser = parserMap.get(type);
        return Objects.isNull(parser) ? defaultNodeParser : parser;
    }

    private static boolean needInit(){
        return CollectionUtil.isEmpty(parserMap);
    }

    private static void init(){
        synchronized (parserMap) {
            if (!needInit()) {
                return;
            }
            ServiceLoader<NodeParser> loader = ServiceLoader.load(NodeParser.class);
            for (NodeParser parser: loader) {
                if (Objects.nonNull(parser.type())) {
                    parserMap.put(parser.type(), parser);
                }
                if (parser instanceof FlowNodeParser) {
                    defaultNodeParser = (FlowNodeParser) parser;
                }
            }
        }
    }
}
