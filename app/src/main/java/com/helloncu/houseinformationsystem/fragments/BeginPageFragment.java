package com.helloncu.houseinformationsystem.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.adatpers.HouseInformationAdapter;
import com.helloncu.houseinformationsystem.entity.HouseInformation;
import com.helloncu.houseinformationsystem.utils.HouseInformationAsyncTask;
import com.helloncu.houseinformationsystem.utils.LoginState;

import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

/**
 * 这是开始界面的fragment
 */
public class BeginPageFragment extends ListFragment {

    private SwipeRefreshLayout srl_house_information;
    private HouseInformationAdapter houseInformationAdapter;
    private static final String ERROR_DATA_SHOW = "获取数据失败，稍后再试！";
    private static final String SUCCEED_DATA_SHOW = "数据已更新到最新状态";
    private static final int REFRESH_WAIT_TIME = 10000;//定义刷新间隔，单位毫秒
    private LoginState loginState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_beginpage, container, false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        srl_house_information = view.findViewById(R.id.srl_house_information);
        srl_house_information.setColorSchemeResources(R.color.colorPrimary);
        srl_house_information.setRefreshing(true);
    }

    private void initData() {
        loginState = new LoginState(getActivity());
        Log.d("BeginPageFragment", "loginState:" + loginState.getUserId());
        //获取数据
        new GetHouseInformation().execute(loginState.getUserId(), "houseInformationQuery");
    }

    private void initEvent() {
        //刷新监听
        srl_house_information.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            private long timeMillis;
            private long timeTemp;

            @Override
            public void onRefresh() {
                timeMillis = System.currentTimeMillis();
                if ((timeMillis - timeTemp) > REFRESH_WAIT_TIME) {//当时间间隔大于1一分钟时刷新才有效
                    new GetHouseInformation().executeOnExecutor(THREAD_POOL_EXECUTOR, loginState.getUserId(), "houseInformationQuery");
                    timeTemp = timeMillis;
                } else {
                    srl_house_information.setRefreshing(false);
                }
                Log.d("BeginPageFragment", "timeTemp" + timeTemp);
            }
        });
    }

    /**
     * 继承自自定义的AsyncTask
     */
    private class GetHouseInformation extends HouseInformationAsyncTask {

        @Override
        public List<HouseInformation> doInBackground(String... strings) {
            return super.doInBackground(strings);
        }

        @Override
        protected void onPostExecute(final List<HouseInformation> houseInformations) {
            super.onPostExecute(houseInformations);
            //注意，当list为空时会报错
            if (houseInformations != null) {
                if (houseInformationAdapter == null) {
                    houseInformationAdapter = new HouseInformationAdapter(houseInformations);
                    setListAdapter(houseInformationAdapter);
                } else {
                    houseInformationAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), SUCCEED_DATA_SHOW, Toast.LENGTH_SHORT).show();
                }
                Log.d("BeginPageFragment", "AsyncTask List:" + houseInformations.toString());
            } else {
                Toast.makeText(getActivity(), ERROR_DATA_SHOW, Toast.LENGTH_SHORT).show();
            }
            srl_house_information.setRefreshing(false);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //焦点呗子控件获取，所以本方法不会执行
        Log.d("BeginPageFragment", "onListItemClick:" + position + "/" + id);
    }

}
