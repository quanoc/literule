package com.yart.literule.support.text.util;

import com.yart.literule.support.text.dict.BasicDict;

public class CharUtils {

    /**
     * 英文字母字母.
     */
    public static boolean a_zA_Z(char c) {
        return (c <= 'z' && c >= 'a')
                || (c <= 'Z' && c >= 'A');
    }

    /**
     * 日语字母.
     */
    public static boolean isJapanese(char c) {
        return (c > '\u0800' && c < '\u4e00');
    }

    /**
     * 数字.
     */
    public static boolean isNum0_9(char c) {
        return c <= 57 && c >= 48;
    }

    /**
     * 汉字、英文字母、数字、@、(标点？)
     */
    public static boolean normalFilter(char c) {
        // a-z0-9\u4e00-\u9fa5
        return (c <= 'z' && c >= 'a')
                || (c <= 'Z' && c >= 'A')
                || (c <= 57 && c >= 48)
                || (c >= 19968 && c <= 40869)
                || BasicDict.numConvertChars(c)
                || c == '@';
    }

    public static String well(int size) {
        StringBuilder str = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            str.append('#');
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println(well(10));
    }
}
