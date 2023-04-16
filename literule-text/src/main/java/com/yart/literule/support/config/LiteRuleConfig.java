package com.yart.literule.support.config;

/**
 * 配置实体类.
 *
 * @author zhangquanquan 2022.08.03.
 */
public class LiteRuleConfig {
    private String ruleSource = "mongodb";

    public String getRuleSource() {
        return ruleSource;
    }

    public void setRuleSource(String ruleSource) {
        this.ruleSource = ruleSource;
    }


}
