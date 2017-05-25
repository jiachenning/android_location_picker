package com.zeonic.icity.location_picker.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zeonic.icity.location_picker.R;
import com.zeonic.icity.location_picker.entity.Location;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninja on 5/10/17.
 */

public class MyAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Location> mLocations;

    MyAdapter(ArrayList<Location> locations, Activity activity) {
        mLocations = locations;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public Object getItem(int i) {
        return mLocations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            ButterKnife.bind(viewHolder, convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.location = (Location) getItem(position);
        bindView(viewHolder);
        return convertView;
    }

    private void bindView(ViewHolder viewHolder) {
        Location location = viewHolder.location;
        viewHolder.latText.setText(String.valueOf(location.getLat()));
        viewHolder.lngText.setText(String.valueOf(location.getLng()));
        viewHolder.remarkText.setText(String.valueOf(location.getRemark()));
        viewHolder.levelText.setText("关卡"+location.getLevel());
    }

    public static class ViewHolder implements Serializable{
        Location location;

        @Bind(R.id.txt_lat)
        TextView latText;
        @Bind(R.id.txt_lng)
        TextView lngText;
        @Bind(R.id.txt_remark)
        TextView remarkText;
        @Bind(R.id.txt_level)
        TextView levelText;
    }
}
