package com.yart.literule.core.rule;

import com.yart.literule.core.model.basic.Facts;

/**
 * A listener for rule execution events.
 */
public interface RuleListener {

    /**
     * 在评估规则之前触发.
     *
     * @param rule 正在被评估的规则.
     * @param facts 评估规则之前的已知事实.
     * @return true 如果规则应该评估，则返回true, 否则返回false.
     */
    default boolean beforeEvaluate(Rule rule, Facts facts) {
        return true;
    }

    /**
     * Triggered after the evaluation of a rule.
     *
     * @param rule that has been evaluated
     * @param facts known after evaluating the rule
     * @param evaluationResult true if the rule evaluated to true, false otherwise
     */
    default void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) { }

    /**
     * Triggered on condition evaluation error due to any runtime exception.
     *
     * @param rule that has been evaluated
     * @param facts known while evaluating the rule
     * @param exception that happened while attempting to evaluate the condition.
     */
    default void onEvaluationError(Rule rule, Facts facts, Exception exception) { }

    /**
     * Triggered before the execution of a rule.
     *
     * @param rule the current rule
     * @param facts known facts before executing the rule
     */
    default void beforeExecute(Rule rule, Facts facts) { }

    /**
     * Triggered after a rule has been executed successfully.
     *
     * @param rule the current rule
     * @param facts known facts after executing the rule
     */
    default void onSuccess(Rule rule, Facts facts) { }

    /**
     * Triggered after a rule has failed.
     *
     * @param rule the current rule
     * @param facts known facts after executing the rule
     * @param exception the exception thrown when attempting to execute the rule
     */
    default void onFailure(Rule rule, Facts facts, Exception exception) { }

}