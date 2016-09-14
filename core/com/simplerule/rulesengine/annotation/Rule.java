package com.simplerule.rulesengine.annotation;

import com.simplerule.rulesengine.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Rule {

    /**
     * The rule name which must be unique within an rules registry.
     * @return The rule name
     */
    String name() default Constants.DEFAULT_RULE_NAME;

    /**
     * The rule description.
     * @return The rule description
     */
    String description() default  Constants.DEFAULT_RULE_DESCRIPTION;

}
