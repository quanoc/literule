package com.yart.literule.core.runtime.knowledge;

import com.yart.literule.core.model.decision.Decision;
import com.yart.literule.core.model.Rete;
import com.yart.literule.core.rule.Rule;

import java.util.Map;

/**
 * 知识包定义.
 */
public interface KnowledgePackage {
	/**
	 * 所有的决策列表, 包含规则集、决策流、决策树等.
	 */
	Map<String, Decision> getDecisionMap();

	Map<String, Rule> getRuleMap();

	Rete getRete();
//	Map<String,String> getVariableCateogoryMap();


	//	Map<String, String> getParameters();
//	ReteInstance newReteInstance();
	long getTimestamp();
	void resetTimestamp();
	//	List<Rule> getNoLhsRules();
//	List<Rule> getWithElseRules();
	String getId();
}