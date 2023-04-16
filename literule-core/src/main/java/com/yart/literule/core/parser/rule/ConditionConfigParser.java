package com.yart.literule.core.parser.rule;

import com.yart.literule.core.admin.DataBus;
import com.yart.literule.core.context.WorkingMemory;
import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.core.internal.util.DataUtils;
import com.yart.literule.core.model.data.DataType;
import com.yart.literule.core.model.data.DataValue;
import com.yart.literule.core.model.flow.NodeType;
import com.yart.literule.core.model.rule.ConditionConfig;
import com.yart.literule.core.model.rule.Op;
import com.yart.literule.core.parser.model.FactorValue;
import com.yart.literule.core.runtime.assertor.AssertorEvaluator;

import java.util.List;
import java.util.Objects;

public class ConditionConfigParser {

    @SuppressWarnings("rawtypes")
    public FactorValue conditionParse(ConditionConfig condition, WorkingMemory memory){
        FactorValue result = new FactorValue();
        result.setId(condition.getId());
        result.setTag(condition.getTag());
        result.setType(NodeType.Rule.name());
        DataValue left = DataBus.getDataValue(condition.getLeft(), memory);
        DataValue right = DataBus.getDataValue(condition.getRight(), memory);
        Op op = Op.parse(condition.getOp());
        DataType datatype = DataUtils.getDataType(left.getValue());
        if (left.hasError()) {
            result.setError(left.getError());
            return result;
        }
        if (right.hasError()) {
            result.setError(left.getError());
            return result;
        }
        // TODO 变量加工. 变量信息记录.
        try {
            if (left.getValue() instanceof List) {
                List leftVal = (List) left.getValue();
                if (CollectionUtil.isEmpty(leftVal)) {
                    return result;
                }
            }
            if (Objects.nonNull(left.getValue()) && Objects.nonNull(right.getValue())) {
                boolean res= AssertorEvaluator.getInstance().evaluate(left,right,datatype,op);
                result.setHit(res);
            } else {
                // TODO. 值存在空的情况处理
            }
        } catch (Exception e){
            result.setError(e);
        }
        return result;
    }

}
