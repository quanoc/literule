package com.yart.literule.support.aviator.function;

import com.yart.literule.support.text.pattern.PatternHolder;
import com.yart.literule.support.text.pattern.PatternUtils;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class PatternMatchFunction extends AbstractFunction {
    @Override
    public AviatorBoolean call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String leftText = FunctionUtils.getStringValue(arg1, env);
        String patternNames = FunctionUtils.getStringValue(arg2, env);
        String[] patternNameArr = patternNames.split(",");
        for (String pName : patternNameArr) {
            Pattern pattern = PatternHolder.getPattern(pName);
            if (Objects.nonNull(pattern)) {
                boolean result = PatternUtils.isPattern(leftText, pattern);
                if (result) {
                    env.put(getName()+".hit", pName);
                    return AviatorBoolean.TRUE;
                }
            }
        }
        return AviatorBoolean.FALSE;
    }
    public String getName() {
        return "pattern";
    }
}