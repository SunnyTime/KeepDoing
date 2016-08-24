package com.team7619.keepdoing.entity;

import java.util.ArrayList;

/**
 * Created by ex-dushiguang201 on 2016-06-12.
 */
public class SpaceBean {
    private String titleIconUrl;//图标地址
    private String name;
    private String publishTime;
    private String articleTitle;
    private String articleAbout;
    private String articleTitleCon;
    private String joinNum;
    private String articleContent;
    private String addComment;
    private String allComment;
    private String likeIt;
    private String shareIt;

    public SpaceBean(String titleIconUrl, String name, String publishTime, String articleTitle,
                     String articleAbout, String articleTitleCon, String joinNum, String articleContent) {
        this.titleIconUrl = titleIconUrl;
        this.name = name;
        this.publishTime = publishTime;
        this.articleTitle = articleTitle;
        this.articleAbout = articleAbout;
        this.articleTitleCon = articleTitleCon;
        this.joinNum = joinNum;
        this.articleContent = articleContent;
    }
    public String getTitleIconUrl() {
        return titleIconUrl;
    }

    public void setTitleIconUrl(String titleIconUrl) {
        this.titleIconUrl = titleIconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAbout() {
        return articleAbout;
    }

    public void setArticleAbout(String articleAbout) {
        this.articleAbout = articleAbout;
    }

    public String getArticleTitleCon() {
        return articleTitleCon;
    }

    public void setArticleTitleCon(String articleTitleCon) {
        this.articleTitleCon = articleTitleCon;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getAddComment() {
        return addComment;
    }

    public void setAddComment(String addComment) {
        this.addComment = addComment;
    }

    public String getAllComment() {
        return allComment;
    }

    public void setAllComment(String allComment) {
        this.allComment = allComment;
    }

    public String getLikeIt() {
        return likeIt;
    }

    public void setLikeIt(String likeIt) {
        this.likeIt = likeIt;
    }

    public String getShareIt() {
        return shareIt;
    }

    public void setShareIt(String shareIt) {
        this.shareIt = shareIt;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<SpaceBean> getTestingList() {
        ArrayList<SpaceBean> items = new ArrayList<>();
        items.add(new SpaceBean("url-------", "千山1", "06.12 17:55", "如何成为构架师", "如何成为构架师1, 如何成为构架师2",
                "如何成为构架师", "20人参与", "如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2"));
        items.add(new SpaceBean("url-------", "千山2", "06.12 17:55", "如何成为构架师", "如何成为构架师1, 如何成为构架师2",
                "如何成为构架师", "20人参与", "如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2"));
        items.add(new SpaceBean("url-------", "千山3", "06.12 17:55", "如何成为构架师", "如何成为构架师1, 如何成为构架师2",
                "如何成为构架师", "20人参与", "如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2"));
        items.add(new SpaceBean("url-------", "千山4", "06.12 17:55", "如何成为构架师", "如何成为构架师1, 如何成为构架师2",
                "如何成为构架师", "20人参与", "如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2, 如何成为构架师1, 如何成为构架师2"));
        return items;

    }
}
