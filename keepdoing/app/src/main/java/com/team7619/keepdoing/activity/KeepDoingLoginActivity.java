package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.Iconfont.IconFontUtil;
import com.team7619.keepdoing.MainActivity;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity._User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dushiguang on 16/12/14.
 */
@EActivity(R.layout.activity_login)
public class KeepDoingLoginActivity extends BaseActivity {
    @ViewById(R.id.user_name_icon)
    TextView mUserNameIcon;
    @ViewById(R.id.password_icon)
    TextView mPasswordIcon;

    @ViewById(R.id.user_name_et)
    EditText mUserName;
    @ViewById(R.id.password_et)
    EditText mPassword;

    private _User user;
    @AfterViews
    public void afterView() {
        user = new _User();
        setTitleNormalGone();
        setIcons();
    }

    private void setIcons() {
        IconFontUtil.setIcon(this, mUserNameIcon, IconFont.IC_USER_NAME_ICON);
        IconFontUtil.setIcon(this, mPasswordIcon, IconFont.IC_PASSWORD_ICON);
    }

    @Background
    public void requestLogin() {
        showprogress();
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e == null) {
                    showToast("登录成功");
                    mApplication.setUseInfo(BmobUser.getCurrentUser());
                    MainActivity.jumpToMainActivity(KeepDoingLoginActivity.this);
                    finish();
                } else {
                    showToast("登录失败," + e.getMessage());
                }
                closeProgress();
            }
        });
    }

    @Click(R.id.log_in_btn)
    public void logInClick() {
        if(TextUtils.isEmpty(mUserName.getText().toString())) {
            showToast("用户名不能为空");
            return;
        }

        if(TextUtils.isEmpty(mPassword.getText().toString())) {
            showToast("密码不能为空");
            return;
        }

        user.setUsername(mUserName.getText().toString());
        user.setPassword(mPassword.getText().toString());
        requestLogin();
    }

    /**
     * 跳转到注册界面
     */
    @Click(R.id.sign_up_btn)
    public void registClick() {
        KeepDoingRegistActivity.jumpToKeepDoingRegistActivity(this);
        finish();
    }

    public static void jumpToKeepDoingLoginActivity(Context context) {
        Intent intent = new Intent(context, KeepDoingLoginActivity_.class);
        context.startActivity(intent);
    }
}
