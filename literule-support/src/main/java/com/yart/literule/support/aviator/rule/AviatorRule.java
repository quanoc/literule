package com.yart.literule.support.aviator.rule;

import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.rule.Action;
import com.yart.literule.core.rule.BasicRule;
import com.yart.literule.core.rule.Condition;

import java.util.ArrayList;
import java.util.List;

public class AviatorRule extends BasicRule {
    private Condition condition;
    private final List<Action> actions;


    public AviatorRule(){
        this.condition = Condition.FALSE;
        this.actions = new ArrayList<>();
    }

    public AviatorRule(List<Action> actions) {
        this.actions = actions;
    }

    public AviatorRule(String name, List<Action> actions) {
        super(name);
        this.actions = actions;
    }

    public AviatorRule(String name, String description, List<Action> actions) {
        super(name, description);
        this.actions = actions;
    }

    public AviatorRule rid(String rid) {
        this.rid = rid;
        return this;
    }

    public AviatorRule name(String name) {
        this.name = name;
        return this;
    }

    public AviatorRule description(String description) {
        this.description = description;
        return this;
    }

    public AviatorRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    public AviatorRule when(String expression) {
        this.condition = new AviatorCondition(expression);
        return this;
    }

    public AviatorRule then(Action action) {
        this.actions.add(action);
        return this;
    }

    public boolean evaluate(Facts facts) {
        return this.condition.evaluate(facts);
    }

    public void execute(Facts facts) throws Exception {
        for (Action action : this.actions) {
            action.execute(facts);
        }
    }
}
