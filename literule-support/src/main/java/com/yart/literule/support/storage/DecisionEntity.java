package com.yart.literule.support.storage;

import lombok.Data;

import java.util.List;

@Data
public class DecisionEntity {
    private String id;
    private String name;
    private String type;
    private String logic;
    private List<RuleEntity> ruleList;
    private Integer status;
    private Integer version;
}
