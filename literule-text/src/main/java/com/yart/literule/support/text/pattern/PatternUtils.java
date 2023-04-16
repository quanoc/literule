package com.yart.literule.support.text.pattern;

import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.support.text.model.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {
    public static List<Tuple3<String, Integer, Integer>> getElementWithPos(
            String text, Pattern pattern
    ) {
        if (StringUtil.isEmpty(text)) {
            return new ArrayList<>();
        }
        List<Tuple3<String, Integer, Integer>> list = new ArrayList<>();
        Matcher m = pattern.matcher(text);
        while (m.find()) {
            list.add(new Tuple3<>(m.group(), m.start(), m.end()));
        }
        return list;
    }

    public static boolean isPattern(String content, Pattern pattern) {
        if (StringUtil.isEmpty(content)) {
            return false;
        }
        return match((content), pattern);
    }

    private static boolean match(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
