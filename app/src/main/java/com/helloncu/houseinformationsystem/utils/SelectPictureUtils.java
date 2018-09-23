package com.helloncu.houseinformationsystem.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;

/**
 * 这是选择图片的方法块
 */
public class SelectPictureUtils {
    private Activity mContext;
    private Uri tempUri;
    private static final int TAKE_PICTURE = 0;
    private static final int LOCAL_PICTURE = 1;
    private static final int PICTURE_CUT = 2;


    public SelectPictureUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * 用于选择头像图片的方法块
     */
    public void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("设置头像");
        String[] item = new String[]{"选择本地图片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    //选择本地图片
                    case TAKE_PICTURE:
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        mContext.startActivityForResult(openAlbumIntent, TAKE_PICTURE);
                        break;
                    //拍照
                    case LOCAL_PICTURE:
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        mContext.startActivityForResult(openCameraIntent, LOCAL_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    /**
     * 裁剪图片方法实现
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
            uri = tempUri;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        mContext.startActivityForResult(intent, PICTURE_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    public Bitmap setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap photo = null;
        if (extras != null) {
            // 取得SDCard图片路径做显示
            //得到得是压缩图
            photo = extras.getParcelable("data");
        }
        return photo;
    }
}
