package com.yart.literule.regex.match.strategy;

public class CharSetMatchStrategy extends BaseMatchStrategy {
    @Override
    public boolean isMatch(char c, String charSet) {
        boolean res = false;
        for (int i = 0; i < charSet.length(); i++) {
            if (charSet.charAt(0) == '^') {
                continue;
            }
            if ('-' == charSet.charAt(i)) {
                return c >= charSet.charAt(i-1) && c <= charSet.charAt(i+1);
            }
            if (c == charSet.charAt(i)) {
                res = true;
                break;
            }
        }
        if (charSet.charAt(0) == '^')  {
            return !res;
        }
        return res;
    }
}
