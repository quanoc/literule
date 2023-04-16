import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.runtime.engine.DefaultRulesEngine;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.model.basic.Results;
import com.yart.literule.regex.rule.RegexRule;
import com.yart.literule.core.rule.*;

public class TestRegexRules {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        Condition condition = facts -> facts.getInteger("age") == 18;
        // define rules
        Rule weatherRule = new RuleBuilder()
                .name("myRule")
                .description("myRuleDescription")
                .priority(3)
                .when(condition)
                .then(facts -> System.out.println("规则命中后执行的Action1."))
                .then(facts -> System.out.println("规则命中后执行的Action2."))
                .then(facts -> System.out.println("规则命中后执行的Action3."))
                .build();

        Rule regexRule = new RegexRule()
                .name("RegexRule_001")
                .description("正则规则_拼音匹配公众号.")
                .when("text","(.)*公众号(.)*")
                .then(facts -> System.out.println("规则命中后执行的Action10."))
                .priority(10);

        Rule regexRule2 = new RegexRule()
                .name("RegexRule_002")
                .description("正则规则_拼音匹配公众号.")
                .when("text","(.)*加微(.)*")
                .then(facts -> System.out.println("规则命中后执行的Action20."))
                .priority(10);




        // define facts
        Facts facts = new Facts();
        facts.put("age", 17);
        facts.put("text", "我的宫重号是的. HR146649 你看考虑的话佳威一 下");

        Rules rules = new Rules();
        rules.register(weatherRule);
        rules.register(regexRule);
        rules.register(regexRule2);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        ExecutionResults results = new Results();
//        rulesEngine.fire(rules, new StatefulSession(facts, results));
    }
}
