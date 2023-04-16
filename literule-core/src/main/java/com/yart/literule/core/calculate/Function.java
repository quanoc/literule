package com.yart.literule.core.calculate;

import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.model.data.DataType;

/**
 * 事实计算.
 */
public interface Function {
    Object execute(Object obj, DataType dtype, WorkingMemory workingMemory);

    String name();
}
