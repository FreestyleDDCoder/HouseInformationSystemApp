package com.helloncu.houseinformationsystem.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.utils.LoginState;

import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;

/**
 * 展示用户信息的activity
 */

public class UserInfoActivity extends AppCompatActivity {

    private SpringingImageView siv_user_icon;
    private TextView tv_user_name;
    private TextView tv_user_mail;
    private TextView tv_user_mott;
    private LoginState loginState;
    private static final String HOST = "http://192.168.1.104:8080/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        initData();
    }

    private void initData() {
        loginState = new LoginState(UserInfoActivity.this);
        Glide.with(UserInfoActivity.this).load(HOST +loginState.getUserIconUrl()).into(siv_user_icon);
        tv_user_name.setText(loginState.getUserId());
        tv_user_mail.setText(loginState.getUserMail());
        tv_user_mott.setText(loginState.getUserMotto());
    }

    private void initView() {
        siv_user_icon = findViewById(R.id.siv_user_icon);
        siv_user_icon.setIsCircleImage(true);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_mail = findViewById(R.id.tv_user_mail);
        tv_user_mott = findViewById(R.id.tv_user_mott);
    }
}
