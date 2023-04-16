package com.yart.literule.regex.rule;

import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.rule.Action;
import com.yart.literule.core.rule.BasicRule;
import com.yart.literule.core.rule.Condition;

import java.util.ArrayList;
import java.util.List;

public class RegexRule extends BasicRule {
    private Condition condition;
    private final List<Action> actions;


    public RegexRule(){
        this.condition = Condition.FALSE;
        this.actions = new ArrayList<>();
    }

    public RegexRule(List<Action> actions) {
        this.actions = actions;
    }

    public RegexRule(String name, List<Action> actions) {
        super(name);
        this.actions = actions;
    }

    public RegexRule(String name, String description, List<Action> actions) {
        super(name, description);
        this.actions = actions;
    }

    public RegexRule rid(String rid) {
        this.rid = rid;
        return this;
    }

    public RegexRule name(String name) {
        this.name = name;
        return this;
    }

    public RegexRule description(String description) {
        this.description = description;
        return this;
    }

    public RegexRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    public RegexRule when(String leftKey, String regex) {
        this.condition = new RegexCondition(leftKey, regex);
        return this;
    }

    public RegexRule when(String leftKey, String regex, String regexType) {
        if ("java".equals(regexType)) {
            this.condition = new JavaRegexCondition(leftKey, regex);
        } else {
            this.condition = new RegexCondition(leftKey, regex);
        }
        return this;
    }

    public RegexRule then(Action action) {
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
