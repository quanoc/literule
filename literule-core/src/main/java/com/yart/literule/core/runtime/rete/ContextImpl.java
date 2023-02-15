package com.yart.literule.core.runtime.rete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.debug.MessageItem;
import com.yart.literule.core.debug.MsgType;
import com.yart.literule.core.internal.util.StringUtil;
import com.yart.literule.core.internal.util.Utils;
import com.yart.literule.core.runtime.ElCalculator;
import com.yart.literule.core.runtime.assertor.AssertorEvaluator;


/**
 * @author Jacky.gao
 * @since 2015年1月8日
 */
public class ContextImpl implements Context {
	private Map<String,String> variableCategoryMap;
	private ValueCompute valueCompute;
	private WorkingMemory workingMemory;
	private List<MessageItem> debugMessageItems;
	private ElCalculator elCalculator=new ElCalculator();
	public ContextImpl(WorkingMemory workingMemory,
					   Map<String,String> variableCategoryMap,
					   List<MessageItem> debugMessageItems
	) {
		this.workingMemory=workingMemory;
		this.variableCategoryMap=variableCategoryMap;
		this.debugMessageItems=debugMessageItems;
		this.valueCompute=new ValueCompute();
	}
	@Override
	public WorkingMemory getWorkingMemory() {
		return workingMemory;
	}

	public AssertorEvaluator getAssertorEvaluator() {
		return AssertorEvaluator.getInstance();
	}
	
	@Override
	public Object parseExpression(String expression) {
		return elCalculator.eval(expression);
	}
	
	@Override
	public void debugMsg(String msg, MsgType type, boolean enableDebug) {
		if(!Utils.isDebug() || !enableDebug){
			return;
		}
		if(!Utils.isDebugToFile()){
			System.out.println(msg);
			return;
		}
		MessageItem item=new MessageItem(msg,type);
		debugMessageItems.add(item);		
	}
	
	@Override
	public List<MessageItem> getDebugMessageItems() {
		return debugMessageItems;
	}
	
	public String getVariableCategoryClass(String variableCategory) {
		String clazz=variableCategoryMap.get(variableCategory);
		if(StringUtil.isEmpty(clazz)){
			//throw new RuleException("Variable category ["+variableCategory+"] not exist.");
			clazz=HashMap.class.getName();
		}
		return clazz;
	}
	public ValueCompute getValueCompute() {
		return valueCompute;
	}
}
