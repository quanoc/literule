package com.yart.literule.support.aviator.rule;

import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.rule.Condition;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.exception.ExpressionRuntimeException;

import java.util.Objects;

public class AviatorCondition implements Condition {

    private final Expression compiledExp;
    public AviatorCondition(String expression) {
        this.compiledExp = AviatorEvaluator.compile(expression,true);
    }

    public boolean evaluate(Facts facts) {
        try {
            Object result = compiledExp.execute(facts);
            return Objects.nonNull(result) && (Boolean)result;
        }catch (ExpressionRuntimeException e){
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(AviatorEvaluator.compile(
                "text_tmp = filter_pattern(text, 'SALARY,PERCENT,DATE_PATTERN,TIME_PATTERN');" +
                        " text_tmp = filter_keywords(text_tmp, 'White,white_intent,white_skills');" +
                        " has_pattern(text_tmp, 'wx_001') && string.length(text) <= 50"
                ));
    }
}
