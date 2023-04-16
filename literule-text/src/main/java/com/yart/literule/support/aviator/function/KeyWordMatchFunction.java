package com.yart.literule.support.aviator.function;

import com.yart.literule.support.text.dict.KeyWordDict;
import com.yart.literule.support.text.dict.Word;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.*;
import java.util.stream.Collectors;

public class KeyWordMatchFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String paramName = ((AviatorJavaType) arg1).getName();
        String left = FunctionUtils.getStringValue(arg1, env);
        String wordType = FunctionUtils.getStringValue(arg2, env);
        List<Word> words =  KeyWordDict.findWords(left);
        Map<String, List<Word>> wordTypeGroup = words.stream()
                .collect(Collectors.groupingBy(Word::getType, Collectors.toList()));
        wordTypeGroup.forEach((k,v) -> env.put(paramName +"." + k + ".words",
                v.stream().map(Word::getValue).collect(Collectors.joining("#"))));
        return new AviatorBigInt(words.stream().filter(w -> matchWordType(wordType, w.getType())).count());
    }

    public static boolean matchWordType(String configType, String resultType){
        if (configType == null) {
            return true;
        } else {
            Set<String> configTypes = new HashSet<>(Arrays.asList(configType.split(",")));
            return configTypes.contains(resultType);
        }
    }
    public String getName() {
        return "keywords";
    }
}