package com.team7619.keepdoing.entity;

/**
 * Created by dushiguang on 16/8/30.
 */
public class Article_Context {
    String artivle_id;
    String article_title;
    String join_num;
    String article_context;

    public String getArtivle_id() {
        return artivle_id;
    }

    public void setArtivle_id(String artivle_id) {
        this.artivle_id = artivle_id;
    }

    public String getArticle_context() {
        return article_context;
    }

    public void setArticle_context(String article_context) {
        this.article_context = article_context;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getJoin_num() {
        return join_num;
    }

    public void setJoin_num(String join_num) {
        this.join_num = join_num;
    }
}
