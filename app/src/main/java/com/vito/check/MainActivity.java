package com.vito.check;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vito.check.Activity.BaseActivity;
import com.vito.check.Fragment.MainFragment;
import com.vito.check.util.SpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * AMapV2地图中介绍自定义可旋转的定位图标
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    @BindView(R.id.week)
    TextView mWeek;
    @BindView(R.id.date)
    TextView mDate;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPersimmions();
        String message = getIntent().getStringExtra("message");
        ButterKnife.bind(this);

        initFragment();
        nick_name.setText(SpUtils.getString(this, "nickname", ""));
        mWeek.setText(getWeek());
        mDate.setText(getDate());
    }

    //获取日期
    public String getDate(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
        return formatter.format(curDate);
    }
    //获取周几
    public String getWeek(){
        Calendar calendar = Calendar.getInstance();
        String mWay=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
       if("1".equals(mWay)){
            mWay="天";
            }else if("2".equals(mWay)){
            mWay="一";
            }else if("3".equals(mWay)){
           mWay="二";
            }else if("4".equals(mWay)){
            mWay="三";
           }else if("5".equals(mWay)){
            mWay="四";
           }else if("6".equals(mWay)){
            mWay="五";
            }else if("7".equals(mWay)){
           mWay="六";
           }

        return "周"+mWay;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_content, new MainFragment());
        transaction.commit();
    }

    public void setToolBarTittle(String name) {
        mTv_toolbar.setText(name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


}
