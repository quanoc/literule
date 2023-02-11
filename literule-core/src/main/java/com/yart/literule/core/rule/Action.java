package com.yart.literule.core.rule;

import com.yart.literule.core.entity.Facts;

/**
 * This interface represents a rule's action.
 */
@FunctionalInterface
public interface Action {

    /**
     * Execute the action when the rule's condition evaluates to true.
     *
     * @param facts known at the time of execution of the action
     * @throws Exception when unable to execute the action
     */
    void execute(Facts facts) throws Exception;
}
