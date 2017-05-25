package com.zeonic.icity.location_picker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zeonic.icity.location_picker.R;
import com.zeonic.icity.location_picker.apis.BootstrapService;
import com.zeonic.icity.location_picker.entity.Location;
import com.zeonic.icity.location_picker.entity.ZeonicMarkersResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninja on 5/12/17.
 */

public class LocationListActivity extends AppCompatActivity {
    @Bind(R.id.listview)
    ListView listview;
    private BaseAdapter adapter;
    ArrayList<Location> mlist;
    private BootstrapService bootstrapService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlist = (ArrayList<Location>) getIntent().getSerializableExtra("data");
        setContentView(R.layout.location_list_activity);
        ButterKnife.bind(this);
        adapter = new MyListAdapter(this, mlist);
        listview.setAdapter(adapter);
        bootstrapService = new BootstrapService();
    }

    public void deleteLocation(final Location location) {
        AsyncTask task = new AsyncTask<Object, Object, ZeonicMarkersResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress();
            }

            @Override
            protected ZeonicMarkersResponse doInBackground(Object[] objects) {
                ZeonicMarkersResponse response = bootstrapService.deleteMarkers(location.getId());
                return response;
            }

            @Override
            protected void onPostExecute(ZeonicMarkersResponse resp) {
                super.onPostExecute(resp);
                hideProgress();
                if(resp != null && resp.getStatus() == 200){
                    Toast.makeText(LocationListActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    mlist.remove(location);
                }else {
                    Toast.makeText(LocationListActivity.this, "删除失败", Toast.LENGTH_LONG).show();
                }
            }
        };
        task.execute();
    }

    private class MyListAdapter extends BaseAdapter {

        private final List<Location> mlist;
        private final LayoutInflater inflater;

        public MyListAdapter(LocationListActivity locationListActivity, List<Location> list) {
            this.mlist = list;
            inflater = LayoutInflater.from(locationListActivity);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int i) {
            return mlist.get(i);
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
//                convertView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(final View view) {
//                        AlertDialog dialog = new AlertDialog.Builder(view.getContext())
//                                .setMessage("确认要删除吗?")
//                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        if(view.getTag() instanceof ViewHolder){
//                                            ViewHolder holder = (ViewHolder) view.getTag();
//                                            deleteLocation(holder.location);
//                                        }
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .show();
//                        return false;
//                    }
//                });
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
    }

    public static class ViewHolder implements Serializable {
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

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * show alert dialog with msg, only with confirm button
     * @param msg
     */
    protected Dialog showAlertDialog(String msg) {
        return new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("获取数据中...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        return dialog;
    }
}
