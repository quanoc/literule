package com.yart.literule.core.rule;

import com.yart.literule.core.runtime.engine.DefaultRulesEngine;

/**
 * Parameters of a rules engine.
 *
 * <ul>
 *     <li> {@link DefaultRulesEngine}, applied on <strong>all registered rules</strong>.</li>
 * </ul>
 *
 */
public class RulesEngineParameters {

    /**
     * Default rule priority threshold.
     */
    public static final int DEFAULT_RULE_PRIORITY_THRESHOLD = Integer.MAX_VALUE;
    
    /**
     * Parameter to skip next applicable rules when a rule is applied.
     */
    private boolean skipOnFirstAppliedRule;

    /**
     * Parameter to skip next applicable rules when a rule is non triggered
     */
    private boolean skipOnFirstNonTriggeredRule;

    /**
     * Parameter to skip next applicable rules when a rule has failed.
     */
    private boolean skipOnFirstFailedRule;

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    private int priorityThreshold;

    /**
     * Create a new {@link RulesEngineParameters} with default values.
     */
    public RulesEngineParameters() {
        this.priorityThreshold = RulesEngineParameters.DEFAULT_RULE_PRIORITY_THRESHOLD;
    }

    /**
     * Create a new {@link RulesEngineParameters}.
     *
     * @param skipOnFirstFailedRule parameter to skip next applicable rules on first failed rule.
     * @param skipOnFirstNonTriggeredRule parameter to skip next applicable rules on first non triggered rule.
     * @param priorityThreshold threshold after which rules should be skipped.
     */
    public RulesEngineParameters(final boolean skipOnFirstFailedRule, final boolean skipOnFirstNonTriggeredRule, final int priorityThreshold) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        this.skipOnFirstNonTriggeredRule = skipOnFirstNonTriggeredRule;
        this.priorityThreshold = priorityThreshold;
    }

    public int getPriorityThreshold() {
        return priorityThreshold;
    }

    public void setPriorityThreshold(final int priorityThreshold) {
        this.priorityThreshold = priorityThreshold;
    }

    public RulesEngineParameters priorityThreshold(final int priorityThreshold) {
        setPriorityThreshold(priorityThreshold);
        return this;
    }


    public void setSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
    }

    public RulesEngineParameters skipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        setSkipOnFirstAppliedRule(skipOnFirstAppliedRule);
        return this;
    }

    public boolean isSkipOnFirstNonTriggeredRule() {
        return skipOnFirstNonTriggeredRule;
    }

    public void setSkipOnFirstNonTriggeredRule(final boolean skipOnFirstNonTriggeredRule) {
        this.skipOnFirstNonTriggeredRule = skipOnFirstNonTriggeredRule;
    }

    public RulesEngineParameters skipOnFirstNonTriggeredRule(final boolean skipOnFirstNonTriggeredRule) {
        setSkipOnFirstNonTriggeredRule(skipOnFirstNonTriggeredRule);
        return this;
    }

    public boolean isSkipOnFirstFailedRule() {
        return skipOnFirstFailedRule;
    }

    public void setSkipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
    }

    public RulesEngineParameters skipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        setSkipOnFirstFailedRule(skipOnFirstFailedRule);
        return this;
    }

    @Override
    public String toString() {
        return "Engine parameters { " +
                "skipOnFirstAppliedRule = " + skipOnFirstAppliedRule +
                ", skipOnFirstNonTriggeredRule = " + skipOnFirstNonTriggeredRule +
                ", skipOnFirstFailedRule = " + skipOnFirstFailedRule +
                ", priorityThreshold = " + priorityThreshold +
                " }";
    }
}
