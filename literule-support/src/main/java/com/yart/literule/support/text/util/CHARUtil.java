package com.yart.literule.support.text.util;

import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.regex.util.PinYinCharDict;
import com.yart.literule.support.text.model.CHAR;
import com.yart.literule.support.text.model.CharType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * CHARUtil.
 *
 * @author zhangquanquan 2020.10.19
 */
public class CHARUtil {
    public static CHAR last(List<CHAR> charList, Predicate<CHAR> predicate) {
        for (int i = charList.size() - 1; i >= 0; i--) {
            if (predicate.test(charList.get(i))) {
                return charList.get(i);
            }
        }
        return null;
    }

    public static String toString(List<CHAR> charList, Predicate<CHAR> accept) {
        if (charList == null) {
            throw new IllegalArgumentException("charList");
        }
        StringBuilder s = new StringBuilder();
        for (CHAR aChar : charList) {
            if (accept.test(aChar)) {
                aChar.setAccept(true);
                s.append(aChar.getChs());
            }
        }
        return s.toString();
    }

    /**
     * 用 # 补位不符合 predicate 条件的字符.
     * @param charList charList
     * @param predicate predicate
     * @return str
     */
    public static String complementToString(List<CHAR> charList, Predicate<CHAR> predicate) {
        if (charList == null) {
            return null;
        }
        StringBuilder s = new StringBuilder();
        for (CHAR aChar : charList) {
            if (predicate.test(aChar)) {
                s.append(aChar.getChs());
            } else {
                s.append(jWell(aChar.getChs().length));
            }
        }
        return s.toString();
    }

    /**
     * jWell.
     * @param size size
     * @return chars
     */
    private static char[] jWell(int size) {
        char[] result = new char[size];
        Arrays.fill(result, '#');
        return result;
    }

    public static String toNumString(List<CHAR> charList, Predicate<CHAR> predicate) {
        if (charList == null) {
            throw new IllegalArgumentException("charList");
        }
        StringBuilder s = new StringBuilder();
        for (CHAR aChar : charList) {
            if (aChar.get() == 'O' || aChar.get() == 'o') {
                s.append('0');
            } else if (predicate.test(aChar)) {
                s.append(aChar.getChs());
            }
        }
        return s.toString();
    }

    public static String toNumStringV3(List<CHAR> charList, Predicate<CHAR> predicate) {
        if (charList == null) {
            throw new IllegalArgumentException("charList");
        }
        StringBuilder s = new StringBuilder();
        for (CHAR aChar : charList) {
            char c = aChar.get();
            if (c == 'O' || c == 'o') {
                s.append('0');
            } else if (aChar.getCnNum() > -1) {
                s.append(aChar.getCnNum());
            } else if (predicate.test(aChar)) {
                s.append(aChar.getChs());
            }
        }
        return s.toString();
    }

    public static String toNumStringV2(List<CHAR> charList, Predicate<CHAR> predicate) {
        if (charList == null) {
            throw new IllegalArgumentException("charList");
        }
        StringBuilder s = new StringBuilder();
        for (CHAR aChar : charList) {
            char c = aChar.get();
            if (c == 'O' || c == 'o') {
                s.append('0');
            } else if (c >= 19968 && c <= 40869) {
                Integer num = PinYinCharDict.convertByPinYin(c);
                if (num != null) {
                    aChar.setCt(CharType.ChinesNum);
                    s.append(num);
                } else if (predicate.test(aChar)) {
                    s.append(aChar.getChs());
                }
            } else if (predicate.test(aChar)) {
                s.append(aChar.getChs());
            }
        }
        return s.toString();
    }

    static Map<Character, Integer> chCharToNumMap = new ConcurrentHashMap<>();

    static {
        chCharToNumMap.put('一', 1);
        chCharToNumMap.put('二', 2);
        chCharToNumMap.put('三', 3);
        chCharToNumMap.put('四', 4);
        chCharToNumMap.put('五', 5);
        chCharToNumMap.put('六', 6);
        chCharToNumMap.put('七', 7);
        chCharToNumMap.put('八', 8);
        chCharToNumMap.put('九', 9);
    }

    public static boolean isLowerCase(char c){
        return c >= 'a' && c <= 'z';
    }

    public static boolean isLowerCaseOrWxChar(char c){
        return isLowerCase(c) || c == '-';
    }

    public static boolean isNumber(char c){
        return c >= '0' && c <= '9';
    }

    public static boolean last3IsNumber(String c){
        return Objects.nonNull(c) && c.length() > 3
                && StringUtil.isNumeric(c.substring(c.length() -3));
    }

    /**
     * 中文数字.
     */
    public static String toNumStringByCH(List<CHAR> charList, Predicate<CHAR> predicate) {
        if (charList == null) {
            throw new IllegalArgumentException("charList");
        }
        StringBuilder s = new StringBuilder();
        for (CHAR aChar : charList) {
            char c = aChar.get();
            if (c == 'O' || c == 'o') {
                s.append('0');
            } else if (c >= 19968 && c <= 40869) {
                Integer num = chCharToNumMap.get(c);
                if (num != null) {
                    aChar.setCt(CharType.ChinesNum);
                    s.append(num);
                } else if (predicate.test(aChar)) {
                    s.append(aChar.getChs());
                }
            } else if (predicate.test(aChar)) {
                s.append(aChar.getChs());
            }
        }
        return s.toString();
    }

    public static String toNumStringByCH(String s) {
        if (StringUtil.isEmpty(s)) {
            return s;
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer num = chCharToNumMap.get(c);
            if (num != null) {
                str.append(num);
            } else {
                str.append(c);
            }
        }
        return str.toString();
    }
}
