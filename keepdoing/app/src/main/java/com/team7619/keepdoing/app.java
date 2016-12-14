package com.team7619.keepdoing;

import android.app.Activity;
import android.app.Application;

import org.androidannotations.annotations.EApplication;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by dushiguang on 16/12/14.
 */
@EApplication
public class App extends Application {
    private BmobUser useInfo = null;
    private List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
    }

    /**
     * 添加Activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if(!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 销毁单个Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if(activityList.contains(activity)) {
            activityList.remove(activity);
            activity.finish();
        }
    }

    public void removeAllActivity() {
        for (Activity activity: activityList) {
            activity.finish();
        }
    }

    public BmobUser getUseInfo() {
        if(useInfo == null) {
            useInfo = new BmobUser();
        }
        return useInfo;
    }

    public void setUseInfo(BmobUser useInfo) {
        if(useInfo == null) {
            return;
        }
        this.useInfo = useInfo;
    }
}
