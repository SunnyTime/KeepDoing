package com.team7619.keepdoing.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.TextView;

import com.team7619.keepdoing.BaseFragment;
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
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
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
    @ViewById(R.id.public_article_icon)
    TextView mPublicArticleIcon;
    @ViewById(R.id.public_article_num)
    TextView mPublicArticleNum;

    private _User mUser;
    private String mUserPicPath;

    @AfterViews
    public void afterViews() {
        IconFontUtil.setIcon(getContext(),mEditUserInfoIcon, IconFont.IC_EDITOR_USER_INFO);
        IconFontUtil.setIcon(getContext(),mRightIcon, IconFont.IC_SETTING_ICON);
        IconFontUtil.setIcon(getContext(),mPublicArticleIcon, IconFont.IC_PUBLIC_ARTICLE_ICON);

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
                    mUser = user;
                    getUserPic(user);
                } else {
                    showToast("获取用户信息失败");
                }
            }
        });
    }

    @Background
    public void getUserPic(final _User info) {
        BmobFile file = info.getUserpic();
        if(file != null) {
            file.download(new DownloadFileListener() {
                @Override
                public void done(String s, BmobException e) {
                    mUserPicPath = s;
                    initViews(info, s);
                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });
        }
    }

    private void initViews(_User user, String picPath) {
        Bitmap bm = BitmapFactory.decodeFile(picPath);
        Uri mUri = Uri.parse(picPath);
        //mUserPic.setImageURI(mUri);
        mUserPic.setImageBitmap(bm );
        mUserName.setText(user.getUsername());
        mPublicArticleNum.setText(String.valueOf(user.getTotalarticle()));
    }
    @Click(R.id.edit_user_info_tv)
    public void editUserInfoClick() {
        EditUserInfoActivity.jumpToEditUserInfoActivity(getContext(), mUser, mUserPicPath);
    }

    @Click(R.id.right_tv)
    public void rightClick(){
        KeepDoingSettingActivity.jumpToKeepDoingSettingActivity(getContext());
    }
}

