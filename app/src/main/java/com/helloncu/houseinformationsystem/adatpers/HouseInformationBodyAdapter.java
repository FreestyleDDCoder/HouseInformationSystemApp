package com.helloncu.houseinformationsystem.adatpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.entity.HouseComment;
import com.helloncu.houseinformationsystem.entity.HouseInformation;

import java.text.SimpleDateFormat;
import java.util.List;

import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;
import dym.unique.com.springinglayoutlibrary.view.SpringingTextView;

/**
 * 这是楼盘信息具体页面的适配器
 */
public class HouseInformationBodyAdapter extends BaseAdapter {
    private Context mContext;
    private HouseInformation houseInformation;
    private List<HouseComment> houseComments;
    private static final String HOST = "http://192.168.1.104:8080/";
    private static final String NAME = "楼盘名称：";
    private static final String PRICE = "楼盘价格：";
    private static final String PRICE_UNIT = "元/平米";
    private static final String NUMBER = "售楼电话：";
    private static final String ADDRESS = "楼盘位置：";
    private static final String KINDS = "楼盘类型：";
    private static final String OPEN_TIME = "开盘时间：";
    private static final String INTRODUCE = "楼盘简介：";
    private static final String COMMENT_UNIT = "楼";
    private static final int TYPE_HOUSE_BODY = 0;
    private static final int TYPE_COMMENT = 1;
    private static final int TYPE_MAX_COUNT = 2;

    public HouseInformationBodyAdapter(HouseInformation houseInformation, List<HouseComment> houseComments) {
        this.houseInformation = houseInformation;
        this.houseComments = houseComments;
    }

    public HouseInformation getHouseInformation() {
        return houseInformation;
    }

    public void setHouseInformation(HouseInformation houseInformation) {
        this.houseInformation = houseInformation;
    }

    public List<HouseComment> getHouseComments() {
        return houseComments;
    }

    public void setHouseComments(List<HouseComment> houseComments) {
        this.houseComments = houseComments;
    }

    @Override
    public int getCount() {
        return houseComments.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return houseInformation;
        } else {
            return houseComments.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        HouseViewHolder houseViewHolder;
        CommentViewHolder commentViewHolder;

        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_HOUSE_BODY:
                if (convertView == null) {
                    houseViewHolder = new HouseViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_house_information_body, parent, false);
                    houseViewHolder.siv_body_picture = convertView.findViewById(R.id.siv_body_picture);
                    houseViewHolder.stv_house_body_name = convertView.findViewById(R.id.stv_house_body_name);
                    houseViewHolder.stv_house_body_price = convertView.findViewById(R.id.stv_house_body_price);
                    houseViewHolder.stv_house_body_kinds = convertView.findViewById(R.id.stv_house_body_kinds);
                    houseViewHolder.stv_house_body_number = convertView.findViewById(R.id.stv_house_body_number);
                    houseViewHolder.stv_house_body_openTime = convertView.findViewById(R.id.stv_house_body_openTime);
                    houseViewHolder.stv_house_body_address = convertView.findViewById(R.id.stv_house_body_address);
                    houseViewHolder.stv_house_introduce = convertView.findViewById(R.id.stv_house_introduce);
                    convertView.setTag(houseViewHolder);
                } else {
                    houseViewHolder = (HouseViewHolder) convertView.getTag();
                }
                Glide.with(mContext).load(HOST + houseInformation.getHouseIconUrl()).into(houseViewHolder.siv_body_picture);
                houseViewHolder.stv_house_body_name.setText(NAME + houseInformation.getHouseName());
                houseViewHolder.stv_house_body_price.setText(PRICE + houseInformation.getHousePrice() + PRICE_UNIT);
                houseViewHolder.stv_house_body_kinds.setText(KINDS + houseInformation.getHouseKinds());
                houseViewHolder.stv_house_body_number.setText(NUMBER + houseInformation.getPhoneNumber());
                houseViewHolder.stv_house_body_openTime.setText(OPEN_TIME + simpleDateFormat.format(houseInformation.getHouseInTime()));
                houseViewHolder.stv_house_body_address.setText(ADDRESS + houseInformation.getHouseAddress());
                houseViewHolder.stv_house_introduce.setText(INTRODUCE + houseInformation.getHouseIntroduce());
                return convertView;

            case TYPE_COMMENT:
                if (convertView == null) {
                    commentViewHolder = new CommentViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_house_comment, parent, false);
                    commentViewHolder.siv_user_icon = convertView.findViewById(R.id.siv_user_icon);
                    commentViewHolder.stv_user_name = convertView.findViewById(R.id.stv_user_name);
                    commentViewHolder.stv_comment_time = convertView.findViewById(R.id.stv_comment_time);
                    commentViewHolder.stv_comment_content = convertView.findViewById(R.id.stv_comment_content);
                    commentViewHolder.stv_comment_number = convertView.findViewById(R.id.stv_comment_number);
                    convertView.setTag(commentViewHolder);
                } else {
                    commentViewHolder = (CommentViewHolder) convertView.getTag();
                }
                HouseComment houseComment = houseComments.get(position - 1);
                Glide.with(mContext).load(HOST + houseComment.getUserIconUrl()).into(commentViewHolder.siv_user_icon);
                commentViewHolder.siv_user_icon.setIsCircleImage(true);
                commentViewHolder.stv_user_name.setText(houseComment.getUserId());
                commentViewHolder.stv_comment_time.setText(simpleDateFormat.format(houseComment.getCommentDate()));
                commentViewHolder.stv_comment_content.setText(houseComment.getComment());
                commentViewHolder.stv_comment_number.setText(houseComments.size() + 1 - position + COMMENT_UNIT);
                return convertView;

            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOUSE_BODY;
        } else {
            return TYPE_COMMENT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    class CommentViewHolder {
        //评论信息界面
        SpringingImageView siv_user_icon;
        SpringingTextView stv_user_name;
        SpringingTextView stv_comment_time;
        SpringingTextView stv_comment_content;
        SpringingTextView stv_comment_number;
    }

    class HouseViewHolder {
        //楼盘的信息
        SpringingImageView siv_body_picture;
        SpringingTextView stv_house_body_name;
        SpringingTextView stv_house_body_price;
        SpringingTextView stv_house_body_kinds;
        SpringingTextView stv_house_body_number;
        SpringingTextView stv_house_body_openTime;
        SpringingTextView stv_house_body_address;
        SpringingTextView stv_house_introduce;
    }
}
