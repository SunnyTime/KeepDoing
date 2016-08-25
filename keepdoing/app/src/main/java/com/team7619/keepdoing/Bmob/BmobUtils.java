package com.team7619.keepdoing.Bmob;

import android.content.Context;
import com.team7619.keepdoing.Key.keys;
import cn.bmob.v3.Bmob;


/**
 * Created by dushiguang on 16/8/25.
 */
public class BmobUtils {
    private Context context;
    private static BmobUtils mBmobUtils;

    public static BmobUtils getBmobUtils() {
        if (null == mBmobUtils) {
            mBmobUtils = new BmobUtils();
        }
        return mBmobUtils;
    }

    public void initBmob(Context context) {
        this.context = context;
        Bmob.initialize(context, keys.BmobApplicationID);
    }
}


