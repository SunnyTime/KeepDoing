package com.team7619.keepdoing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team7619.keepdoing.Bmob.BmobUtils;
import com.team7619.keepdoing.Iconfont.Icon;
import com.team7619.keepdoing.Iconfont.IconFontUtil;
import com.team7619.keepdoing.widget.CustomerProgressDialog;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.BmobUser;

/**
 * Created by ex-dushiguang201 on 2016-06-08.
 */
@EActivity
public abstract class BaseActivity extends FragmentActivity {
    @ViewById(R.id.title_normal)
    public RelativeLayout mTitleNormal;
    @ViewById(R.id.title_back_tv)
    public TextView mTitleBack;
    @ViewById(R.id.title_pagelabel_tv)
    public TextView mTitlePageLable;
    @ViewById(R.id.right_tv)
    public TextView mRight;

    @App
    public com.team7619.keepdoing.App mApplication;

    private CustomerProgressDialog dialog;
    private BaseActivity context;
    public BmobUtils mBmobUtils;
    protected BmobUser user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消title
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mBmobUtils = new BmobUtils();
        mBmobUtils.initBmob(this);
        user = mApplication.getUseInfo();
        context = this;
        addActivity();
    }

    /**
     * 添加Activity
     */
    private void addActivity() {
        mApplication.addActivity(context);
    }

    /**
     * 销毁指定的Activity
     */
    public void removeActivity() {
        mApplication.removeActivity(context);
    }

    /**
     * 销毁所有的Activity，一般在注销时使用
     */
    public void removeAllActivity() {
        mApplication.removeAllActivity();
    }

    protected void setTitleNormalGone() {
        mTitleNormal.setVisibility(View.GONE);
    }

    protected void setBackIcon(Icon icon) {
        IconFontUtil.setIcon(this, mTitleBack, "#4CAF50", icon);
    }

    public void showToast(int strId){
        showToast(getString(strId));
    }

    public void showToast(String str) {
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
    }

    public void setPageLable(int strId) {
        mTitlePageLable.setText(getResources().getString(strId));
    }

    public void setRightButtonIcon(Icon icon) {
        IconFontUtil.setIcon(this, mRight, icon);
    }

    public void setRightButtonText() {
        mRight.setText("");
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

    @Click(R.id.title_back_tv)
    public void backClick() {
        finish();
    }

}
