package com.yart.literule.regex.rule;

import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.rule.Condition;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java 的 正则引擎.
 */
public class JavaRegexCondition implements Condition {
    private final Pattern pattern;
    private final String key;

    public JavaRegexCondition(String key, String regex) {
        this.pattern = Pattern.compile(regex);
        this.key = key;
    }

    public boolean evaluate(Facts facts) {
        String text = facts.getString(key);
        if (Objects.nonNull(text) && text.length() > 0) {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
        return false;
    }
}
