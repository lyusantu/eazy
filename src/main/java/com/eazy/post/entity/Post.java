package com.eazy.post.entity;

import com.eazy.column.entity.Column;
import com.eazy.user.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Post implements Serializable {

    private Integer id;

    private String title;

    private Integer type;

    private String content;

    private Integer reward;

    private Integer delete;

    private Timestamp createTime;

    private Integer author;

    private Integer comments;

    private Integer readers;

    private Integer status;

    private Integer top;

    private Integer wonderful;

    private Integer allow;

    private Integer postReward;

    private String rewardContent;

    private Integer rewardNum;

    private User user;

    private Column column;

    private Keyword keyword;

    private List<Keyword> keywords;

    public Post() {

    }

    public Integer getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(Integer rewardNum) {
        this.rewardNum = rewardNum;
    }

    public Post(Integer id) {
        this.id = id;
    }

    public Post(Integer id, Integer readers) {
        this.id = id;
        this.readers = readers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getReaders() {
        return readers;
    }

    public void setReaders(Integer readers) {
        this.readers = readers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getWonderful() {
        return wonderful;
    }

    public void setWonderful(Integer wonderful) {
        this.wonderful = wonderful;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    public Integer getPostReward() {
        return postReward;
    }

    public void setPostReward(Integer postReward) {
        this.postReward = postReward;
    }

    public String getRewardContent() {
        return rewardContent;
    }

    public void setRewardContent(String rewardContent) {
        this.rewardContent = rewardContent;
    }
}
