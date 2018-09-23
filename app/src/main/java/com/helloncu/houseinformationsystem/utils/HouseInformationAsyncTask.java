package com.helloncu.houseinformationsystem.utils;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.helloncu.houseinformationsystem.entity.HouseInformation;
import com.helloncu.houseinformationsystem.entity.jsonType.JsonTransportType;

import java.util.List;

public class HouseInformationAsyncTask extends AsyncTask<String, Integer, List<HouseInformation>> {
    @Override
    public List<HouseInformation> doInBackground(String... strings) {
        String query = JSON.toJSONString(new JsonTransportType("", "", strings[0], strings[1]));
        String result = new ClientSocketHandle().sendMessage(query);
        return JSON.parseArray(result, HouseInformation.class);
    }
}
