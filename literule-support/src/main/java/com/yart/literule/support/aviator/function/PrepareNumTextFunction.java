package com.yart.literule.support.aviator.function;

import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.support.text.TextPreProcess;
import com.yart.literule.support.text.model.CHAR;
import com.yart.literule.support.text.model.CharType;
import com.yart.literule.support.text.util.CHARUtil;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.*;
import java.util.stream.Collectors;

public class PrepareNumTextFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        String paramName = ((AviatorJavaType) arg1).getName();
        String left = FunctionUtils.getStringValue(arg1, env);
        List<CHAR> charList = TextPreProcess.preprocess(left);
        String result = CHARUtil.toNumStringV3(charList, aChar ->
                !TextPreProcess.removeSpecTypes.contains(aChar.getCt())
        );
        envDataPut(paramName, charList, env);
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
        String result;
        if (CollectionUtil.isEmpty(acceptTypes)) {
            result = CHARUtil.toNumStringV3(charList, aChar ->
                    !TextPreProcess.removeSpecTypes.contains(aChar.getCt())
            );
        } else {
            result = CHARUtil.toNumStringV3(charList, aChar -> acceptTypes.contains(aChar.getCt()));
        }
        envDataPut(paramName, charList, env);
        return new AviatorString(result);
    }

    private void envDataPut(String paramName, List<CHAR> charList, Map<String, Object> env){
        Map<CharType, List<CHAR>> group =  charList.stream()
                .collect(Collectors.groupingBy(CHAR::getCt, Collectors.toList()));
        group.forEach((k,v) -> env.put(paramName +"." + k + ".size", v.size()));
    }

    private CharType type(String type){
        try {
            return CharType.valueOf(type);
        } catch (Exception e) {
            return null;
        }
    }
    public String getName() {
        return "prepare_num";
    }
}