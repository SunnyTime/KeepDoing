package com.team7619.keepdoing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.team7619.keepdoing.Bmob.BmobUtils;

import org.androidannotations.annotations.EActivity;

/**
 * Created by ex-dushiguang201 on 2016-06-08.
 */
@EActivity
public abstract class BaseActivity extends FragmentActivity {
    public BmobUtils mBmobUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消title
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mBmobUtils = new BmobUtils();
        mBmobUtils.initBmob(this);
    }
}
