package com.yart.literule.core.rule;

import java.util.*;


public class Rules implements Iterable<Rule> {
    private final RuleLogic DEFAULT_ruleLogic = RuleLogic.OR;
    /**
     * rules id.
     */
    private String gid;
    private String name;
    /**
     * default status: draft.
     */
    private RStatus status = RStatus.Draft;
    private Set<Rule> rules = new TreeSet<>();
    private RuleLogic ruleLogic = DEFAULT_ruleLogic;

    /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
    public Rules(Set<Rule> rules) {
        this.rules = new TreeSet<>(rules);
    }

    /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
    public Rules(Rule... rules) {
        Collections.addAll(this.rules, rules);
    }

    /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
    public Rules(Object... rules) {
        this.register(rules);
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public RuleLogic getRuleLogic() {
        return ruleLogic;
    }

    public void setRuleLogic(RuleLogic ruleLogic) {
        this.ruleLogic = ruleLogic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(RStatus status) {
        this.status = status;
    }

    public RStatus getStatus() {
        return status;
    }

    static Set<RStatus> aliveSet = new HashSet<>(
            Arrays.asList(RStatus.Test,RStatus.Stage,RStatus.Up)
    );

    public boolean isAlive() {
        return aliveSet.contains(status);
    }

    /**
     * Register one or more new rules.
     *
     * @param rules to register, must not be null
     */
    public void register(Object... rules) {
        Objects.requireNonNull(rules);
        for (Object rule : rules) {
            Objects.requireNonNull(rule);
            this.rules.add(RuleProxy.asRule(rule));
        }
    }

    /**
     * Unregister one or more rules.
     *
     * @param rules to unregister, must not be null
     */
    public void unregister(Object... rules) {
        Objects.requireNonNull(rules);
        for (Object rule : rules) {
            Objects.requireNonNull(rule);
//            this.rules.remove(RuleProxy.asRule(rule));
        }
    }

    /**
     * Unregister a rule by name.
     *
     * @param ruleName name of the rule to unregister, must not be null
     */
    public void unregister(final String ruleName) {
        Objects.requireNonNull(ruleName);
        Rule rule = findRuleByName(ruleName);
        if (rule != null) {
            unregister(rule);
        }
    }

    /**
     * Check if the rule set is empty.
     *
     * @return true if the rule set is empty, false otherwise
     */
    public boolean isEmpty() {
        return rules.isEmpty();
    }

    /**
     * Clear rules.
     */
    public void clear() {
        rules.clear();
    }

    /**
     * Return how many rules are currently registered.
     *
     * @return the number of rules currently registered
     */
    public int size() {
        return rules.size();
    }

    /**
     * Return an iterator on the rules set. It is not intended to remove rules
     * using this iterator.
     * @return an iterator on the rules set
     */
    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    private Rule findRuleByName(String ruleName) {
        return rules.stream()
                .filter(rule -> rule.getName().equalsIgnoreCase(ruleName))
                .findFirst()
                .orElse(null);
    }
}
