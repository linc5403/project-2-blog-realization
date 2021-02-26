package com.example.blog.utils;

import java.lang.reflect.Array;
import java.util.*;

public class MapperUtil {
  public static Map<String, Object> removeNullFields(Object o) throws IllegalAccessException {
    if (o != null) {
      var mapper = new HashMap<String, Object>();
      var fields = o.getClass().getDeclaredFields();
      for (var field : fields) {
        field.setAccessible(true);
        var v = field.get(o);
        if (v == null) {
          continue;
        }

        var cls = v.getClass();
        if (cls.isArray()) {
          int len = Array.getLength(v);
          var list = new ArrayList<Map<String, Object>>();
          for (int i = 0; i < len; i++) {
            list.add(removeNullFields(Array.get(v, i)));
          }
          if (list.size() != 0) {
            mapper.put(field.getName(), list);
          }
        } else if (List.class.isAssignableFrom(cls) || Set.class.isAssignableFrom(cls)) {
          var list = new ArrayList<Map<String, Object>>();
          for (var e : (Collection) v) {
            list.add(removeNullFields(e));
          }
          if (list.size() != 0) {
            mapper.put(field.getName(), list);
          }
        } else if (Map.class.isAssignableFrom(cls)) {
          var map = new HashMap<String, Object>();
          for (var key : ((Map) v).keySet()) {
            map.put(key.toString(), removeNullFields(v).get(key));
          }
          if (map.size() != 0) {
            mapper.put(field.getName(), map);
          }
        } else if (cls.getClassLoader() == null) {
          // Java 类型
          mapper.put(field.getName(), v);
        } else {
          // 自定义类型
          var innerMapper = removeNullFields(field.get(o));
          if ((innerMapper != null) && (!innerMapper.isEmpty())) {
            mapper.put(field.getName(), innerMapper);
          }
        }
      }
      return mapper;
    } else {
      return null;
    }
  }
}
