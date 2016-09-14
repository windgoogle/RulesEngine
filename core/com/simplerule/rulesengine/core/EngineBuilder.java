package com.simplerule.rulesengine.core;

import com.simplerule.rulesengine.Constants;
import com.simplerule.rulesengine.RuleListener;
import com.simplerule.rulesengine.RulesEngine;

import java.util.ArrayList;
import java.util.List;


public class EngineBuilder {

    private EngineParameters parameters;

    private List<RuleListener> ruleListeners;

    public static EngineBuilder aNewRulesEngine() {
        return new EngineBuilder();
    }

    private EngineBuilder() {
        parameters = new EngineParameters(Constants.DEFAULT_ENGINE_NAME, false, false, Constants.DEFAULT_RULE_PRIORITY_THRESHOLD);
        ruleListeners = new ArrayList<RuleListener>();
    }

    public EngineBuilder named(final String name) {
        parameters.setName(name);
        return this;
    }

    public EngineBuilder withSkipOnFirstApplied(final boolean skipOnFirstApplied) {
        parameters.setSkipOnFirstApplied(skipOnFirstApplied);
        return this;
    }

    public EngineBuilder withSkipOnFirstFailed(final boolean skipOnFirstFailed) {
        parameters.setSkipOnFirstFailed(skipOnFirstFailed);
        return this;
    }

    public EngineBuilder withRulePriorityThreshold(final int priorityThreshold) {
        parameters.setPriorityThreshold(priorityThreshold);
        return this;
    }

    public EngineBuilder withRuleListener(final RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

  
    public RulesEngine build() {
        return new DefaultRulesEngine(parameters, ruleListeners);
    }

}
