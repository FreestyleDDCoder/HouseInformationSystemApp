package com.helloncu.houseinformationsystem.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.adatpers.HouseInformationAdapter;
import com.helloncu.houseinformationsystem.adatpers.HouseKindsAdapter;
import com.helloncu.houseinformationsystem.entity.HouseInformation;
import com.helloncu.houseinformationsystem.entity.HouseKinds;
import com.helloncu.houseinformationsystem.entity.jsonType.JsonTransportType;
import com.helloncu.houseinformationsystem.utils.ClientSocketHandle;
import com.helloncu.houseinformationsystem.utils.GetHouseInfo;
import com.helloncu.houseinformationsystem.utils.LoginState;

import java.util.List;

/**
 * 这是楼房分类的Fragment
 */
public class HouseKindsFragment extends ListFragment {

    private HouseKindsAdapter houseKindsAdapter;
    private static final String ERROR_DATA_SHOW = "获取数据失败，稍后再试！";
    private static final String SUCCEED_DATA_SHOW = "数据已更新到最新状态";
    private HouseInformationAdapter houseInformationAdapter;
    private SwipeRefreshLayout srl_house_kinds;
    private static final int REFRESH_WAIT_TIME = 1000;//定义刷新间隔，单位毫秒
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_housekinds, container, false);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    private void initEvent() {
        //下拉刷新功能
        srl_house_kinds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            private long timeMillis;
            private long timeTemp;

            @Override
            public void onRefresh() {
                timeMillis = System.currentTimeMillis();
                if ((timeMillis - timeTemp) > REFRESH_WAIT_TIME) {//当时间间隔大于1一分钟时刷新才有效
                    houseKindsAdapter = null;
                    initData();
                    timeTemp = timeMillis;
                } else {
                    srl_house_kinds.setRefreshing(false);
                }
                Log.d("BeginPageFragment", "timeTemp" + timeTemp);
            }
        });

    }

    private void initView(View view) {
        srl_house_kinds = view.findViewById(R.id.srl_house_kinds);
        srl_house_kinds.setColorSchemeResources(R.color.colorPrimary);
        srl_house_kinds.setRefreshing(true);
    }

    /**
     * 加载类别数据
     */
    private void initData() {
        new GetHouseKinds().execute(new LoginState(getActivity()).getUserId());
    }

    /**
     * 加载数据线程类
     */
    private class GetHouseKinds extends AsyncTask<String, Integer, List<HouseKinds>> {

        @Override
        protected List<HouseKinds> doInBackground(String... strings) {
            String jsonString = JSON.toJSONString(new JsonTransportType("", "", strings[0], "houseKindsQuery"));
            String result = new ClientSocketHandle().sendMessage(jsonString);
            return JSON.parseArray(result, HouseKinds.class);
        }

        @Override
        protected void onPostExecute(List<HouseKinds> houseKinds) {
            super.onPostExecute(houseKinds);
            if (houseKinds != null) {
                if (houseKindsAdapter == null) {
                    houseKindsAdapter = new HouseKindsAdapter(houseKinds);
                    setListAdapter(houseKindsAdapter);
                } else {
                    houseKindsAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), SUCCEED_DATA_SHOW, Toast.LENGTH_SHORT).show();
                }
                Log.d("HouseKindsFragment", "AsyncTask List:" + houseKinds.toString());
            } else {
                Toast.makeText(getActivity(), ERROR_DATA_SHOW, Toast.LENGTH_SHORT).show();
            }
            srl_house_kinds.setRefreshing(false);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //获取相应类别的楼盘信息列表,设置新的适配器，显示新的列表
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter instanceof HouseKindsAdapter) {
            HouseKinds houseKinds = (HouseKinds) listAdapter.getItem(position);
            Log.d("HouseKindsFragment", "houseKinds:" + houseKinds.getHouseKinds());
            JsonTransportType jsonTransportType = new JsonTransportType(houseKinds, "", new LoginState(getActivity()).getUserId(), "getHouseInfoByKind");
            houseInformationAdapter = null;
            new GetHouseInfoByKind().execute(jsonTransportType);
        } else if (listAdapter instanceof HouseInformationAdapter) {
            HouseInformation houseInformation = (HouseInformation) listAdapter.getItem(position);
            Log.d("HouseKindsFragment", "houseInformation:" + houseInformation.toString());
        }
    }

    /**
     * 通过楼盘类型获取相应的楼盘信息
     */
    private class GetHouseInfoByKind extends GetHouseInfo {
        @Override
        protected List<HouseInformation> doInBackground(JsonTransportType... jsonTransportTypes) {
            return super.doInBackground(jsonTransportTypes);
        }

        @Override
        protected void onPostExecute(List<HouseInformation> houseInformations) {
            super.onPostExecute(houseInformations);
            if (houseInformations != null) {
                if (houseInformationAdapter == null) {
                    houseInformationAdapter = new HouseInformationAdapter(houseInformations);
                    setListAdapter(houseInformationAdapter);
                } else {
                    houseInformationAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), SUCCEED_DATA_SHOW, Toast.LENGTH_SHORT).show();
                }
                Log.d("HouseKindsFragment", "AsyncTask List:" + houseInformations.toString());
            } else {
                Toast.makeText(getActivity(), ERROR_DATA_SHOW, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
