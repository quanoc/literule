
package com.yart.literule.core.rule;

import com.yart.literule.core.entity.Facts;

import java.util.Objects;


/**
 * Basic rule implementation class that provides common methods.
 */
public class BasicRule implements Rule {

    /**
     * Rule rid.
     */
    protected String rid;

    /**
     * Rule name.
     */
    protected String name;

    /**
     * Rule description.
     */
    protected String description;

    /**
     * Rule priority.
     */
    protected int priority;

    /**
     * Create a new {@link BasicRule}.
     */
    public BasicRule() {
        this(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
    }

    /**
     * Create a new {@link BasicRule}.
     *
     * @param name rule name
     */
    public BasicRule(final String name) {
        this(name, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
    }

    /**
     * Create a new {@link BasicRule}.
     *
     * @param name rule name
     * @param description rule description
     */
    public BasicRule(final String name, final String description) {
        this(name, description, Rule.DEFAULT_PRIORITY);
    }

    /**
     * Create a new {@link BasicRule}.
     *
     * @param name rule name
     * @param description rule description
     * @param priority rule priority
     */
    public BasicRule(final String name, final String description, final int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    public boolean evaluate(Facts facts) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void execute(Facts facts) throws Exception {
        // no op
    }

    public String getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(final int priority) {
        this.priority = priority;
    }

    /*
     * Rules are unique according to their names within a rules engine registry.
     */

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BasicRule basicRule = (BasicRule) o;

        if (priority != basicRule.priority)
            return false;
        if (!name.equals(basicRule.name))
            return false;
        return Objects.equals(description, basicRule.description);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final Rule rule) {
        if (getPriority() < rule.getPriority()) {
            return -1;
        } else if (getPriority() > rule.getPriority()) {
            return 1;
        } else {
            return getName().compareTo(rule.getName());
        }
    }

}
