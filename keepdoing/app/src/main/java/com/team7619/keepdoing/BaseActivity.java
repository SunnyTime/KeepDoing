package com.team7619.keepdoing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.team7619.keepdoing.Bmob.BmobUtils;
import com.team7619.keepdoing.widget.CircularProgress;
import com.team7619.keepdoing.widget.CustomerProgressDialog;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

/**
 * Created by ex-dushiguang201 on 2016-06-08.
 */
@EActivity
public abstract class BaseActivity extends FragmentActivity {
    private CustomerProgressDialog dialog;
    public BmobUtils mBmobUtils;

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
        if(isFinishing()) {
            return;
        }
        if(null == dialog) {
            dialog = new CustomerProgressDialog(this);
            dialog.show();
        }
    }

    @UiThread
    public void closeProgress() {
        if(null != dialog) {
            dialog.closeProgress();
            dialog.dismiss();
        }
        dialog = null;
    }

}
