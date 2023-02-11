package com.yart.literule.core.entity;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class RulesResult {
    private Boolean hit;
    private String gid;
    private final List<RuleResult> resultList = new CopyOnWriteArrayList<>();
    public RulesResult() {
    }

    public RulesResult(String gid) {
        this.gid = gid;
    }

    public Boolean getHit(){
        return this.hit;
    }
    public boolean isHit() {
        return Objects.nonNull(hit) && hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public RulesResult addResult(RuleResult result){
        resultList.add(result);
        return this;
    }

    public RulesResult addBlock(String blockRid){
        resultList.add(new RuleResult(blockRid, RuleResult.Result.BLOCK));
        this.hit = false;
        return this;
    }

    public String getGid() {
        return gid;
    }

    public List<RuleResult> getResultList() {
        return resultList;
    }

    @Override
    public String toString() {
        return "RulesResult{" +
                "hit=" + hit +
                ", gid='" + gid + '\'' +
                ", resultList=" + resultList +
                '}';
    }
}
