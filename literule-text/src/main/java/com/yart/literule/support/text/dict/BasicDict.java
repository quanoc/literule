package com.yart.literule.support.text.dict;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicDict {
    private static final Map<Integer, Integer> convertToNumMap = new ConcurrentHashMap<>();
    private static final Map<Integer, String> convertToEnMap = new ConcurrentHashMap<>();
    private static final  Map<String, String> wxConvertWordsMap = new ConcurrentHashMap<>();
    private static final  Map<Character, Integer> punctuation = new ConcurrentHashMap<>();
    // 特殊表情.
    private static final Map<String, Integer> symbolMap = new ConcurrentHashMap<>();
    // 其他特殊字符转换.
    // 拼音转数字
    private static final Map<String, Integer> pinyinNumMap = new ConcurrentHashMap<>();

    static {
        DictLoader.load();
        // 空格无实际意义.
        punctuation.put(' ', 5);
    }



    public static String convertEnglish(char c) {
        return convertToEnMap.get((int) c);
    }

    public static String convert(char c) {
        return convert(c, true);
    }

    public static boolean numConvertChars(char c) {
        return convertToNumMap.containsKey((int) c);
    }

    //  两位转两位[vx-微信]
    public static String convertToken(String token) {
        return wxConvertWordsMap.get(token);
    }

    public static Integer getPunctuation(char c) {
        return punctuation.get(c);
    }

    /**
     * 是否是标点符号.
     *
     * @param c 字符.
     * @return 是否是标点符号.
     */
    public static boolean isPunctuation(char c) {
        Integer value = punctuation.get(c);
        return null != value && value > 1;
    }


    /**
     * 特殊字符转换.
     * @param forWx 是否是微信场景。
     */
    public static String convert(char c, boolean forWx) {
        // 108 -> l
        if (forWx && (c == 'o' || c == 'O' || c == 108)) {
            // 微信这几个不做转换
            return null;
        }
        Integer num = convertToNumMap.get((int) c);
        if (num != null) {
            return num + "";
        }
        // 特殊字符
        return convertToEnMap.get((int) c);
    }

    public static void addConvertEn(Integer key, String value) {
        convertToEnMap.put(key, value);
    }

    public static void addConvertNum(Integer key, Integer value) {
        convertToNumMap.put(key, value);
    }

    public static void addPunctuation(Character key, Integer value) {
        punctuation.put(key, value);
    }

    public static void addSymbol(String key, Integer value) {
        symbolMap.put(key, value);
    }

    public static void addPinYinToNum(String key, Integer value) {
        pinyinNumMap.put(key, value);
    }
}
