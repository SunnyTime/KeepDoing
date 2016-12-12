package com.team7619.keepdoing.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.team7619.keepdoing.Iconfont.Icon;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.Iconfont.IconFontUtil;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.activity.EditUserInfoActivity;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
@EFragment(R.layout.fragment_person_center)
public class PersonCenterFragment extends Fragment {
    @ViewById(R.id.title_pagelabel_tv)
    TextView mTitlePagelabel;
    @ViewById(R.id.user_pic)
    RoundImageView mUserPic;
    @ViewById(R.id.user_name_tv)
    TextView mUserName;
    @ViewById(R.id.edit_user_info_tv)
    TextView mEditUserInfoIcon;

    @AfterViews
    public void afterViews() {
        IconFontUtil.setIcon(getContext(),mEditUserInfoIcon, IconFont.IC_EDITOR_USER_INFO);
        mTitlePagelabel.setText("个人中心");
        //mUserPic.setBackground();
    }

    private Drawable getpicFromAlbum() {
        return null;
    }

    @Click(R.id.edit_user_info_tv)
    public void editUserInfoClick() {
        EditUserInfoActivity.jumpToEditUserInfoActivity(getContext());
    }
}

