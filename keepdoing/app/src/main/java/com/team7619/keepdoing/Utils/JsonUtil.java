package com.team7619.keepdoing.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dushiguang on 16/12/13.
 */
public class JsonUtil {

    /**
     * 解析实体
     * @param jsonStr
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> entityClass) {
        T ret = null;
        ret = JSON.parseObject(jsonStr, entityClass);
        return ret;
    }

    public static <T> T parseObkect(String jsonStr, Type type) {
        T ret = null;
        ret = JSON.parseObject(jsonStr, type, Feature.AutoCloseSource);
        return ret;
    }

    public static <T> T parseObject(String jsonStr, TypeReference<T> tf) {
        T ret = null;
        ret = JSON.parseObject(jsonStr, tf, Feature.AutoCloseSource);
        return ret;
    }

    /**
     * 解析List
     * @param jsonStr
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T>List<T> parseList(String jsonStr, Class<T> entityClass) {
        List<T> ret = null;
        ret = JSON.parseArray(jsonStr, entityClass);
        return ret;
    }

    public static String toJsonString(Object obj) {
        String ret;
        ret = JSON.toJSONString(obj);
        return ret;

    }


}















