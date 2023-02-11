package com.yart.literule.support.aviator.function;

import com.yart.literule.support.text.dict.Word;
import com.yart.literule.support.text.pattern.PatternHolder;
import com.yart.literule.support.text.pattern.PatternUtils;
import com.yart.literule.support.text.util.CharUtils;
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

public class FilterPatternFunction extends AbstractFunction {
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
        StringBuilder str = new StringBuilder(leftText);
        findResult.forEach(r -> str.replace(r.getS(), r.getE(), CharUtils.well(r.getValue().length())));
        env.put(getName()+".filterWords", findResult);
        return new AviatorString(str.toString());

    }
    public String getName() {
        return "filter_pattern";
    }
}