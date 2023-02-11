package com.yart.literule.regex.match.strategy;

public class CharMatchStrategy extends BaseMatchStrategy{
    @Override
    public boolean isMatch(char c, String edge) {
        return edge.charAt(0) == c;
    }
}
