package com.yart.literule.regex.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static net.sourceforge.pinyin4j.format.HanyuPinyinCaseType.LOWERCASE;
import static net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITHOUT_TONE;

/**
 * PingYin convert.
 *
 * @author zhangquanquan 2020.08.09
 */

public class PinYinConverter {
    static Logger log = LoggerFactory.getLogger(PinYinConverter.class);
    static HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat();

    static {
        pinyinOutputFormat.setToneType(WITHOUT_TONE);
        pinyinOutputFormat.setCaseType(LOWERCASE);
        convert('张');
        log.info("load pingYin converter success.");
    }

    public static void init() {
        pinyinOutputFormat.setToneType(WITHOUT_TONE);
        pinyinOutputFormat.setCaseType(LOWERCASE);
        convert('张');
        log.info("load pingYin converter success.");
    }

    public static String convert(char c) {
        String[] pinYin;
        try {
            pinYin = PinyinHelper.toHanyuPinyinStringArray(c, pinyinOutputFormat);
            if (pinYin != null && pinYin.length > 0) {
                return pinYin[0];
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("convert error. c:{}", c, e);
        }
        return null;
    }

    public static String[] convertMulti(char c) {
        try {
            return PinyinHelper.toHanyuPinyinStringArray(c, pinyinOutputFormat);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("convertV2 error. c:{}", c, e);
            return new String[]{};
        }
    }

    public static String convert(String hanzi, CharSequence split) {
        if (null == hanzi || hanzi.isEmpty()) {
            return hanzi;
        }

        StringBuilder stringBuilder = new StringBuilder();
        //        char[] chars = hanzi.toCharArray();
        for (int i = 0; i < hanzi.length(); i++) {
            char c = hanzi.charAt(i);
            if (c < 19968 || c > 40869) {
                stringBuilder.append(c).append(split);
                continue;
            }
            String[] sr = new String[0];
            try {
                sr = PinyinHelper.toHanyuPinyinStringArray(c, pinyinOutputFormat);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            // fix
            if (sr == null) {
                if (c == '汣') {
                    sr = new String[]{"jiu"};
                } else {
                    log.warn("toHanyuPinyinStringArray error. chars:{}, hanzi:{}", c, hanzi);
                    sr = new String[0];
                }
            }
            if (sr.length > 0) {
                stringBuilder.append(sr[0]).append(split);
            }
            if (sr.length != 1) {
            //                if (log.isDebugEnabled()) {
            //                    log.debug("word:{} to pinyin:{}", c, Arrays.asList(sr));
            //                }
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        //        System.out.println(convert("叶莎莎"));
        System.out.println(convert("汣", ""));
        System.out.println(convert('a'));
        long start = System.currentTimeMillis();
        String s = null;
        for (int i = 0; i < 10000; i++) {
            s = convert('张');
        }
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(convert("连A起来", ","));
        System.out.println(convert("张圈圈", ""));
        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray('陆')));
    }

}
