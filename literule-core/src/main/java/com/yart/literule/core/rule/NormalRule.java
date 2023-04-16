package com.yart.literule.core.rule;

import com.yart.literule.core.internal.exception.ConfigErrorException;
import com.yart.literule.core.model.basic.Facts;

import java.util.List;

/**
 * 普通规则.
 */
public class NormalRule extends BasicRule {

    private final List<Condition> conditions;
    private final List<Action> actions;
    private final Rule.ConditionLogic logic;

    public NormalRule(
            String name, String description, int priority,
            List<Condition> conditions, List<Action> actions
    ) {
        super(name, description, priority);
        this.conditions = conditions;
        this.actions = actions;
        this.logic = Rule.ConditionLogic.OR;
    }
    public NormalRule(
            String rid, String name, String description, int priority,
            List<Condition> conditions, List<Action> actions,
            Rule.ConditionLogic conditionLogic
    ) {
        super(name, description, priority);
        this.rid = rid;
        this.conditions = conditions;
        this.actions = actions;
        this.logic = conditionLogic;
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (logic.equals(Rule.ConditionLogic.AND)) {
            return conditions.stream().allMatch(condition -> condition.evaluate(facts));
        } else if (logic.equals(Rule.ConditionLogic.OR)) {
            return conditions.stream().anyMatch(condition -> condition.evaluate(facts));
        } else {
            throw new ConfigErrorException("ruleLogic not support." + logic);
        }
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Action action : actions) {
            action.execute(facts);
        }
    }

    @Override
    public RuleType type() {
        return RuleType.Normal;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
