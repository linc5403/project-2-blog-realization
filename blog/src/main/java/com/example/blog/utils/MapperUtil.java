package com.example.blog.utils;

import java.lang.reflect.Array;
import java.util.*;

public class MapperUtil {

  private static Boolean isCustomizedClass(Object o) {
    return o != null && o.getClass().getClassLoader() != null;
  }

  private static final ThreadLocal<Set<Object>> mapper = ThreadLocal.withInitial(HashSet::new);

  @SuppressWarnings("rawtypes")
  public static Object removeNullFields(Object o) throws IllegalAccessException {
    if (o != null) {
      // 1. 判断o的类型是否是Java类
      var clz = o.getClass();

      // 如果输入的是一个数组，则应该返回一个list
      if (clz.isArray()) {
        int len = Array.getLength(o);
        var list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
          var e = Array.get(o, i);
          if (!(isCustomizedClass(e) && mapper.get().contains(e))) {

            var del = removeNullFields(e);

            if (del != null) {
              list.add(del);
            }
            if (isCustomizedClass(e)) mapper.get().add(e);
          }
        }
        return list.size() == 0 ? null : list;
      }

      // List 和 Set的处理
      if (List.class.isAssignableFrom(clz) || Set.class.isAssignableFrom(clz)) {
        var list = new ArrayList<>();
        for (var e : (Collection) o) {
          if (!(isCustomizedClass(e) && mapper.get().contains(e))) {
            if (isCustomizedClass(e)) mapper.get().add(e);
            var del = removeNullFields(e);
            if (del != null) {
              list.add(del);
            }
          }
        }
        return list.size() == 0 ? null : list;
      }

      // Map的处理
      if (Map.class.isAssignableFrom(clz)) {
        var map = new HashMap<String, Object>();
        for (var key : ((Map) o).keySet()) {
          var v = ((Map) o).get(key);
          if (!(isCustomizedClass(v) && mapper.get().contains(v))) {
            if (isCustomizedClass(v)) mapper.get().add(v);
            var del = removeNullFields(((Map) o).get(key));
            if (del != null) {
              map.put(key.toString(), del);
            }
          }
        }
        return map.size() == 0 ? null : map;
      }

      // 其他Java类
      if (clz.getClassLoader() == null) return o;

      // 用户自定义类
      var fields = o.getClass().getDeclaredFields();
      var map = new HashMap<String, Object>();
      for (var field : fields) {
        field.setAccessible(true);
        var v = field.get(o);
        if (v != null) {
          if (!(isCustomizedClass(v) && mapper.get().contains(v))) {
            var del = removeNullFields(v);
            if (del != null) {
              map.put(field.getName(), del);
            }
            if (isCustomizedClass(v)) mapper.get().add(v);
          }
        }
      }
      return map.size() == 0 ? null : map;
    } else {
      return null;
    }
  }
}
