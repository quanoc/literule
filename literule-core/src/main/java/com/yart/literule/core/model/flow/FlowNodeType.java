package com.yart.literule.core.model.flow;


public enum FlowNodeType {
    Start("start", "开始"),
    Router("router", "路由"),
    Risk("risk", "风险"),
    Filter("filter", "过滤"),
    Action("action", "动作"),
    Mvel("mvel", "表达式"),
    RuleSet("rule_set", "规则集"),
    LoopRule("loop_rule", "循环开始"),
    LoopRuleEnd("loop_rule_end", "循环结束"),
    Agenda("advice_agenda", "决议节点");
    FlowNodeType(String name, String desc){
        this.name = name; this.desc = desc;
    }
    public String getDesc(){
        return desc;
    }
    public String getName(){
        return name;
    }
    private final String desc;
    private final String name;
}
