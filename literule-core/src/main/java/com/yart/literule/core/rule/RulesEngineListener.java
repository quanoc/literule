package com.yart.literule.core.rule;

import com.yart.literule.core.entity.Facts;

/**
 * A listener for rules engine execution events.
 */
public interface RulesEngineListener {

    /**
     * Triggered before evaluating the rule set.
     * <strong>When this listener is used with a {@link InferenceRulesEngine},
     * this method will be triggered before the evaluation of each candidate rule
     * set in each iteration.</strong>
     *
     * @param rules to fire
     * @param facts present before firing rules
     */
    default void beforeEvaluate(Rules rules, Facts facts) { }

    /**
     * Triggered after executing the rule set
     * <strong>When this listener is used with a {@link InferenceRulesEngine},
     * this method will be triggered after the execution of each candidate rule
     * set in each iteration.</strong>
     *
     * @param rules fired
     * @param facts present after firing rules
     */
    default void afterExecute(Rules rules, Facts facts) { }
}
