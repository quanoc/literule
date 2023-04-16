package com.yart.literule.core.runtime.knowledge;

import com.yart.literule.core.internal.util.TypeUtil;
import com.yart.literule.core.model.decision.Decision;
import com.yart.literule.core.model.Rete;
import com.yart.literule.core.rule.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class KnowledgePackageImpl implements KnowledgePackage {
	private Rete rete;
	private Map<String,String> variableCategoryMap=new HashMap<>();
	private final Map<String, Decision> decisionMap;
	private final Map<String, Rule> ruleMap;
	private Map<String, String> parameters;

	private long timestamp;
	private final String id = TypeUtil.getReqId();
	public KnowledgePackageImpl() {
		timestamp=System.currentTimeMillis();
		decisionMap = new ConcurrentHashMap<>();
		ruleMap = new ConcurrentHashMap<>();
	}


	public String getId() {
		return id;
	}

	@Override
	public Map<String, Decision> getDecisionMap() {
		return decisionMap;
	}

	@Override
	public Map<String, Rule> getRuleMap() {
		return ruleMap;
	}

	public Rete getRete() {
		return rete;
	}
	public void setRete(Rete rete) {
		this.rete = rete;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void resetTimestamp() {
		timestamp=System.currentTimeMillis();
	}


}
