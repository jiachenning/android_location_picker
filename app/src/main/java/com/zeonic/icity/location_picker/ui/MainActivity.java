package com.zeonic.icity.location_picker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zeonic.icity.location_picker.BootstrapApplication;
import com.zeonic.icity.location_picker.R;
import com.zeonic.icity.location_picker.apis.BootstrapService;
import com.zeonic.icity.location_picker.entity.Location;
import com.zeonic.icity.location_picker.entity.ZeonicResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements BDLocationListener {
    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.TV_latitude)
    TextView latText;
    @Bind(R.id.TV_longitude)
    TextView lngText;
    @Bind(R.id.remark)
    TextView remarkText;

    @Bind(R.id.numPicker)
    NumberPicker numPicker;
    LocationClient mLocClient;
    BootstrapService bootstrapService;
    private ArrayList<Location> mlist = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initLocationClient(3000);

        bootstrapService = new BootstrapService();

        myAdapter = new MyAdapter(mlist, this);
        listView.setAdapter(myAdapter);
        numPicker.setMinValue(1);
        numPicker.setMaxValue(99);
    }

    /**
     * init baidu location client and start it. the callback will invoke onReceiveLocation()
     *
     * @param scanSpan in Millisecond, 0 is scan once
     */
    protected void initLocationClient(int scanSpan) {
        mLocClient = new LocationClient(BootstrapApplication.getInstance());
        //开始定位
        Timber.e("开始定位");
        // 定位初始化
        mLocClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(scanSpan);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * Should be called when upload button click
     * @param view
     */
    public void onUploadLocationClick(View view){
        Location[] list = new Location[1];

        if(remarkText.getText().length() == 0){
            Toast.makeText(this, "请输入备注", Toast.LENGTH_LONG).show();
            return;
        }
        final Location location = new Location();
        location.setLat(Double.valueOf(latText.getText().toString()));
        location.setLng(Double.valueOf(lngText.getText().toString()));
        location.setRemark(remarkText.getText().toString());
        location.setLevel(numPicker.getValue());
        // Run task to access http
          AsyncTask task = new AsyncTask<Location, Void, ZeonicResponse>() {

            @Override
            protected ZeonicResponse doInBackground(Location... locations) {
                ZeonicResponse zeonicResponse = bootstrapService.locationPicker(locations[0]);
                return zeonicResponse;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress();
            }

            @Override
            protected void onPostExecute(ZeonicResponse zeonicResponse) {
                super.onPostExecute(zeonicResponse);
                hideProgress();
                if(zeonicResponse ==null || zeonicResponse.getStatus() != 200){
                    showAlertDialog("上传失败");
                }else{
                    showAlertDialog("上传成功");
                    mlist.add(location);
                    myAdapter.notifyDataSetChanged();
                }
            }
        };
        list[0] = location;
        task.execute(list);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Timber.e("onReceiveLocation");
        latText.setText(String.valueOf(bdLocation.getLatitude()));
        lngText.setText(String.valueOf(bdLocation.getLongitude()));
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
