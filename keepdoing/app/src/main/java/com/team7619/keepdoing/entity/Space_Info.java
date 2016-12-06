package com.team7619.keepdoing.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by dushiguang on 16/8/25.
 */
public class Space_Info  extends BmobObject {
    String user_id;
    String publish_time;
    String name;
    String info_title;
    String info_about;
    String Icon;
    String article_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo_title() {
        return info_title;
    }

    public void setInfo_title(String info_title) {
        this.info_title = info_title;
    }

    public String getInfo_about() {
        return info_about;
    }

    public void setInfo_about(String info_about) {
        this.info_about = info_about;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }


}

