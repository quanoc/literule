package com.yart.literule.support.aviator.function;

import com.yart.literule.support.text.dict.Word;
import com.yart.literule.support.text.pattern.PatternHolder;
import com.yart.literule.support.text.pattern.PatternUtils;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GetPatternFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String leftText = FunctionUtils.getStringValue(arg1, env);
        String patternNames = FunctionUtils.getStringValue(arg2, env);
        String[] patternNameArr = patternNames.split(",");
        List<Word> findResult = new ArrayList<>();
        for (String pName : patternNameArr) {
            Pattern pattern = PatternHolder.getPattern(pName);
            if (Objects.nonNull(pattern)) {
                findResult.addAll(
                        PatternUtils.getElementWithPos(leftText, pattern)
                                .stream()
                                .map(t -> new Word(t._1, pName, t._2, t._3))
                                .collect(Collectors.toList())
                );
            }
        }
        List<String> result = findResult.stream().map(Word::getValue).collect(Collectors.toList());
        return new AviatorString(String.join("^", result));
    }

    public String getName() {
        return "get_pattern";
    }
}