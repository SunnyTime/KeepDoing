package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.Iconfont.IconFontUtil;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity._User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dushiguang on 16/12/14.
 */
@EActivity(R.layout.activity_login)
public class KeepDoingLoginActivity extends BaseActivity {
    @ViewById(R.id.user_phone_icon)
    TextView mUserPhoneIcon;
    @ViewById(R.id.password_icon)
    TextView mPasswordIcon;

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
        IconFontUtil.setIcon(this, mUserPhoneIcon, IconFont.IC_PHONE_ICON);
        IconFontUtil.setIcon(this, mPasswordIcon, IconFont.IC_PASSWORD_ICON);
    }

    public static void jumpToKeepDoingLoginActivity(Context context) {
        Intent intent = new Intent(context, KeepDoingLoginActivity_.class);
        context.startActivity(intent);
    }
}
