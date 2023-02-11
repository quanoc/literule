package com.yart.literule.regex.match.strategy;

public class DotMatchStrategy extends BaseMatchStrategy {
    @Override
    public boolean isMatch(char c, String edge) {
        return c != '\n' && c != '\r';
    }
}
