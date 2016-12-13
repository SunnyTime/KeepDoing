package com.team7619.keepdoing.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.Iconfont.IconFontUtil;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity._User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dushiguang on 16/12/13.
 */
@EActivity(R.layout.activity_regist)
public class KeepDoingRegistActivity extends BaseActivity {
    @ViewById(R.id.user_name_icon)
    TextView mUserNameIcon;
    @ViewById(R.id.user_phone_icon)
    TextView mUserPhoneIcon;
    @ViewById(R.id.password_icon)
    TextView mPasswordIcon;

    @ViewById(R.id.user_name_et)
    EditText mUserName;
    @ViewById(R.id.phone_et)
    EditText mPhone;
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
        IconFontUtil.setIcon(this, mUserPhoneIcon, IconFont.IC_PHONE_ICON);
        IconFontUtil.setIcon(this, mPasswordIcon, IconFont.IC_PASSWORD_ICON);
    }

    @Click(R.id.regist_btn)
    public void registClick() {
        if(TextUtils.isEmpty(mUserName.getText().toString())){
            showToast("用户名不能为空");
            return;
        }

        if(TextUtils.isEmpty(mPhone.getText().toString())) {
            showToast("手机号不能为空");
            return;
        }

        if(TextUtils.isEmpty(mPassword.getText().toString())) {
            showToast("密码不能为空");
            return;
        }

        user.setUsername(mUserName.getText().toString());
        user.setMobilePhoneNumber(mPhone.getText().toString());
        user.setPassword(mPassword.getText().toString());

        requstRegist();
    }

    @Background
    public void requstRegist() {
        showprogress();
        user.signUp(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                if(e == null) {
                    showToast("注册成功");
                } else {
                    showToast("注册失败");
                }

                closeProgress();
            }
        });
    }
}
