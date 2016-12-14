package com.team7619.keepdoing.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.MainActivity;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity._User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.BmobUser;

/**
 * Created by dushiguang on 16/12/14.
 */
@EActivity(R.layout.activity_logo)
public class LogoActivity extends BaseActivity {

    @ViewById(R.id.title_tv)
    TextView mTitle;

    private BmobUser user;
    private ScaleAnimation animationShow;
    @AfterViews
    public void afterViews() {
        setTitleNormalGone();
        setScaleAnimation();
        mTitle.startAnimation(animationShow);
        mTitle.setVisibility(View.VISIBLE);
        getCurrentUser();
    }

    private void getCurrentUser() {
        user = BmobUser.getCurrentUser();

        if(user != null) {
            mApplication.setUseInfo(user);
            MainActivity.jumpToMainActivity(this);
        } else {
            KeepDoingRegistActivity.jumpToKeepDoingRegistActivity(this);
        }
        finish();
    }

    /**
     * 设置缩放动画
     */
    private void setScaleAnimation() {
        animationShow = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animationShow.setDuration(1000);
    }
}
