package com.simplerule.rulesengine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.simplerule.rulesengine.core.EngineParameters;

public interface RulesEngine {

    EngineParameters getParameters();


    void registerRule(Object rule);


    void unregisterRule(Object rule);


    Set<Rule> getRules();

    List<RuleListener> getRuleListeners();

    
    void fireRules();

 
    Map<Rule, Boolean> checkRules();

  
    void clearRules();

}
