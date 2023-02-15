package com.yart.literule.core.runtime.assertor;

import com.yart.literule.core.calculate.DType;
import com.yart.literule.core.model.rule.Op;

/**
 * 断言器, 用于数据的比较.
 */
public interface Assertor {
	boolean eval(Object left, Object right, DType dataType);
	boolean support(Op op);
}