//import org.junit.Test;
//
//import com.bzl.literule.core.entity.Facts;
//import com.bzl.literule.core.rule.Condition;
//import com.bzl.literule.core.rule.Rule;
//import com.bzl.literule.core.rule.RuleBuilder;
//import com.bzl.literule.core.rule.Rules;
//import com.bzl.literule.support.config.LiteRuleConfig;
//import com.bzl.literule.support.rule.RuleBus;
//import com.bzl.literule.support.rule.executor.RuleExecutor;
//import com.bzl.literule.support.storage.DecisionEntity;
//import com.bzl.literule.support.storage.RuleEntity;
//
///**
// * .
// */
//public class HitCodeTest {
//    @Test
//    public void test() {
//        Facts facts = new Facts();
//        facts.put("text", "我的宫重号是的. HR146649 你看考虑的话佳威一 下");
//
//        RuleEntity rule2 = new RuleEntity();
//        rule2.setExpression("(.)*公众号(.)*");
//        rule2.setType("regex");
//        rule2.setId("r002");
//
//        Rule defaultRule1 = new RuleBuilder()
//              .name("DefaultRule_003")
//              .description("普通规则执行")
//              .priority(3)
//              .when(有触发词)
//              .when(微信字符识别)
//              .when(非白名单)
//              .logic(Condition.ConditionLogic.AND)
//              .then(打码)
//              .build();
//
//        Rule defaultRule2 = new RuleBuilder()
//              .name("DefaultRule_003")
//              .description("普通规则执行")
//              .priority(3)
//              .when(有触发词)
//              .when(手机号识别)
//              .when(非白名单)
//              .logic(Condition.ConditionLogic.AND)
//              .then(打码)
//              .build();
//
//        Rules rules=new Rules(defaultRule1,defaultRule2);
//
//        RuleBus.addRules(rules);
//
//        LiteRuleConfig config = new LiteRuleConfig();
//        RuleExecutor.getInstance().initExecutor(config);
//        RuleExecutor.getInstance().execute(facts);
//    }
//}
