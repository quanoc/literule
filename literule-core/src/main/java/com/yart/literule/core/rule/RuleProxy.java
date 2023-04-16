package com.yart.literule.core.rule;

import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.internal.exception.NoSuchFactException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Main class to create rule proxies from annotated objects.
 */
public class RuleProxy implements InvocationHandler {

    private final Object target;
    private String name;
    private String description;
    private Integer priority;
    private Method[] methods;
    private Method conditionMethod;
//    private Set<ActionMethodOrderBean> actionMethods;
    private Method compareToMethod;
    private Method toStringMethod;
//    private org.jeasy.rules.annotation.Rule annotation;

    private static final RuleDefinitionValidator ruleDefinitionValidator = new RuleDefinitionValidator();
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleProxy.class);

    /**
     * Makes the rule object implement the {@link Rule} interface.
     *
     * @param rule the annotated rule object.
     * @return a proxy that implements the {@link Rule} interface.
     */
    public static Rule asRule(final Object rule) {
        Rule result;
        if (rule instanceof Rule) {
            result = (Rule) rule;
        } else {
            ruleDefinitionValidator.validateRuleDefinition(rule);
            result = (Rule) Proxy.newProxyInstance(
                    Rule.class.getClassLoader(),
                    new Class[]{Rule.class, Comparable.class},
                    new RuleProxy(rule));
        }
        return result;
    }

    private RuleProxy(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        String methodName = method.getName();
        switch (methodName) {
            case "getName":
                return getRuleName();
            case "getDescription":
                return getRuleDescription();
            case "getPriority":
                return getRulePriority();
            case "compareTo":
                return compareToMethod(args);
            case "evaluate":
                return evaluateMethod(args);
            case "execute":
                return executeMethod(args);
            case "equals":
                return equalsMethod(args);
            case "hashCode":
                return hashCodeMethod();
            case "toString":
                return toStringMethod();
            default:
                return null;
        }
    }

    private Object evaluateMethod(final Object[] args) throws IllegalAccessException, InvocationTargetException {
        Facts facts = (Facts) args[0];
        Method conditionMethod = getConditionMethod();
        try {
            List<Object> actualParameters = getActualParameters(conditionMethod, facts);
            return conditionMethod.invoke(target, actualParameters.toArray()); // validated upfront
        } catch (NoSuchFactException e) {
            LOGGER.warn("Rule '{}' has been evaluated to false due to a declared but missing fact '{}' in {}",
                    getTargetClass().getName(), e.getMissingFact(), facts);
            return false;
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Types of injected facts in method '{}' in rule '{}' do not match parameters types",
                    conditionMethod.getName(), getTargetClass().getName(), e);
            return false;
        }
    }

    private Object executeMethod(final Object[] args) throws IllegalAccessException, InvocationTargetException {
        Facts facts = (Facts) args[0];
//        for (ActionMethodOrderBean actionMethodBean : getActionMethodBeans()) {
//            Method actionMethod = actionMethodBean.getMethod();
//            List<Object> actualParameters = getActualParameters(actionMethod, facts);
//            actionMethod.invoke(target, actualParameters.toArray());
//        }
        return null;
    }

    private Object compareToMethod(final Object[] args) throws Exception {
        Method compareToMethod = getCompareToMethod();
        Object otherRule = args[0]; // validated upfront
        if (compareToMethod != null && Proxy.isProxyClass(otherRule.getClass())) {
            if (compareToMethod.getParameters().length != 1) {
                throw new IllegalArgumentException("compareTo method must have a single argument");
            }
            RuleProxy ruleProxy = (RuleProxy) Proxy.getInvocationHandler(otherRule);
            return compareToMethod.invoke(target, ruleProxy.getTarget());
        } else {
            return compareTo((Rule) otherRule);
        }
    }

    private List<Object> getActualParameters(Method method, Facts facts) {
        List<Object> actualParameters = new ArrayList<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            if (annotations.length == 1) {
//                String factName = ((Fact) (annotations[0])).value(); //validated upfront.
//                Object fact = facts.get(factName);
//                if (fact == null && !facts.asMap().containsKey(factName)) {
//                    throw new NoSuchFactException(format("No fact named '%s' found in known facts: %n%s", factName, facts), factName);
//                }
//                actualParameters.add(fact);
            } else {
                actualParameters.add(facts); //validated upfront, there may be only one parameter not annotated and which is of type Facts.class
            }
        }
        return actualParameters;
    }

    private boolean equalsMethod(final Object[] args) throws Exception {
        if (!(args[0] instanceof Rule)) {
            return false;
        }
        Rule otherRule = (Rule) args[0];
        int otherPriority = otherRule.getPriority();
        int priority = getRulePriority();
        if (priority != otherPriority) {
            return false;
        }
        String otherName = otherRule.getName();
        String name = getRuleName();
        if (!name.equals(otherName)) {
            return false;
        }
        String otherDescription = otherRule.getDescription();
        String description =  getRuleDescription();
        return Objects.equals(description, otherDescription);
    }

    private int hashCodeMethod() throws Exception {
        int result   = getRuleName().hashCode();
        int priority = getRulePriority();
        String description = getRuleDescription();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    private Method getToStringMethod() {
        if (this.toStringMethod == null) {
            Method[] methods = getMethods();
            for (Method method : methods) {
                if ("toString".equals(method.getName())) {
                    this.toStringMethod = method;
                    return this.toStringMethod;
                }
            }
        }
        return this.toStringMethod;
    }

    private String toStringMethod() throws Exception {
        Method toStringMethod = getToStringMethod();
        if (toStringMethod != null) {
            return (String) toStringMethod.invoke(target);
        } else {
            return getRuleName();
        }
    }

    private int compareTo(final Rule otherRule) throws Exception {
        int otherPriority = otherRule.getPriority();
        int priority = getRulePriority();
        if (priority < otherPriority) {
            return -1;
        } else if (priority > otherPriority) {
            return 1;
        } else {
            String otherName = otherRule.getName();
            String name = getRuleName();
            return name.compareTo(otherName);
        }
    }

    private int getRulePriority() throws Exception {
        if (this.priority == null) {
            int priority = Rule.DEFAULT_PRIORITY;

//            Rule rule = getRuleAnnotation();
//            if (rule.priority() != Rule.DEFAULT_PRIORITY) {
//                priority = rule.priority();
//            }

            Method[] methods = getMethods();
//            for (Method method : methods) {
//                if (method.isAnnotationPresent(Priority.class)) {
//                    priority = (int) method.invoke(target);
//                    break;
//                }
//            }
            this.priority = priority;
        }
        return this.priority;
    }

    private Method getConditionMethod() {
        if (this.conditionMethod == null) {
            Method[] methods = getMethods();
            for (Method method : methods) {
//                if (method.isAnnotationPresent(Condition.class)) {
//                    this.conditionMethod = method;
//                    return this.conditionMethod;
//                }
            }
        }
        return this.conditionMethod;
    }

//    private Set<ActionMethodOrderBean> getActionMethodBeans() {
//        if (this.actionMethods == null) {
//            this.actionMethods = new TreeSet<>();
//            Method[] methods = getMethods();
//            for (Method method : methods) {
//                if (method.isAnnotationPresent(Action.class)) {
//                    Action actionAnnotation = method.getAnnotation(Action.class);
//                    int order = actionAnnotation.order();
//                    this.actionMethods.add(new ActionMethodOrderBean(method, order));
//                }
//            }
//        }
//        return this.actionMethods;
//    }

    private Method getCompareToMethod() {
        if (this.compareToMethod == null) {
            Method[] methods = getMethods();
            for (Method method : methods) {
                if (method.getName().equals("compareTo")) {
                    this.compareToMethod = method;
                    return this.compareToMethod;
                }
            }
        }
        return this.compareToMethod;
    }

    private Method[] getMethods() {
        if (this.methods == null) {
            this.methods = getTargetClass().getMethods();
        }
        return this.methods;
    }

//    private org.jeasy.rules.annotation.Rule getRuleAnnotation() {
////        if (this.annotation == null) {
////            this.annotation = Utils.findAnnotation(org.jeasy.rules.annotation.Rule.class, getTargetClass());
////        }
//        return this.annotation;
//    }

    private String getRuleName() {
//        if (this.name == null) {
//            org.jeasy.rules.annotation.Rule rule = getRuleAnnotation();
//            this.name = rule.name().equals(Rule.DEFAULT_NAME) ? getTargetClass().getSimpleName() : rule.name();
//        }
        return this.name;
    }

    private String getRuleDescription() {
        if (this.description == null) {
            // Default description = "when " + conditionMethodName + " then " + comma separated actionMethodsNames
            StringBuilder description = new StringBuilder();
            appendConditionMethodName(description);
            appendActionMethodsNames(description);
//            org.jeasy.rules.annotation.Rule rule = getRuleAnnotation();
//            this.description = rule.description().equals(Rule.DEFAULT_DESCRIPTION) ? description.toString() : rule.description();
        }
        return this.description;
    }

    private void appendConditionMethodName(StringBuilder description) {
        Method method = getConditionMethod();
        if (method != null) {
            description.append("when ");
            description.append(method.getName());
            description.append(" then ");
        }
    }

    private void appendActionMethodsNames(StringBuilder description) {
//        Iterator<ActionMethodOrderBean> iterator = getActionMethodBeans().iterator();
//        while (iterator.hasNext()) {
//            description.append(iterator.next().getMethod().getName());
//            if (iterator.hasNext()) {
//                description.append(",");
//            }
//        }
    }

    public Object getTarget() {
        return target;
    }

    private Class<?> getTargetClass() {
        return target.getClass();
    }

}
