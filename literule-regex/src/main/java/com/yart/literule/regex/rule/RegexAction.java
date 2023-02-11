
package com.yart.literule.regex.rule;

import com.yart.literule.core.entity.Facts;
import com.yart.literule.core.rule.Action;
import com.yart.literule.regex.match.PinYinMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RegexAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexAction.class);
    private final String regex;
    private final PinYinMatcher pinYinRegex;

    public RegexAction(String regex) {
        this.regex = regex;
        this.pinYinRegex = PinYinMatcher.compile(regex);
    }

//    public RegexAction(String expression, ParserContext parserContext) {
//        this.expression = expression;
//        this.compiledExpression = MVEL.compileExpression(expression, parserContext);
//    }

    public void execute(Facts facts) {
        try {
            String text = facts.getString("text");
            if (Objects.nonNull(text) && text.length() > 0) {
                pinYinRegex.isMatch(text);
            }
        } catch (Exception var3) {
            LOGGER.error("Unable to evaluate regex: '" + this.regex + "' on facts: " + facts, var3);
            throw var3;
        }
    }
}
