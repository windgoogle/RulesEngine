package com.simplerule.rulesengine.core;

import com.simplerule.rulesengine.Rule;
import com.simplerule.rulesengine.RuleListener;
import com.simplerule.rulesengine.RulesEngine;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.simplerule.rulesengine.core.RuleProxy.asRule;
import static java.lang.String.format;


class DefaultRulesEngine implements RulesEngine {

    private static final Logger LOGGER = Logger.getLogger(RulesEngine.class.getName());

    
    protected Set<Rule> rules;

   
    protected EngineParameters parameters;


    private List<RuleListener> ruleListeners;

    DefaultRulesEngine(final EngineParameters parameters, final List<RuleListener> ruleListeners) {
        this.parameters = parameters;
        this.rules = new TreeSet<Rule>();
        this.ruleListeners = ruleListeners;
       
    }

    @Override
    public EngineParameters getParameters() {
        return parameters;
    }

    @Override
    public void registerRule(final Object rule) {
        rules.add(asRule(rule));
    }

    @Override
    public void unregisterRule(final Object rule) {
        rules.remove(asRule(rule));
    }

    @Override
    public Set<Rule> getRules() {
        return rules;
    }

    @Override
    public List<RuleListener> getRuleListeners() {
        return ruleListeners;
    }

    @Override
    public void clearRules() {
        rules.clear();
        LOGGER.info("Rules cleared.");
    }

    @Override
    public void fireRules() {

        if (rules.isEmpty()) {
            LOGGER.warning("No rules registered! Nothing to apply");
            return;
        }

        logEngineParameters();
        sortRules();
        logRegisteredRules();
        applyRules();

    }

    @Override
    public Map<Rule, Boolean> checkRules() {
        LOGGER.info("Checking rules");
        sortRules();
        Map<Rule, Boolean> result = new HashMap<Rule, Boolean>();
        for (Rule rule : rules) {
            if (shouldBeEvaluated(rule)) {
                result.put(rule, rule.evaluate());
            }
        }
        return result;
    }

    private void sortRules() {
        rules = new TreeSet<Rule>(rules);
    }

    private void applyRules() {

        LOGGER.info("Rules evaluation started");
        for (Rule rule : rules) {

            final String name = rule.getName();
            final int priority = rule.getPriority();

            if (priority > parameters.getPriorityThreshold()) {
                LOGGER.log(Level.INFO,
                        "Rule priority threshold ({0}) exceeded at rule ''{1}'' with priority={2}, next rules will be skipped",
                        new Object[]{parameters.getPriorityThreshold(), name, priority});
                break;
            }

            if (!shouldBeEvaluated(rule)) {
                LOGGER.log(Level.INFO, "Rule ''{0}'' has been skipped before being evaluated", name);
                continue;
            }
            if (rule.evaluate()) {
                LOGGER.log(Level.INFO, "Rule ''{0}'' triggered", name);
                try {
                    triggerListenersBeforeExecute(rule);
                    rule.execute();
                    LOGGER.log(Level.INFO, "Rule ''{0}'' performed successfully", name);
                    triggerListenersOnSuccess(rule);

                    if (parameters.isSkipOnFirstApplied()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstApplied is set");
                        break;
                    }
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, String.format("Rule '%s' performed with error", name), exception);
                    triggerListenersOnFailure(rule, exception);
                    if (parameters.isSkipOnFirstFailed()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstFailed is set");
                        break;
                    }
                }
            } else {
                LOGGER.log(Level.INFO, "Rule ''{0}'' has been evaluated to false, it has not been executed", name);
            }

        }

    }

    private void triggerListenersOnFailure(final Rule rule, final Exception exception) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onFailure(rule, exception);
        }
    }

    private void triggerListenersOnSuccess(final Rule rule) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onSuccess(rule);
        }
    }

    private void triggerListenersBeforeExecute(final Rule rule) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.beforeExecute(rule);
        }
    }

    private boolean triggerListenersBeforeEvaluate(Rule rule) {
        for (RuleListener ruleListener : ruleListeners) {
            if (!ruleListener.beforeEvaluate(rule)) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldBeEvaluated(Rule rule) {
        return triggerListenersBeforeEvaluate(rule);
    }

    private void logEngineParameters() {
        LOGGER.log(Level.INFO, "Engine name: {0}", parameters.getName());
        LOGGER.log(Level.INFO, "Rule priority threshold: {0}", parameters.getPriorityThreshold());
        LOGGER.log(Level.INFO, "Skip on first applied rule: {0}", parameters.isSkipOnFirstApplied());
        LOGGER.log(Level.INFO, "Skip on first failed rule: {0}", parameters.isSkipOnFirstFailed());
    }

    private void logRegisteredRules() {
        LOGGER.log(Level.INFO, "Registered rules:");
        for (Rule rule : rules) {
            LOGGER.log(Level.INFO, format("Rule { name = '%s', description = '%s', priority = '%s'}",
                    rule.getName(), rule.getDescription(), rule.getPriority()));
        }
    }

    @Override
    public String toString() {
        return parameters.getName();
    }

}
