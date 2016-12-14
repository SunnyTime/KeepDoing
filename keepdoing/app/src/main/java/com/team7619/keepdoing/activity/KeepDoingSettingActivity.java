package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by dushiguang on 16/12/14.
 */
@EActivity(R.layout.activity_setting)
public class KeepDoingSettingActivity extends BaseActivity{

    @AfterViews
    public void afterView() {
        setPageLable(R.string.setting);
        setBackIcon(IconFont.IC_BACK);
    }

    /**
     * 注销账号
     */
    @Click(R.id.log_out_btn)
    public void logOutClock() {
        BmobUser.logOut();
        removeAllActivity();
        finish();
        KeepDoingRegistActivity.jumpToKeepDoingRegistActivity(this);
    }

    public static void jumpToKeepDoingSettingActivity(Context context) {
        Intent intent = new Intent(context, KeepDoingSettingActivity_.class);
        context.startActivity(intent);
    }
}
