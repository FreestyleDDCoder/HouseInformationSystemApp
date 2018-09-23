package com.helloncu.houseinformationsystem.entity;

/**
 * Created by liangzhan on 18-3-14.
 * 这是用户信息表实体
 */
public class UserInformation {
    private String userId;
    private String userMail;
    private String userSex;
    private String userMotto;
    private String userIconUrl;
    private String userVip;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UserInformation() {
    }

    public UserInformation(String userId, String userMail, String userSex, String userMotto, String userIconUrl, String userVip) {
        this.userId = userId;
        this.userMail = userMail;
        this.userSex = userSex;
        this.userMotto = userMotto;
        this.userIconUrl = userIconUrl;
        this.userVip = userVip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserMotto() {
        return userMotto;
    }

    public void setUserMotto(String userMotto) {
        this.userMotto = userMotto;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getUserVip() {
        return userVip;
    }

    public void setUserVip(String userVip) {
        this.userVip = userVip;
    }
}
