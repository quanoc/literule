package com.yart.literule.core.internal.util;

import com.yart.literule.core.entity.Facts;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Type convert for Facts data.
 * @author zhangquanquan
 */
public class TypeUtil {
    private final static Logger log = Logger.getLogger(TypeUtil.class.getSimpleName());
    /**
     * 从msg中获取 Long 值
     *
     * @param o 原始obj.
     * @return 取到的 Long
     */
    public static Long getLong(Object o) {
        if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof String) {
            return Long.valueOf((String) o);
        } else if (o instanceof Integer) {
            return Long.valueOf((Integer) o);
        }
        return null;
    }

    /**
     * 从msg中获取 Long 值
     *
     * @param o 原始obj.
     * @return 取到的 Long
     */
    public static Long getLong(Object o, long def) {
        if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof String) {
            return Long.valueOf((String) o);
        } else if (o instanceof Integer) {
            return Long.valueOf((Integer) o);
        }
        return def;
    }

    /**
     * 从msg中获取 Integer 值
     *
     * @param o 原始obj.
     * @return 取到的 Integer
     */
    public static Integer getInteger(Object o) {
        if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof String) {
            return Integer.valueOf((String) o);
        } else if (o instanceof Long) {
            return ((Long) o).intValue();
        }
        return null;
    }

    public static Double getDouble(Object o) {
        if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof String) {
            return Double.parseDouble((String) o);
        } else if (o instanceof Float) {
            return Double.valueOf((Float) o);
        }
        return null;
    }

    /**
     * 从msg中获取 String 值
     *
     * @param o 原始obj.
     * @return 取到的 String
     */
    public static String getString(Object o) {
        if (o instanceof String) {
            return (String) o;
        } else if (o == null) {
            return null;
        } else if (o instanceof Integer || o instanceof Long) {
            return o + "";
        } else if (o instanceof Boolean) {
            return (boolean) o ? "true" : "false";
        }
        return null;
    }

    /**
     * 从msg中获取 Boolean 值
     *
     * @param o 原始obj.
     * @return 取到的 Boolean
     */
    public static Boolean getBoolean(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Integer) {
            Integer io = (Integer) o;
            return io == 1;
        } else if (o instanceof Long) {
            Long io = (Long) o;
            return io == 1;
        }
        return null;
    }

    public static boolean getBool(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Integer) {
            Integer io = (Integer) o;
            return io == 1;
        } else if (o instanceof Long) {
            Long io = (Long) o;
            return io == 1;
        }
        return false;
    }

    /**
     * 从msg中获取 byte[] 值 . 图片数据等
     *
     * @param o 原始obj.
     * @return 取到的 byte[]
     */
    public static byte[] getBytes(Object o) {
        if (o instanceof byte[]) {
            return (byte[]) o;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(Object o, Class<T> tClass) {
        if (null == o) {
            return null;
        }
        if (tClass.isAssignableFrom(o.getClass())) {
            return (T) o;
        }
        return null;
    }

    public static Facts toFacts(Object obj) {
        return toFacts(obj, false);
    }

    public static Facts clone(Facts facts){
        Facts f = new Facts(facts.size());
        f.putAll(facts);
        return f;
    }

    /**
     * 统一大小写. Bean 转换成 Fact
     *
     * @param obj Obj
     * @return Fact
     */
    public static Facts toFacts(Object obj, boolean toLower) {
        Facts fact = new Facts();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String varName;
            if (toLower) {
                varName = field.getName().toLowerCase();
            } else {
                varName = field.getName();
            }
            try {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o != null) {
                    fact.put(varName, o);
                }
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                log.warning("error. obj:{}" + obj +"," + ex);
            }
        }
        return fact;
    }

    /**
     * 统一大小写. Bean 转换成 Fact
     *
     * @param obj Obj
     */
    public static void toMap(Map<String, Object> map, Object obj, boolean toLower) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName;
            if (toLower) {
                varName = field.getName().toLowerCase();
            } else {
                varName = field.getName();
            }
            // key统一 小写
            try {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o != null) {
                    map.put(varName, o);
                }
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                log.warning("error. obj:{}" + obj +"," + ex);
            }
        }
    }

    public static <T> T toBean(Facts fact, Class<T> clazz) {
        return toBean(fact, clazz, false);
    }

    /**
     * 统一大小写. Fact 转换成其他Bean
     *
     * @param fact msgEvent
     * @param clazz    Class
     * @param <T>      Type
     * @return Obj
     */
    public static <T> T toBean(Facts fact, Class<T> clazz, boolean toLower) {
        if (fact == null || fact.isEmpty()) {
            return null;
        }
        T obj;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String varName;
            if (toLower) {
                varName = field.getName().toLowerCase();
            } else {
                varName = field.getName();
            }
            try {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object o = fact.get(varName);
                if (o != null) {
                    field.set(obj, o);
                }
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                log.warning("error. obj:{}" + obj +"," + ex);
            }
        }
        return obj;
    }

    public static String getReqId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
