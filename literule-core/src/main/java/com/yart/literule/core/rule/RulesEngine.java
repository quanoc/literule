
package com.yart.literule.core.rule;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.entity.Facts;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Rules engine interface.
 */
public interface RulesEngine {

    /**
     * Return the rules engine parameters.
     *
     * @return The rules engine parameters
     */
    RulesEngineParameters getParameters();

    /**
     * Return the list of registered rule listeners.
     *
     * @return the list of registered rule listeners
     */
    default List<RuleListener> getRuleListeners() {
        return Collections.emptyList();
    }

    /**
     * Return the list of registered rules engine listeners.
     *
     * @return the list of registered rules engine listeners
     */
    default List<RulesEngineListener> getRulesEngineListeners() {
        return Collections.emptyList();
    }

    /**
     * Fire all registered rules on given facts.
     */
    void fire(Rules rules, WorkingMemory wm);

    /**
     * Check rules without firing them.
     * @return a map with the result of evaluation of each rule
     */
    default Map<Rule, Boolean> check(Rules rules, Facts facts) {
        return Collections.emptyMap();
    }
}
