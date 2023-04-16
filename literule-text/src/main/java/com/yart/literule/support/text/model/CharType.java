package com.yart.literule.support.text.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字符类型(转换后的字符).
 */
@Getter
@AllArgsConstructor
public enum CharType {
    /**
     * type.
     */
    Cn(0),
    En(9),
    Ja(10),
    Punctuation(5),
    Num/*[0-9]*/(2),
    AnT(5),
    CvChine(0)/*转换的中文*/,
    CvPinYin(0),
    Line/* [-_0-9a-zA-Z]*/(1),
    CvEn(1),
    CvNum(2),
    ChinesNum(2),
    SpecialA(3),
    Convert(8),
    CvNew/*转换产生的新字符*/(4),
    Special(7) /* emoji */;

    private final int bType; // 大类
}
