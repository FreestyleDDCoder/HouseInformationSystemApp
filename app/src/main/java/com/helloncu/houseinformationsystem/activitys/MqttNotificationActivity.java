package com.helloncu.houseinformationsystem.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.helloncu.houseinformationsystem.R;

public class MqttNotificationActivity extends AppCompatActivity {

    private TextView tv_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqttnotification);
        initView();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        String topic = intent.getStringExtra("topic");
        String message = intent.getStringExtra("message");
        tv_content.setText(topic + message);
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //当按返回键时，销毁掉当前activity
        finish();
    }
}
