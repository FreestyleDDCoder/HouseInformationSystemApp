package com.helloncu.houseinformationsystem.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.animation.OvershootInterpolator;

import dym.unique.com.springinglayoutlibrary.handler.SpringTouchRippleHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchDragHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchPointHandler;
import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;
import dym.unique.com.springinglayoutlibrary.view.SpringingTextView;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingLinearLayout;

/**
 * 这是SpringLayout效果展示的类
 */
public class SpringLayoutUtils {
    private Context mContext;

    public SpringLayoutUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void setSpringImageEffect(SpringingImageView springingImageView) {
        springingImageView.getSpringingHandlerController().addSpringingHandler(new SpringingTouchPointHandler(mContext, springingImageView).setAngle(SpringingTouchPointHandler.ANGLE_LEFT));
    }

    public void setSpringTextEffect(SpringingTextView springingTextView) {
        springingTextView.getSpringingHandlerController().addSpringingHandler(new SpringTouchRippleHandler(mContext, springingTextView));
    }

    public void setSpringLinearLayout(SpringingLinearLayout springingLinearLayout) {
        springingLinearLayout.getSpringingHandlerController().addSpringingHandler(new SpringingTouchDragHandler(mContext, springingLinearLayout).setBackInterpolator(new OvershootInterpolator()).setBackDuration(SpringingTouchDragHandler.DURATION_LONG).setDirection(SpringingTouchDragHandler.DIRECTOR_BOTTOM | SpringingTouchDragHandler.DIRECTOR_TOP).setMinDistance(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mContext.getResources().getDisplayMetrics())));
    }
}
