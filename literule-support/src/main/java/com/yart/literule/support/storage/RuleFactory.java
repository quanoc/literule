package com.yart.literule.support.storage;

import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.internal.exception.RuleTypeNotFoundException;
import com.yart.literule.core.internal.util.ParamUtil;
import com.yart.literule.core.rule.Condition;
import com.yart.literule.core.rule.Rule;
import com.yart.literule.core.rule.RuleBuilder;
import com.yart.literule.regex.rule.RegexRule;
import com.yart.literule.support.aviator.rule.AviatorRule;

import java.util.Objects;

public class RuleFactory {
    public static Rule createRule(RuleEntity r){
        Rule rule;
        switch (r.getType()) {
            case "":
            case "normal":{
                rule = new RuleBuilder()
                        .name(r.getName())
                        .rid(r.getId())
                        .priority(3)
                        .when(new Condition() {
                            @Override
                            public boolean evaluate(Facts facts) {
                                // TODO. condition; Pattern Matcher by RuleEntity[ConditionConfig]
                                return false;
                            }
                        })
                        //.then(facts -> facts.putResult(r.getId(), true))
                        .build();
                return rule;
            }
            case "regex": {
                ParamUtil.notNull4Config(r.getLeft());
                ParamUtil.notNull4Config(r.getLeft().getProperty());
                rule = new RegexRule()
                        .name(r.getName())
                        .rid(r.getId())
//                        .description("正则规则_拼音匹配公众号.")
                        .when(r.getLeft().getProperty(),r.getExpression(),
                                Objects.isNull(r.getRight()) ? null : r.getRight().getValue()
                        )
                        //.then(facts -> facts.putResult(r.getId(), true))
                        .priority(10);
                return rule;
            }
            case "aviator":  {
                rule = new AviatorRule()
                        .name(r.getName())
                        .rid(r.getId())
                        .when(r.getExpression())
                        //.then(facts -> facts.putResult("hit", true))
                        //.then(facts -> facts.putResult(r.getId(), true))
                        //.then(facts -> facts.putResult("tag.rid_003", "aviator_calculate"))
                        .priority(10);
                return rule;
            }
            case "mvel": {

            }

        }
        throw new RuleTypeNotFoundException("rule:" + r);
    }
}
