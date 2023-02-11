package com.yart.literule.regex.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拼音处理
 * 同音字处理,多音字处理
 * 1. 拼音转数字, 拼音转字符
 */
public class PinYinCharDict {
    static Map<String, Integer> pinyinCharMap = new ConcurrentHashMap<>();
    static Map<String, Integer> pinyinNumMap = new ConcurrentHashMap<>();
    static Map<Integer, Integer> multiToneConverter = new ConcurrentHashMap<>();
    // 2、3个字符的 坏拼音单词.
    static Map<String, String> blackPinYinWords = new ConcurrentHashMap<>();


    /**
     * 是否是有问题的拼音.
     *
     * @param pinyin 2~4个字符的拼音.
     */
    public static String blackByPinYin(String pinyin) {
        return blackPinYinWords.get(pinyin);
    }

    public static Integer convertByPinYin(char c) {
        String[] pinYin = PinYinConverter.convertMulti(c);
        if (pinYin != null) {
            if (pinYin.length == 1) {
                return pinyinNumMap.get(pinYin[0]);
            } else if (pinYin.length > 1) {
                Integer num = multiToneConverter.get((int) c);
                if (num == null) {
                    return pinyinNumMap.get(pinYin[0]);
                } else {
                    return num;
                }
            }
        }
        return null;
    }

    public static Integer numByPinYin(char c, String[] pinYin) {
        if (pinYin != null) {
            if (pinYin.length == 1) {
                return pinyinNumMap.get(pinYin[0]);
            } else if (pinYin.length > 1) {
                Integer num = multiToneConverter.get((int) c);
                if (num == null) {
                    return pinyinNumMap.get(pinYin[0]);
                } else {
                    return num;
                }
            }
        }
        return null;
    }

    public static void addPinYinToNum(String key, Integer value) {
        pinyinNumMap.put(key, value);
    }
}
