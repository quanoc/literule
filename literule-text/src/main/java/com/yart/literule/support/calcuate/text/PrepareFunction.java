package com.yart.literule.support.calcuate.text;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.support.text.model.CHAR;
import com.yart.literule.support.text.model.CharType;
import com.yart.literule.support.text.TextPreProcess;
import com.yart.literule.support.text.util.CHARUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文本预处理计算.
 */
public class PrepareFunction extends TextFunction {

    public PrepareFunction() {
        // function名称.
        super("prepare");
    }

    @Override
    String execute(String text, WorkingMemory workingMemory) {
        List<CHAR> charList = TextPreProcess.preprocess(text);
        Map<String, String> feature = new HashMap<>();
        // TODO.  数据计算传递.
        workingMemory.facts().put("textFeatures", feature);
        return CHARUtil.toString(charList, aChar -> aChar.getCt().getBType() != CharType.SpecialA.getBType());
    }

}
