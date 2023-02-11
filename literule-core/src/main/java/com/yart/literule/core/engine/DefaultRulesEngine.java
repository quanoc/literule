package com.yart.literule.core.engine;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.entity.RuleResult;
import com.yart.literule.core.entity.RulesResult;
import com.yart.literule.core.rule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Default {@link RulesEngine} implementation.
 * Rules are fired according to their natural order which is priority by default.
 * This implementation iterates over the sorted set of rules, evaluates the condition
 * of each rule and executes its actions if the condition evaluates to true.
 */
public final class DefaultRulesEngine extends AbstractRulesEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRulesEngine.class);

    /**
     * Create a new {@link DefaultRulesEngine} with default parameters.
     */
    public DefaultRulesEngine() {
        super();
    }

    /**
     * Create a new {@link DefaultRulesEngine}.
     *
     * @param parameters of the engine
     */
    public DefaultRulesEngine(final RulesEngineParameters parameters) {
        super(parameters);
    }

    @Override
    public void fire(Rules rules, WorkingMemory wm) {
        triggerListenersBeforeRules(rules, wm.facts());
        doFire(rules, wm);
        triggerListenersAfterRules(rules, wm.facts());
    }

    void doFire(Rules rules, WorkingMemory wm) {
        if (rules.isEmpty()) {
            LOGGER.warn("No rules registered! Nothing to apply");
            return;
        }
        Facts envFacts = wm.facts();
        logEngineParameters();
        log(rules);
        log(envFacts);
        LOGGER.debug("Rules evaluation started");
        RulesResult result = new RulesResult(rules.getGid());
        wm.results().addResult(result);
        for (Rule rule : rules) {
            final String name = rule.getName();
            final int priority = rule.getPriority();
            if (priority > parameters.getPriorityThreshold()) {
                LOGGER.debug("Rule priority threshold ({}) exceeded at rule '{}' with priority={}, next rules will be skipped",
                        parameters.getPriorityThreshold(), name, priority);
                break;
            }
            if (!shouldBeEvaluated(rule, envFacts)) {
                LOGGER.debug("Rule '{}' has been skipped before being evaluated", name);
                continue;
            }
            boolean evaluationResult = false;
            try {
                evaluationResult = rule.evaluate(envFacts);
            } catch (RuntimeException exception) {
                LOGGER.error("Rule '" + name + "' evaluated with error", exception);
                triggerListenersOnEvaluationError(rule, wm.facts(), exception);
                // give the option to either skip next rules on evaluation error or continue by considering the evaluation error as false
                if (parameters.isSkipOnFirstNonTriggeredRule()) {
                    LOGGER.debug("Next rules will be skipped since parameter skipOnFirstNonTriggeredRule is set");
                    break;
                }
            }
            if (evaluationResult) {
                result.addResult(new RuleResult(rule.getRid(), RuleResult.Result.HIT));
                LOGGER.debug("Rule '{}' triggered", name);
                triggerListenersAfterEvaluate(rule, wm.facts(), true);
                try {
                    triggerListenersBeforeExecute(rule, wm.facts());
                    rule.execute(wm.facts());
                    LOGGER.debug("Rule '{}' performed successfully", name);
                    triggerListenersOnSuccess(rule, wm.facts());
                    if (rules.getRuleLogic().equals(RuleLogic.OR)){
                        break;
                    }
                } catch (Exception exception) {
                    LOGGER.error("Rule '" + name + "' performed with error", exception);
                    triggerListenersOnFailure(rule, exception, wm.facts());
                    if (parameters.isSkipOnFirstFailedRule()) {
                        LOGGER.debug("Next rules will be skipped since parameter skipOnFirstFailedRule is set");
                        break;
                    }
                }
            } else {
                LOGGER.debug("Rule '{}' has been evaluated to false, it has not been executed", name);
                triggerListenersAfterEvaluate(rule, wm.facts(), false);
                if (rules.getRuleLogic().equals(RuleLogic.AND)){
                    result.addBlock(rule.getRid());
                    break;
                }
            }
        }
        calculateResult(result, rules);
    }

    private void calculateResult(RulesResult result, Rules rules){
        if (Objects.isNull(result.getHit())) {
            if (rules.getRuleLogic() == RuleLogic.OR) {
                result.setHit( result.getResultList().stream()
                        .anyMatch(RuleResult::isHit)
                );
            } else if (rules.getRuleLogic() == RuleLogic.AND){
                result.setHit(result.getResultList().stream()
                        .allMatch(RuleResult::isHit)
                );
            }
        }
    }

    private void logEngineParameters() {
        LOGGER.debug("{}", parameters);
    }

    private void log(Rules rules) {
        LOGGER.debug("Registered rules:");
        for (Rule rule : rules) {
            LOGGER.debug("Rule { id = '{}', name = '{}', description = '{}', priority = '{}'}",
                    rule.getRid(), rule.getName(), rule.getDescription(), rule.getPriority());
        }
    }

    private void log(Facts facts) {
        LOGGER.debug("Known facts:");
        LOGGER.debug("{}", facts);
    }

    @Override
    public Map<Rule, Boolean> check(Rules rules, Facts facts) {
        triggerListenersBeforeRules(rules, facts);
        Map<Rule, Boolean> result = doCheck(rules, facts);
        triggerListenersAfterRules(rules, facts);
        return result;
    }

    private Map<Rule, Boolean> doCheck(Rules rules, Facts facts) {
        LOGGER.debug("Checking rules");
        Map<Rule, Boolean> result = new HashMap<>();
        for (Rule rule : rules) {
            if (shouldBeEvaluated(rule, facts)) {
                result.put(rule, rule.evaluate(facts));
            }
        }
        return result;
    }

    private void triggerListenersOnFailure(final Rule rule, final Exception exception, Facts facts) {
        ruleListeners.forEach(ruleListener -> ruleListener.onFailure(rule, facts, exception));
    }

    private void triggerListenersOnSuccess(final Rule rule, Facts facts) {
        ruleListeners.forEach(ruleListener -> ruleListener.onSuccess(rule, facts));
    }

    private void triggerListenersBeforeExecute(final Rule rule, Facts facts) {
        ruleListeners.forEach(ruleListener -> ruleListener.beforeExecute(rule, facts));
    }

    private boolean triggerListenersBeforeEvaluate(Rule rule, Facts facts) {
        return ruleListeners.stream().allMatch(ruleListener -> ruleListener.beforeEvaluate(rule, facts));
    }

    private void triggerListenersAfterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
        ruleListeners.forEach(ruleListener -> ruleListener.afterEvaluate(rule, facts, evaluationResult));
    }

    private void triggerListenersOnEvaluationError(Rule rule, Facts facts, Exception exception) {
        ruleListeners.forEach(ruleListener -> ruleListener.onEvaluationError(rule, facts, exception));
    }

    private void triggerListenersBeforeRules(Rules rule, Facts facts) {
        rulesEngineListeners.forEach(rulesEngineListener -> rulesEngineListener.beforeEvaluate(rule, facts));
    }

    private void triggerListenersAfterRules(Rules rule, Facts facts) {
        rulesEngineListeners.forEach(rulesEngineListener -> rulesEngineListener.afterExecute(rule, facts));
    }

    private boolean shouldBeEvaluated(Rule rule, Facts facts) {
        return triggerListenersBeforeEvaluate(rule, facts);
    }

}
