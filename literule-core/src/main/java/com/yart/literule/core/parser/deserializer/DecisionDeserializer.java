package com.yart.literule.core.parser.deserializer;

import com.yart.literule.core.model.decision.Decision;
import com.yart.literule.core.model.decision.DecisionType;

public interface DecisionDeserializer {
    Decision deserializer(String json);

    DecisionType type();
}
