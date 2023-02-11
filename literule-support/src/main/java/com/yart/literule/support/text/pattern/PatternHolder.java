package com.yart.literule.support.text.pattern;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class PatternHolder {
    public static final Pattern NUM1_9_PATTERN = Pattern.compile("[0-9]{1,9}");
    public static final Pattern a_Z_AND_NUM_PATTERN = Pattern.compile("[a-zA-Z0-9]{1,9}");
    public static final Pattern SALARY_PATTERN = Pattern.compile("[1-9][0-9]{0,5}([.][0-9])?" +
            "[kKwW千万]?[-~～_—至到]{1,2}[1-9][0-9]{0,5}([.][0-9])?[kKwW千万]?");
    public static final Pattern PERCENT_SPAN_PATTERN = Pattern.compile(
            "\\d+(\\.\\d+)?%([-~～_至到](\\d+(\\.\\d+)?%))?");

    //(年)?月(日)? | 年
    public static final Pattern DATE_PATTERN = Pattern.compile(
            "((([12]\\d{3})年)?(0?[1-9]|1[0-2])月(((0?[1-9])|(([12])\\d)|30|31)[号日])?)|(([12]\\d{3})年)"
    );
    //时间相关
    public static final Pattern TIME_PATTERN = Pattern.compile(
            "([1-9]|[0-1]\\d|2[0-3])[:：点](半|([0-5]\\d(分)?([:：][0-5]\\d)?))" +
                    "([-~～_至到]([1-9]|[0-1]\\d|2[0-3])[:：点](半|([0-5]\\d(分)?([:：][0-5]\\d)?)))?"
    );
    //    public static final Pattern TIME_PATTERN1 = Pattern.compile(
//            "(0?\\d|1\\d|2[0-3])[.](0\\d|[1-5]\\d)" +
//                    "([-~～_至到](0?[0-9]|1[0-9]|2[0-3])[.](0[0-9]|[1-5][0-9]))?"
//    );
    private static final Pattern chSplitPattern = Pattern.compile(
            "1[3-9]\\d{0,5}([一-龥]{1,4}\\d{2,5}){1,4}"
    );

    private static final Pattern chSplit2Pattern = Pattern.compile(
            "1[3-9]\\d{0,5}([\u4E00-\u9FA5]{1,8}\\d{2,5}){1,4}"
    );

    public static Map<String, Pattern> patternMap = new ConcurrentHashMap<>();

    static {
        patternMap.put("SALARY", SALARY_PATTERN);
        patternMap.put("PERCENT", PERCENT_SPAN_PATTERN);
        patternMap.put("DATE_PATTERN", DATE_PATTERN);
        patternMap.put("TIME_PATTERN", TIME_PATTERN);
        patternMap.put("phone_ch_split", chSplitPattern);
        patternMap.put("phone_long_ch_split", chSplit2Pattern);
        //patternMap.put("TIME_PATTERN1", TIME_PATTERN1);
    }

    public static void main(String[] args) {
        String text = "你好,我们公司正在招聘高中数学运营(J30658),请问考虑吗";
        String text2 = "您好,招在家客服,介绍如下1,售前售后都做。早晚https://meeting.tencent.com/dm/6ELcHaUFgzlZ班,早班800-1700,晚班1700-2400或030,大部分是晚班。2,客服薪资4500-7000元1500123";
        String text3 = "我是Carolyn,我对你的经历很感兴趣,方便详细说说么?";
        Pattern PERCENT_SPAN_PATTERN = Pattern.compile(
                "(主播|理|店|师|运营)\\([J|]\\d{5,7}\\)");
        Pattern NUM_100_PATTERN = Pattern.compile(
                "(?<!\\d)\\d{1,3}00(?!\\d)");
        Pattern url = Pattern.compile("((ht|f)tps?)://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:/~+#]*[\\w\\-@?^=%&/~+#])?");
        Pattern NamePattern = Pattern.compile(
                "^[A-Z][a-zA-Z]{4,9}[,，][\\u4e00-\\u9fa5]");
        System.out.println(PatternUtils.getElementWithPos(
                text3,
                NamePattern)
        );
    }

    public static Pattern getPattern(String name) {
        return patternMap.get(name);
    }

}
