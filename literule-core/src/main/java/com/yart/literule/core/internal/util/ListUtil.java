package com.yart.literule.core.internal.util;

import java.util.*;

@SuppressWarnings("unchecked")
public class ListUtil {
    @SafeVarargs
    public static <T> ArrayList<T> toList(T... values) {
        return (ArrayList)list(false, values);
    }
    @SafeVarargs
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (CollectionUtil.isEmpty(values)) {
            return list(isLinked);
        } else {
            List<T> arrayList = isLinked ? new LinkedList() : new ArrayList(values.length);
            Collections.addAll((Collection)arrayList, values);
            return (List)arrayList;
        }
    }
}
