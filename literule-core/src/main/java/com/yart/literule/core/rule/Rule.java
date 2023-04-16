package com.yart.literule.core.rule;

import com.yart.literule.core.model.basic.Facts;

/**
 * Abstraction for a rule that can be fired by a rules engine.
 *
 * Rules are registered in a namespace of rule of type {@link Rules}
 * in which they must have a <strong>unique</strong> name.
 */
public interface Rule extends Comparable<Rule> {

    /**
     * Default rule name.
     */
    String DEFAULT_RID = "rid";
    /**
     * Default rule name.
     */
    String DEFAULT_NAME = "rule";
    /**
     * Default rule description.
     */
    String DEFAULT_DESCRIPTION = "description";

    /**
     * Default rule priority.
     */
    int DEFAULT_PRIORITY = Integer.MAX_VALUE - 1;

    /**
     * Getter for rule rid.
     * @return the rule rid
     */
    default String getRid() {
        return DEFAULT_RID;
    }


    /**
     * Getter for rule name.
     * @return the rule name
     */
    default String getName() {
        return DEFAULT_NAME;
    }

    RuleType type();

    /**
     * Getter for rule description.
     * @return rule description
     */
    default String getDescription() {
        return DEFAULT_DESCRIPTION;
    }

    /**
     * Getter for rule priority.
     * @return rule priority
     */
    default int getPriority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * 封装规则的条件.
     *
     * @return true 如果根据提供的事实可以应用规则，则为true, 否则为false.
     */
    boolean evaluate(Facts facts);

    /**
     * 封装规则的操作.
     * @throws Exception 如果执行操作期间发生错误, 则抛出异常.
     */
    void execute(Facts facts) throws Exception;

    enum RuleType{
        None,Normal,Regex,Script
    }

    enum ConditionLogic {
        AND, OR
    }

}