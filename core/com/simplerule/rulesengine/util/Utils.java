package com.simplerule.rulesengine.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public final class Utils {

	public static final Logger logger = Logger.getLogger(Utils.class);
    
	private Utils() {
    
	}

    
    @SuppressWarnings("rawtypes")
	public static List<Class> getInterfaces(final Object rule) {
        List<Class> interfaces = new ArrayList<Class>();
        Class clazz = rule.getClass();
        while (clazz.getSuperclass() != null) {
            interfaces.addAll(asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A findAnnotation(
            final Class<? extends Annotation> targetAnnotation, @SuppressWarnings("rawtypes") final Class annotatedType) {

        checkNotNull(targetAnnotation, "targetAnnotation");
        checkNotNull(annotatedType, "annotatedType");

        Annotation foundAnnotation = annotatedType.getAnnotation(targetAnnotation);
        if (foundAnnotation == null) {
            for (Annotation annotation : annotatedType.getAnnotations()) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(targetAnnotation)) {
                    foundAnnotation = annotationType.getAnnotation(targetAnnotation);
                    break;
                }
            }
        }
        return (A) foundAnnotation;
    }

    public static boolean isAnnotationPresent(
            final Class<? extends Annotation> targetAnnotation, @SuppressWarnings("rawtypes") final Class annotatedType) {

        return findAnnotation(targetAnnotation, annotatedType) != null;
    }

    public static void checkNotNull(final Object argument, final String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException(format("The %s must not be null", argumentName));
        }
    }

}
