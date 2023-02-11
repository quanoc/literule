package com.yart.literule.core.rule;

import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.internal.exception.ConfigErrorException;

import java.util.List;

class DefaultRule extends BasicRule {

    private final List<Condition> conditions;
    private final List<Action> actions;
    private final Condition.ConditionLogic conditionLogic;

    DefaultRule(
            String name, String description, int priority,
            List<Condition> conditions, List<Action> actions
    ) {
        super(name, description, priority);
        this.conditions = conditions;
        this.actions = actions;
        this.conditionLogic = Condition.ConditionLogic.OR;
    }
    DefaultRule(
            String rid, String name, String description, int priority,
            List<Condition> conditions, List<Action> actions,
            Condition.ConditionLogic conditionLogic
    ) {
        super(name, description, priority);
        this.rid = rid;
        this.conditions = conditions;
        this.actions = actions;
        this.conditionLogic = conditionLogic;
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (conditionLogic.equals(Condition.ConditionLogic.AND)) {
            return conditions.stream().allMatch(condition -> condition.evaluate(facts));
        } else if (conditionLogic.equals(Condition.ConditionLogic.OR)) {
            return conditions.stream().anyMatch(condition -> condition.evaluate(facts));
        } else {
            throw new ConfigErrorException("ruleLogic not support." + conditionLogic);
        }
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Action action : actions) {
            action.execute(facts);
        }
    }
}
