package com.yart.literule.regex.match.strategy;

import com.yart.literule.regex.common.Constant;

import java.util.HashMap;
import java.util.Map;

public class MatchStrategyManager {
    private final static Map<String, MatchStrategy> matchStrategyMap;

    private final static Map<String, MatchStrategy> pinYinMatchStrategyMap;

    static {
        matchStrategyMap = new HashMap<>();
        matchStrategyMap.put("\\d", new DigitalMatchStrategy(false));
        matchStrategyMap.put("\\D", new DigitalMatchStrategy(true));
        matchStrategyMap.put("\\w", new WMatchStrategy(false));
        matchStrategyMap.put("\\W", new WMatchStrategy(true));
        matchStrategyMap.put("\\s", new SpaceMatchStrategy(false));
        matchStrategyMap.put("\\S", new SpaceMatchStrategy(true));
        matchStrategyMap.put(".", new DotMatchStrategy());
        matchStrategyMap.put(Constant.EPSILON, new EpsilonMatchStrategy());
        matchStrategyMap.put(Constant.CHAR, new CharMatchStrategy());
        matchStrategyMap.put(Constant.CHARSET, new CharSetMatchStrategy());

        pinYinMatchStrategyMap = new HashMap<>();
        pinYinMatchStrategyMap.put(Constant.CHAR, new PinYinCharMatchStrategy());
    }

    public static MatchStrategy getStrategy(String key) {
        // 特殊字符的匹配
        if (matchStrategyMap.containsKey(key)) {
            return matchStrategyMap.get(key);
        }
        // 单字符和字符集的匹配
        if (key.length() == 1) {
            return matchStrategyMap.get(Constant.CHAR);
        } else {
            return matchStrategyMap.get(Constant.CHARSET);
        }
    }

    public static MatchStrategy getPinYinStrategy(String key) {
        // 特殊字符的匹配
        if (matchStrategyMap.containsKey(key)) {
            return matchStrategyMap.get(key);
        }
        // 单字符和字符集的匹配
        if (key.length() == 1) {
            return pinYinMatchStrategyMap.get(Constant.CHAR);
        } else {
            return matchStrategyMap.get(Constant.CHARSET);
        }
    }
}
