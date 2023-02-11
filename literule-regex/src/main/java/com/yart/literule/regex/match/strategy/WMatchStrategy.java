package com.yart.literule.regex.match.strategy;

/**
 * 匹配 \w和\W
 */
public class WMatchStrategy extends BaseMatchStrategy {

    private final boolean reverse;

    @Override
    public boolean isReverse() {
        return reverse;
    }

    public WMatchStrategy(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public boolean isMatch(char c, String edge) {
        boolean res = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9';
        if (reverse) {
            return !res;
        }
        return res;
    }
}
