package com.nynu.goule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * json转换工具类
 */
public class JsonUtil {

    private static ObjectMapper objectMapper;
    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(JsonUtil.class);
    private static Map convertorMap = new HashMap<>();

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-mm-dd HH:MM:SS"));
    }

    private JsonUtil() {
    }

    /**
     * 将object对象转换为json对象
     */
    public static String convertObject2Json(Object object) {
        String method = "convertObject2Json";
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json对象转换为Object
     */
    public static Object convertJson2Object(String json, Class cls) {
        String method = "convertJson2Object";
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     */
    public static <T> List<Map<String, Object>> objects2Maps(List<T> objectList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (int i = 0, size = objectList.size(); i < size; i++) {
                T bean = objectList.get(i);
                Map<String, Object> map = bean2Map(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将对象转换成map
     */
    public static <T> Map<String, Object> bean2Map(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
}
