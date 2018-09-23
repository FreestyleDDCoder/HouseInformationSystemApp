package com.helloncu.houseinformationsystem.adatpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.activitys.HouseInformationActivity;
import com.helloncu.houseinformationsystem.entity.HouseInformation;
import com.helloncu.houseinformationsystem.utils.SpringLayoutUtils;

import java.io.Serializable;
import java.util.List;

import dym.unique.com.springinglayoutlibrary.handler.SpringingAlphaShowHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingNotificationRotateHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchScaleHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTranslationShowHandler;
import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;
import dym.unique.com.springinglayoutlibrary.view.SpringingTextView;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingLinearLayout;

public class HouseInformationAdapter extends BaseAdapter {
    private List<HouseInformation> houseInformations;
    private Context mContext;
    private static final String HOST = "http://192.168.1.104:8080/";
    private static final String NAME = "楼盘名称：";
    private static final String PRICE = "楼盘价格：";
    private static final String PRICE_UNIT = "元/平米";
    private static final String NUMBER = "售楼电话：";
    private static final String ADDRESS = "楼盘位置：";
    private static final String KINDS = "楼盘类型：";

    public HouseInformationAdapter(List<HouseInformation> houseInformations) {
        this.houseInformations = houseInformations;
    }

    @Override
    public int getCount() {
        return houseInformations.size();
    }

    @Override
    public Object getItem(int position) {
        return houseInformations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_house_information, parent, false);
            viewHolder.sll_house_information = convertView.findViewById(R.id.sll_house_information);
            viewHolder.sll_house_information_text = convertView.findViewById(R.id.sll_house_information_text);
            viewHolder.siv_house_picture = convertView.findViewById(R.id.siv_house_picture);
            viewHolder.stv_house_name = convertView.findViewById(R.id.stv_house_name);
            viewHolder.stv_house_price = convertView.findViewById(R.id.stv_house_price);
            viewHolder.stv_house_number = convertView.findViewById(R.id.stv_house_number);
            viewHolder.stv_house_address = convertView.findViewById(R.id.stv_house_address);
            viewHolder.stv_house_kinds = convertView.findViewById(R.id.stv_house_kinds);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HouseInformation houseInformation = houseInformations.get(position);
        //设置图片
        Glide.with(mContext).load(HOST + houseInformation.getHouseIconUrl()).into(viewHolder.siv_house_picture);
        viewHolder.stv_house_name.setText(NAME + houseInformation.getHouseName());
        viewHolder.stv_house_price.setText(PRICE + houseInformation.getHousePrice() + PRICE_UNIT);
        viewHolder.stv_house_number.setText(NUMBER + houseInformation.getPhoneNumber());
        viewHolder.stv_house_kinds.setText(KINDS + houseInformation.getHouseKinds());
        viewHolder.stv_house_address.setText(ADDRESS + houseInformation.getHouseAddress());
        initSpringLayout(viewHolder);
        initEvent(viewHolder, houseInformation);
        return convertView;
    }

    /**
     * 点击监听事件
     *
     * @param viewHolder viewHolder
     */
    private void initEvent(ViewHolder viewHolder, final HouseInformation houseInformation) {
        viewHolder.sll_house_information_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });

        viewHolder.siv_house_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });

        viewHolder.stv_house_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });

        viewHolder.stv_house_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });

        viewHolder.stv_house_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });

        viewHolder.stv_house_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });

        viewHolder.stv_house_kinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShowHouseInformation(houseInformation);
            }
        });
    }

    /**
     * 展现SpringLayout动态效果的方法
     *
     * @param viewHolder viewHolder
     */
    private void initSpringLayout(ViewHolder viewHolder) {
        new SpringingAlphaShowHandler(mContext, viewHolder.sll_house_information).showChildrenSequence(500, 100);
        new SpringingTranslationShowHandler(mContext, viewHolder.sll_house_information).showChildrenSequence(500, 100);
        new SpringingAlphaShowHandler(mContext, viewHolder.sll_house_information_text).showChildrenSequence(500, 100);
        new SpringingTranslationShowHandler(mContext, viewHolder.sll_house_information_text).showChildrenSequence(500, 100);
        new SpringingNotificationRotateHandler(mContext, viewHolder.siv_house_picture).start(1);
    }

    /**
     * 用于单独展示某一楼盘信息
     */
    private void goShowHouseInformation(HouseInformation houseInformation) {
        Log.d("HouseInformationAdapter", "goShowHouseInformation" + houseInformation.toString());
        Intent intent = new Intent(mContext.getApplicationContext(), HouseInformationActivity.class);
        intent.putExtra("houseInformation", houseInformation);
        mContext.startActivity(intent);
    }

    class ViewHolder {
        SpringingLinearLayout sll_house_information;
        SpringingLinearLayout sll_house_information_text;
        SpringingImageView siv_house_picture;
        SpringingTextView stv_house_name;
        SpringingTextView stv_house_price;
        SpringingTextView stv_house_number;
        SpringingTextView stv_house_address;
        SpringingTextView stv_house_kinds;
    }
}
