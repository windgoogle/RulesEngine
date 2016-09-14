package com.simplerule.rulesengine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {

    /**
     * The order in which the action should be executed.
     * @return the order in which the action should be executed
     */
    int order() default 0;

}
