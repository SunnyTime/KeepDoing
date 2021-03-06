package com.team7619.keepdoing.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.team7619.keepdoing.BaseActivity;
import com.team7619.keepdoing.Iconfont.IconFont;
import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity.Article_Context;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ex-dushiguang201 on 2016-06-13.
 */
@EActivity(R.layout.activity_space_details)
public class SpaceDetailsActivity extends BaseActivity {
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
        setBackIcon(IconFont.IC_BACK);
        setPageLable(R.string.null_str);
        setRightButtonIcon(IconFont.IC_MENU_MORE);
        getArticleContent();
    }

    @Background
    void getArticleContent() {
        //查找Person表里面id为articleId的数据
        BmobQuery<Article_Context> query = new BmobQuery<Article_Context>();
        query.addWhereEqualTo("article_id", articleId);
        query.findObjects(new FindListener<Article_Context>() {
            @Override
            public void done(List<Article_Context> list, BmobException e) {
                if(e == null){
                    //toast("查询成功");
                    setArticleInfo(list);
                }else{
                    //toast("查询失败：" + e.getMessage());
                    Toast.makeText(SpaceDetailsActivity.this, "查询失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @UiThread
    void setArticleInfo(List<Article_Context> list) {
        Article_Context bean = list.get(0);
        mTitle.setText(bean.getArticle_title());
        mJoinNum.setText(bean.getJoin_num() + "参与");
        mContent.setText(bean.getArticle_context());
    }

    @Click(R.id.title_back_tv)
    void btBackClick() {
        finish();
    }

    public static void jumpToSpaceDetails(Context context, String articleId) {
        Intent intent = new Intent(context, SpaceDetailsActivity_.class);
        intent.putExtra("articleId", articleId);
        context.startActivity(intent);
    }
}
