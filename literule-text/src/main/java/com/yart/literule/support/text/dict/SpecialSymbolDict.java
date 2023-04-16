package com.yart.literule.support.text.dict;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpecialSymbolDict.
 */
@Slf4j
public class SpecialSymbolDict {
    static Map<String, Integer> symbolMap = new ConcurrentHashMap<>();
    static Map<Character, Integer> punctuationMap = new ConcurrentHashMap<>();

    public static boolean isSpecialSymbol4Wx(String s) {
        Integer value = symbolMap.get(s);
        return null != value && value > 1;
    }

    /**
     * 是否是标点符号.
     *
     * @param c 字符.
     * @return 是否是标点符号.
     */
    public static boolean isPunctuation(char c) {
        Integer value = punctuationMap.get(c);
        return null != value && value > 1;
    }

    /**
     * 是否是标点符号或中文字符.
     *
     * @param c 字符.
     * @return 是否是标点符号或中文字符.
     */
    public static boolean isPunctuationOrCh(char c) {
        if (isCh(c)) {
            return true;
        }
        Integer value = punctuationMap.get(c);
        return null != value && value > 1;
    }

    public static boolean isCh(char c) {
        return c >= 19968 && c <= 40869;
    }
}
