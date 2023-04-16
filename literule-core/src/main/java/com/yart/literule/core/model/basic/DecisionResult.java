package com.yart.literule.core.model.basic;

import com.yart.literule.core.model.flow.NodeTrace;
import com.yart.literule.core.parser.model.NodeResult;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 策略(一组规则)的结果.
 *
 * @author zhangquanquan
 */
public class DecisionResult {
    private Boolean hit;
    /**
     * 策略的id;
     */
    private String gid;
    /**
     * 策略的最终结果状态.
     */
    private Result res = Result.NONE;
    /**
     * 命中的节点信息.
     */
    private final List<String> hitNodes = new CopyOnWriteArrayList<>() ;
    /**
     * 是否出现异常.
     */
    private boolean error;
    /**
     * 消息ID.
     */
    private String msgId;
    /**
     * 因子节点的执行结果，用于记录执行流程;规则集 分支 抓取 过滤.
     */
    private final List<NodeResult> resultList = new CopyOnWriteArrayList<>();
//    private final List<RuleResult> resultList = new CopyOnWriteArrayList<>();
    public DecisionResult() {
    }

    public DecisionResult(String gid) {
        this.gid = gid;
    }

    public boolean isHit() {
        return Objects.nonNull(hit) && hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public DecisionResult addResult(RuleResult result){
//        resultList.add(result);
        return this;
    }

    public DecisionResult addBlock(String blockRid){
//        resultList.add(new RuleResult(blockRid, RuleResult.Result.BLOCK));
        this.hit = false;
        return this;
    }

    public String getGid() {
        return gid;
    }

//    public List<RuleResult> getResultList() {
//        return resultList;
//    }


    public Result getRes() {
        return res;
    }

    public void setRes(Result res) {
        this.res = res;
    }

    public List<String> getHitNodes() {
        return hitNodes;
    }

    public void addHitNodes(String hitNode) {
        this.hitNodes.add(hitNode);
    }

    public boolean hasError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public List<NodeResult> getResultList() {
        return resultList;
    }

    public void addResultList(NodeResult result) {
        this.resultList.add(result);
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public enum Result{
        HIT, BLOCK, ACT/*执行了action*/, NONE
    }

    @Override
    public String toString() {
        return "DecisionResult{" +
                "hit=" + hit +
                ", gid='" + gid + '\'' +
                ", resultList=" + resultList +
                '}';
    }
}
