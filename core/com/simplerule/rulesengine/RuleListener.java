package com.simplerule.rulesengine;


public interface RuleListener {

   
    boolean beforeEvaluate(Rule rule);

    
    void beforeExecute(Rule rule);

  
    void onSuccess(Rule rule);

  
    void onFailure(Rule rule, Exception exception);

}
