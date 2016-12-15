package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.Utils.FileUtil;
import com.team7619.keepdoing.entity._User;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by dushiguang on 16/12/12.
 */
@EActivity(R.layout.activity_edit_user_info)
public class EditUserInfoActivity extends BaseActivity {
    @ViewById(R.id.user_pic_roundiv)
    RoundImageView mUserPicRoundIv;
    @ViewById(R.id.user_name_tv)
    TextView mUserName;

    @Extra(userInfoKey)
    _User mExUser;
    @Extra(userPicPathKey)
    String mUserPicPathStr;

    private String userPicUrlKey;
    private _User user;

    private static final int ALBUM_REQEST_CODE = 1;
    private static final String userInfoKey = "USERINFO";
    private static final String userPicPathKey = "USERPICPATH";

    @AfterViews
    public void afterViews() {
        setPageLable(R.string.edit_user_info_pagelable);
        setBackIcon(IconFont.IC_BACK);
        setViews();
        user = new _User();
    }

    private void setViews() {
        Bitmap bp = BitmapFactory.decodeFile(mUserPicPathStr);

        mUserPicRoundIv.setImageBitmap(bp);
        mUserName.setText(mExUser.getUsername());
    }

    private void takePhote() {
        String filePath = getDefaultFilePath();
    }

    private String getDefaultFilePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    private File getFile(Intent data) {
        String path = "";
        Uri uri = data.getData();
        path = FileUtil.getFilePath(this, uri);
        if(null == path) {
            return null;
        } else {
            uploadFile(path);
            return new File(path);
        }
    }

    @Background
    public void uploadFile(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));

        showprogress();
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    user.setUserpic(bmobFile);
                    updateUserPic();
                } else {
                    showToast("图像上传失败" + e.getMessage());
                }
            }
        });
    }

    @Background
    public void updateUserPic() {
        user.update(mApplication.getUseInfo().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    showToast("更新成功");
                } else {
                    showToast("更新失败" + e.getMessage());
                }
                closeProgress();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case ALBUM_REQEST_CODE:
                    if(data !=null && data.getData() != null) {
                        File temp = getFile(data);
                    }
                    break;
            }
        }
    }

    @Click(R.id.change_user_pic_lly)
    public void changeUserPicClick() {
        //TODO 添加拍照获取图片
        //takePhote();
        //通过本地相册获取图片
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //判断系统中是否存在该activity
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, ALBUM_REQEST_CODE);
        }
    }

    public static void jumpToEditUserInfoActivity(Context context, _User user, String userPicPath ) {
        Intent intent = new Intent(context, EditUserInfoActivity_.class);
        intent.putExtra(userInfoKey, user);
        intent.putExtra(userPicPathKey, userPicPath);
        context.startActivity(intent);
    }

}
