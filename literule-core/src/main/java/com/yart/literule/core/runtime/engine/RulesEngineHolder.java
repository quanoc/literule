package com.yart.literule.core.runtime.engine;

import com.yart.literule.core.rule.RulesEngine;
import com.yart.literule.core.rule.RulesEngine.EngineType;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class RulesEngineHolder {
    private static final RulesEngineHolder instance = new RulesEngineHolder();
    private RulesEngineHolder(){
        ServiceLoader<RulesEngine> loader = ServiceLoader.load(RulesEngine.class);
        for (RulesEngine engine: loader) {
            engineMap.put(engine.engineType(), engine);
        }
    }
    public static RulesEngineHolder getInstance(){
        return instance;
    }
    private final Map<EngineType, RulesEngine> engineMap = new HashMap<>();
    public RulesEngine getEngine(EngineType type){
        return engineMap.get(type);
    }

}
