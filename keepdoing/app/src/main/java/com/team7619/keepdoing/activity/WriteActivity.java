package com.team7619.keepdoing.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.MainActivity;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity.Article_Context;
import com.team7619.keepdoing.entity.Space_Info;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
@EActivity(R.layout.fragment_write)
public class WriteActivity extends BaseActivity{
    @ViewById(R.id.publish_btn)
    TextView mPublish_Btn;
    @ViewById(R.id.ic_more_tv)
    TextView mIcMore;

    @ViewById(R.id.title_et)
    EditText mTitle;
    @ViewById(R.id.content_et)
    EditText mContent;

    private Space_Info space_info;
    private Article_Context article_context;

    private String title;
    private String content;

    @AfterViews
    void afterViews() {
        space_info = new Space_Info();
        article_context = new Article_Context();
    }

    @Click(R.id.publish_btn)
    void clickPublishBtn() {
        title = mTitle.getText().toString();
        content = mContent.getText().toString();

        if(TextUtils.isEmpty(title)) {
            showToast("标题不能为空！");
            return;
        }

        if(content.length() < 20) {
            showToast("内容字数不能少于20字");
            return;
        }

        space_info.setName(title);
        space_info.setInfo_about(content.substring(0,20));
        publishInfo();
    }

    @Background
    void publishInfo() {
        space_info.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    article_context.setArticle_title(title);
                    article_context.setArticle_context(content);
                    article_context.setArticle_id(s);
                    publishContext();
                } else {
                    showToast("发布失败！" + e);
                    Log.e("dushiguang","---" + e);

                }
            }
        });

    }

    @Background
    void publishContext() {
        article_context.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    showToast("发布成功！");
                    finishActivity();

                } else {
                    showToast("发布失败！" + e);
                }
            }
        });
    }

    private void finishActivity(){
        Intent intent = new Intent();
        WriteActivity.this.setResult(MainActivity.MAINACTIVITYKEY, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Click(R.id.ic_more_tv)
    void clickMore() {

    }

    public static void jumpToWriteActivity(Activity context) {
        Intent intent = new Intent(context, WriteActivity_.class);
        context.startActivityForResult(intent, MainActivity.MAINACTIVITYKEY);
    }
}
