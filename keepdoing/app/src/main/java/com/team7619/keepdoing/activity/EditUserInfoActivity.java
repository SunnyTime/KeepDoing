package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.Utils.FileUtil;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

/**
 * Created by dushiguang on 16/12/12.
 */
@EActivity(R.layout.activity_edit_user_info)
public class EditUserInfoActivity extends BaseActivity {
    @ViewById(R.id.user_pic_roundiv)
    RoundImageView mUserPicRoundIv;

    private static final int ALBUM_REQEST_CODE = 1;

    @AfterViews
    public void afterViews() {

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
            return new File(path);
        }
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

    public static void jumpToEditUserInfoActivity(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity_.class);
        context.startActivity(intent);
    }

}