import com.yart.literule.core.admin.RuleBus;
import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.core.rule.RuleLogic;
import com.yart.literule.core.rule.Rules;
import com.yart.literule.support.aviator.function.FilterPatternFunction;
import com.yart.literule.support.aviator.function.KeyWordMatchFunction;
import com.yart.literule.support.aviator.function.PrepareTextFunction;
import com.yart.literule.support.config.LiteRuleConfig;
import com.yart.literule.support.rule.executor.RuleExecutor;
import com.yart.literule.support.storage.DecisionEntity;
import com.yart.literule.support.storage.RuleEntity;
import com.yart.literule.support.storage.RuleFactory;
import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务场景使用举例.
 */
public class TestBusiness {

    @Test
    public void test(){
        AviatorEvaluator.addFunction(new PrepareTextFunction());
        AviatorEvaluator.addFunction(new KeyWordMatchFunction());
        AviatorEvaluator.addFunction(new FilterPatternFunction());
        RuleEntity entity = new RuleEntity();
        entity.setExpression("prepare(text); text.Lost.size > 3 && string.length(text) > 10 " +
                "&& keywords(text, 'WxTrigger,WxIntent') > 0");
        entity.setType("aviator");
        entity.setId("r001");
        entity.setName("特殊字符数大于3并且文本长度大于10");
        RuleEntity rule2 = new RuleEntity();
        rule2.setExpression("(.)*公众号(.)*");
        rule2.setType("regex");
        rule2.setId("r002");
        RuleEntity.ConditionConfig cconfig = new RuleEntity.ConditionConfig();
        cconfig.setProperty("text");
        rule2.setLeft(cconfig);
        rule2.setName("拼音匹配公众号");

        // rule3
        RuleEntity rule3 = new RuleEntity();
        rule3.setExpression("[_a-zA-Z][-_a-zA-Z0-9]{5,19}");
        rule3.setType("regex");
        rule3.setId("r003");
        RuleEntity.ConditionConfig lconfig = new RuleEntity.ConditionConfig();
        lconfig.setProperty("text3");
        RuleEntity.ConditionConfig rconfig = new RuleEntity.ConditionConfig();
        rconfig.setValue("java");
        rule3.setLeft(lconfig);
        rule3.setRight(rconfig);
        rule3.setName("正则匹配");


        DecisionEntity decision = new DecisionEntity();
        decision.setId("decId001");
        decision.setName("变体内容识别");
        decision.setRuleList(new ArrayList<>());
        // 添加2个规则.
        decision.getRuleList().add(entity);
//        decision.getRuleList().add(rule2);
//        decision.getRuleList().add(rule3);
        decision.setLogic("AND");
        List<DecisionEntity> decisionEntities = new ArrayList<>();
        decisionEntities.add(decision);
        decisionEntities.forEach(dec -> {
            Rules rules = new Rules();
            rules.setGid(dec.getId());
            rules.setRuleLogic(RuleLogic.getOrDefault(dec.getLogic()));
            dec.getRuleList().forEach(r -> rules.register(RuleFactory.createRule(r)));
            RuleBus.addRules(rules);
        });


        // define facts
        Facts facts = new Facts();
        facts.put("age", 17);
        facts.put("text", "！！！！！！！！！！入꯭职꯭请꯭加꯭2꯭0꯭2꯭中꯭间꯭3꯭9꯭数꯭字꯭0꯭0꯭扣꯭扣꯭1꯭3꯭裙꯭꯭扣扣裙微信");
        //我的宫重号是的. HR146649 你看考虑的话佳威一 下
        facts.put("text2", "入꯭职꯭请꯭加꯭2꯭0꯭2꯭中꯭间꯭3꯭9꯭数꯭字꯭0꯭0꯭扣꯭扣꯭1꯭3꯭裙꯭꯭扣扣裙");
        facts.put("text3", "HR146649");
        facts.put("a", 100.3);
        facts.put("b", 45);
        facts.put("c", -199.100);
        facts.put("edu", 985);

        LiteRuleConfig config = new LiteRuleConfig();
        RuleExecutor.getInstance().initExecutor(config);

        ExecutionResults results =RuleExecutor.getInstance().execute(facts);
        System.out.println(facts);
        System.out.println(results);
    }
}
