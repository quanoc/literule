import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.runtime.engine.DefaultRulesEngine;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.model.basic.Results;
import com.yart.literule.support.aviator.rule.AviatorRule;
import com.yart.literule.core.rule.*;

public class TestAviatorRules {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        Condition conditionAge = facts -> facts.getInt("age") < 35;
        Condition conditionEdu = facts -> facts.getInt("edu") == 985;

        // define rules
        Rule defaultRule = new RuleBuilder()
                .name("DefaultRule_003")
                .description("普通规则执行")
                .priority(3)
                .when(conditionAge)
                .when(conditionEdu)
                .logic(Condition.ConditionLogic.AND)
                .then(facts -> System.out.println("规则命中后执行的Action1."))
                .then(facts -> System.out.println("规则命中后执行的Action2."))
                .then(facts -> System.out.println("规则命中后执行的Action3."))
                .build();

        Rule aviatorRule = new AviatorRule()
                .name("RegexRule_002")
                .description("表达式规则_拼音匹配公众号.")
                .when("a+(b-c)>100")
                .then(facts -> System.out.println("规则命中后执行的Action20."))
                .priority(10);

        // define facts
        Facts facts = new Facts();
        facts.put("age", 17);
        facts.put("text", "我的宫重号是的. HR146649 你看考虑的话佳威一 下");
        facts.put("a", 100.3);
        facts.put("b", 45);
        facts.put("c", -199.100);
        facts.put("edu", 985);

        Rules rules = new Rules();
        rules.setGid("g001");
        rules.register(defaultRule);
        rules.register(aviatorRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        ExecutionResults results = new Results();
//        rulesEngine.fire(rules, new StatefulSession(facts, results));
        System.out.println(results);
    }
}
