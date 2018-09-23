package com.helloncu.houseinformationsystem.adatpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.helloncu.houseinformationsystem.R;
import com.helloncu.houseinformationsystem.entity.HouseKinds;

import java.util.List;

public class HouseKindsAdapter extends BaseAdapter {
    private List<HouseKinds> houseKinds;
    private Context context;

    public HouseKindsAdapter(List<HouseKinds> houseKinds) {
        this.houseKinds = houseKinds;
    }

    @Override
    public int getCount() {
        return houseKinds.size();
    }

    @Override
    public Object getItem(int position) {
        return houseKinds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (context == null) {
            context = parent.getContext();
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_house_kinds, parent, false);
            viewHolder.tv_house_kinds = convertView.findViewById(R.id.tv_house_kinds);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HouseKinds houseKinds = this.houseKinds.get(position);
        viewHolder.tv_house_kinds.setText(houseKinds.getHouseKinds());
        return convertView;
    }

    class ViewHolder {
        TextView tv_house_kinds;
    }
}
