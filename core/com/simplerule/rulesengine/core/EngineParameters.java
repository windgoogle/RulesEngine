package com.simplerule.rulesengine.core;


public class EngineParameters {

  
    protected String name;
    
    
    private boolean skipOnFirstApplied;

    /**
     * Parameter to skip next applicable rules when a rule has failed.
     */
    private boolean skipOnFirstFailed;

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    private int priorityThreshold;

  

    public EngineParameters(String name, boolean skipOnFirstApplied, boolean skipOnFirstFailed, int priorityThreshold) {
        this.name = name;
        this.skipOnFirstApplied = skipOnFirstApplied;
        this.skipOnFirstFailed = skipOnFirstFailed;
        this.priorityThreshold = priorityThreshold;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriorityThreshold() {
        return priorityThreshold;
    }

    public void setPriorityThreshold(int priorityThreshold) {
        this.priorityThreshold = priorityThreshold;
    }

  

    public boolean isSkipOnFirstApplied() {
        return skipOnFirstApplied;
    }

    public void setSkipOnFirstApplied(boolean skipOnFirstApplied) {
        this.skipOnFirstApplied = skipOnFirstApplied;
    }

    public boolean isSkipOnFirstFailed() {
        return skipOnFirstFailed;
    }

    public void setSkipOnFirstFailed(boolean skipOnFirstFailed) {
        this.skipOnFirstFailed = skipOnFirstFailed;
    }
}
