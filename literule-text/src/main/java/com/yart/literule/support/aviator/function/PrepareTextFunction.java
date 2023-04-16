package com.yart.literule.support.aviator.function;

import com.yart.literule.support.text.model.CHAR;
import com.yart.literule.support.text.model.CharType;
import com.yart.literule.support.text.TextPreProcess;
import com.yart.literule.support.text.util.CHARUtil;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.*;
import java.util.stream.Collectors;

public class PrepareTextFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        String paramName = ((AviatorJavaType) arg1).getName();
        String left = FunctionUtils.getStringValue(arg1, env);
        List<CHAR> charList = TextPreProcess.preprocess(left);
        String result = CHARUtil.toString(charList, aChar -> !TextPreProcess.
                removeSpecTypes.contains(aChar.getCt())
        );
        Map<CharType, List<CHAR>> group =  charList.stream()
                .collect(Collectors.groupingBy(CHAR::getCt, Collectors.toList()));
        for (CharType t : CharType.values()) {
            List<CHAR> chars = group.get(t);
            if (Objects.nonNull(chars)) {
                env.put(paramName +"." + t + ".size", chars.size());
            } else {
                env.put(paramName+"." + t + ".size", 0);
            }
        }
        return new AviatorString(result);
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String paramName = ((AviatorJavaType) arg1).getName();
        String left = FunctionUtils.getStringValue(arg1, env);
        String patternNames = FunctionUtils.getStringValue(arg2, env);
        String[] patternNameArr = patternNames.split(",");
        Set<CharType> acceptTypes = Arrays.stream(patternNameArr).map(this::type)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<CHAR> charList = TextPreProcess.preprocess(left);
        String result = CHARUtil.toString(charList, aChar -> acceptTypes.contains(aChar.getCt()));
        Map<CharType, List<CHAR>> group =  charList.stream()
                .collect(Collectors.groupingBy(CHAR::getCt, Collectors.toList()));
        for (CharType t : CharType.values()) {
            List<CHAR> chars = group.get(t);
            if (Objects.nonNull(chars)) {
                env.put(paramName +"." + t + ".size", chars.size());
            } else {
                env.put(paramName+"." + t + ".size", 0);
            }
        }
        return new AviatorString(result);
    }

    private CharType type(String type){
        try {
            return CharType.valueOf(type);
        } catch (Exception e) {
            return null;
        }
    }

    public String getName() {
        return "prepare";
    }
}