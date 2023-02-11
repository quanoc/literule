package com.yart.literule.core.internal.util;

public class StringUtil {

    public static final char SBC_SPACE = 12288; // 全角空格 12288
    public static final char DBC_SPACE = 32; //半角空格 32
    public static final char UNICODE_START = 65281;
    public static final char UNICODE_END = 65374;
    public static final char DBC_SBC_STEP = 65248; // 全角半角转换间隔
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * 全角转半角.
     */
    public static char full2Half(char src) {
        if (src == SBC_SPACE) {
            return DBC_SPACE;
        }
        if (src >= UNICODE_START && src <= UNICODE_END) {
            return (char) (src - DBC_SBC_STEP);
        }
        return src;
    }
}
