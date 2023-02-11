package com.yart.literule.support.text.model;

import lombok.ToString;

/**
 * CHAR.
 */
@ToString
public class CHAR {
    // 原字符
    private char origin;
    // 转换后的字符
    private char[] chs = new char[1];
    private int index;
    private CharType ct;
    private boolean full;
    // 是否可以转为 数字的中文字符.
    private int cnNum = -1;
    /**
     * chs[0] 的拼音(v->微:[origin:v. chs[0]:微])
     */
    private String pinyin;
    /**
     * 当前字符是否保留.
     */
    private boolean accept;
    // 预处理后
    private int preIdx;

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    private boolean ignore;

    public CHAR init(char c, CharType ct, char origin, int index) {
        this.chs[0] = c;
        this.ct = ct;
        this.index = index;
        this.origin = origin;
        return this;
    }

    public CHAR init(String convert, CharType ct, char origin, int index) {
        if (null == convert) {
            throw new IllegalArgumentException("convert");
        }
        this.chs = convert.toCharArray();
        this.ct = ct;
        this.index = index;
        this.origin = origin;
        return this;
    }

    public CHAR update(char c, CharType ct) {
        this.chs[0] = c;
        this.ct = ct;
        return this;
    }

    public CHAR update(String convert, CharType ct) {
        if (null == convert) {
            throw new IllegalArgumentException("convert");
        }
        this.chs = convert.toCharArray();
        this.ct = ct;
        return this;
    }

    public CHAR add(char c, CharType ct) {
        if (this.chs == null) {
            this.chs = new char[]{c};
        } else {
            char[] tmp = new char[this.chs.length + 1];
            System.arraycopy(this.chs, 0, tmp, 0, tmp.length - 1);
            tmp[tmp.length - 1] = c;
            this.chs = tmp;
        }
        this.ct = ct;
        return this;
    }

    public CHAR add(String convert, CharType ct) {
        if (null == convert) {
            throw new IllegalArgumentException("convert");
        }
        if (this.chs == null) {
            this.chs = convert.toCharArray();
        } else {
            char[] tmp = new char[this.chs.length + convert.length()];
            System.arraycopy(this.chs, 0, tmp, 0, tmp.length - convert.length());
            for (int i = tmp.length - 1; i >= chs.length; i--) {
                tmp[i] = convert.charAt(i - chs.length);
            }
            this.chs = tmp;
        }
        this.ct = ct;
        return this;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return pinyin == null ? "" : pinyin;
    }

    public char get() {
        return this.getChs()[chs.length - 1];
    }

    public void setCt(CharType ct) {
        this.ct = ct;
    }

    public void setCnNum(int cnNum) {
        this.cnNum = cnNum;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public char getOrigin() {
        return origin;
    }

    public char[] getChs() {
        return chs;
    }

    public int getIndex() {
        return index;
    }

    public CharType getCt() {
        return ct;
    }

    public boolean isFull() {
        return full;
    }

    public int getCnNum() {
        return cnNum;
    }

    public boolean isAccept() {
        return accept;
    }

    public int getPreIdx() {
        return preIdx;
    }

    public boolean isIgnore() {
        return ignore;
    }
}
