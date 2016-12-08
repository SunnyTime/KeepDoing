package com.team7619.keepdoing.Utils;

import android.content.Context;

/**
 * Created by dushiguang on 16/12/8.
 */
public class UiUtils {
    public static float Sp2px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }
}
