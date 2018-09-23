package com.helloncu.houseinformationsystem.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.services.MqttReceiveService;

/**
 * 这是软件的闪屏界面，用于完成初始化工作
 * 当前需要开启消息推送监听服务，接收mqtt的推送
 */
public class SplashActivity extends AppCompatActivity {
    protected static final int CODE_START_SERVICE_FINISH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initData() {
        checkVersion();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_START_SERVICE_FINISH:
                    //服务启动成功，则进入下个界面
                    goBeginPageActivity();
                    break;
            }
        }
    };

    /**
     * 检查版本更新代码块
     */
    private void checkVersion() {
        //记录时间，有时候网速太快了，来不及显示就已经进入下一个界面了
        final long startTime = System.currentTimeMillis();
        //初始化监听服务，耗时应用采用子线程去做
        //创建无线大小线程池
        new Thread() {
            @Override
            public void run() {
                super.run();
                Intent intent = new Intent(SplashActivity.this, MqttReceiveService.class);
                startService(intent);
                //记录结束时间
                long endTime = System.currentTimeMillis();
                Message message = Message.obtain();
                message.what = CODE_START_SERVICE_FINISH;
                if ((endTime - startTime) > 2000) {
                    mHandler.sendMessage(message);
                } else {
                    //系统休眠到两秒时限
                    try {
                        Thread.sleep(2000 - (endTime - startTime));
                        mHandler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void goBeginPageActivity() {
        Intent intent = new Intent(SplashActivity.this, BeginPageActivity.class);
        startActivity(intent);
        //进入主页后把闪屏activity给结束掉，避免返回看到
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText("当前版本：" + getVersionName());
    }

    // 获取版本名称，用以显示
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有找到包名的时候会走此异常
            e.printStackTrace();
        }
        return versionName;
    }
}
