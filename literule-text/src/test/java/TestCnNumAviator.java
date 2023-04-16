import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.support.aviator.function.*;
import com.yart.literule.support.text.dict.BasicDict;
import com.yart.literule.support.text.dict.DictLoader;
import com.yart.literule.support.text.pattern.PatternHolder;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

import java.util.regex.Pattern;

public class TestCnNumAviator {
    String s0 = "一三六一五六三一七六一,我的电话";
    static {
        BasicDict.convertEnglish('a');
        DictLoader.load();
        //注册函数
        AviatorEvaluator.addFunction(new PrepareTextFunction());
        AviatorEvaluator.addFunction(new FilterPatternFunction());
        AviatorEvaluator.addFunction(new FilterKeywordFunction());
        AviatorEvaluator.addFunction(new GetPatternFunction());
        AviatorEvaluator.addFunction(new KeyWordMatchFunction());
        AviatorEvaluator.addFunction(new PrepareNumTextFunction());
        AviatorEvaluator.addFunction(new HasPatternFunction());
    }

    @Test
    public void test002(){
        String text32 = "一三六一五六三一七六一,我的电话";
        String text33 = "依伞舞流4伞期丝丝八舞";
        String text34 = "158..袁..1..8.3..8..生.5.0...9...6,可以电话联系我";
        String text35 = "电画刘,158先,7o62生,2618找工作,call我。";
        String text36 = "lhj空格1空格8空格9空格1空格1空格0空格0空格8空格2空格0空格8";
        String text37 = "你加我1377434";
        String text38 = "1504905的这个号码";
        String text39 = "幺无无久陆柒叁幺叁叁柒";
        Facts facts = new Facts();
        facts.put("text", text39);
        PatternHolder.patternMap.put("phone_001", Pattern.compile("[1][3,4,5,6,7,8,9][0-9]{9}$*"));
        String expression0 = "text_tmp=filter_pattern(text, 'url_002,phone_001');text_pre=prepare_num(text_tmp,'Cn,En,Num');prepare(text_tmp);prepare(text_pre);string.length(text)<=50&&(has_pattern(text_pre,'phone_001')||(text_pre.Num.size-text_tmp.Num.size)>9)";
//        String expression = "text_tmp=prepare_num(text,'Num')";
        String expression = "text_tmp=filter_pattern(text, 'url_002,phone_001');text_pre=prepare(text_tmp,'Num');string.length(text)<=50&&string.length(text_pre)>0&&has_pattern(text_pre,'phone_001')";
        Expression express = AviatorEvaluator.compile(expression0,true);
        Object result = express.execute(facts);
        System.out.println(result);
        System.out.println(facts);
    }
}
