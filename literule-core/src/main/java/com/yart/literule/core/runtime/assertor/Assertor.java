package com.yart.literule.core.runtime.assertor;

import com.yart.literule.core.model.data.DataType;
import com.yart.literule.core.model.rule.Op;

/**
 * 断言器, 用于数据的比较.
 */
public interface Assertor {
	boolean eval(Object left, Object right, DataType dataType);
	boolean support(Op op);
}