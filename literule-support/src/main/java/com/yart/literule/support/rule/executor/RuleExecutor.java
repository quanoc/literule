package com.yart.literule.support.rule.executor;

import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.context.StatefulSession;
import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.engine.DefaultRulesEngine;
import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.entity.Results;
import com.yart.literule.core.internal.exception.ConfigErrorException;
import com.yart.literule.core.internal.exception.RuleNotFoundException;
import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.regex.rule.RegexRule;
import com.yart.literule.support.aviator.rule.AviatorRule;
import com.yart.literule.support.config.LiteRuleConfig;
import com.yart.literule.support.rule.RuleBus;
import com.yart.literule.core.rule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RuleExecutor {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static volatile RuleExecutor instance;

    public static RuleExecutor getInstance(){
        if(instance == null){
            synchronized (RuleExecutor.class){
                if(instance == null){
                    instance = new RuleExecutor();
                }
            }
        }
        return instance;
    }

    private LiteRuleConfig config;
    private RulesEngine rulesEngine;

    public boolean initExecutor(LiteRuleConfig config){
        this.config = config;
        this.rulesEngine = new DefaultRulesEngine();
        return true;
    }
    public boolean initExecutor(LiteRuleConfig config, RulesEngine rulesEngine){
        this.rulesEngine = rulesEngine;
        initExecutor(config);
        return true;
    }

    public ExecutionResults execute(Facts facts) {
        return this.doExecute(null, facts);
    }

    public void execute(String gid, Facts facts) {
        this.doExecute(gid, facts);
    }

    /**
     * 规则执行入口.
     * @param gid 规则集ID. 为空-则执行已知的所有规则.
     * @param facts 事实.
     */
    protected ExecutionResults doExecute(String gid, Facts facts){
        if (RuleBus.needInit()) {
            init();
        }
        Facts envFacts = facts.clone();
        Results results = new Results(envFacts);
        WorkingMemory memory = new StatefulSession(envFacts, results);
        if (StringUtil.isEmpty(gid)) {
            RuleBus.getRuleSet().forEach(rules -> {
                if (rules.isAlive()) {
                    rulesEngine.fire(rules, memory);
                }
            });
        } else {
            Rules rules = RuleBus.getRuleSet(gid);
            if (Objects.isNull(rules)) {
                throw new RuleNotFoundException("couldn't find rules with the id["+gid+"]");
            }
            rulesEngine.fire(rules, memory);
        }
        return results;
    }


    public void init(){
        if (Objects.isNull(config)) {
            throw new ConfigErrorException("config error, please check literule config property.");
        }
        if (StringUtil.isEmpty(config.getRuleSource())) {
            LOG.warn("config.ruleSource is empty.");
            return;
        }
        //initTestRules();
        // MongoDB中加载 规则集. TODO.
    }

    private void initTestRules(){
        Rules rules = new Rules();
        rules.setGid("sid");

        Condition condition = facts -> facts.getInteger("age") == 18;
        Rule weatherRule = new RuleBuilder()
                .name("myRule")
                .rid("rid_001")
                .description("myRuleDescription")
                .priority(3)
                .when(condition)
                //.then(facts -> facts.putResult("tag.rid_001", "age18"))
                .build();
        Rule regexRule = new RegexRule()
                .name("RegexRule_001")
                .rid("rid_002")
                .description("正则规则_拼音匹配公众号.")
                .when("text","(.)*公众号(.)*")
                //.then(facts -> facts.putResult("tag.rid_002", "aviator_calculate"))
                .priority(10);
        Rule aviatorRule = new AviatorRule()
                .name("RegexRule_002")
                .rid("rid_003")
                .description("表达式规则_表达式计算比较.")
                .when("a+(b-c)>100")
                //.then(facts -> facts.putResult("tag.rid_003", "aviator_calculate"))
                .priority(10);
        rules.register(weatherRule, aviatorRule, regexRule);
        RuleBus.addRules(rules);
    }

}
