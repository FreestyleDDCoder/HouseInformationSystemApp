package com.helloncu.houseinformationsystem.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RadioButton;
import android.widget.Toast;

import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.entity.UserInformation;
import com.helloncu.houseinformationsystem.entity.UserLogin;
import com.helloncu.houseinformationsystem.utils.MD5Utils;
import com.helloncu.houseinformationsystem.utils.SelectPictureUtils;

import java.util.Date;

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
 * Created by liangzhan on 17-3-22.
 * 登陆界面
 */

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MAN = "m";
    private static final String WOMAN = "w";
    private SpringingImageView mSimg_back;
    private SpringingImageView mSimg_avatarMan;
    private SpringingEditText mSedt_account;
    private SpringingEditText mSedt_emailHint;
    private SpringingEditText mSedt_password;
    private RadioButton mBoys;
    private RadioButton mGirls;
    private SpringingTextView mStv_regist;
    private SpringingRelativeLayout mSrl_actionBar;
    private SpringingLinearLayout mSll_mainContainer;
    private boolean boys = true;
    private Uri tempUri;
    String dateTime;
    private String uri;
    private Bitmap photo;
    private SpringingTextView stv_setimage;
    private String emailHint;
    private SelectPictureUtils selectPictureUtils;
    private static final int TAKE_PICTURE = 0;
    private static final int LOCAL_PICTURE = 1;
    private static final int PICTURE_CUT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        dateTime = new Date(System.currentTimeMillis()).getTime() + "";
        initUI();
        initSpringLayout();
        initEvent();
        showViews();
    }

    private void showViews() {
        new SpringingAlphaShowHandler(this, mSll_mainContainer).showChildrenSequence(500, 100);
        new SpringingTranslationShowHandler(this, mSll_mainContainer).showChildrenSequence(500, 100);
        new SpringingNotificationRotateHandler(this, mSimg_avatarMan).start(1);
    }

    private void initEvent() {
        mSimg_back.setOnClickListener(this);
        mSimg_avatarMan.setOnClickListener(this);
        mStv_regist.setOnClickListener(this);
        mBoys.setOnClickListener(this);
        mGirls.setOnClickListener(this);
    }

    private void initSpringLayout() {
        mSrl_actionBar.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, mSrl_actionBar).setOnlyOnChildren(true, mSimg_back));
        mSimg_back.getSpringingHandlerController().addSpringingHandler(new SpringingTouchPointHandler(this, mSimg_back).setAngle(SpringingTouchPointHandler.ANGLE_LEFT));
        mSll_mainContainer.getSpringingHandlerController().addSpringingHandler(new SpringingTouchDragHandler(this, mSll_mainContainer).setBackInterpolator(new OvershootInterpolator()).setBackDuration(SpringingTouchDragHandler.DURATION_LONG).setDirection(SpringingTouchDragHandler.DIRECTOR_BOTTOM | SpringingTouchDragHandler.DIRECTOR_TOP).setMinDistance(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics())));
        mSll_mainContainer.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, mSll_mainContainer).setOnlyOnChildren(true, mSedt_account, mSedt_password));
        mSimg_avatarMan.getSpringingHandlerController().addSpringingHandler(new SpringingTouchScaleHandler(this, mSimg_avatarMan));
        mStv_regist.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, mStv_regist));
        stv_setimage.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(this, stv_setimage));
    }

    private void initUI() {
        mSrl_actionBar = findViewById(R.id.srl_actionBar2);
        mSimg_back = findViewById(R.id.simg_back2);
        mSll_mainContainer = findViewById(R.id.sll_mainContainer2);
        mSimg_avatarMan = findViewById(R.id.simg_avatarMan2);
        mSimg_avatarMan.setIsCircleImage(true);
        mSedt_account = findViewById(R.id.sedt_account2);
        mSedt_emailHint = findViewById(R.id.sedt_EmailHint);
        mSedt_password = findViewById(R.id.sedt_password2);
        mBoys = findViewById(R.id.boys);
        mGirls = findViewById(R.id.girls);
        mStv_regist = findViewById(R.id.stv_regist);
        stv_setimage = findViewById(R.id.stv_setimage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.simg_back2:
                finish();
                break;
            //设置头像
            case R.id.simg_avatarMan2:
                selectPictureUtils = new SelectPictureUtils(RegistActivity.this);
                selectPictureUtils.showChoosePicDialog();
                break;
            //性别设置
            case R.id.boys:
                if (!boys) {
                    mBoys.setChecked(true);
                    mGirls.setChecked(false);
                }
                boys = true;
                break;
            case R.id.girls:
                if (boys) {
                    mBoys.setChecked(false);
                    mGirls.setChecked(true);
                }
                boys = false;
                break;
            //点击注册按钮
            case R.id.stv_regist:
                emailHint = mSedt_emailHint.getText().toString();
                String account = mSedt_account.getText().toString();
                String password = mSedt_password.getText().toString();
                if (TextUtils.isEmpty(emailHint)) {
                    Toast.makeText(RegistActivity.this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(account)) {
                    Toast.makeText(RegistActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegistActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    regist(emailHint, account, password);
                }
                break;
        }
    }

    private void checkEmail(String emailHint) {

    }

    private void regist(String emailHint, String account, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(account);
        userLogin.setUserPassword(MD5Utils.MD5Encryption(password));

        UserInformation userInformation = new UserInformation();
        userInformation.setUserMail(emailHint);
        userInformation.setUserId(account);

        if (mBoys.isChecked()) {
            userInformation.setUserSex(MAN);
        } else {
            userInformation.setUserSex(WOMAN);
        }

        //这里进行登录请求和结果返回处理

    }


    /**
     * 处理选择头像返回结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果返回码有用，则进行下一步
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //开始裁剪图片
                case TAKE_PICTURE:
                    selectPictureUtils.startPhotoZoom(data.getData());
                    break;
                case LOCAL_PICTURE:
                    selectPictureUtils.startPhotoZoom(null);
                    break;
                case PICTURE_CUT:
                    if (data != null) {
                        // 让刚才选择裁剪得到的图片显示在界面上
                        Bitmap bitmap = selectPictureUtils.setImageToView(data);
                        mSimg_avatarMan.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }

    /**
     * 更新头像的方法块
     *
     * @param avataPath
     */
    private void updateIcon(String avataPath) {

    }

}
