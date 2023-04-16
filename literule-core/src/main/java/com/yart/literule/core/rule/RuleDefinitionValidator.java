
package com.yart.literule.core.rule;

import com.yart.literule.core.model.basic.Facts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


/**
 * This component validates that an annotated rule object is well defined.
 */
class RuleDefinitionValidator {

    void validateRuleDefinition(final Object rule) {
        checkRuleClass(rule);
        checkConditionMethod(rule);
        checkActionMethods(rule);
        checkPriorityMethod(rule);
    }

    private void checkRuleClass(final Object rule) {
        if (!isRuleClassWellDefined(rule)) {
            throw new IllegalArgumentException(format("Rule '%s' is not annotated with '%s'", rule.getClass().getName(), Rule.class.getName()));
        }
    }

    private void checkConditionMethod(final Object rule) {
//        List<Method> conditionMethods = getMethodsAnnotatedWith(Condition.class, rule);
//        if (conditionMethods.isEmpty()) {
//            throw new IllegalArgumentException(format("Rule '%s' must have a public method annotated with '%s'", rule.getClass().getName(), Condition.class.getName()));
//        }
//
//        if (conditionMethods.size() > 1) {
//            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with '%s'", rule.getClass().getName(), Condition.class.getName()));
//        }
//
//        Method conditionMethod = conditionMethods.get(0);
//
//        if (!isConditionMethodWellDefined(conditionMethod)) {
//            throw new IllegalArgumentException(format("Condition method '%s' defined in rule '%s' must be public, must return boolean type and may have parameters annotated with @Fact (and/or exactly one parameter of type Facts or one of its sub-types).", conditionMethod, rule.getClass().getName()));
//        }
    }

    private void checkActionMethods(final Object rule) {
//        List<Method> actionMethods = getMethodsAnnotatedWith(Action.class, rule);
//        if (actionMethods.isEmpty()) {
//            throw new IllegalArgumentException(format("Rule '%s' must have at least one public method annotated with '%s'", rule.getClass().getName(), Action.class.getName()));
//        }
//
//        for (Method actionMethod : actionMethods) {
//            if (!isActionMethodWellDefined(actionMethod)) {
//                throw new IllegalArgumentException(format("Action method '%s' defined in rule '%s' must be public, must return void type and may have parameters annotated with @Fact (and/or exactly one parameter of type Facts or one of its sub-types).", actionMethod, rule.getClass().getName()));
//            }
//        }
    }

    private void checkPriorityMethod(final Object rule) {

//        List<Method> priorityMethods = getMethodsAnnotatedWith(Priority.class, rule);
//
//        if (priorityMethods.isEmpty()) {
//            return;
//        }
//
//        if (priorityMethods.size() > 1) {
//            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with '%s'", rule.getClass().getName(), Priority.class.getName()));
//        }
//
//        Method priorityMethod = priorityMethods.get(0);
//
//        if (!isPriorityMethodWellDefined(priorityMethod)) {
//            throw new IllegalArgumentException(format("Priority method '%s' defined in rule '%s' must be public, have no parameters and return integer type.", priorityMethod, rule.getClass().getName()));
//        }
    }

    private boolean isRuleClassWellDefined(final Object rule) {
//        return Utils.isAnnotationPresent(Rule.class, rule.getClass());
        return false;
    }

    private boolean isConditionMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Boolean.TYPE)
                && validParameters(method);
    }

    private boolean validParameters(final Method method) {
        int notAnnotatedParameterCount = 0;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            if (annotations.length == 0) {
                notAnnotatedParameterCount += 1;
            } else {
                //Annotation types has to be Fact
                for (Annotation annotation : annotations) {
//                    if (!annotation.annotationType().equals(Fact.class)) {
//                        return false;
//                    }
                }
            }
        }
        if (notAnnotatedParameterCount > 1) {
            return false;
        }
        if (notAnnotatedParameterCount == 1) {
            Parameter notAnnotatedParameter = getNotAnnotatedParameter(method);
            if (notAnnotatedParameter != null) {
                return Facts.class.isAssignableFrom(notAnnotatedParameter.getType());
            }
        }
        return true;
    }

    private Parameter getNotAnnotatedParameter(Method method) {
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.getAnnotations().length == 0) {
                return parameter;
            }
        }
        return null;
    }

    private boolean isActionMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Void.TYPE)
                && validParameters(method);
    }

    private boolean isPriorityMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Integer.TYPE)
                && method.getParameterTypes().length == 0;
    }

    private List<Method> getMethodsAnnotatedWith(final Class<? extends Annotation> annotation, final Object rule) {
        Method[] methods = getMethods(rule);
        List<Method> annotatedMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private Method[] getMethods(final Object rule) {
        return rule.getClass().getMethods();
    }

}
