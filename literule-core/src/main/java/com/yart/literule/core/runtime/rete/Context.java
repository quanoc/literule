package com.yart.literule.core.runtime.rete;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.debug.MessageItem;
import com.yart.literule.core.debug.MsgType;
import com.yart.literule.core.runtime.assertor.AssertorEvaluator;

import java.util.List;

public interface Context {
	AssertorEvaluator getAssertorEvaluator();
	ValueCompute getValueCompute();
	String getVariableCategoryClass(String variableCategory);
	WorkingMemory getWorkingMemory();
	Object parseExpression(String expression);
	List<MessageItem> getDebugMessageItems();
	void debugMsg(String msg, MsgType type, boolean debug);
}