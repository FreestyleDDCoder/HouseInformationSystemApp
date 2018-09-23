package com.helloncu.houseinformationsystem;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.helloncu.houseinformationsystem.entity.jsonType.JsonTransportType;
import com.helloncu.houseinformationsystem.utils.ClientSocketHandle;
import com.helloncu.houseinformationsystem.utils.MQTTMessageHandle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mainTest() {
        String query = JSON.toJSONString(new JsonTransportType("", "", "visitor", "houseInformationQuery"));
        String result = new ClientSocketHandle().sendMessage(query);
        Log.d("mainTest", "result:" + result);
        //new MQTTMessageHandle().sendMessage("stationInformation","这是测试消息2！");
    }
}