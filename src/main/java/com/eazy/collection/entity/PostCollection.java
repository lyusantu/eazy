package com.eazy.collection.entity;

import com.eazy.post.entity.Post;

import java.sql.Timestamp;

public class PostCollection {

    private Integer id;

    private Integer postId;

    private Integer userId;

    private Timestamp collectionTime;

    private Post post;

    public PostCollection() {

    }

    public PostCollection(Integer postId, Integer userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public PostCollection(Integer postId, Integer userId, Timestamp collectionTime) {
        this.postId = postId;
        this.userId = userId;
        this.collectionTime = collectionTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Timestamp collectionTime) {
        this.collectionTime = collectionTime;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
