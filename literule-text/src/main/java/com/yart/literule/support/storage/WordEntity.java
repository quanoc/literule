package com.yart.literule.support.storage;

import lombok.Data;

/**
 * 关键词实体.用于关键词匹配
 */
@Data
public class WordEntity {
    private String id;
    private String word;
    private String type;
    private Long uTime;
    private Long cTime;
}
