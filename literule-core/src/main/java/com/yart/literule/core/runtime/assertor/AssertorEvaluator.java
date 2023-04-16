package com.yart.literule.core.runtime.assertor;

import com.yart.literule.core.model.data.DataType;
import com.yart.literule.core.internal.exception.RuleException;
import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.model.rule.Op;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

public class AssertorEvaluator {
    private final static Collection<Assertor> assertorSet = new ArrayList<>();

    private final static AssertorEvaluator evaluator = new AssertorEvaluator();

    public static AssertorEvaluator getInstance() {
        if (CollectionUtil.isEmpty(assertorSet)) {
            init();
        }
        return evaluator;
    }
    public static void init(){
        ServiceLoader<Assertor> loader = ServiceLoader.load(Assertor.class);
        loader.forEach(assertorSet::add);
    }

    public boolean evaluate(Object left, Object right, DataType datatype, Op op){
        Assertor targetAssertor=null;
        for(Assertor assertor:assertorSet){
            if(assertor.support(op)){
                targetAssertor=assertor;
                break;
            }
        }
        if(targetAssertor==null){
            throw new RuleException("not support op:"+op);
        }
        return targetAssertor.eval(left, right,datatype);
    }
}
