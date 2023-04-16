package com.yart.literule.core.example;

import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.session.StatefulSession;
import com.yart.literule.core.runtime.engine.DefaultRulesEngine;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.model.basic.Results;
import com.yart.literule.core.rule.*;

public class TestBzlRules {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        Condition condition = facts -> facts.getInteger("age") == 18;
        // define rules
        Rule weatherRule = new RuleBuilder()
                .name("myRule")
                .description("myRuleDescription")
                .priority(3)
                .when(condition)
                .then(facts -> System.out.println("规则命中后执行的Action1."))
                .then(facts -> System.out.println("规则命中后执行的Action2."))
                .then(facts -> System.out.println("规则命中后执行的Action3."))
                .build();


        // define facts
        Facts facts = new Facts();
        facts.put("age", 18);

        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        ExecutionResults results = new Results();
        rulesEngine.fire(rules, new StatefulSession());
    }
}
