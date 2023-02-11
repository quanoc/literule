package com.yart.literule.regex.match.strategy;

public class EpsilonMatchStrategy extends BaseMatchStrategy {
    @Override
    public boolean isMatch(char c, String edge) {
        return true;
    }
}
