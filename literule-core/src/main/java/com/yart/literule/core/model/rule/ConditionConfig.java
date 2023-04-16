package com.yart.literule.core.model.rule;

import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.rule.Condition;

/**
 * 普通规则<>com.yart.literule.core.rule.NormalRule</>的条件配置.
 * TODO 完善解析逻辑.
 *
 * @author zhangquanquan
 */
public class ConditionConfig implements Condition {

    private String id;
    /**
     * 条件表达式.
     */
    private String script;
    /**
     * 左右参数.
     */
    private ParameterConfig left;
    private ParameterConfig right;
    /**
     * 条件标签：risk、filter.
     */
    private String tag;
    /**
     * 断言类型.
     */
    private String op;

    @Override
    public boolean evaluate(Facts facts) {
        return false;
    }

    public static class ParameterConfig{
        /**
         * 参数类型: var, const, enum, function, data等.
         */
        private String type;
        /**
         * 加工方式，默认无(none).
         */
        private String function;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public ParameterConfig getLeft() {
        return left;
    }

    public void setLeft(ParameterConfig left) {
        this.left = left;
    }

    public ParameterConfig getRight() {
        return right;
    }

    public void setRight(ParameterConfig right) {
        this.right = right;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
