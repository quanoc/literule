package com.yart.literule.support.calcuate.text;

import com.yart.literule.core.model.data.DataType;
import com.yart.literule.core.calculate.Function;
import com.yart.literule.core.context.WorkingMemory;

public abstract class TextFunction implements Function {

    protected String name;

    public TextFunction(String name) {
        this.name = name;
    }

    abstract String execute(String text, WorkingMemory workingMemory);

    @Override
    public Object execute(Object obj, DataType dtype, WorkingMemory workingMemory) {
        if (!dtype.equals(DataType.String)) {
            return null;
        }
        return execute((String) obj, workingMemory);
    }

    @Override
    public String name() {
        return name;
    }
}
