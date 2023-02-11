package com.yart.literule.core.rule;

public enum RuleLogic {
    AND, OR; // 与 或.;

    public static RuleLogic getOrDefault(String name){
        try {
            return RuleLogic.valueOf(name);
        }catch (Exception e) {
            return RuleLogic.OR;
        }
    }
}
