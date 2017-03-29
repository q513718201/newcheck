package com.vito.check.Fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.github.ybq.android.spinkit.SpinKitView;
import com.vito.check.Activity.AllUserActivity;
import com.vito.check.Activity.LoginActivity;
import com.vito.check.Adapter.AllUserAdapter;
import com.vito.check.MainActivity;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.Device;
import com.vito.check.util.DensityUtils;
import com.vito.check.util.DialogUtil;
import com.vito.check.util.MyOrientationListener;
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

public class BaiduDeviceFragment extends Fragment implements View.OnClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener {

    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.arrow)
    ImageView mArrow;
    @BindView(R.id.device_no)
    TextView mDeviceNo;

    @BindView(R.id.ll_progress_)
    LinearLayout mLlProgress;
    @BindView(R.id.deviceno)
    EditText mDeviceno;
    @BindView(R.id.query)
    Button mQuery;
    @BindView(R.id.et_chose)
    EditText mEtChose;
    @BindView(R.id.arrow1)
    ImageView mArrow1;
    @BindView(R.id.location)
    ImageView mLocation;
    @BindView(R.id.rl1)
    RelativeLayout mRl1;


    private MainActivity mActivity;
    private List<LatLng> mList;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    private volatile boolean isFristLocation = true;
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    private float mCurrentAccracy;
    private MyOrientationListener myOrientationListener;
    BitmapDescriptor mCurrentMarker;
    private int mXDirection;
    private String mToken;
    private Observable<Device> mDevices;
    private LatLng mLatLng;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;
    private MarkerOptions mOoD;
    private Bundle mBundle;
    private View mView;
    private boolean isShowPopWindow = false;
    private List<Device.ContentBean> mContent;
    private ListView lv_name;
    private AllUserAdapter allUserAdapter;
    private List<AllUsers.ContentBean> allUsersContent;
    String xjName="";
    private String et;
    private String isOnLine="";
    private String isChecked="";
    private String deviceNo="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baidudevice, container, false);
        ButterKnife.bind(this, view);
        mMapView = (MapView) view.findViewById(R.id.device_map);
        init();
        initMap();
        return view;
    }

    //获取之前请求的值
    private void init() {
        String role = SpUtils.getString(mActivity, "role", "");
        mToken = SpUtils.getString(mActivity, "token", "");
        et = SpUtils.getString(mActivity, "et", "");
        isOnLine = SpUtils.getString(mActivity, "isOnLine", "");
        isChecked = SpUtils.getString(mActivity, "isChecked", "");

        if (role.equals("manager")) {
            mRl1.setVisibility(View.VISIBLE);
        }

        if (et != "") {
            mEt.setText(et);
            getDevices(isOnLine, isChecked, xjName,"");
        } else {
            mEt.setText("全部设备");
            getDevices("", "", xjName,"");
        }
        mArrow.setOnClickListener(this);
        mArrow1.setOnClickListener(this);
        mQuery.setOnClickListener(this);
    }


    //初始化地图
    private void initMap() {
        mBaiduMap = mMapView.getMap();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

        // 初始化定位
        initMyLocation();
        // 初始化传感器
        initOritationListener();

        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(this);
    }


    private void initMyLocation() {
        // 定位初始化
        mLocationClient = new LocationClient(mActivity);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        String title = marker.getTitle();
        Bundle extraInfo = marker.getExtraInfo();
        String address = extraInfo.getString("address");
        pushWindow(title, address, marker.getPosition());
//        Toast.makeText(mActivity, marker.getTitle() + "--" + address, Toast.LENGTH_SHORT).show();
        return false;
    }


    private boolean pushWindow(String title, String address, LatLng ll) {
        //创建InfoWindow展示的view
        ViewHold viewHold;

        if (mView == null) {
            viewHold = new ViewHold();
            mView = View.inflate(mActivity, R.layout.pop_window, null);
            viewHold.title = (TextView) mView.findViewById(R.id.title);
            viewHold.snippet = (TextView) mView.findViewById(R.id.snippet);
            viewHold.start_amap_app = (ImageButton) mView.findViewById(R.id.start_amap_app);
            mView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) mView.getTag();
        }
        viewHold.title.setText(title);
        viewHold.snippet.setText(address);
        viewHold.start_amap_app.setOnClickListener(this);
        //将marker所在的经纬度的信息转化成屏幕上的坐标
        InfoWindow mInfoWindow = new InfoWindow(mView, ll, DensityUtils.dp2px(mActivity, -32));
        if (!isShowPopWindow) {
            mBaiduMap.showInfoWindow(mInfoWindow);
            isShowPopWindow = true;
        } else {
            mBaiduMap.hideInfoWindow();
            mBaiduMap.showInfoWindow(mInfoWindow);
        }

        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (isShowPopWindow) {
            mBaiduMap.hideInfoWindow();
            isShowPopWindow = !isShowPopWindow;
        }

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    public static class ViewHold {
        TextView title;
        TextView snippet;
        ImageButton start_amap_app;
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mXDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mCurrentAccracy = location.getRadius();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();

            // 设置自定义图标

            MyLocationConfiguration config = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);
            // 第一次定位时，将地图位置移动到当前位置
            if (isFristLocation) {
                isFristLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


    }

    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                mActivity);
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(mCurrentLantitude)
                                .longitude(mCurrentLongitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        MyLocationConfiguration config = new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
                        mBaiduMap.setMyLocationConfigeration(config);

                    }
                });
    }

    //    //网络请求
    public void getDevices(final String isOnline, final String isChecked, String xjName,String devNo) {

        mLlProgress.setVisibility(View.VISIBLE);

        Log.d("aa", mToken);

        if (!isOnline.equals("")) {
            mDevices = ApiWrapper.getInstance().getDevices(mToken, isOnline,xjName,devNo);
        }
        if(isOnline.equals("")&&isChecked.equals("")){
            mDevices = ApiWrapper.getInstance().getAllDevices(mToken,xjName,devNo);
        }


        if (!isChecked.equals("")) {
            mDevices = ApiWrapper.getInstance().getCheckedDevices(mToken, isChecked,xjName,devNo);
        }


        Log.d("aa", "我开始网络请求");
        mActivity.addSubscription(mDevices, new Subscriber<Device>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }


            @Override
            public void onError(Throwable e) {
                mLlProgress.setVisibility(View.GONE);
                Log.d("aa", e.getMessage());
                Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
//                startActivity(new Intent(mActivity, LoginActivity.class));
//                mActivity.finish();
            }

            @Override
            public void onNext(Device device) {

                Log.d("aa", "我请求了");
                Log.d("aa", device.getContent().toString());
                Log.d("aa", device.isSuccess() + "");
                int size = device.getContent().size();
                mDeviceNo.setText(size + "");
                mBaiduMap.clear();


                if (!device.isSuccess()) {
                    Toast.makeText(mActivity, "账号在其他地方登陆，请重新登陆", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    mActivity.finish();
                }
                if (device.getContent().size() == 0) {
                    center2myLoc(new LatLng(mCurrentLantitude, mCurrentLantitude));
                    Toast.makeText(mActivity, "未查到相关设备信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                addMarkersToMap(device);


            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.arrow) {
            showPopWindow();
            return;
        }
        if (v.getId() == R.id.arrow1) {
            showPopWindow1();
            return;
        }
        if (v.getId() == R.id.start_amap_app) {
            DialogUtil.startRoutePlanDriving(mActivity, new LatLng(mCurrentLantitude, mCurrentLongitude));
            return;
        }

        if (v.getId() == R.id.query) {
            mBaiduMap.setMyLocationEnabled(false);
            deviceNo = mDeviceno.getText().toString();
            Log.d("mContent", mContent + "");
            if (TextUtils.isEmpty(deviceNo)) {
                Toast.makeText(mActivity, "请输入设备号", Toast.LENGTH_SHORT).show();
                return;
            }else{
                getDevices(isOnLine,isChecked,xjName, deviceNo);
            }
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
        deviceNo = mDeviceno.getText().toString();
        getDevices(isOnLine, isChecked, xjName,deviceNo);

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

    private void showPopWindow1() {
        if (mPopupWindow1 == null) {
            //弹出PopupWindow
            int width = mEtChose.getWidth();//PopupWindow宽度和编辑框一致
            int height = DensityUtils.dp2px(mActivity, 180);
            mPopupWindow1 = new PopupWindow(width, height);
        }
        View view = View.inflate(mActivity, R.layout.user_pop_window, null);
        lv_name = (ListView) view.findViewById(R.id.lv);
        getUserData();
        lv_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xjName=allUsersContent.get(i).getLogin_name();
                mEtChose.setText(allUsersContent.get(i).getNick_name());
                getDevices(isOnLine, isChecked, xjName,deviceNo);
                mPopupWindow1.dismiss();

            }
        });
        //设置PopupWindow里面的View
        mPopupWindow1.setContentView(view);


        //让PopupWindow能够消失
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow1.showAsDropDown(mEtChose);
    }

    private void getUserData() {
        Observable<AllUsers> users = ApiWrapper.getInstance().getAllUsers(mToken);
        mActivity.addSubscription(users, new Subscriber<AllUsers>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllUsers allUsers) {
                allUsersContent = allUsers.getContent();
                if(allUsersContent.size()!=0){
                    allUserAdapter = new AllUserAdapter(allUsersContent, mActivity);
                    lv_name.setAdapter(allUserAdapter);
                }else{
                    Toast.makeText(mActivity,"未能查询到巡检人员",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    /**
     * 添加标记
     *
     * @param b
     */
    private void addMarkersToMap(Device b) {
        mContent = b.getContent();
        isFristLocation = false;

        Log.d("aa", "获取到的设备数量" + mContent.size() + "");
        mList = new ArrayList<>();

        for (Device.ContentBean device : mContent) {
            double lat = device.getLat();
            double lng = device.getLng();
            mLatLng = new LatLng(lat, lng);
            mList.add(mLatLng);
        }

        Log.d("aa", "坐标数量" + mList.size() + "");
        for (int i = 0; i < mList.size(); i++) {
            mBundle = new Bundle();
            mOoD = new MarkerOptions().position(mList.get(i));
            mBundle.putString("address", mContent.get(i).getDescription());
            mOoD.title(mContent.get(i).getT_id()).extraInfo(mBundle);

            if (mContent.get(i).getOnline().equals("1")) {
                mOoD.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.mark_blue));
            } else {
                mOoD.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.mark_red));
            }
//            // 生长动画
//            mOoD.animateType(MarkerOptions.MarkerAnimateType.drop);
            mBaiduMap.addOverlay(mOoD);
        }
        if (mList.get(0) != null) {
            center2myLoc(mList.get(0));
        }
    }

    /**
     * 地图移动到我的位置,此处可以重新发定位请求，然后定位；
     * 直接拿最近一次经纬度，如果长时间没有定位成功，可能会显示效果不好
     */
    private void center2myLoc(LatLng a) {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(a);
        mBaiduMap.animateMapStatus(u);
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onStop() {
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

        // 关闭方向传感器
        myOrientationListener.stop();
        super.onStop();
    }
    @Override
    public void onStart() {
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        // 开启方向传感器
        myOrientationListener.start();
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        mMapView = null;
    }
}
