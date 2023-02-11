package com.yart.literule.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Builder to create {@link Rule} instances.
 *
 * @author zhangquanquan.
 */
public class RuleBuilder {

    private String rid = Rule.DEFAULT_RID;
    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private int priority = Rule.DEFAULT_PRIORITY;
    private Condition.ConditionLogic ruleLogic;

    private final List<Condition> conditions = new ArrayList<>();
    private final List<Action> actions = new ArrayList<>();

    /**
     * Set rule rid.
     *
     * @param rid of the rule
     * @return the builder instance
     */
    public RuleBuilder rid(String rid) {
        this.rid = rid;
        return this;
    }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return the builder instance
     */
    public RuleBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return the builder instance
     */
    public RuleBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return the builder instance
     */
    public RuleBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Set rule condition.
     *
     * @param condition of the rule
     * @return the builder instance
     */
    public RuleBuilder when(Condition condition) {
        this.conditions.add(condition);
        return this;
    }

    public RuleBuilder logic(Condition.ConditionLogic logic) {
        this.ruleLogic = logic;
        return this;
    }


    /**
     * Add an action to the rule.
     *
     * @param action to add
     * @return the builder instance
     */
    public RuleBuilder then(Action action) {
        this.actions.add(action);
        return this;
    }

    /**
     * Create a new {@link Rule}.
     *
     * @return a new rule instance
     */
    public Rule build() {
        return new DefaultRule(rid, name, description, priority,
                conditions, actions, Objects.nonNull(ruleLogic) ? ruleLogic : Condition.ConditionLogic.OR);
    }
}
