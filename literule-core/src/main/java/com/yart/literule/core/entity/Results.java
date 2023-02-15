package com.yart.literule.core.entity;

import com.yart.literule.core.context.ExecutionResults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Results extends BaseMapEntity<String, Object> implements ExecutionResults, Serializable {
    private final static String ENV_FACTS_KEY = "env";

    public Results() {
    }

    public Results(Facts envFacts) {
        this.put(ENV_FACTS_KEY, envFacts);
    }

    @Override
    public Collection<String> getIdentifiers() {
        return this.keySet();
    }

    @Override
    public Object getValue(String identifier) {
        return this.get( identifier );
    }

    @Override
    public Object getFactHandle(String identifier) {
        return getEnvNotNull().get(identifier);
    }

    @Override
    public List<RulesResult> getRulesResults() {
        List<RulesResult> res = this.getList(RULES_RESULTS_KEY);
        return Objects.isNull(res) ? new ArrayList<>() : res;
    }

    @Override
    public void addResult(RulesResult value) {
        List<RulesResult> list = this.getList(RULES_RESULTS_KEY);
        if (Objects.isNull(list)) {
            list = new CopyOnWriteArrayList<>();
            this.put(RULES_RESULTS_KEY, list);
        }
        list.add(value);
    }

    @Override
    public boolean isHit() {
        List<RulesResult> list = this.getList(RULES_RESULTS_KEY);
        if (Objects.nonNull(list)) {
            return list.stream().anyMatch(RulesResult::isHit);
        }
        return false;
    }
    public Facts getEnv() {
        return this.get(ENV_FACTS_KEY, Facts.class);
    }

    public void setEnv(Facts env){
        this.put(ENV_FACTS_KEY,env);
    }

    private Facts getEnvNotNull() {
        Facts facts = getEnv();
        return Objects.isNull(facts) ? new Facts():facts;
    }

    @Override
    public String toString() {
        return "Results{" +
                "results=" + super.toString() +
                ",\n env=" + getEnv() +
                "\n}";
    }
}
