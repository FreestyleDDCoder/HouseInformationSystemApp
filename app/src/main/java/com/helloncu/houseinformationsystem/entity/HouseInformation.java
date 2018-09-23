package com.helloncu.houseinformationsystem.entity;

import java.io.Serializable;
import java.util.Date;

public class HouseInformation implements Serializable {
    //楼盘ID号
    private String houseId;
    //楼盘名字
    private String houseName;
    //楼盘地址
    private String houseAddress;
    //楼盘类型
    private String houseKinds;
    //楼盘价格
    private String housePrice;
    //楼盘所属地区
    private String houseArea;
    //楼盘添加时间
    private Date houseInTime;
    //楼盘简介
    private String houseIntroduce;
    //楼盘销售电话号码
    private String phoneNumber;
    //楼盘图片地址链接
    private String houseIconUrl;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getHouseKinds() {
        return houseKinds;
    }

    public void setHouseKinds(String houseKinds) {
        this.houseKinds = houseKinds;
    }

    public String getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(String housePrice) {
        this.housePrice = housePrice;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public Date getHouseInTime() {
        return houseInTime;
    }

    public void setHouseInTime(Date houseInTime) {
        this.houseInTime = houseInTime;
    }

    public String getHouseIntroduce() {
        return houseIntroduce;
    }

    public void setHouseIntroduce(String houseIntroduce) {
        this.houseIntroduce = houseIntroduce;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHouseIconUrl() {
        return houseIconUrl;
    }

    public void setHouseIconUrl(String houseIconUrl) {
        this.houseIconUrl = houseIconUrl;
    }
}
