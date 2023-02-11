//package com.bzl.literule.core.entity;
//
//import com.bzl.literule.core.context.ExecutionResults;
//
//import java.io.Serializable;
//import java.util.*;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class ExecutionResultImpl implements ExecutionResults, Serializable {
//    Results results = new Results();
//    Facts facts;
//    Facts env;
//
//    public ExecutionResultImpl() {}
//
//    public ExecutionResultImpl(Facts facts, Facts env) {
//        this.facts = facts;
//        this.env = env;
//    }
//
//    public Collection<String> getIdentifiers() {
//        return this.results.keySet();
//    }
//
//    public Object getValue(String identifier) {
//        return this.results.get( identifier );
//    }
//
//    public Object getFactHandle(String identifier) {
//        return this.facts.get( identifier );
//    }
//
//    @Override
//    public List<RulesResult> getRulesResults() {
//        List<RulesResult> res = this.results.getList(RULES_RESULTS_KEY);
//        return Objects.isNull(res) ? new ArrayList<>() : res;
//    }
//
//
//    public Results getResults() {
//        return this.results;
//    }
//
//    public Facts getEnv() {
//        return env;
//    }
//
//
//    public void add(RulesResult value) {
//        List<RulesResult> list = this.results.getList(RULES_RESULTS_KEY);
//        if (Objects.isNull(list)) {
//            list = new CopyOnWriteArrayList<>();
//            this.results.put(RULES_RESULTS_KEY, list);
//        }
//        list.add(value);
//    }
//
//    @Override
//    public boolean isHit() {
//        List<RulesResult> list = this.results.getList(RULES_RESULTS_KEY);
//        if (Objects.nonNull(list)) {
//            return list.stream().anyMatch(RulesResult::isHit);
//        }
//        return false;
//    }
//
//    public Map<String, Object> getFactHandles() {
//        return this.facts;
//    }
//
//    public void setFactHandles(Facts facts) {
//        this.facts = facts;
//    }
//
//
//    @Override
//    public String toString() {
//        return "ExecutionResultImpl{" +
//                "results=" + results +
//                ",\n facts=" + facts +
//                ",\n env=" + env +
//                "\n}";
//    }
//}
