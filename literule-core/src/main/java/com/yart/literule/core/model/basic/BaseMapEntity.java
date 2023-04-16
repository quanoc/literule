package com.yart.literule.core.model.basic;

import com.yart.literule.core.internal.util.TypeUtil;

import java.util.*;
import java.util.logging.Logger;

/**
 * Map的封装. 实现类型的自动转换.
 * @author zhangquanquan 2022.07.28.
 */
public class BaseMapEntity<K, V> extends HashMap<K, V> {
    protected Logger log = Logger.getLogger(super.getClass().getSimpleName());
    public BaseMapEntity() {}
    public BaseMapEntity(int initialCapacity) {
        super(initialCapacity);
    }
    /**
     * 类型必须 byte[] 的value值. 否则都返回空(null)
     *
     * @param key key
     */
    public byte[] getBytes(Object key) {
        return TypeUtil.getBytes(this.get(key));
    }

    /**
     * @param key    key
     * @param tClass 需要的类型，如果没有指定类型(即便key存在)，返回空
     * @param <T>    type
     */
    public <T> T get(Object key, Class<T> tClass) {
        return TypeUtil.parse(this.get(key), tClass);
    }

    /**
     * @param key key
     */
    public String getString(Object key) {
        return TypeUtil.getString(this.get(key));
    }

    public Boolean getBoolean(Object key) {
        return TypeUtil.getBoolean(this.get(key));
    }

    public boolean getBool(Object key) {
        return TypeUtil.getBool(this.get(key));
    }

    /**
     * @param key key
     */
    public Long getLong(Object key) {
        return TypeUtil.getLong(this.get(key));
    }

    public Integer getInteger(Object key) {
        return TypeUtil.getInteger(this.get(key));
    }

    public int getInt(Object key) {
        Integer res = TypeUtil.getInteger(this.get(key));
        return res == null ? -1 : res;
    }

    public Double getDouble(Object key) {
        return TypeUtil.getDouble(this.get(key));
    }


    @SuppressWarnings("unchecked")
    public <T> List<T> getList(Object key) {
        Object o = this.get(key);
        try {
            if (o instanceof List) {
                return (List<T>) o;
            } else if (o instanceof String) {
                String[] l = ((String) o).split(",");
                return (List<T>) Arrays.asList(l);
            }
            return null;
        } catch (Exception e) {
            log.warning("error." + e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> getSet(Object key) {
        Object o = this.get(key);
        try {
            if (o instanceof Set) {
                return (Set<T>) o;
            } if (o instanceof List) {
                return new HashSet<>((List<T>) o);
            }else if (o instanceof String) {
                String[] l = ((String) o).split(",");
                return new HashSet<>((List<T>) Arrays.asList(l));
            }
            return null;
        } catch (Exception e) {
            log.warning("error." + e);
            return null;
        }
    }
}
