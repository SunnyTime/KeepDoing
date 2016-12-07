package com.team7619.keepdoing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.team7619.keepdoing.Bmob.BmobUtils;
import com.team7619.keepdoing.widget.CircularProgress;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

/**
 * Created by ex-dushiguang201 on 2016-06-08.
 */
@EActivity
public abstract class BaseActivity extends FragmentActivity {
    public BmobUtils mBmobUtils;
    private CircularProgress progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消title
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mBmobUtils = new BmobUtils();
        mBmobUtils.initBmob(this);
    }

    public void showToast(int strId){
        showToast(getString(strId));
    }
    public void showToast(String str) {
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    public void showprogress() {
        if(null == progressView) {
            progressView = new CircularProgress(this);
            progressView.setIndeterminate(true);
            progressView.startAnimation();
        }
    }

    @UiThread
    public void closeProgress() {
        if(null != progressView) {
            progressView.stopAnimation();
        }
        progressView = null;
    }

}
