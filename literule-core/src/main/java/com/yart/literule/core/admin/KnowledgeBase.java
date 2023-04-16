package com.yart.literule.core.admin;

import com.yart.literule.core.model.decision.Decision;
import com.yart.literule.core.model.Rete;
import com.yart.literule.core.rule.Rule;
import com.yart.literule.core.rule.Rules;
import com.yart.literule.core.runtime.knowledge.KnowledgePackage;
import com.yart.literule.core.runtime.knowledge.KnowledgePackageImpl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 定义: base规则知识库 - 默认的规则知识数据管理总线<>RuleBus</>.
 *  a. 负责规则和决策的注册/注销.
 *  b. 负责规则和决策的缓存和管理.
 *  c. 负责规则和决策的获取.
 *  d. 后续通过 不同的知识包对 规则元数据进行隔离（是否有必要）.
 *
 * @author zhangquanquan
 */
public class KnowledgeBase implements KnowledgePackage{
    private static final KnowledgeBase instance = new KnowledgeBase();
    private KnowledgeBase(){}
    public static KnowledgeBase getInstance(){
        return instance;
    }
    private KnowledgePackage knowledgePackage;

    /**
     * 获取全部决策列表.
     */
    public static Collection<Decision> getDecisions(){
        return instance.getDecisionMap().values();
    }

    public static Decision getDecision(String gid){
        return instance.getDecisionMap().get(gid);
    }

    public static Rule getRule(String rid){
        return instance.getRuleMap().get(rid);
    }

    public static void addRules(Rules rules){
        if (Objects.isNull(rules.getGid())) {
            throw new IllegalArgumentException("rules id must not be empty.");
        }
        instance.getDecisionMap().put(rules.getGid(), rules);
    }

    public void init(KnowledgePackage knowledgePackage){
        this.knowledgePackage = knowledgePackage;
    }

    /**
     * default init.
     */
    public void init0(){
        this.knowledgePackage = new KnowledgePackageImpl();
    }

    private KnowledgePackage getKnowledgePackage(){
        if (needInit()) {
            init0();
        }
        return knowledgePackage;
    }

    public boolean needInit(){
        return Objects.isNull(knowledgePackage);
    }

    @Override
    public Map<String, Decision> getDecisionMap() {
        return getKnowledgePackage().getDecisionMap();
    }

    @Override
    public Map<String, Rule> getRuleMap() {
        return getKnowledgePackage().getRuleMap();
    }

    @Override
    public Rete getRete() {
        return getKnowledgePackage().getRete();
    }

    @Override
    public long getTimestamp() {
        return getKnowledgePackage().getTimestamp();
    }

    @Override
    public void resetTimestamp() {

    }

    @Override
    public String getId() {
        return getKnowledgePackage().getId();
    }
}
