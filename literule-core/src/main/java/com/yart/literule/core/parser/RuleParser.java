package com.yart.literule.core.parser;

import java.util.List;

/**
 * 规则解析的抽象定义，所有RuleParser需继实现此interface.
 *
 * @author zhangquanquan 2022.07.28.
 */
public interface RuleParser {

    void parseMain(List<String> pathList) throws Exception;

    void parse(List<String> contentList) throws Exception;

}
