package com.helloncu.houseinformationsystem.activitys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.adatpers.HouseInformationBodyAdapter;
import com.helloncu.houseinformationsystem.entity.HouseComment;
import com.helloncu.houseinformationsystem.entity.HouseInformation;
import com.helloncu.houseinformationsystem.entity.jsonType.JsonTransportType;
import com.helloncu.houseinformationsystem.utils.ClientSocketHandle;
import com.helloncu.houseinformationsystem.utils.LoginState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dym.unique.com.springinglayoutlibrary.handler.SpringingAlphaShowHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTranslationShowHandler;
import dym.unique.com.springinglayoutlibrary.view.SpringingButton;
import dym.unique.com.springinglayoutlibrary.view.SpringingEditText;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingLinearLayout;

/**
 * 这是用于具体展示楼盘信息的Activity，包含了评论展示
 */
public class HouseInformationActivity extends AppCompatActivity {

    private ListView lv_house_information;
    private HouseInformation houseInformation;
    private HouseInformationBodyAdapter informationBodyAdapter;
    private static final String ERROR_DATA_SHOW = "获取评论数据失败，稍后再试！";
    private static final String LOGIN_DATA_SHOW = "请先登录！";
    private static final String COMMENT_EMPTY_SHOW = "内容不能为空！";
    private static final String COMMENT_SUCCESS_SHOW = "评论成功！";
    private SpringingLinearLayout sll_house_comment;
    private SpringingEditText set_house_comment;
    private SpringingButton sbt_house_comment;
    private FloatingActionButton fab_comment;
    private LoginState loginState;
    List<HouseComment> newHouseComments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseinformation);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        fab_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginState.isLogin()) {//如果已经登录，显示发表框
                    initSpringLayout();
                    fab_comment.setVisibility(View.GONE);
                } else {
                    Toast.makeText(HouseInformationActivity.this, LOGIN_DATA_SHOW, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //评论按钮点击事件
        sbt_house_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = set_house_comment.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(HouseInformationActivity.this, COMMENT_EMPTY_SHOW, Toast.LENGTH_SHORT).show();
                } else {
                    //发起评论请求
                    HouseComment houseComment = new HouseComment();
                    houseComment.setComment(comment);
                    houseComment.setCommentDate(new Date());
                    houseComment.setHouseId(houseInformation.getHouseId());
                    houseComment.setUserId(loginState.getUserId());
                    houseComment.setUserIconUrl(loginState.getUserIconUrl());
                    new GoComment().execute(houseComment);
                    sll_house_comment.setVisibility(View.GONE);
                    set_house_comment.setVisibility(View.GONE);
                    sbt_house_comment.setVisibility(View.GONE);
                    fab_comment.setVisibility(View.VISIBLE);
                    goNotify(houseComment);
                }
            }
        });
    }

    /**
     * 更新数据的方法块
     *
     * @param houseComment 新的评论
     */
    private void goNotify(HouseComment houseComment) {
        if (houseComment != null) {
            List<HouseComment> houseComments = new ArrayList<>();
            houseComments.add(houseComment);
            houseComments.addAll(newHouseComments);
            newHouseComments = houseComments;
        } else {
            newHouseComments.remove(0);
        }
        informationBodyAdapter.setHouseComments(newHouseComments);
        informationBodyAdapter.notifyDataSetChanged();
    }

    /**
     * 展现SpringLayout动态效果的方法
     */
    private void initSpringLayout() {
        sll_house_comment.setVisibility(View.VISIBLE);
        set_house_comment.setVisibility(View.VISIBLE);
        sbt_house_comment.setVisibility(View.VISIBLE);
        new SpringingAlphaShowHandler(HouseInformationActivity.this, sll_house_comment).showChildrenSequence(500, 100);
        new SpringingTranslationShowHandler(HouseInformationActivity.this, sll_house_comment).showChildrenSequence(500, 100);
    }

    private void initView() {
        lv_house_information = findViewById(R.id.lv_house_information);
        sll_house_comment = findViewById(R.id.sll_house_comment);
        set_house_comment = findViewById(R.id.set_house_comment);
        sbt_house_comment = findViewById(R.id.sbt_house_comment);
        fab_comment = findViewById(R.id.fab_comment);
    }

    private void initData() {
        loginState = new LoginState(HouseInformationActivity.this);
        houseInformation = (HouseInformation) getIntent().getSerializableExtra("houseInformation");
        Log.d("InformationActivity", "houseInformation:" + houseInformation.getHouseId());
        //发送请求把该楼盘的评论信息获取下来
        new GetCommentList().execute();

        //初始化一个新的评论实体列表
        newHouseComments = new ArrayList<>();
    }

    /**
     * 执行获取评论列表的多线程方法
     */
    private class GetCommentList extends AsyncTask<Void, Void, List<HouseComment>> {

        @Override
        protected List<HouseComment> doInBackground(Void... voids) {
            //查询楼盘评论信息
            String jsonString = JSON.toJSONString(new JsonTransportType(houseInformation, "", "visitor", "houseCommentQuery"));
            String result = new ClientSocketHandle().sendMessage(jsonString);
            return JSON.parseArray(result, HouseComment.class);
        }

        @Override
        protected void onPostExecute(List<HouseComment> houseComments) {
            super.onPostExecute(houseComments);
            if (houseComments != null) {
                newHouseComments = houseComments;
                if (informationBodyAdapter == null) {
                    informationBodyAdapter = new HouseInformationBodyAdapter(houseInformation, houseComments);
                    lv_house_information.setAdapter(informationBodyAdapter);
                } else {
                    informationBodyAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(HouseInformationActivity.this, ERROR_DATA_SHOW, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class GoComment extends AsyncTask<HouseComment, Integer, String> {
        @Override
        protected String doInBackground(HouseComment... houseComments) {
            String jsonString = JSON.toJSONString(new JsonTransportType(houseComments[0], "", loginState.getUserId(), "houseComment"));
            return new ClientSocketHandle().sendMessage(jsonString);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //执行成功，吐司提示
            if (s.equals("success")) {
                Toast.makeText(HouseInformationActivity.this, COMMENT_SUCCESS_SHOW, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HouseInformationActivity.this, s, Toast.LENGTH_SHORT).show();
                goNotify(null);
            }
        }
    }
}
