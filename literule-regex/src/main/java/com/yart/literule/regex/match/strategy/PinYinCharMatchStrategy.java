package com.yart.literule.regex.match.strategy;

import com.yart.literule.regex.util.PinYinConverter;

import java.util.Objects;

public class PinYinCharMatchStrategy extends BaseMatchStrategy{

    @Override
    public boolean isMatch(char c, String edge) {
        String[] pinYin = PinYinConverter.convertMulti(c);
        String[] pinYinEdge = PinYinConverter.convertMulti(edge.charAt(0));
        if (Objects.isNull(pinYin) || Objects.isNull(pinYinEdge)) {
            return false;
        }
        if (pinYin.length ==1 && pinYinEdge.length == 1) {
            return Objects.equals(pinYin[0], pinYinEdge[0]);
        } else {
            return match(pinYin, pinYinEdge);
        }
    }

    /**
     * 拼音是否匹配.
     * @param pinYin 待匹配字符的拼音
     * @param pinYinEdge 正则边界的字符拼音
     */
    private boolean match(String[] pinYin, String[] pinYinEdge){
        for (String py : pinYin) {
            for (String pye: pinYinEdge) {
                if (py.equals(pye)) {
                    return true;
                }
            }
        }
        return false;
    }
}
