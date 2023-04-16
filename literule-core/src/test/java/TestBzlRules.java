
import com.yart.literule.core.admin.KnowledgeBase;
import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.session.RuleSession;
import com.yart.literule.core.session.StatefulSession;
import com.yart.literule.core.runtime.engine.DefaultRulesEngine;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.rule.*;
import org.junit.Test;

public class TestBzlRules {
    @Test
    public void test(){
        Condition condition = facts -> facts.getInt("age") == 18;
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


        // define facts
        Facts facts = new Facts();
        facts.put("rain", true);
        facts.put("age", 18);

        Rules rules = new Rules();
        rules.register(weatherRule);
        rules.setGid("rule_test_002");

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, new StatefulSession(facts));
        //
        rules.setStatus(RStatus.Up);
//        RuleBus.addRules(rules);
        KnowledgeBase.addRules(rules);
        RuleSession session = new StatefulSession();
        ExecutionResults results = session.execute(null, facts);
        System.out.println(results);
    }
}
