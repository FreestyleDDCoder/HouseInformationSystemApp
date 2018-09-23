package com.helloncu.houseinformationsystem.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.fragments.BeginPageFragment;
import com.helloncu.houseinformationsystem.fragments.HouseKindsFragment;
import com.helloncu.houseinformationsystem.fragments.MonthOpenFragment;
import com.helloncu.houseinformationsystem.fragments.NewOpenFragment;
import com.helloncu.houseinformationsystem.utils.LoginState;
import com.helloncu.houseinformationsystem.utils.ReqPermissionUtils;
import com.helloncu.houseinformationsystem.utils.SpringLayoutUtils;

import java.util.ArrayList;

import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;
import dym.unique.com.springinglayoutlibrary.view.SpringingTextView;

/**
 * 这是楼盘信息系统开始界面
 */
public class BeginPageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String BEGIN_PAGE = "首页";
    private static final String HOUSE_KINDS = "分类";
    private static final String MONTH_OPEN = "当月开盘";
    private static final String NEW_OPEN = "最新开盘";
    private static final int BEGIN_PAGE_FRAGMENT = 0;
    private static final int HOUSE_KINDS_FRAGMENT = 1;
    private static final int MONTH_OPEN_FRAGMENT = 2;
    private static final int NEW_OPEN_FRAGMENT = 3;
    private static final String HOST = "http://192.168.1.104:8080/";

    private Toolbar mToolbar;
    private DrawerLayout mDrawer_layout;
    private SpringingImageView mIcon_image;
    private SpringingImageView siv_sexs;
    private SpringingImageView mLogout;
    private SpringingTextView mTv_state;
    private ViewPager vp_view_page;
    private ArrayList<Fragment> fragments;
    private LoginState loginState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginpage);
        initView();
        initSpringLayout();//初始化Spring.jar效果的方法
        initViewPages();
        initEvent();
        initData();
    }

    private void initEvent() {

        //设置按钮监听
        mIcon_image.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mTv_state.setOnClickListener(this);

        //设置页面变化监听
        vp_view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("BeginPageActivity", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("BeginPageActivity", "onPageSelected:" + position);
                //当ViewPage滑动的时候
                int currentItem = vp_view_page.getCurrentItem();
                switch (currentItem) {//设置标题栏显示
                    case BEGIN_PAGE_FRAGMENT:
                        mToolbar.setTitle(BEGIN_PAGE);
                        break;
                    case HOUSE_KINDS_FRAGMENT:
                        mToolbar.setTitle(HOUSE_KINDS);
                        break;
                    case MONTH_OPEN_FRAGMENT:
                        mToolbar.setTitle(MONTH_OPEN);
                        break;
                    case NEW_OPEN_FRAGMENT:
                        mToolbar.setTitle(NEW_OPEN);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("BeginPageActivity", "onPageScrollStateChanged");
            }
        });
    }

    /**
     * 初始化ViewPage
     */
    private void initViewPages() {
        BeginPageFragment beginPageFragment = new BeginPageFragment();
        HouseKindsFragment houseKindsFragment = new HouseKindsFragment();
        MonthOpenFragment monthOpenFragment = new MonthOpenFragment();
        NewOpenFragment newOpenFragment = new NewOpenFragment();
        fragments = new ArrayList<>();
        fragments.add(beginPageFragment);
        fragments.add(houseKindsFragment);
        fragments.add(monthOpenFragment);
        fragments.add(newOpenFragment);
        //初始化Adapter这里使用FragmentPagerAdapter
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case BEGIN_PAGE_FRAGMENT:
                        return BEGIN_PAGE;
                    case HOUSE_KINDS_FRAGMENT:
                        return HOUSE_KINDS;
                    case MONTH_OPEN_FRAGMENT:
                        return MONTH_OPEN;
                    case NEW_OPEN_FRAGMENT:
                        return NEW_OPEN;
                    default:
                        return "";
                }
            }
        };

        vp_view_page.setAdapter(adapter);

        TabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(vp_view_page);
        //设置tab的图标显示
        tab_layout.getTabAt(BEGIN_PAGE_FRAGMENT).setIcon(R.drawable.beginpage_selector);
        tab_layout.getTabAt(HOUSE_KINDS_FRAGMENT).setIcon(R.drawable.house_kinds_selector);
        tab_layout.getTabAt(MONTH_OPEN_FRAGMENT).setIcon(R.drawable.month_open_selector);
        tab_layout.getTabAt(NEW_OPEN_FRAGMENT).setIcon(R.drawable.new_open_selector);
    }

    /**
     * 加载UI效果的方法
     */
    private void initSpringLayout() {
        SpringLayoutUtils springLayoutUtils = new SpringLayoutUtils(BeginPageActivity.this);
        springLayoutUtils.setSpringTextEffect(mTv_state);
        springLayoutUtils.setSpringImageEffect(siv_sexs);
        springLayoutUtils.setSpringImageEffect(mIcon_image);
        springLayoutUtils.setSpringImageEffect(mLogout);
    }

    private void initData() {
        //运行时权限
        ArrayList<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);

        new ReqPermissionUtils(BeginPageActivity.this).reqPermissions(permissionList);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(BEGIN_PAGE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_m3);//设置点击出现侧滑栏图标
        }

        mDrawer_layout = findViewById(R.id.drawer_layout);
        NavigationView mNav_view = findViewById(R.id.nav_view);

        //注意不是当前ContentView是不可以直接使用findViewById的
        View headerView = mNav_view.getHeaderView(0);
        mIcon_image = headerView.findViewById(R.id.icon_image);
        mIcon_image.setIsCircleImage(true);
        siv_sexs = headerView.findViewById(R.id.siv_sexs);

        mLogout = headerView.findViewById(R.id.logout);
        mTv_state = headerView.findViewById(R.id.tv_state);

        //viewPage布局
        vp_view_page = findViewById(R.id.vp_view_page);
        //设置缓存页面
        vp_view_page.setOffscreenPageLimit(3);
        //设置侧滑栏条目监听
        mNav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //头像点击监听
                mIcon_image.setOnClickListener(BeginPageActivity.this);
                Log.d("icon_image", "到了这里");
                //退出登录监听
                mLogout.setOnClickListener(BeginPageActivity.this);

                switch (item.getItemId()) {
                    //站内消息
                    case R.id.nav_station_information:
                        mDrawer_layout.closeDrawers();//单击后关闭侧栏
                        if (BeginPageActivity.this.loginState.isLogin()) {
                            goStationInformation();
                        } else {
                            goLoginActivity();
                        }
                        break;
                    //浏览过的楼盘信息
                    case R.id.nav_house_see:
                        mDrawer_layout.closeDrawers();
                        if (BeginPageActivity.this.loginState.isLogin()) {

                        } else {
                            goLoginActivity();
                        }
                        break;
                    //好友申请
                    case R.id.nav_friends_add:
                        mDrawer_layout.closeDrawers();
                        if (BeginPageActivity.this.loginState.isLogin()) {

                        } else {
                            goLoginActivity();
                        }
                        break;
                    //会员注册
                    case R.id.nav_vip_register:
                        mDrawer_layout.closeDrawers();
                        if (BeginPageActivity.this.loginState.isLogin()) {

                        } else {
                            goLoginActivity();
                        }
                        break;
                    //用户资料
                    case R.id.nav_user_message:
                        mDrawer_layout.closeDrawers();
                        if (BeginPageActivity.this.loginState.isLogin()) {
                            goShowUserInformation();
                        } else {
                            goLoginActivity();
                        }
                        break;
                }
                return true;
            }
        });

        //获取登录状态,设置用户图片，头像等信息
        loginState = new LoginState(BeginPageActivity.this);
        goShowUserInfo();
    }

    /**
     * 展示用户信息的方法块
     */
    private void goShowUserInformation() {
        Intent intent = new Intent(BeginPageActivity.this, UserInfoActivity.class);
        startActivity(intent);
    }

    /**
     * 设置用户信息显示的方法块
     */
    private void goShowUserInfo() {
        if (loginState.isLogin()) {//如果已经登录，设置基本信息
            mTv_state.setText(loginState.getUserId());
            mTv_state.setVisibility(View.VISIBLE);
            Glide.with(BeginPageActivity.this).load(HOST + loginState.getUserIconUrl()).into(mIcon_image);
            if (loginState.getUserSex().equals("m")) {
                Glide.with(BeginPageActivity.this).load(R.mipmap.boy2).into(siv_sexs);
            } else {
                Glide.with(BeginPageActivity.this).load(R.mipmap.nv2).into(siv_sexs);
            }
            siv_sexs.setVisibility(View.VISIBLE);
        }else {
            mTv_state.setText("点击登录");
            siv_sexs.setVisibility(View.GONE);
            Glide.with(BeginPageActivity.this).load(R.mipmap.default_avatar_man).into(mIcon_image);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像
            case R.id.icon_image:
                if (!loginState.isLogin()) {
                    goLoginActivity();
                }
                break;
            //点击退出登录
            case R.id.logout:
                if (!loginState.isLogin()) {
                    Toast.makeText(BeginPageActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                } else {
                    loginState.outLogin();
                    //相应的业务逻辑处理
                    goShowUserInfo();
                }
                break;
            case R.id.tv_state://点击登录
                if (!loginState.isLogin()) {
                    goLoginActivity();
                }
                break;
        }
    }

    //当程序要退出时弹框提示
    @Override
    public void onBackPressed() {
        //退出提示
        Snackbar.make(mToolbar.getRootView(), "确定退出？", Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginState.outLogin();
                finish();
            }
        }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer_layout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 6103:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        //当所有权限都开启后才进行地图活动
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(BeginPageActivity.this, "必须同意所有权限才能使用本功能", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Toast.makeText(BeginPageActivity.this, "设备已经获取所需权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BeginPageActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 引导用户登录
     */
    private void goLoginActivity() {
        Intent intent = new Intent(BeginPageActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 处理站内消息
     */
    private void goStationInformation() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        goShowUserInfo();
    }
}
