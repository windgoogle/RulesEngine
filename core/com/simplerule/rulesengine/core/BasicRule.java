package com.simplerule.rulesengine.core;

import com.simplerule.rulesengine.Constants;
import com.simplerule.rulesengine.Rule;


/**
 * Basic rule implementation class that provides common methods.
 */
public class BasicRule implements Rule, Comparable<Rule> {

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

    public BasicRule() {
        this(Constants.DEFAULT_RULE_NAME, Constants.DEFAULT_RULE_DESCRIPTION, Constants.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name) {
        this(name, Constants.DEFAULT_RULE_DESCRIPTION, Constants.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name, final String description) {
        this(name, description, Constants.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name, final String description, final int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    public boolean evaluate() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void execute() throws Exception {
        //no op
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicRule basicRule = (BasicRule) o;

        if (priority != basicRule.priority) return false;
        if (!name.equals(basicRule.name)) return false;
        return !(description != null ? !description.equals(basicRule.description) : basicRule.description != null);

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
        if (priority < rule.getPriority()) {
            return -1;
        } else if (priority > rule.getPriority()) {
            return 1;
        } else {
            return name.compareTo(rule.getName());
        }
    }

}
