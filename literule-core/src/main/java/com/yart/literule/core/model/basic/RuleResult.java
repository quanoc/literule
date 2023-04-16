package com.yart.literule.core.model.basic;

public class RuleResult {
    private Result res = Result.NONE;
    private String rid;

    public RuleResult() {
    }

    public RuleResult(String rid, Result res) {
        this.rid = rid;
        this.res = res;
    }

    public String getRid() {
        return rid;
    }

    public Result getRes() {
        return res;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public boolean isHit() {
        return Result.HIT == res;
    }

    public void setRes(Result res) {
        this.res = res;
    }

    public enum Result{
        HIT, BLOCK, NONE
    }

    @Override
    public String toString() {
        return "RuleResult{" +
                "res=" + res +
                ", rid='" + rid + '\'' +
                '}';
    }
}
