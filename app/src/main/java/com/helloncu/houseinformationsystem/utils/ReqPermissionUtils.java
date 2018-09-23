package com.helloncu.houseinformationsystem.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/3.
 * 这是一个运行时权限请求类
 * 针对安卓6.0以上版本危险的权限请求
 * 比如读写外存
 */

public class ReqPermissionUtils {
    private Activity mContext;

    public ReqPermissionUtils(Activity mContext) {
        this.mContext = mContext;
    }

    public void reqPermission(String permission) {
        //看当前权限是否已经获取
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
            //请求当前权限
            ActivityCompat.requestPermissions(mContext, new String[]{permission}, 6103);
        }
    }

    /**
     * 申请多个权限的方法
     * @param permissionList 权限列表
     */
    public void reqPermissions(ArrayList<String> permissionList) {
        //运行时权限
        ArrayList<String> permissionList2 = new ArrayList<>();

        for (String permission : permissionList) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList2.add(permission);
            }
        }
        if (!permissionList2.isEmpty()) {
            String[] permissions = permissionList2.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(mContext, permissions, 6103);
        }
    }
}
