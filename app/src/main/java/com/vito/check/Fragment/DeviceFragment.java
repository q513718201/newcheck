package com.vito.check.Fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.NaviPara;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.vito.check.MainActivity;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.Device;
import com.vito.check.data.DialogUtils;
import com.vito.check.util.DensityUtils;
import com.vito.check.util.SensorEventHelper;
import com.vito.check.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/16.
 */

public class DeviceFragment extends Fragment implements View.OnClickListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, LocationSource, AMapLocationListener {

    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.arrow)
    ImageView mArrow;
    MapView mapView;

    private MainActivity mActivity;
    private PopupWindow mPopupWindow;
    private AMap aMap;
    private LatLng latlng = null;
    private MarkerOptions markerOption;
    private List<LatLng> mList;
    private Marker currentMarker;
    private Observable<Device> mDevices;
    private String mToken;
    private SimpleArcDialog mDialog;

    private SensorEventHelper mSensorHelper;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private Circle mCircle;
    private Marker mLocMarker;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean mFirstFix = false;
    private LatLng mylocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, view);
        mActivity.setToolBarTittle("设备状态");
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
        mArrow.setOnClickListener(this);
        initMap();

        return view;
    }

    //获取之前请求的值
    private void init() {
        mToken = SpUtils.getString(mActivity, "token", "");
        String et = SpUtils.getString(mActivity, "et", "");
        String isOnLine = SpUtils.getString(mActivity, "isOnLine", "");
        String isChecked = SpUtils.getString(mActivity, "isChecked", "");
        if (et != "") {
            mEt.setText(et);
            getDevices(isOnLine, isChecked);
        }


    }

    //初始化地图
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mSensorHelper = new SensorEventHelper(mActivity);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);

    }

    //开启定位
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setLocationSource(this);
    }


    //网络请求
    public void getDevices(final String isOnline, final String isChecked) {
        if (mDialog == null) {
            mDialog = new SimpleArcDialog(mActivity);
        }

        ArcConfiguration arcConfiguration = new ArcConfiguration(mActivity);
        mDialog.setConfiguration(arcConfiguration);
        arcConfiguration.setText("正在查询中...");
        mDialog.show();

        Log.d("aa", mToken);

        if (!isOnline.equals("")) {
            mDevices = ApiWrapper.getInstance().getDevices(mToken, isOnline);
        } else {
            mDevices = ApiWrapper.getInstance().getAllDevices(mToken);
        }
        if (!isChecked.equals("")) {
            mDevices = ApiWrapper.getInstance().getCheckedDevices(mToken, isChecked);
        }


        Log.d("aa", "我开始网络请求");
        mActivity.addSubscription(mDevices, new Subscriber<Device>() {
            @Override
            public void onCompleted() {
                mDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Device device) {
                Log.d("aa", "我请求了");
                Log.d("aa", device.getContent().toString());

                if (aMap != null) {
                    aMap.clear();
                }
                if (device.getContent().size() == 0) {
                    mFirstFix = false;
                    mLocMarker = null;
                    Toast.makeText(mActivity, "未查到相关设备信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addMarkersToMap(device);
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        String isOnLine = "";
        String isChecked = "";
        if (v.getId() == R.id.arrow) {
            showPopWindow();
            return;
        }
        switch (v.getId()) {
            case R.id.all:
                mEt.setText("全部设备");
                SpUtils.putString(mActivity, "et", "全部设备");
                isOnLine = "";
                isChecked = "";
                break;
            case R.id.not_check:
                mEt.setText("未巡检设备");
                SpUtils.putString(mActivity, "et", "未巡检设备");
                isOnLine = "";
                isChecked = "0";
                break;
            case R.id.yes_check:
                mEt.setText("已巡检设备");
                SpUtils.putString(mActivity, "et", "已巡检设备");
                isOnLine = "";
                isChecked = "1";
                break;
            case R.id.online:
                mEt.setText("在线设备");
                SpUtils.putString(mActivity, "et", "在线设备");
                isOnLine = "1";
                isChecked = "";
                break;
            case R.id.outline:
                mEt.setText("离线设备");
                SpUtils.putString(mActivity, "et", "离线设备");
                isOnLine = "0";
                isChecked = "";
                break;
        }
        getDevices(isOnLine, isChecked);
        SpUtils.putString(mActivity, "isOnLine", isOnLine);
        SpUtils.putString(mActivity, "isChecked", isChecked);
        mPopupWindow.dismiss();

    }

    /**
     * 切换查询设备
     */
    private void showPopWindow() {
        if (mPopupWindow == null) {
            //弹出PopupWindow
            int width = mEt.getWidth();//PopupWindow宽度和编辑框一致
            int height = DensityUtils.dp2px(mActivity, 140);
            mPopupWindow = new PopupWindow(width, height);
        }
        View view = View.inflate(mActivity, R.layout.device_pop_window, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.all);
        TextView tv2 = (TextView) view.findViewById(R.id.not_check);
        TextView tv3 = (TextView) view.findViewById(R.id.yes_check);
        TextView tv4 = (TextView) view.findViewById(R.id.online);
        TextView tv5 = (TextView) view.findViewById(R.id.outline);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);

        //让PopupWindow能够消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow.showAsDropDown(mEt);
    }

    /**
     * 添加标记
     *
     * @param b
     */
    private void addMarkersToMap(Device b) {
        List<Device.ContentBean> content = b.getContent();
        Log.d("aa", "获取到的设备数量" + content.size() + "");
        mList = new ArrayList<>();
        for (Device.ContentBean device : content) {
            double lat = device.getLat();
            double lng = device.getLng();
            latlng = new LatLng(lat, lng);
            mList.add(latlng);
        }
        Log.d("aa", "坐标数量" + mList.size() + "");
        for (int i = 0; i < mList.size(); i++) {
            markerOption = new MarkerOptions()
                    .position(mList.get(i))
                    .draggable(true)
                    .title(content.get(i).getT_id()).snippet(b.getContent().get(i).getDescription());
            if (content.get(i).getOnline().equals("0")) {
                markerOption.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED));
            } else {
                markerOption.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }
            aMap.addMarker(markerOption);
        }
        //将地图移动至个人位置
        mFirstFix = false;
        mLocMarker = null;
    }

    /**
     * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
     */
    public void startAMapNavi(Marker marker) {
        // 构造导航参数
        NaviPara naviPara = new NaviPara();
        // 设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);

        // 调起高德地图导航
        try {
            AMapUtils.openAMapNavi(naviPara, mActivity);
        } catch (com.amap.api.maps2d.AMapException e) {

            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(mActivity);

        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (currentMarker != null) {
            currentMarker.hideInfoWindow();
            currentMarker = null;
        } else {
            marker.showInfoWindow();
            currentMarker = marker;
        }

        return true;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = View.inflate(mActivity, R.layout.pop_window, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        ImageButton button = (ImageButton) view
                .findViewById(R.id.start_amap_app);
        // 调起高德地图app
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAMapNavi(marker);
            }
        });
        return view;
    }


    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mActivity);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔时间
            mLocationOption.setInterval(1000 * 3);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {

            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mylocation = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
                    addCircle(mylocation, amapLocation.getAccuracy());//添加定位精度圆
                    addMarker(mylocation);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 13));
                } else {
                    mCircle.setCenter(mylocation);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(mylocation);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
              //  DialogUtils.dialog(mActivity);
                aa();
            }
        }
    }

    private void aa() {
        if (Build.VERSION.SDK_INT >= 23) {//判断当前系统的版本
            int checkWriteStoragePermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION);//获取系统是否被授予该种权限
            if (checkWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {//如果没有被授予
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;//请求获取该种权限
            } else {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }
    }

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        aMap = null;
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }


}
