package com.yart.literule.support.text;

import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.regex.util.PinYinCharDict;
import com.yart.literule.regex.util.PinYinConverter;
import com.yart.literule.support.text.dict.BasicDict;
import com.yart.literule.support.text.dict.SpecialSymbolDict;
import com.yart.literule.support.text.model.CHAR;
import com.yart.literule.support.text.model.CharType;
import com.yart.literule.support.text.util.CHARUtil;
import com.yart.literule.support.text.util.CharUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 文本预处理.
 * 1. 字符标注：标点符号、中文拼音、特殊转换字符、表情符(不是文本:emoji).
 * 2. 文本归一化： 全角转半角、特殊字符转换(数字、英文字符)、(单字符转多字符：待评估)
 * 1. 过滤不符合微信的字符, 保留[_-][英文字母][中文][数字][char_to_weChat_char.txt文件中的(+,特殊+,..)]
 * 2. 转换特殊字符 [+][特殊+]
 * 3. 2字符的word转换 [vx -> 微信][2字符同音字符串转换:威信->微信]
 * 4. 微信没有 拼音数字转换. 手机号有[吆->1][撒->3].
 *
 * @author zhangquanquan 2020.09.15s
 */
@Slf4j
public class TextPreProcess {
    public static Set<CharType> removeSpecTypes = new HashSet<>(Arrays.asList(CharType.Special, CharType.SpecialA));
    public static String process(String s) {
        List<CHAR> charList = preprocess(s);
        return CHARUtil.toString(charList, aChar -> !removeSpecTypes.contains(aChar.getCt()));
    }

    public static List<CHAR> preprocess(String s) {
        List<CHAR> charList = new ArrayList<>();
        process(s, charList);
        return charList;
    }

    /**
     * 预处理：字符标注.
     *
     * @param s pending string.
     * @return 预处理后的内容
     */
    public static boolean process(
            String s,
            List<CHAR> charList
    ) {
        if (StringUtil.isEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            CHAR rc = new CHAR();
            charList.add(rc);
            char cSrc = s.charAt(i);
            // 1. 全角转半角
            char half = StringUtil.full2Half(cSrc);
            if (half != cSrc) {
                rc.setFull(true);
            }
            if ((half >= 19968 && half <= 40869)) {
                rc.init(half, CharType.Cn, cSrc, i);
                if (s.length() < 3000) {
                    String[] pinyin = PinYinConverter.convertMulti(half);
                    if (Objects.nonNull(pinyin) && pinyin.length > 0) {
                        rc.setPinyin(pinyin[0]);
                    }
                    Integer num = PinYinCharDict.numByPinYin(half, pinyin);
                    if (num != null) {
                        rc.setCnNum(num);
                    }
                }
            } else if (CharUtils.a_zA_Z(half)) {
                rc.init(half, CharType.En, cSrc, i);
            } else if (CharUtils.isNum0_9(half)) {
                rc.init(half, CharType.Num, cSrc, i);
            } else if (half == '.') {
                rc.init(half, CharType.Punctuation, cSrc, i);
            } else if (half == '-' || half == '_') {
                rc.init(half, CharType.Line, cSrc, i);
            } else if (half == '@' || half == '＠') {
                rc.init(half, CharType.AnT, cSrc, i);
            } else if (half == '\uD83C' || half == '\uD83D') {
                // 特殊字符. 2个拼接.
                rc.init(half, CharType.Special, cSrc, i);
                if (i < s.length() - 1) {
                    CHAR rcNext = new CHAR();
                    charList.add(rcNext);
                    rcNext.init(s.charAt(i + 1), CharType.Special, cSrc, i + 1);
                    i++;
                }
            } else if (BasicDict.isPunctuation(half)) {
                rc.init(half, CharType.Punctuation, cSrc, i);
            } else {
                // 2. 转多字符
                String str = BasicDict.convert(half);
                if (str == null) {
                    String convertEnglishChars = BasicDict.convertEnglish(half);
                    if (null != convertEnglishChars && convertEnglishChars.length() > 0) {
                        rc.init(convertEnglishChars, CharType.CvEn, cSrc, i);
                    } else {
                        boolean punctuation = SpecialSymbolDict.isPunctuation(half);
                        if (punctuation) {
                            rc.init(half, CharType.Punctuation, cSrc, i);
                        } else if (CharUtils.isJapanese(half)) {
                            rc.init(half, CharType.Ja, cSrc, i);
                        } else {
                            rc.init(half, CharType.SpecialA, cSrc, i);
                        }
                    }
                } else {
                    if (BasicDict.numConvertChars(half)) {
                        rc.init(str, CharType.CvNum, cSrc, i);
                    } else if (str.length() == 1 && str.charAt(0) >= 19968 && str.charAt(0) < 40869) {
                        rc.init(str, CharType.CvChine, cSrc, i);
                        rc.setPinyin(PinYinConverter.convert(str.charAt(0)));
                    } else {
                        rc.init(str, CharType.Convert, cSrc, i);
                    }
                }
            }
        }
        try {
            if (charList.size() != s.length()) {
                throw new IllegalStateException("check");
            }
            return false;
//            return rePreProcess(charList);
        } catch (Exception e) {
            log.error("rePreProcess. s:{},result:{}", s, charList, e);
            return false;
        }
    }

    /**
     * 第一步: [-_][因为字母][数字][中文][+➕..].
     * 第二步:  [vx -> 微信][威信 -> 微信]
     * 1. 多字符匹配
     *
     * @return true - 是否有contacts转换
     */
    public static boolean rePreProcess(List<CHAR> charList) {
        StringBuilder tmp = new StringBuilder();
        boolean contactConvert = false;
        for (int i = 0; i < charList.size(); i++) {
            CHAR ch = charList.get(i);
            if (ch.getCt() == CharType.SpecialA) {
                continue;
            }
            tmp.append(ch.getChs());
            CHAR nextNotLost = nextNotLost(charList, i + 1);
            // 两个字之间存在其他字符分割,则不处理(认为是不成词:对于特意分割隐藏的,其他方式解决)
            if (nextNotLost != null && nextNotLost.getIndex() - ch.getIndex() == 1) {
                tmp.append(nextNotLost.getChs());
                String tmpS2 = tmp.toString();
                String wxWordConvert = BasicDict.convertToken(tmpS2);
                // [vx-> 微信]
                if (null != wxWordConvert && !wxWordConvert.equals(tmpS2)) {
                    contactConvert = true;
                    ch.update(wxWordConvert.charAt(0), CharType.CvChine);
                    if (wxWordConvert.length() > 1) {
                        nextNotLost.update(wxWordConvert.charAt(1), CharType.CvChine);
                    } else {
//                        nextNotLost.update(nextNotLost.get(), CharType.CvLost);
                    }
                } else {
                    CHAR nextNextNotLost = nextNotLost(charList, i + 2);
                    // 两个字之间存在其他字符分割,则不处理(认为是不成词:对于特意分割隐藏的,其他方式解决)
                    if (nextNextNotLost != null && nextNextNotLost.getIndex() - nextNotLost.getIndex() == 1) {
                        tmp.append(nextNextNotLost.getChs());
                        String tmpS3 = tmp.toString();
                        String wxWordConvertS3 = BasicDict.convertToken(tmpS3);
                        if (null != wxWordConvertS3 && !wxWordConvertS3.equals(tmpS3)) {
                            contactConvert = true;
                            ch.update(wxWordConvertS3.charAt(0), CharType.CvChine);
                            if (wxWordConvertS3.length() > 2) {
                                nextNextNotLost.update(wxWordConvertS3.charAt(2), CharType.CvChine);
                                nextNotLost.update(wxWordConvertS3.charAt(1), CharType.CvChine);
                            } else if (wxWordConvertS3.length() == 2) {
                                nextNotLost.update(wxWordConvertS3.charAt(1), CharType.CvChine);
                            } else {
//                                nextNotLost.update(nextNextNotLost.get(), CharType.CvLost);
                            }
                        } else {
                            contactConvert = pinYinConvert(charList, ch, i, tmpS2);
                        }
                    } else {
                        contactConvert = pinYinConvert(charList, ch, i, tmpS2);
                    }
                }
            }
            tmp.delete(0, tmp.length());
        }
        return contactConvert;
    }

    private static boolean pinYinConvert(List<CHAR> charList, CHAR ch, int i, String tmpS2) {
        if (ch.getCt() != CharType.Cn && ch.getCt() != CharType.CvChine) {
            return false;
        }
        boolean contactConvert = false;
        {
            CHAR nextChinese = nextChinese(charList, i + 1);
            StringBuilder pinyin23 = new StringBuilder("");
            pinyin23.append(ch.getPinyin());
            if (nextChinese != null) {
                pinyin23.append(nextChinese.getPinyin());
                String keyword = pinYinConvert(pinyin23.toString(), tmpS2);
                if (null != keyword && !keyword.equals(tmpS2)) {
                    contactConvert = true;
                    ch.update(keyword.charAt(0), CharType.CvPinYin);
                    nextChinese.update(keyword.charAt(1), CharType.CvPinYin);
                } else {
                    CHAR nextNextChinese = nextChinese(charList, nextChinese.getIndex() + 1);
                    if (nextNextChinese != null) {
                        pinyin23.append(nextNextChinese.getPinyin());
                    }
                    // pinYin3
                    keyword = pinYinConvert(pinyin23.toString(), tmpS2);
                    if (null != keyword && !keyword.equals(tmpS2)) {
                        contactConvert = true;
                        ch.update(keyword.charAt(0), CharType.CvPinYin);
                        if (keyword.length() == 2) {
                            nextChinese.update(keyword.charAt(1), CharType.CvPinYin);
                        }
                        if (nextNextChinese != null && keyword.length() == 3) {
                            nextNextChinese.update(keyword.charAt(2), CharType.CvPinYin);
                        }
                    }
                }
            }
        }
        return contactConvert;
    }

    private static CHAR nextNotLost(List<CHAR> charList, int index) {
        if (index >= charList.size()) {
            return null;
        }
        CHAR next = charList.get(index);
        if (next.getCt() != CharType.SpecialA ) {
            return next;
        } else {
            return nextNotLost(charList, ++index);
        }
    }

    private static CHAR next(List<CHAR> charList, int index) {
        if (index >= charList.size()) {
            return null;
        }
        return charList.get(index);
    }

    private static CHAR nextChinese(List<CHAR> charList, int index) {
        if (index >= charList.size()) {
            return null;
        }
        CHAR next = charList.get(index);
        if (next.getCt() == CharType.CvChine || next.getCt() == CharType.Cn) {
            return next;
        } else {
            return nextChinese(charList, ++index);
        }
    }


    // 2字符 多音字符串 转换
    public static Map<String, String> contacts = new ConcurrentHashMap<>();
    public static Set<String> whites = new CopyOnWriteArraySet<>();
    static {
        whites.addAll(Arrays.asList("流维", "佳伟", "位兴", "位薪", "价格", "个位", "加各", "位信", "假个",
                "假一", "为行", "为新"));
    }

    public static String pinYinConvert(String pinYin, String origin) {
        String word = contacts.get(pinYin);
        if (word != null && !whites.contains(origin)) {
            return word;
        }
        return null;
    }

    static {
        //contacts.put("jiawo", "加我");
        contacts.put("jiawei", "加微");
        contacts.put("huanwei", "换微");
        contacts.put("weixin", "威信");
        contacts.put("weixing", "威信"); // 岗位薪资， 一位信用， 岗位信息
        contacts.put("jiaohuan", "交换");
        contacts.put("weiliao", "微聊");
        contacts.put("jiayixia", "加一下");
        contacts.put("jiagewei", "加个微");
        // contacts.put("gewei", "个微"); // 个 位置  个卫生间
        contacts.put("jiage", "加个"); // 价格
        contacts.put("jiaxia", "加下");
        contacts.put("jiayige", "加一个");
        contacts.put("jiazhege", "加这个");
        contacts.put("liugewei", "留个微");
        contacts.put("liuwei", "留微"); // 日常交流维护
        contacts.put("jiahaoyou", "加好友");
        contacts.put("lianxifang", "联系方");
        contacts.put("weilianxi", "微联系");
        contacts.put("jiataiduo", "加太多");
        contacts.put("weishuo", "微说");
        contacts.put("koukou", "QQ");
    }
}
