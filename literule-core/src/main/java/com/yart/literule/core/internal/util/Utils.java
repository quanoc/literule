package com.yart.literule.core.internal.util;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;

public final class Utils {
    private static boolean debug;
    private static boolean debugToFile;

    private Utils() { }
    public static boolean isDebugToFile() {
        return debugToFile;
    }
    public static boolean isDebug() {
        return debug;
    }
    public static BigDecimal toBigDecimal(Object val) {
        try{
            if (val instanceof BigDecimal) {
                return (BigDecimal) val;
            } else if (val == null) {
                throw new IllegalArgumentException("Null can not to BigDecimal.");
            } else if (val instanceof String) {
                String str = (String) val;
                if ("".equals(str.trim())) {
                    return BigDecimal.valueOf(0);
                }
                str=str.trim();
                return new BigDecimal(str);
            } else if (val instanceof Number) {
                return new BigDecimal(val.toString());
            } else if (val instanceof Character) {
                int i = (Character) val;
                return new BigDecimal(i);
            }
        }catch(Exception ex){
            throw new NumberFormatException("Can not convert "+val+" to number.");
        }

        throw new IllegalArgumentException(val.getClass().getName()+" can not to BigDecimal.");
    }

    static <A extends Annotation> A findAnnotation(
            final Class<A> targetAnnotation,
            final Class<?> annotatedType
    ) {
        A foundAnnotation = annotatedType.getAnnotation(targetAnnotation);
        if (foundAnnotation == null) {
            for (Annotation annotation : annotatedType.getAnnotations()) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(targetAnnotation)) {
                    foundAnnotation = annotationType.getAnnotation(targetAnnotation);
                    break;
                }
            }
        }
        return foundAnnotation;
    }

    static boolean isAnnotationPresent(
            final Class<? extends Annotation> targetAnnotation,
            final Class<?> annotatedType
    ) {
        return findAnnotation(targetAnnotation, annotatedType) != null;
    }

}