package com.simplerule.rulesengine;


public interface Rule {

 
    String getName();

    
    String getDescription();

  
    int getPriority();

    boolean evaluate();

   
    void execute() throws Exception;

}
