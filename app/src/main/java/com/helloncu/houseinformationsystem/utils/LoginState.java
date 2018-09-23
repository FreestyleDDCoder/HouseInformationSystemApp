package com.helloncu.houseinformationsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.helloncu.houseinformationsystem.entity.UserInformation;

/**
 * 这是用户登录状态的工具类
 */
public class LoginState {
    private final SharedPreferences sharedPreferences;

    public LoginState(Context mContext) {
        this.sharedPreferences = mContext.getSharedPreferences("LOGIN_STATE", Context.MODE_PRIVATE);
    }

    /**
     * 判断是否已经登录的方法
     *
     * @return 布尔值
     */
    public boolean isLogin() {
        return sharedPreferences.getBoolean("IS_LOGIN", false);
    }

    public String getUserId() {
        return sharedPreferences.getString("LOGIN_ID", "");
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(userId, "").apply();
    }

    public void outLogin() {
        sharedPreferences.edit().putBoolean("IS_LOGIN", false).apply();
    }

    public String getUserMail() {
        return sharedPreferences.getString("USER_MAIL", "");
    }

    public String getUserIconUrl() {
        return sharedPreferences.getString("USER_ICON_URL", "");
    }

    public String getUserMotto() {
        return sharedPreferences.getString("USER_MOTTO", "");
    }

    public String getUserSex() {
        return sharedPreferences.getString("USER_SEX", "");
    }

    public String getUserVip() {
        return sharedPreferences.getString("USER_VIP", "");
    }

    /**
     * 记录登录状态,根据账号密码，返回值来判定是否登录成功
     */
    public void goLogin(UserInformation userInformation, Boolean isLogin) {
        if (isLogin) {
            setUserId(userInformation.getUserId());
            sharedPreferences.edit().putString("USER_MAIL", userInformation.getUserMail()).apply();
            sharedPreferences.edit().putString("USER_ICON_URL", userInformation.getUserIconUrl()).apply();
            sharedPreferences.edit().putString("USER_MOTTO", userInformation.getUserMotto()).apply();
            sharedPreferences.edit().putString("USER_SEX", userInformation.getUserSex()).apply();
            sharedPreferences.edit().putString("USER_VIP", userInformation.getUserVip()).apply();
            sharedPreferences.edit().putString("LOGIN_ID", userInformation.getUserId()).apply();
        } else {
            sharedPreferences.edit().putString("USER_MAIL", "").apply();
            sharedPreferences.edit().putString("USER_ICON_URL", "").apply();
            sharedPreferences.edit().putString("USER_MOTTO", "").apply();
            sharedPreferences.edit().putString("USER_SEX", "").apply();
            sharedPreferences.edit().putString("USER_VIP", "").apply();
            sharedPreferences.edit().putString("LOGIN_ID", "").apply();
        }
        sharedPreferences.edit().putBoolean("IS_LOGIN", isLogin).apply();
    }
}
