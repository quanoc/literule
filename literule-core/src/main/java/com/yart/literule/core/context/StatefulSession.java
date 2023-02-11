package com.yart.literule.core.context;

import com.yart.literule.core.entity.Facts;

public class StatefulSession implements WorkingMemory, RuleSession{
    Facts envFacts;
    ExecutionResults results;
    // 一个会话处理一个facts. 会话复用呢?
    public StatefulSession() {
    }

    public StatefulSession(Facts envFacts, ExecutionResults results) {
        this.envFacts = envFacts;
        this.results = results;
    }

    @Override
    public void addPropagation(PropagationEntry propagationEntry) {

    }

    @Override
    public void addPropagation(Facts env) {
        envFacts = env;
    }

    @Override
    public Facts facts() {
        return envFacts;
    }

    @Override
    public ExecutionResults results() {
        return results;
    }

    @Override
    public void setResult(ExecutionResults results) {
        this.results = results;
    }
}
