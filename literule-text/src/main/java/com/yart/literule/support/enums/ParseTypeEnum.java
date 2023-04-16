package com.yart.literule.support.enums;

/**
 * @author zhangquanquan 2022.08.03
 */
public enum ParseTypeEnum {
    YML("yml"), JSON("json"),
    MONGODB("mongodb");
    private final String name;
    ParseTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
