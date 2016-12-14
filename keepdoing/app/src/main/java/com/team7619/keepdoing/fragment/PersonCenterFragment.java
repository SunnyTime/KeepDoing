package com.team7619.keepdoing.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.team7619.keepdoing.BaseFragment;
import com.team7619.keepdoing.Iconfont.Icon;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.Iconfont.IconFontUtil;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.activity.EditUserInfoActivity;
import com.team7619.keepdoing.activity.KeepDoingSettingActivity;
import com.team7619.keepdoing.entity._User;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
@EFragment(R.layout.fragment_person_center)
public class PersonCenterFragment extends BaseFragment {
    @ViewById(R.id.title_pagelabel_tv)
    TextView mTitlePagelabel;
    @ViewById(R.id.right_tv)
    TextView mRightIcon;
    @ViewById(R.id.user_pic)
    RoundImageView mUserPic;
    @ViewById(R.id.user_name_tv)
    TextView mUserName;
    @ViewById(R.id.edit_user_info_tv)
    TextView mEditUserInfoIcon;

    @AfterViews
    public void afterViews() {
        IconFontUtil.setIcon(getContext(),mEditUserInfoIcon, IconFont.IC_EDITOR_USER_INFO);
        IconFontUtil.setIcon(getContext(),mRightIcon, IconFont.IC_SETTING_ICON);

        mTitlePagelabel.setText("个人中心");
        getUserInfo();
    }

    @Background
    public void getUserInfo() {
        BmobQuery<_User> query = new BmobQuery<>();

        query.getObject(mApplication.getUseInfo().getObjectId(), new QueryListener<_User>() {
            @Override
            public void done(_User user, BmobException e) {
                if(e == null) {
                    initViews(user);
                } else {
                    showToast("获取用户信息失败");
                }
            }
        });
    }

    private void initViews(_User user) {
        mUserName.setText(user.getUsername());
    }
    @Click(R.id.edit_user_info_tv)
    public void editUserInfoClick() {
        EditUserInfoActivity.jumpToEditUserInfoActivity(getContext());
    }

    @Click(R.id.right_tv)
    public void rightClick(){
        KeepDoingSettingActivity.jumpToKeepDoingSettingActivity(getContext());
    }
}

