package com.yart.literule.core.calculate;

import com.yart.literule.core.context.WorkingMemory;

/**
 * 事实计算.
 */
public interface Function {
    Object execute(Object obj, DType dtype, WorkingMemory workingMemory);

    String name();
}
