package com.helloncu.houseinformationsystem.utils;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.helloncu.houseinformationsystem.entity.HouseInformation;
import com.helloncu.houseinformationsystem.entity.jsonType.JsonTransportType;

import java.util.List;

public class GetHouseInfo extends AsyncTask<JsonTransportType, Integer, List<HouseInformation>> {
    @Override
    protected List<HouseInformation> doInBackground(JsonTransportType... jsonTransportTypes) {
        String jsonString = JSON.toJSONString(jsonTransportTypes[0]);
        String result = new ClientSocketHandle().sendMessage(jsonString);
        return JSON.parseArray(result, HouseInformation.class);
    }
}
