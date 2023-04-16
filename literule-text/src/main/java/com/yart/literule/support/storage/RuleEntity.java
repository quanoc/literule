package com.yart.literule.support.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 匹配的条件实体.
 */
@Data
public class RuleEntity {
    private String id;
    private String name;
    // 普通规则、aviator表达式、mvel表达式.
    private String type;
    // 表达式.  当type为表达式时 使用.
    private String expression;
//    private
    private ConditionConfig left;
    private ConditionConfig right;
    // 匹配方式: 等于、不等于、为空、不为空、
    private String matchType;
    // 变量加工函数.
    private String function;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConditionConfig{
        // DataFromEnum.
        private String from;
        // @see DataFromEnum.Const
        private String value;
        // for DataFromEnum Var. 考虑多字段情况!
        private String property;
    }

}
