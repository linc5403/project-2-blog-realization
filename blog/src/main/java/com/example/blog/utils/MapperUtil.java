package com.example.blog.utils;

import java.lang.reflect.Array;
import java.util.*;

public class MapperUtil {

  private static Boolean isCustomizedClass(Object o) {
    return o != null && o.getClass().getClassLoader() != null;
  }

  private static Object innerDel(Object o, Set<Object> set) throws IllegalAccessException {
    if (o != null) {
      // 1. 判断o的类型是否是Java类
      var clz = o.getClass();

      // 如果输入的是一个数组，则应该返回一个list
      if (clz.isArray()) {
        int len = Array.getLength(o);
        var list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
          var e = Array.get(o, i);
          if (!(isCustomizedClass(e) && set.contains(e))) {
            if (isCustomizedClass(e)) set.add(e);
            var del = innerDel(e, set);
            if (del != null) {
              list.add(del);
            }
          }
        }
        return list.size() == 0 ? null : list;
      }

      // List 和 Set的处理
      if (List.class.isAssignableFrom(clz) || Set.class.isAssignableFrom(clz)) {
        var list = new ArrayList<>();
        for (var e : (Collection) o) {
          if (!(isCustomizedClass(e) && set.contains(e))) {
            if (isCustomizedClass(e)) set.add(e);
            var del = innerDel(e, set);
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
          if (!(isCustomizedClass(v) && set.contains(v))) {
            if (isCustomizedClass(v)) set.add(v);
            var del = innerDel(((Map) o).get(key), set);
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
          if (!(isCustomizedClass(v) && set.contains(v))) {
            if (isCustomizedClass(v)) set.add(v);
            var del = innerDel(v, set);
            if (del != null) map.put(field.getName(), del);
          }
        }
      }
      return map.size() == 0 ? null : map;
    } else {
      return null;
    }
  }

  public static Object removeNullFields(Object o) throws IllegalAccessException {
    // 每次被外面调用的时候都需要申请新的存储空间来过滤重复项
    var set = new HashSet<>();
    return innerDel(o, set);
  }
}
