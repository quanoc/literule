package com.yart.literule.core.engine;

import com.yart.literule.core.rule.RuleListener;
import com.yart.literule.core.rule.RulesEngine;
import com.yart.literule.core.rule.RulesEngineListener;
import com.yart.literule.core.rule.RulesEngineParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for {@link RulesEngine} implementations.
 */
public abstract class AbstractRulesEngine implements RulesEngine {

    RulesEngineParameters parameters;
    List<RuleListener> ruleListeners;
    List<RulesEngineListener> rulesEngineListeners;

    AbstractRulesEngine() {
        this(new RulesEngineParameters());
    }

    AbstractRulesEngine(final RulesEngineParameters parameters) {
        this.parameters = parameters;
        this.ruleListeners = new ArrayList<>();
        this.rulesEngineListeners = new ArrayList<>();
    }

    /**
     * Return a copy of the rules engine parameters.
     * @return copy of the rules engine parameters
     */
    @Override
    public RulesEngineParameters getParameters() {
        return new RulesEngineParameters(
                parameters.isSkipOnFirstFailedRule(),
                parameters.isSkipOnFirstNonTriggeredRule(),
                parameters.getPriorityThreshold()
        );
    }

    /**
     * Return an unmodifiable list of the registered rule listeners.
     * @return an unmodifiable list of the registered rule listeners
     */
    @Override
    public List<RuleListener> getRuleListeners() {
        return Collections.unmodifiableList(ruleListeners);
    }

    /**
     * Return an unmodifiable list of the registered rules engine listeners
     * @return an unmodifiable list of the registered rules engine listeners
     */
    @Override
    public List<RulesEngineListener> getRulesEngineListeners() {
        return Collections.unmodifiableList(rulesEngineListeners);
    }

    public void registerRuleListener(RuleListener ruleListener) {
        ruleListeners.add(ruleListener);
    }

    public void registerRuleListeners(List<RuleListener> ruleListeners) {
        this.ruleListeners.addAll(ruleListeners);
    }

    public void registerRulesEngineListener(RulesEngineListener rulesEngineListener) {
        rulesEngineListeners.add(rulesEngineListener);
    }

    public void registerRulesEngineListeners(List<RulesEngineListener> rulesEngineListeners) {
        this.rulesEngineListeners.addAll(rulesEngineListeners);
    }
}
