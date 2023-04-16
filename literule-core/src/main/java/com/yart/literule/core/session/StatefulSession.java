package com.yart.literule.core.session;

import com.yart.literule.core.admin.KnowledgeBase;
import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.context.PropagationEntry;
import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.internal.exception.RuleNotFoundException;
import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.core.model.decision.Decision;
import com.yart.literule.core.model.decision.DecisionType;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.model.basic.Results;
import com.yart.literule.core.rule.Rules;
import com.yart.literule.core.rule.RulesEngine;
import com.yart.literule.core.runtime.engine.RulesEngineHolder;
import com.yart.literule.core.runtime.knowledge.KnowledgePackage;
import com.yart.literule.core.runtime.knowledge.KnowledgePackageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 有状态规则会话.
 */
public class StatefulSession implements WorkingMemory, RuleSession{
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    Results results = new Results();

    // 一个会话处理一个facts. 会话复用呢?
    public StatefulSession() {}

    public StatefulSession(Facts facts) {
        results.setEnv(facts.clone());
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
        if (KnowledgeBase.getInstance().needInit()) {
            init();
        }
        // 使用 中间变量的前缀 实现变量隔离，替代clone方式，提高性能.
        this.results.setEnv(facts.clone());
        if (StringUtil.isEmpty(gid)) {
            KnowledgeBase.getDecisions().forEach(decision -> {
                if (!decision.isAlive()) {
                    return;
                }
                if (decision.type().equals(DecisionType.set)) {
                    RulesEngineHolder.getInstance().getEngine(RulesEngine.EngineType.Default)
                            .fire((Rules)decision, this);
                }
            });
        } else {
            Decision rules = KnowledgeBase.getDecision(gid);
            if (Objects.isNull(rules)) {
                throw new RuleNotFoundException("couldn't find rules with the id["+gid+"]");
            }
            if (rules.type().equals(DecisionType.set)) {
                RulesEngineHolder.getInstance().getEngine(RulesEngine.EngineType.Default)
                        .fire((Rules)rules, this);
            }
        }
        return results;
    }

    public void init(){
        KnowledgePackage knowledgePackage = new KnowledgePackageImpl();
        // 初始化KnowledgeBase. 外部可自定义初始化(从指定的源「mongodb等」 加载规则知识数据).
        KnowledgeBase.getInstance().init(knowledgePackage);
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
