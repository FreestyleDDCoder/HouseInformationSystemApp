package com.helloncu.houseinformationsystem.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.entity.UserInformation;
import com.helloncu.houseinformationsystem.entity.UserLogin;
import com.helloncu.houseinformationsystem.entity.jsonType.JsonTransportType;
import com.helloncu.houseinformationsystem.utils.ClientSocketHandle;
import com.helloncu.houseinformationsystem.utils.LoginState;

import dym.unique.com.springinglayoutlibrary.handler.SpringTouchRippleHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingAlphaShowHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingNotificationRotateHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchDragHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchPointHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchScaleHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTranslationShowHandler;
import dym.unique.com.springinglayoutlibrary.view.SpringingEditText;
import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;
import dym.unique.com.springinglayoutlibrary.view.SpringingTextView;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingLinearLayout;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingRelativeLayout;

/**
 * Created by liangzhan on 17-3-21.
 * 登录注册页面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SpringingRelativeLayout srl_actionBar;
    private SpringingImageView simg_back;
    private SpringingLinearLayout sll_mainContainer;
    private SpringingImageView simg_avatarMan;
    private SpringingEditText sedt_account;
    private SpringingEditText sedt_password;
    private SpringingTextView stv_login;
    private TextView tv_forgetpassword;
    private TextView tv_noregist;

    private static final String NULL_USER_SHOW = "账号不能为空！";
    private static final String NULL_PASSWORD_SHOW = "密码不能为空！";
    private static final String LOGIN_SUCCESS = "success";
    private static final String LOGIN_SUCCESS_SHOW = "登录成功！";
    private static final String LOGIN_FAIL_SHOW = "登录失败！";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initSpringLayout();
        initEvent();
        showViews();
    }

    private void showViews() {
        new SpringingAlphaShowHandler(this, sll_mainContainer).showChildrenSequence(500, 100);
        new SpringingTranslationShowHandler(this, sll_mainContainer).showChildrenSequence(500, 100);
        new SpringingNotificationRotateHandler(this, simg_avatarMan).start(1);
    }

    private void initEvent() {
        stv_login.setOnClickListener(this);
        simg_back.setOnClickListener(this);
        tv_noregist.setOnClickListener(this);
        tv_forgetpassword.setOnClickListener(this);
    }

    private void initSpringLayout() {
        srl_actionBar.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, srl_actionBar).setOnlyOnChildren(true, simg_back));
        simg_back.getSpringingHandlerController().addSpringingHandler(new SpringingTouchPointHandler(this, simg_back).setAngle(SpringingTouchPointHandler.ANGLE_LEFT));
        sll_mainContainer.getSpringingHandlerController().addSpringingHandler(new SpringingTouchDragHandler(this, sll_mainContainer).setBackInterpolator(new OvershootInterpolator()).setBackDuration(SpringingTouchDragHandler.DURATION_LONG).setDirection(SpringingTouchDragHandler.DIRECTOR_BOTTOM | SpringingTouchDragHandler.DIRECTOR_TOP).setMinDistance(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics())));
        sll_mainContainer.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, sll_mainContainer).setOnlyOnChildren(true, sedt_account, sedt_password));
        simg_avatarMan.getSpringingHandlerController().addSpringingHandler(new SpringingTouchScaleHandler(this, simg_avatarMan));
        stv_login.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, stv_login));

    }

    private void initUI() {
        srl_actionBar = findViewById(R.id.srl_actionBar);
        simg_back = findViewById(R.id.simg_back);
        sll_mainContainer = findViewById(R.id.sll_mainContainer);
        simg_avatarMan = ((SpringingImageView) findViewById(R.id.simg_avatarMan)).setIsCircleImage(true);
        sedt_account = findViewById(R.id.sedt_account);
        sedt_password = findViewById(R.id.sedt_password);
        stv_login = findViewById(R.id.stv_login);
        tv_noregist = findViewById(R.id.tv_noregist);
        tv_forgetpassword = findViewById(R.id.tv_forgetpassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stv_login://登录控件
                String account = sedt_account.getText().toString();
                String password = sedt_password.getText().toString();
                if (TextUtils.isEmpty(account)) {//账号为空
                    Toast.makeText(LoginActivity.this, NULL_USER_SHOW, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {//密码为空
                    Toast.makeText(LoginActivity.this, NULL_PASSWORD_SHOW, Toast.LENGTH_SHORT).show();
                } else {
                    gotoLogin(account, password);
                }
                break;
            case R.id.tv_noregist://注册
                gotoRegister();
                break;
            case R.id.tv_forgetpassword://忘记密码
                gotoResetPassword();
                break;
            case R.id.simg_back:
                finish();
                break;
        }
    }

    //密码重置功能
    private void gotoResetPassword() {

    }

    /**
     * 执行登录的方法块
     *
     * @param account  账号
     * @param password 密码
     */
    private void gotoLogin(String account, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(account);
        userLogin.setUserPassword(password);
        //执行登录请求
        new GoLogin().execute(userLogin);
    }

    private void gotoRegister() {
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class GoLogin extends AsyncTask<UserLogin, Integer, String> {

        @Override
        protected String doInBackground(UserLogin... userLogins) {
            String jsonString = JSON.toJSONString(new JsonTransportType(userLogins[0], "", userLogins[0].getUserId(), "UserLogin"));
            return new ClientSocketHandle().sendMessage(jsonString);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("LoginActivity","result:"+result);
            LoginState loginState = new LoginState(LoginActivity.this);
            UserInformation userInformation = JSON.parseObject(result, UserInformation.class);
            //登录成功该做什么？失败该做什么
            if (userInformation.getResult().equals("success")) {//登录成功
                loginState.goLogin(userInformation, true);
                Toast.makeText(LoginActivity.this, LOGIN_SUCCESS_SHOW, Toast.LENGTH_SHORT).show();
                //关闭当前页面
                finish();
            } else {
                loginState.goLogin(userInformation, false);
                Toast.makeText(LoginActivity.this, LOGIN_FAIL_SHOW + result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
