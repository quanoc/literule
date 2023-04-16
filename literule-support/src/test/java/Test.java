import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.support.text.TextPreProcess;
import com.yart.literule.support.text.dict.BasicDict;
import com.yart.literule.support.text.dict.DictLoader;
import com.yart.literule.support.text.model.CHAR;
import com.yart.literule.support.text.model.Tuple3;
import com.yart.literule.support.text.pattern.PatternUtils;
import com.googlecode.aviator.AviatorEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Test {

    @org.junit.Test
    public void testPre(){
        DictLoader.load();
        String s = "9*6箱车转让，连线路一起打包，带线路转让，固定货源联系1衫8-洞2##幺散幺2删4,手机号，非诚勿扰2+1合同";
        List<CHAR> charTypeList = new ArrayList<>();
        TextPreProcess.process(s, charTypeList);
        System.out.println(charTypeList);
    }


    @org.junit.Test
    public void testFun(){
        // init
        BasicDict.convertEnglish('a');
        //注册函数
        AviatorEvaluator.addFunction(new PrepareTextFunction());
        AviatorEvaluator.addFunction(new FilterPatternFunction());
        AviatorEvaluator.addFunction(new FilterKeywordFunction());
        AviatorEvaluator.addFunction(new PrepareNumTextFunction());
        String s = "9*6箱车转让，连线路一起打包，带线路转让，固定货源联系1衫8-洞2##幺散幺2删4,手机号，非诚勿扰2+1合同";
        String s2 = "地址济南市历城区彩石街道尚客优酒店(宾馆)办理顾客入住,离店,问询,预定,办理入住信息,微壹伍伍扒扒扒贰壹贰零伍";
        String s3 = "电183话18话1819号646话联系号6467打电话联系";
        String s4 = "找临时工或者长期，手脚伶俐，穿着干净整洁，可包吃住151-5586-5330，工资可面议，具体工作就是外卖打包，帮忙配菜。";
        String s5 = "173你加我的2的2216吧,合起来就了6203了6203";
        Facts facts = new Facts();
        facts.put("text", s5);
        String expr2 = "text_pre=prepare_num(text,'Cn,En,Num');text_tmp = filter_pattern(text, 'SALARY');" +
                "text_tmp=filter_keywords(text_tmp, 'WxIntent')";
        String expr5 = "text_tmp=filter_pattern(text, 'phone_001');text_tmp=prepare(text_tmp,'Cn,En,Num');has_pattern(text_tmp, 'phone_001');";
        String expr3 = "text_pre=prepare(text,'Cn,En,Num');text_tmp = filter_pattern(text, 'SALARY');" +
                "text_tmp=filter_keywords(text_tmp, 'WxIntent')";
        String expr4 = "text_tmp = filter_pattern(text, 'SALARY');";
        String expr6 = "text_pre=prepare(text,'Num');string.length(text) < 40 && (text.Num.size>8&&text.Num.size<20)";


        Object result =  AviatorEvaluator.execute(
                expr6, facts
        );
        System.out.println(result);
        System.out.println(facts);
        List<CHAR> charTypeList = new ArrayList<>();
        TextPreProcess.process(s, charTypeList);
        System.out.println(charTypeList);
    }

    @org.junit.Test
    public void test(){
        String PHONE_PTN = "[1][3,4,5,6,7,8,9][0-9]{9}$*";
        String text = "电283话18话1819号646话联系号6467打电话";
        Pattern pattern = Pattern.compile("1[3-9]\\d{0,5}([\u4E00-\u9FA5]{1,4}\\d{2,5}){1,4}");
        List<Tuple3<String, Integer, Integer>> result = PatternUtils.getElementWithPos(text, pattern);
        System.out.println(result);

        String text2 = "1.取得法律职业资格证书(A),专业知识丰富2、拥有C1驾照3、取得普通话二级乙等证书4、取得英语CET-4证书5、精通office办公软件及相关办公器材6、能很好的与他人沟通,具有良好的团队合作精神;7、工作认真细致、思维严谨、善于学习、善于分析问题、适应能力强;8、抗压能力强。";
        Pattern pattern2 = Pattern.compile("(((\\d|[1\\d])([、，,.:：]))(?!\\d))");
        System.out.println(PatternUtils.getElementWithPos(text2, pattern2));
    }

    public static void main(String[] args) {

        System.out.println("入҉职҉ 193 请҉加 372҉ 中҉间҉数҉字҉ 721 ႳႳ群҉");
    }
}
