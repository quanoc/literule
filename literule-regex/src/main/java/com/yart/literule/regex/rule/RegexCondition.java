package com.yart.literule.regex.rule;

import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.rule.Condition;
import com.yart.literule.regex.match.PinYinMatcher;

import java.util.Objects;

/**
 * 自定义正则引擎.
 */
public class RegexCondition implements Condition {
    private final PinYinMatcher pinYinRegex;
    private final String key;

    public RegexCondition(String key, String regex) {
        this.pinYinRegex = PinYinMatcher.compile(regex);
        this.key = key;
    }

    public boolean evaluate(Facts facts) {
        String text = facts.getString(key);
        if (Objects.nonNull(text) && text.length() > 0) {
            return pinYinRegex.isMatch(text);
        }
        return false;
    }
}
