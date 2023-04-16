//package com.yart.literule.support.text.util;
//
//import com.yart.literule.core.internal.util.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.*;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * TokenizerUtils.
// *
// * @author zhangquanquan 2021.01.06.
// */
//@Slf4j
//public class TokenizerUtils {
//    private final static Tokenizer tokenizer = Analyzer.defaultTokenizer();
//
//    private static final String SPLIT = "♒";
//    private static final String SPACE = "❉";
//    private static final String NUMBER = "☎";
//
//
//    public static List<String> cur(String sentence) {
//        return tokenizer.cut(sentence);
//    }
//
//    static Set<String> delims = new CopyOnWriteArraySet<>();
//    static {
//        delims.addAll(Arrays.asList("\t", "\n", "\r", "\f", "。", "?", "？"));
//    }
//    public static List<String> sentences(String paragraph) {
//        List<String> sentences = new ArrayList<>();
//        if (StringUtil.isEmpty(paragraph)) {
//            return sentences;
//        }
//        StringTokenizer strTokenizer = new StringTokenizer(paragraph, "\t\n\r\f。？?", true);
//        String tmp = null;
//        while (strTokenizer.hasMoreTokens()) {
//            String token = strTokenizer.nextToken();
//            if (delims.contains(token)) {
//                sentences.add(tmp + token);
//                tmp = null;
//            } else {
//                tmp = token;
//            }
//        }
//        if (Objects.nonNull(tmp)) {
//            sentences.add(tmp);
//        }
//        return sentences;
//    }
//
//    public static void main(String[] args) {
//        List<String> s = tokenizer.cut("地址：重庆市 江北区 东原D7三期四号楼1619 ☎️02381622988");
//        System.out.println(s);
//        System.out.println(s.get(s.size() - 2));
//        System.out.println((char) 9742);
//        System.out.println(tokenizer.cut("BOSS好，我正在找文员转月嫂的工作。目前工作1年以内，" +
//                "积累了常规护理等方面的技能。详情可查看我的微简历，静候您的回复。"));
//
//        String s1 = "喂喂你好。唉你好，是刘先生是吗？啊对。唉你好，我是报的指定的工作人员，您正好方便沟通吗？你你你是哪。Boss创新的，对。嗯嗯嗯老师他们打到这边嗯。噢咱们这边是统招硕士毕业了，然后的话一直从事的工作也也是研发Java这方面。所以说的话他们有没有意向他们这一个岗？咱们这里是中国光大银行信用卡中心，是总行的信用卡中心在招聘的家。吧你有印象吗？嗯其实其实我还是挺想换个工作再试一试，但是你们是在北京是吧？对，是在北京，不过咱们是线上面试的，所以说比较方便。啊因为实际上是这样，其实我本身是在那个沈阳这边就是专家了，啊我当时我我应该是去对我我去年的时候然后在北京出差，然后有一段时间，然后我觉得其实那边环境也挺好也，应该想去试试，所以当时在在那个 Boss视频上就是写了一个在线的简历，啊然后对。但是现在吧其实我也没特别想好，就是说一定要去去外地这个什么。就是说咱们也就是说嗯有这样意向，但也没有就是说心里没有特别决定好要不要换。嗯对对对就是外地的。工作是吗？是这样，嗯好。咱们北京这边毕竟是一线城市，嘛是咱们的国家首都，而且的话这边的岗位可能嗯根据您您和您学的也非常好，非常的高。然后这边也是统招的，硕士也是985毕毕业的，那么到这边的话，它发展空间可能会更偏高一些，嗯因为相对它比沈阳的薪资可能会更高，而且这边的话这边岗位的话也是国企这边的岗位，嗯估计虽然也多，但是也分情况。我们信用卡中心这边的话，嗯按照啊我看到你们这边啊在这家公司所做的不错，你还担任过这边的产品研发经理对吧？嗯唉其实我们这个研发吧可能跟就是正常互联网公司的研发可能不是特别一样。啊是。没没有那么那么高级。啊噢那其实初步接触的话呢可能就是说嗯光大银行信用卡这边可能是先进行跟咱们一个面试比中面试的感觉，啊那是比较合适，聊得比较好的话，咱们可以比如说有一个多一个选择的机会，你可以考虑到时候再过来。唉我。想问一下就是这个岗位的话，大概薪资是什么？范围的？行为特别的范围的话这个心比较层次比较大，因为偏研发这方面的话，他可能有些人做到架构了，然后可能是在带团队的，带团队的话也可以说那这种薪资可能屈服就是曲线还是比较大的。跟您认识来听，但是比如说嗯但比如说您技术到位了，然后正常情况下他绝对不会压力薪资的，您同意咱们再导入他这边的薪资，可不可以？经验光大的人，他都不会说有压心的情况，那比如说您您期望是多少呢也行的话。嗯实际上是这样，就我现在我在沈阳这边的话，嗯就是正常的薪资是15,000，啊就虽然不是很高，但是啊对，但实际上其实在沈阳来说嗯还可以。啊对对对，那那如果到北京这边的话，嗯您的这个薪资我觉得应该至少我给你涨一番货，再往上再高。嗯其实说实话，我我我是期望于希望能有35,000左右，啊就是如果说低的话，我可能觉得因为就就没必要对对对。对对因为我想的是啥。呢嗯对。大概是这样的。嗯对对对，因为我我说啥意思呢就是嗯对不对？不好意思，我先我先打断一下。啊就是说因为我这个就是嗯现在这份工作实际上我在我相当于毕业之后，呃实际上是第二份工作，但实际上也可以算是第一份工作，我在这做了有7年左右，然后其实我工作还是比较稳定，然后我想如果是换工作的话，我就是希望能换一个，就是啊就是我能认认认定这家，然后就能长期好好好在呢我不想去说干两年再换一个，干两年换一个，就那样，感觉没啥意义。所以说吧我希望所以对薪资这块的话，我还是希望能稍微好一些。然后我回家你的心思是吧？对对对别的话其实嗯。在我刚跟您聊的过程中的话，听到您这边的一个嗯也希望是咱们是一个相对的稳定性，这边稳定性企业非常看重的，而且我也是看了礼仪之后，也看到没有人性也非常好。第二个就是说嗯咱们想希望那个新的话光大银行是可以给得到的，所以说你不用担心咱们薪资给不到，那么然后这里我看你也是有意向，那我就是先把你的简历吧给咱们这个领导先看一下，比如说咱们年报，咱们这边领导看我理解了之后咱们出差通过了，然后后续我帮您跟进，咱们先给您约个视频面试。现在是有学生进行沟通，比如说咱们叫主任来是吧？比如说薪资给到位了，然后工作环境工作方式，还有包括你对企业也非常满意的情况下的话，他们到时候再决定要不要过来。嗯嗯可以可以也行。嗯那小伙子加那个微信是您这个手机号码。啊对。行，您方便说一下吗？我这个是虚拟号。嗯嗯150。150。4006。4006。0485。0485。对那行，那所以我就加你微信，我是报的是15年我在襄阳，然后给你备注过去。好的好的。嗯好的辛苦。那之后咱们微信模块的。嗯唉好的。好拜拜嗯唉唉，拜拜。嗯";
//        sentences(s1).forEach(t -> {
//            System.out.println(t);
//        });
//    }
//
//}
