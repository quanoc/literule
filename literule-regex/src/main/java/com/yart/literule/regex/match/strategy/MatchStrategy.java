package com.yart.literule.regex.match.strategy;

public interface MatchStrategy {
    boolean isReverse();
    default boolean isMatch(char c, String edge) {
        return false;
    }
}
