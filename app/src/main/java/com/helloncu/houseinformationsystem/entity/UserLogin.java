package com.helloncu.houseinformationsystem.entity;

/**
 * Created by liangzhan on 18-3-14.
 * 这是用户登录表实现
 */
public class UserLogin {
    private String userId;
    private String userPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
