package com.eazy.message.entity;

import com.eazy.post.entity.Post;
import com.eazy.user.entity.User;

import java.sql.Timestamp;

public class Message {

    private Integer id;

    private Integer pid;

    private Integer uid;

    private Integer atid;

    /**
     * 0 = 评论
     * 1 = 艾特
     * 2 = 系统消息
     * 3 = 发表的主题
     * 4 = 感谢的主题
     * 5 = 打赏
     */
    private Integer type;

    private String content;

    private Timestamp time;

    private Integer status;

    private User user;

    private User atUser;

    private Post post;

    private Integer reply;

    public Message(Integer pid, Integer uid, Integer atid, Integer type, String content, Timestamp time, Integer status,Integer reply) {
        this.pid = pid;
        this.uid = uid;
        this.atid = atid;
        this.type = type;
        this.content = content;
        this.time = time;
        this.status = status;
        this.reply = reply;
    }

    public Message(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAtid() {
        return atid;
    }

    public void setAtid(Integer atid) {
        this.atid = atid;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAtUser() {
        return atUser;
    }

    public void setAtUser(User atUser) {
        this.atUser = atUser;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }
}
