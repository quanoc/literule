import com.yart.literule.core.admin.RuleBus;
import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.support.config.LiteRuleConfig;
import com.yart.literule.support.rule.executor.RuleExecutor;
import com.yart.literule.core.rule.Condition;
import com.yart.literule.core.rule.Rule;
import com.yart.literule.core.rule.RuleBuilder;
import com.yart.literule.core.rule.Rules;

public class TestRuleEngine {
    public static void main(String[] args) {
        LiteRuleConfig config = new LiteRuleConfig();
        RuleExecutor.getInstance().initExecutor(config);

        Condition conditionAge = facts -> facts.getInt("age") < 35;
        Condition conditionEdu = facts -> facts.getInt("edu") == 985;
        // define facts
        Facts facts = new Facts();
        facts.put("age", 36);
        facts.put("text", "我的宫重号是的. HR146649 你看考虑的话佳威一 下");
        facts.put("a", 100.3);
        facts.put("b", 45);
        facts.put("c", -199.100);
        facts.put("edu", 985);

        // define rules
        Rule defaultRule = new RuleBuilder()
                .rid("000123")
                .name("DefaultRule_003")
                .description("普通规则执行")
                .priority(3)
                .when(conditionAge)
                .when(conditionEdu)
                .logic(Condition.ConditionLogic.AND)
                .then(f -> System.out.println("规则命中后执行的Action1."))
                .build();
        Rules rules = new Rules();
        rules.register(defaultRule);
        rules.setGid("rules001");
        RuleBus.addRules(rules);
        ExecutionResults results = RuleExecutor.getInstance().execute(facts);
        // TODO 规则网络.
        System.out.println(facts);
        System.out.println(results);

    }

}
