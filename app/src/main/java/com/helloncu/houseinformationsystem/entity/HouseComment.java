package com.helloncu.houseinformationsystem.entity;

import java.util.Date;

/**
 * 这是楼盘评论的实体类
 */
public class HouseComment {
    //评论包含用户名，评论内容，评论日期，用户头像图片链接
    private String userId;
    private String houseId;
    private String comment;
    private Date commentDate;
    private String userIconUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }
}
