package com.yart.literule.core.internal.util;

import com.yart.literule.core.internal.exception.RuleStorageException;

import java.util.Objects;

public class ParamUtil {
    public static void notNull4Config(Object obj){
        if (Objects.isNull(obj)) {
            throw new RuleStorageException("config error!");
        }
    }
}
