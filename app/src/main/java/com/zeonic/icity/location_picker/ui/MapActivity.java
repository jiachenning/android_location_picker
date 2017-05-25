package com.zeonic.icity.location_picker.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.zeonic.icity.location_picker.R;
import com.zeonic.icity.location_picker.apis.BootstrapService;
import com.zeonic.icity.location_picker.entity.Location;
import com.zeonic.icity.location_picker.entity.ZeonicMarkersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by IrrElephant on 17/5/11.
 */

public class MapActivity extends AppCompatActivity {
    MapView mMapView = null;
    MapStatus mMapStatus;
    MapStatusUpdate mMapStatusUpdate;
    BaiduMap mBaiduMap;
    Marker coverMarker;
    List<Map<String, String>> coverArray = new ArrayList<Map<String, String>>();
    private BootstrapService bootstrapService;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private ArrayList<Location> mlist = new ArrayList<>();

    @Bind(R.id.my_awesome_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_activity);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        bootstrapService = new BootstrapService();
        setSupportActionBar(toolbar);
        queryMarkers();
        findViewById(R.id.remove_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoList(view);
            }
        });
        MapStatus.Builder builder = new MapStatus.Builder()
                .zoom(19);
        MapStatus mMapStatus = builder.build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getExtraInfo() != null && marker.getExtraInfo().get("data") instanceof Location){
                    Location location = (Location) marker.getExtraInfo().getSerializable("data");
                    String msg = String.format("关卡%s %s", String.valueOf(location.getLevel()), location.getRemark());
                    Toast.makeText(MapActivity.this, msg, Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    /**
     * query all the locations and show
     */
    private void queryMarkers() {
        AsyncTask task = new AsyncTask<Object, Void, ZeonicMarkersResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress();
            }

            @Override
            protected ZeonicMarkersResponse doInBackground(Object... objects) {
                ZeonicMarkersResponse response = bootstrapService.queryMarkers();
                return response;
            }

            @Override
            protected void onPostExecute(ZeonicMarkersResponse response) {
                super.onPostExecute(response);
                hideProgress();
                if(response.getStatus() == 200 && response.getResult() != null
                        && response.getResult().getLocationPickers() != null){
                    removeMarkers();
                    buildMarkers(response.getResult().getLocationPickers());
                }
            }
        };
        task.execute();
    }

    private void buildMarkers(List<Location> markers) {
        Location last = null;
        for (Location location : markers) {
            last = location;
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_map_bike1, options);
            Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.75), (int)(bitmap.getHeight()*0.75), true);
//            int lastIndex = allWayPoints.size() - 1;

            Bundle bundle = new Bundle();
            bundle.putSerializable("data", location);
            Timber.e("buildMarkers: " + location.toString() );
            MarkerOptions option = new MarkerOptions()
                    .position(new LatLng(location.getLat(), location.getLng()))
                    .zIndex(12)//above 10
                    .anchor(0.5f, 0.5f)
                    .extraInfo(bundle)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            Overlay overlay = mBaiduMap.addOverlay(option);
            if(overlay instanceof Marker){
                mlist.add(location);
                this.markerList.add((Marker) overlay);
            }
        }
        if(last != null){
            centerLocation(new LatLng(31.054419, 121.213037));
        }
    }

    /**
     * make the location center
     *
     * @param location 让该地址居中
     */
    public void centerLocation(LatLng location) {
        Timber.i("centerLocation " + location);
//        if(mBaiduMap == null)return;
        if (location == null) return;
        LatLng ll = location;
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
//                .zoom(DEFAULT_MAP_ZOOM_LEVEL)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    private void removeMarkers() {
        for (Marker marker : markerList){
            marker.remove();
        }
        mlist.clear();
        markerList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

//    Intent intent = getIntent();
//    coverArray = (List<Map<String, String>>) intent.getSerializableExtra("coverArray");
//    System.out.println("coverArray：" + coverArray.size());
//    if (coverArray.size() != 0)
//        {
//            //经纬度   Latitude and longitude
//            String latitude = coverArray.get(0).get("latitude");
//            String longitude = coverArray.get(0).get("longitude");
//            System.out.println("latitude:" + latitude + "   longitude:" + longitude);
//            LatLng cenpt = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
//            mMapStatus = new MapStatus.Builder().target(cenpt).zoom(16).build();
//            mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//            mBaiduMap.setMapStatus(mMapStatusUpdate);
//            addMarkers();   //添加标注。
//        }

    private Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private void addMarkers() {
        double[][] coordinates = new double[coverArray.size()][2];
        for (int i = 0; i < coverArray.size(); i++) {
            coordinates[i][0] = Double.parseDouble(coverArray.get(i).get("longitude"));
            coordinates[i][1] = Double.parseDouble(coverArray.get(i).get("latitude"));
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.marker, null);
        TextView coverLevelTv = (TextView) view.findViewById(R.id.cover_level);
        TextView coverLatTv = (TextView) view.findViewById(R.id.cover_lat);
        TextView coverLngTv = (TextView) view.findViewById(R.id.cover_lng);
        for (int j = 0; j < coverArray.size(); j++) {
            LatLng lla = new LatLng(coordinates[j][1], coordinates[j][0]);
            coverLevelTv.setText(coverArray.get(j).get("covername"));
            coverLatTv.setText("纬度：" + coverArray.get(j).get("latitude") + "℃");
            coverLngTv.setText("经度：" + coverArray.get(j).get("longitude"));
            if (coverArray.get(j).get("lostflag").equals("1")) {
                coverLevelTv.setBackgroundColor(Color.RED);
                coverLatTv.setBackgroundColor(Color.RED);
                coverLngTv.setBackgroundColor(Color.RED);
            } else {
                coverLevelTv.setBackgroundColor(Color.GRAY);
                coverLatTv.setBackgroundColor(Color.WHITE);
                coverLngTv.setBackgroundColor(Color.WHITE);
            }
            BitmapDescriptor bd1 = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(view));
            MarkerOptions ooA = new MarkerOptions().position(lla).icon(bd1).zIndex(9).draggable(true).title(coverArray.get(j).get("coverid"));

            coverMarker = (Marker) mBaiduMap.addOverlay(ooA);
        }
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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_remove:
//                startActivityForResult(new Intent(this, LocationListActivity.class), 100);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            queryMarkers();
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.remove_text)
    public void gotoList(View v){
        startActivityForResult(new Intent(this, LocationListActivity.class)
                .putExtra("data", mlist), 100);
    }
}

