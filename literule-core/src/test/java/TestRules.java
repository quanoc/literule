//import org.jeasy.rules.api.Facts;
//import org.jeasy.rules.api.Rule;
//import org.jeasy.rules.api.Rules;
//import org.jeasy.rules.api.RulesEngine;
//import org.jeasy.rules.core.DefaultRulesEngine;
//import org.jeasy.rules.mvel.MVELRule;
//import org.junit.Test;
//
//public class TestRules {
//    @Test
//    public void test(){
//        // define rules
//        Rule weatherRule = new MVELRule()
//                .name("weather rule")
//                .description("if it rains then take an umbrella")
//                .when("rain == true")
//                .then("System.out.println(\"It rains, take an umbrella!\");");
//
//
//        // define facts
//        Facts facts = new Facts();
//        facts.put("rain", true);
//
//        Rules rules = new Rules();
//        rules.register(weatherRule);
//
//        // fire rules on known facts
//        RulesEngine rulesEngine = new DefaultRulesEngine();
//        rulesEngine.fire(rules, facts);
//    }
//}
