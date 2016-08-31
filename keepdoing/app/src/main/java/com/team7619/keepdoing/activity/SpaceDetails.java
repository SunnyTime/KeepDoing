package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity.Article_Context;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by ex-dushiguang201 on 2016-06-13.
 */
@EActivity(R.layout.activity_space_details)
public class SpaceDetails extends BaseActivity {
    @ViewById(R.id.title_back_tv)
    TextView btBack;

    @ViewById(R.id.title)
    TextView mTitle;
    @ViewById(R.id.join_num)
    TextView mJoinNum;
    @ViewById(R.id.content)
    TextView mContent;

    @Extra
    String articleId;

    @AfterViews
    void afterViews() {
        getArticleContent();
    }

    @Background
    void getArticleContent() {
        //查找Person表里面id为articleId的数据
        BmobQuery<Article_Context> bmobQuery = new BmobQuery<Article_Context>();
        bmobQuery.getObject(articleId, new QueryListener<Article_Context>() {
            @Override
            public void done(Article_Context bean,BmobException e) {
                if(e==null){
                    //toast("查询成功");
                    setArticleInfo(bean);
                }else{
                    //toast("查询失败：" + e.getMessage());
                    Toast.makeText(SpaceDetails.this, "查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @UiThread
    void setArticleInfo(Article_Context bean) {
        mTitle.setText(bean.getArticle_title());
        mJoinNum.setText(bean.getJoin_num());
        mContent.setText(bean.getArticle_context());
    }

    @Click(R.id.title_back_tv)
    void btBackClick() {
        finish();
    }

    public static void jumpToSpaceDetails(Context context, String articleId) {
        Intent intent = new Intent(context, SpaceDetails_.class);
        intent.putExtra("articleId", articleId);
        context.startActivity(intent);
    }
}
