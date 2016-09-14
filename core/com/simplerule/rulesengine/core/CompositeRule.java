package com.simplerule.rulesengine.core;

import com.simplerule.rulesengine.Constants;
import com.simplerule.rulesengine.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.simplerule.rulesengine.core.RuleProxy.asRule;

public class CompositeRule extends BasicRule {

  
    protected Set<Rule> rules;

    protected Map<Object, Rule> proxyRules;

    public CompositeRule() {
        this(Constants.DEFAULT_RULE_NAME, Constants.DEFAULT_RULE_DESCRIPTION, Constants.DEFAULT_RULE_PRIORITY);
    }

    public CompositeRule(final String name) {
        this(name, Constants.DEFAULT_RULE_DESCRIPTION, Constants.DEFAULT_RULE_PRIORITY);
    }

    public CompositeRule(final String name, final String description) {
        this(name, description, Constants.DEFAULT_RULE_PRIORITY);
    }

    public CompositeRule(final String name, final String description, final int priority) {
        super(name, description, priority);
        rules = new TreeSet<Rule>();
        proxyRules = new HashMap<Object, Rule>();
    }

 
    @Override
    public boolean evaluate() {
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if (!rule.evaluate()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public void execute() throws Exception {
        for (Rule rule : rules) {
            rule.execute();
        }
    }


    public void addRule(final Object rule) {
        Rule proxy = asRule(rule);
        rules.add(proxy);
        proxyRules.put(rule, proxy);
    }


    public void removeRule(final Object rule) {
        Rule proxy = proxyRules.get(rule);
        if (proxy != null) {
            rules.remove(proxy);
        }
    }

}
