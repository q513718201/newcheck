package com.vito.check.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vito.check.Activity.LoginActivity;
import com.vito.check.Adapter.AllUserAdapter;
import com.vito.check.Adapter.OnLineManagerAdapter;
import com.vito.check.MainActivity;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.AddressModify;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.Device;
import com.vito.check.bean.OnlineRate;
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
    @BindView(R.id.ll_query)
    LinearLayout mLlQuery;
    @BindView(R.id.isshowquery)
    ImageView mIsshowquery;
    @BindView(R.id.isshowchose)
    ImageView mIsshowchose;
    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    @BindView(R.id.online)
    TextView mOnline;

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
    String xjName = "";
    private String et;
    private String isOnLine = "";
    private String isChecked = "";
    private String deviceNo = "";
    private String mRole;
    private String mDeviceNumber;
    private String mDeviceAddress;
    private double mDevicelatitude;
    private double mDevicelongitude;
    private LatLng mDeviceposition;
    private boolean isShow = true;
    private boolean isShowChose = true;
    private AllUserAdapter mAllUserAdapter;
    private Device.ContentBean mDeviceBean;
    private PopupWindow mPopupWindow2;
    private View mInflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflate = inflater.inflate(R.layout.fragment_baidudevice, container, false);
        ButterKnife.bind(this, mInflate);
        mMapView = (MapView) mInflate.findViewById(R.id.device_map);
        mMapView.showZoomControls(false);
        init();
        initMap();
        getOnline();
        return mInflate;
    }

    private void getOnline() {
        Observable<OnlineRate> onlineRate = ApiWrapper.getInstance().getOnlineRate(mToken, xjName);
        mActivity.addSubscription(onlineRate, new Subscriber<OnlineRate>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(OnlineRate onlineRate) {
                if(onlineRate.isSuccess()){
                    List<OnlineRate.ContentBean> content = onlineRate.getContent();
                    OnlineRate.ContentBean contentBean = content.get(0);
                    mOnline.setText(contentBean.getNowOnlineRate());
                }

            }
        });
    }

    //获取之前请求的值
    private void init() {
        mRole = SpUtils.getString(mActivity, "role", "");
        mToken = SpUtils.getString(mActivity, "token", "");
        et = SpUtils.getString(mActivity, "et", "");
        isOnLine = SpUtils.getString(mActivity, "isOnLine", "");
        isChecked = SpUtils.getString(mActivity, "isChecked", "");

        if (mRole.equals("manager")) {
            mRl1.setVisibility(View.VISIBLE);
            mIsshowchose.setVisibility(View.VISIBLE);
        }

//        if (et != "") {
//            if (et.equals("派单设备")) {
//                getDevice();
//            } else {
//                mEt.setText(et);
//                getDevices(isOnLine, isChecked, xjName, "");
//            }
//
//        } else {
        mEt.setText("在线设备");
        isOnLine = "1";
        isChecked = "";
        getDevices(isOnLine, isChecked, xjName, deviceNo);
        //  }
        mEt.setOnClickListener(this);
        mEtChose.setOnClickListener(this);
        mQuery.setOnClickListener(this);
        mLocation.setOnClickListener(this);
        mIsshowquery.setOnClickListener(this);
        mIsshowchose.setOnClickListener(this);
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
        mDeviceNumber = marker.getTitle();
        Bundle extraInfo = marker.getExtraInfo();
        mDeviceAddress = extraInfo.getString("address");
        mDeviceBean = (Device.ContentBean) extraInfo.getSerializable("device");
        mDeviceposition = marker.getPosition();
        mDevicelatitude = mDeviceposition.latitude;
        mDevicelongitude = mDeviceposition.longitude;

        pushWindow(mDeviceNumber, mDeviceAddress, mDeviceposition);
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
            viewHold.address = (ImageButton) mView.findViewById(R.id.modify);
            mView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) mView.getTag();
        }
        viewHold.title.setText(title);
        viewHold.snippet.setText(address);
        viewHold.start_amap_app.setOnClickListener(this);
        viewHold.address.setOnClickListener(this);
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
        ImageButton address;
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
    public void getDevices(final String isOnline, final String isChecked, String xjName, String devNo) {

        mLlProgress.setVisibility(View.VISIBLE);

        Log.d("aa", mToken);

        if (!isOnline.equals("")) {
            mDevices = ApiWrapper.getInstance().getDevices(mToken, isOnline, xjName, devNo);
        }
        if (isOnline.equals("") && isChecked.equals("")) {
            mDevices = ApiWrapper.getInstance().getAllDevices(mToken, xjName, devNo);
        }
        if (!isChecked.equals("")) {
            mDevices = ApiWrapper.getInstance().getCheckedDevices(mToken, isChecked, xjName, devNo);
        }


        Log.d("aa", "我开始网络请求");
        mActivity.addSubscription(mDevices, new Subscriber<Device>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }


            @Override
            public void onError(Throwable e) {
                // mLlProgress.setVisibility(View.GONE);
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

                if (size == 0) {
                    Toast.makeText(mActivity, "未查到相关设备信息", Toast.LENGTH_SHORT).show();
                    center2myLoc(new LatLng(mCurrentLantitude, mCurrentLongitude));
                    return;
                }

                addMarkersToMap(device);


            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.et) {
            showPopWindow();
            return;
        }
        if (v.getId() == R.id.isshowquery) {
            if (isShow) {
                mLlQuery.setVisibility(View.VISIBLE);
                isShow = !isShow;
            } else {
                mLlQuery.setVisibility(View.GONE);
                mDeviceno.setText("");
                deviceNo = "";
                isShow = !isShow;
            }
            return;
        }
        if (v.getId() == R.id.isshowchose) {

            if (isShowChose) {
                mRl1.setVisibility(View.VISIBLE);
                isShowChose = !isShowChose;
            } else {
                mRl1.setVisibility(View.GONE);
                isShowChose = !isShowChose;
            }
            return;
        }

        if (v.getId() == R.id.et_chose) {
            showPopWindow1();
            return;
        }
        if (v.getId() == R.id.start_amap_app) {
            DialogUtil.startRoutePlanDriving(mActivity, new LatLng(mCurrentLantitude, mCurrentLongitude), mDeviceAddress);
            return;
        }

        if (v.getId() == R.id.location) {
            // mBaiduMap.setMyLocationEnabled(false);
            center2myLoc(new LatLng(mCurrentLantitude, mCurrentLongitude));
            return;
        }

        if (v.getId() == R.id.query) {

            deviceNo = mDeviceno.getText().toString();
            Log.d("mContent", mContent + "");
            if (TextUtils.isEmpty(deviceNo)) {
                Toast.makeText(mActivity, "请输入设备号", Toast.LENGTH_SHORT).show();
                return;
            } else {
                getDevices(isOnLine, isChecked, xjName, deviceNo);
            }
            return;

        }

        if (v.getId() == R.id.order) {
            mEt.setText("派单设备");
            SpUtils.putString(mActivity, "et", "派单设备");
            getDevice();
            mPopupWindow.dismiss();
            return;
        }

        if (v.getId() == R.id.modify) {
            showDescription();
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
        getDevices(isOnLine, isChecked, xjName, deviceNo);

        SpUtils.putString(mActivity, "isOnLine", isOnLine);
        SpUtils.putString(mActivity, "isChecked", isChecked);
        mPopupWindow.dismiss();

    }

    private void showDescription() {
        if (mPopupWindow2 == null) {
            mPopupWindow2 = new PopupWindow(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        View view = View.inflate(mActivity, R.layout.device_desc_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow2.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.device_no);
        TextView tv2 = (TextView) view.findViewById(R.id.device_address);
        TextView tv3 = (TextView) view.findViewById(R.id.device_time);
        TextView tv4 = (TextView) view.findViewById(R.id.device_bianhao);
        TextView tv5 = (TextView) view.findViewById(R.id.device_people);
        TextView tv6 = (TextView) view.findViewById(R.id.device_phone);
        TextView tv7 = (TextView) view.findViewById(R.id.device_version);
        TextView tv8 = (TextView) view.findViewById(R.id.device_update);

        tv1.setText("设备编号 :" + " " + mDeviceBean.getT_id());
        tv2.setText("设备地址 :" + " " + mDeviceBean.getDescription());
        tv3.setText("设备放置时间 :" + " " + mDeviceBean.getCreate_time());
        tv4.setText("终端号 :" + " " + mDeviceBean.getKey_t_id());
        tv5.setText("设备联系人 :" + " " + mDeviceBean.getUser_name());
        tv6.setText("联系电话 :" + " " + mDeviceBean.getUser_phone());
        tv7.setText("当前版本 :" + " " + mDeviceBean.getSoft_version());
        tv8.setText("更新时间 :" + " " + mDeviceBean.getUpdate_time());

        mPopupWindow2.setOutsideTouchable(true);
        mPopupWindow2.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow2.showAtLocation(mInflate, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 修改设备地址
     */
    private void modifyAddress() {
        View view = View.inflate(mActivity, R.layout.dialog_input, null);
        final EditText et_address = (EditText) view.findViewById(R.id.et_address);

        new AlertDialog.Builder(mActivity)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String dev_id = et_address.getText().toString();
                        if (TextUtils.isEmpty(dev_id)) {
                            Toast.makeText(mActivity, "请输入地址", Toast.LENGTH_SHORT).show();
                        } else {
                            Observable<AddressModify> addressModifyObservable = ApiWrapper.getInstance().addressModify(mToken, dev_id, mDevicelatitude, mDevicelongitude, mDeviceAddress);
                            mActivity.addSubscription(addressModifyObservable, new Subscriber<AddressModify>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(AddressModify addressModify) {
                                    if (addressModify.isSuccess()) {
                                        mDeviceAddress = dev_id;
                                        pushWindow(mDeviceNumber, mDeviceAddress, mDeviceposition);
                                        Toast.makeText(mActivity, addressModify.getContent(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mActivity, addressModify.getContent(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }

                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    private void getDevice() {

        Observable<Device> orderDevices = ApiWrapper.getInstance().getOrderDevices(mToken);
        mActivity.addSubscription(orderDevices, new Subscriber<Device>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Device device) {
                int size = device.getContent().size();
                mDeviceNo.setText(size + "");
                mBaiduMap.clear();


                if (!device.isSuccess()) {
                    Toast.makeText(mActivity, "账号在其他地方登陆，请重新登陆", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    mActivity.finish();
                    return;
                }
                if (device.getContent().size() == 0) {
                    Log.d("hhh", "我来了");
                    center2myLoc(new LatLng(mCurrentLantitude, mCurrentLongitude));
                    Toast.makeText(mActivity, "未查到相关设备信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                addMarkersToMap(device);


            }
        });
    }

    /**
     * 切换查询设备
     */
    private void showPopWindow() {
        if (mPopupWindow == null) {
            //弹出PopupWindow
            int width = mEt.getWidth();//PopupWindow宽度和编辑框一致
            int height = DensityUtils.dp2px(mActivity, 140);
            mPopupWindow = new PopupWindow(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        View view = View.inflate(mActivity, R.layout.device_pop_window, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.all);
        TextView tv2 = (TextView) view.findViewById(R.id.not_check);
        TextView tv3 = (TextView) view.findViewById(R.id.yes_check);
        TextView tv4 = (TextView) view.findViewById(R.id.online);
        TextView tv5 = (TextView) view.findViewById(R.id.outline);
        TextView tv6 = (TextView) view.findViewById(R.id.order);
        if (mRole.equals("manager") && mEtChose.getText().toString().equals("查看人员")) {
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);
            tv6.setVisibility(View.GONE);
        }
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
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
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllUsers allUsers) {
                allUsersContent = allUsers.getContent();
                if (allUsersContent.size() != 0) {
                    allUserAdapter = new AllUserAdapter(allUsersContent, mActivity);
                    lv_name.setAdapter(allUserAdapter);

                    mAllUserAdapter = new AllUserAdapter(allUsersContent, mActivity);
                    lv_name.setAdapter(mAllUserAdapter);

                    mAllUserAdapter.setOnClickDay(new AllUserAdapter.OnClickUserListener() {
                        @Override
                        public void onClickUser(AllUsers.ContentBean bean) {
                            xjName = bean.getLogin_name();
                            mEtChose.setText(bean.getNick_name());
                            getDevices(isOnLine, isChecked, xjName, deviceNo);
                            mPopupWindow1.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(mActivity, "未能查询到巡检人员", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    /**
     * 添加标记
     *
     * @param b
     */
    private void addMarkersToMap(final Device b) {
        mContent = b.getContent();
        isFristLocation = false;
        double latcenter = 0;
        double lngcenter = 0;
        Log.d("aa", "获取到的设备数量" + mContent.size() + "");
        mList = new ArrayList<>();
        for (Device.ContentBean device : mContent) {
            double lat = device.getLat();
            double lng = device.getLng();
            latcenter = latcenter + lat;
            lngcenter = lngcenter + lng;
            mLatLng = new LatLng(lat, lng);
            mList.add(mLatLng);
        }

        Log.d("aa", "坐标数量" + mList.size() + "");
        for (int i = 0; i < mList.size(); i++) {
            mBundle = new Bundle();
            mOoD = new MarkerOptions().position(mList.get(i));
            mBundle.putString("address", mContent.get(i).getDescription());
            mBundle.putSerializable("device", mContent.get(i));
            mOoD.title(mContent.get(i).getT_id()).extraInfo(mBundle);

            if (mContent.get(i).getOnline()) {
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
        Log.d("bbb", mList.size() / 2 + "");
        // center2myLoc(mList.get(mList.size() ));
        center2myLoc(new LatLng(latcenter / mList.size(), (lngcenter / mList.size())));


    }

    /**
     * 地图移动到我的位置,此处可以重新发定位请求，然后定位；
     * 直接拿最近一次经纬度，如果长时间没有定位成功，可能会显示效果不好
     */
    private void center2myLoc(LatLng a) {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
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
