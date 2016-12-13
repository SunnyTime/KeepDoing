package com.team7619.keepdoing.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dushiguang on 16/12/13.
 */
public class SharedPreferencesUtil {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferencesUtil mInstance;
    private SharedPreferencesUtil(Context context) {
        String packageName = context.getPackageName();
        mSharedPreferences = context.getSharedPreferences(packageName, 0);
    }

    public static synchronized  SharedPreferencesUtil getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPreferencesUtil(context);
        }

        return mInstance;
    }

    /**
     * 保持String
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        synchronized (mSharedPreferences) {
            mSharedPreferences.edit().putString(key, value).commit();
        }
    }

    /**
     * 获取String
     * @param key
     * @return
     */
    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * 有默认值
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        synchronized (mSharedPreferences) {
            return mSharedPreferences.getString(key, defValue);
        }
    }

    //TODO 后续完成保存和获取int，double，Long，hashmap，list，array等类型数据
    public void putObject(String key, Object obj) {
        String saveStr = JsonUtil.toJsonString(obj);
        putString(key, saveStr);
    }

    public <T> T getObject(String key, Class<T> calss) {
        String temp = getString(key);

        if(temp == null || temp.trim().length() == 0) {
            return null;
        }

        return JsonUtil.parseObkect(temp, calss);
    }
}
