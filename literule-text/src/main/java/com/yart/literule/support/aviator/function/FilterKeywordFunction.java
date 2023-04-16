package com.yart.literule.support.aviator.function;

import com.yart.literule.support.text.dict.KeyWordDict;
import com.yart.literule.support.text.dict.Word;
import com.yart.literule.support.text.util.CharUtils;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterKeywordFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String leftText = FunctionUtils.getStringValue(arg1, env);
        String wordType = FunctionUtils.getStringValue(arg2, env);
        List<Word> words =  KeyWordDict.findWords(leftText);
        List<Word> whiteWords = words.stream()
                .filter(w -> KeyWordMatchFunction.matchWordType(wordType, w.getType()))
                .collect(Collectors.toList());
        StringBuilder str = new StringBuilder(leftText);
        whiteWords.forEach(r -> str.replace(r.getS(), r.getE(), CharUtils.well(r.getValue().length())));
        env.put(getName()+".filterWords", whiteWords);
        return new AviatorString(str.toString());
    }
    public String getName() {
        return "filter_keywords";
    }
}