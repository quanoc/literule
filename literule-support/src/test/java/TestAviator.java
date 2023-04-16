import com.yart.literule.core.model.basic.Facts;
import com.yart.literule.support.text.dict.BasicDict;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

public class TestAviator {
    String s0 = "【岗位职责】： 1、汇总、整理各平台运营数据，制定业绩报表 2、根据各平台销量情况，协助运营提供分析数据 3、完成上级交代的其他任务 【任职条件】： 1、熟练使用excel函数常用公式，数据透视表，善于统计数据 2、对数据敏感，头脑灵活，细心细腻 3、具有较强的学习能力、沟通能力、抗压能力 4、大专以上学历，统计学应届毕业生也可以接受 【福利待遇】 1、办公环境：宽敞明亮，设有3台冰箱、4台微波炉； 2、社会保险：购买社会保险（养老、生育、工伤、失业、医疗保险）+住房公积金； 3、人文关怀：停不下的小点心，如咖啡、牛奶、酸奶、糕点、饼干、八宝粥、方便面等； 4、全勤奖励：公司设员工全勤奖每月300元（每月2次调换班，3次20分钟以内的迟到免扣除）； 5、晋升机制：公平公正的晋升空间及薪资晋升，每年年中及年终有两次调薪机会； 6、培训体系：公司为新员工提供入职培训，入职引导一对一帮带； 7、员工生活：丰富多彩的户外活动、旅游、聚餐、拓展等； 8、假期福利：享有带薪国家法定节假日及带薪病假、婚假、产假、生育津贴、丧假、年假等； 9、享受福利：每年免费为员工做一次健康体检、每月为员工准备生日会及生日礼物、每月每人有50元的团建费用、每月公司为销售人员准备激励奖金； 10、试用期：1-3个月，我们给予新人莫大的认可与鼓励，以能力和结果为导向，帮助你提前达成目标，提前转正； 工作时间：am9:00-pm6:00，中午1.5小时休息 面试地址：广州市白云区三元里中心31号20楼2007 面试乘车路线：交通便利，地铁2号线到三元里A2出口走路12分钟可达； 公交乘24、34、38、58A、58、87、101、103、124、181、182、186、210、244、251、254、257、265、273、280、284、470、807、886至中医学院站下车";

    static {
        BasicDict.convertEnglish('a');
        //注册函数
        AviatorEvaluator.addFunction(new PrepareTextFunction());
        AviatorEvaluator.addFunction(new FilterPatternFunction());
        AviatorEvaluator.addFunction(new FilterKeywordFunction());
        AviatorEvaluator.addFunction(new GetPatternFunction());
        AviatorEvaluator.addFunction(new KeyWordMatchFunction());
    }

    @Test
    public void test(){
        // init

        String text2 = "入ོ职ོ7ོ1ོ4ོ请ོ加ོ4ོ0ོ7ོ中ོ间ོ数ོ字ོ7ོ8ོ4ོོႳႳ群ོ 5000~8000伽人";
        String text3 = "电283话18话1819号646话联系号6467打电话";
        String text31 = "你好,1557411我司诚聘篮球教练岗位,有意向欢迎咨询。可以先电话沟通0075;或者hhy加微信\n" +
                "zzi;";
        String text32 = "我们招服务员,这里聊不了很多,加微信沟通一下1910733";
        String text = "无责入҉职҉³҉⁰҉⁷҉请҉加҉⁶҉⁹҉⁷҉中҉间҉⁸҉⁸҉⁶҉数҉字҉Ⴓ҉Ⴓ҉群҉   底薪5805+开单奖+59%-78%统提提成+到访奖，公司提供资源，平均收入8k起，转正缴纳社保，节假日正常休息，方便交流几句吗？ 加中间数字Ⴓ裙Ⴓ";
        String text4 = "无责保底薪资（5000-1.5w）+后台提成（80%~100%下播自提）（欢迎来公司考察核实）公司提供住宿租房报销 不需要任何投资\n";
        String text5 = "面试入职流程 ↓\\\\n\\\\n1.先保存图片 微信扫码搜索适合自己岗位可同时投递2～3个岗位 \\\\n2. 微信视频 线上面试\\\\n3. 电话沟通 薪资福利待遇\\\\n4. 背景调查 （背调信息 认真填写）\\\\n5. 入职体检 （上海免费 外地报销）\\\\n6. 入职沟通 协商入职时间\\\\n\\\\n〖备 注〗\\\\n ★1~5项流程 可以在外地完成\\\\n ★建议外地的先不要来上海 入职再来上海\\\\n \\\\n★简历筛选约1～7个工作日 \\\\n★全部流程 约20个工作日\\\\n★具体流程时间以邮件和电话通知为准";
        Facts facts = new Facts();
        facts.put("text", text32);
        String expr = "prepare(text); text.Lost.size > 3 && string.length(text) > 10";
        String expr2 = "text_tmp=filter_pattern(text, 'phone_001');text_tmp=get_pattern(text_tmp, 'phone_ch_split');prepare(text_tmp);string.length(text_tmp)>4 && text_tmp.Num.size>10 && string.length(text)<=100" +
                "text_tmp=filter_keywords(text_tmp, 'WxIntent')";
        String expr3 = "string.length(text) <= 100;text_tmp=filter_pattern(text, 'phone_001');text_tmp=get_pattern(text_tmp, 'phone_ch_split');text_tmp=prepare(text_tmp);text_tmp.Num.size>10";
        String expr4 = "prepare(text); string.length(text)<=20 && (string.length(text)!=text.Num.size && text.Num.size>7 && text.Num.size<=12)";
        String expr5 = "text_tmp=filter_pattern(text, 'url_002,job_code_001,SALARY,PERCENT,DATE_PATTERN,TIME_PATTERN,serial_001,wx_001'); text_tmp=filter_keywords(text_tmp, 'White,white_intent,white_skills'); text_pre=prepare(text_tmp);keywords(text_pre, 'WxTrigger,WxIntent') > 0 && (text_tmp.Num.size + text_tmp.En.size) > 5 && string.length(text)<=60";
        Object result =  AviatorEvaluator.execute(
                expr5, facts
        );
        System.out.println(result);
        System.out.println(facts);
        System.out.println("朝九晚五更好\\\\n4⃣️薪资结算：计件式，多劳多得");
    }

    @Test
    public void test002(){
        String text32 = "我们招服务员,这里聊不了很多,加微信沟通一下1910733";
        String text33 = "19时候回5还是有08时间是43估计是82水库85";
        String text34 = "你可以加我微信1822588";
        String text35 = "方便1888发一份600你的简历过来吗?1109,请加信一个,数字";
        String text36 = "有意向加V1@5@2@9@0@0@3@8@0@9@5";
        String text37 = "你加我1377434";
        String text38 = "1504905的这个号码";
        Facts facts = new Facts();
        facts.put("text", text38);
        String expression = "text_tmp=filter_pattern(text, 'url_002,job_code_001,SALARY,PERCENT,DATE_PATTERN,TIME_PATTERN,serial_001,wx_001'); text_tmp=filter_keywords(text_tmp, 'White,white_intent,white_skills'); text_pre=prepare(text_tmp);keywords(text_pre, 'WxTrigger,WxIntent') > 0 && (text_tmp.Num.size + text_tmp.En.size) > 5 && string.length(text)<=60";
        Expression express = AviatorEvaluator.compile(expression,true);
        Object result = express.execute(facts);
        System.out.println(result);
        System.out.println(facts);
    }
}
