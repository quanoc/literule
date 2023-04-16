package com.yart.literule.core.session;

import com.yart.literule.core.context.ExecutionResults;
import com.yart.literule.core.model.basic.Facts;

/**
 * 规则会话，用于执行规则.
 */
public interface RuleSession {
    ExecutionResults execute(String gid, Facts facts);
}
