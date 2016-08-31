package com.team7619.keepdoing.Bmob;

import android.content.Context;
import com.team7619.keepdoing.Key.keys;
import cn.bmob.v3.Bmob;


/**
 * Created by dushiguang on 16/8/25.
 */
public class BmobUtils {
//    private static BmobUtils mBmobUtils;
//
//    public static BmobUtils getBmobUtils(Context context) {
//        if (null == mBmobUtils) {
//            mBmobUtils = new BmobUtils();
//        }
//        Bmob.initialize(context, keys.BmobApplicationID);
//        return mBmobUtils;
//    }

    public void initBmob(Context context) {
        Bmob.initialize(context, keys.BmobApplicationID);
    }
}


