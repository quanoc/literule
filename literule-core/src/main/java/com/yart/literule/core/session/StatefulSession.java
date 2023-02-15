package com.yart.literule.core.session;

import com.yart.literule.core.admin.RuleBus;
import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.context.PropagationEntry;
import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.engine.DefaultRulesEngine;
import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.entity.Results;
import com.yart.literule.core.internal.exception.RuleNotFoundException;
import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.core.rule.Rules;
import com.yart.literule.core.rule.RulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 有状态规则会话.
 */
public class StatefulSession implements WorkingMemory, RuleSession{
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final RulesEngine rulesEngine;
    Results results = new Results();

    // 一个会话处理一个facts. 会话复用呢?
    public StatefulSession() {
        this.rulesEngine = new DefaultRulesEngine();
    }
    public StatefulSession(RulesEngine rulesEngine) {
        this.rulesEngine = rulesEngine;
    }

    public StatefulSession(Facts facts) {
        results.setEnv(facts.clone());
        this.rulesEngine = new DefaultRulesEngine();
    }
    @Override
    public void addPropagation(PropagationEntry propagationEntry) {

    }

    @Override
    public Facts facts() {
        return results.getEnv();
    }

    @Override
    public ExecutionResults results() {
        return results;
    }

    @Override
    public ExecutionResults execute(String gid, Facts facts) {
        if (RuleBus.needInit()) {
            init();
        }
        this.results.setEnv(facts.clone());
        if (StringUtil.isEmpty(gid)) {
            RuleBus.getRuleSet().forEach(rules -> {
                if (rules.isAlive()) {
                    rulesEngine.fire(rules, this);
                }
            });
        } else {
            Rules rules = RuleBus.getRuleSet(gid);
            if (Objects.isNull(rules)) {
                throw new RuleNotFoundException("couldn't find rules with the id["+gid+"]");
            }
            rulesEngine.fire(rules, this);
        }
        return results;
    }

    public void init(){
//        if (Objects.isNull(config)) {
//            throw new ConfigErrorException("config error, please check literule config property.");
//        }
//        if (StringUtil.isEmpty(config.getRuleSource())) {
//            LOG.warn("config.ruleSource is empty.");
//            return;
//        }
        //initTestRules();
        // MongoDB中加载 规则集. TODO.
    }
}
