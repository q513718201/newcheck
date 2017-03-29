package com.vito.check.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vito.check.MainActivity;
import com.vito.check.R;
import com.vito.check.View.NotificationButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xk on 2017/3/15.
 */

public class MainFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.message)
    NotificationButton mMessage;
    @BindView(R.id.device)
    NotificationButton mDevice;
    @BindView(R.id.work)
    NotificationButton mWork;
    @BindView(R.id.content_rg)
    RadioGroup mRg;
    private MainActivity mActivity;
    private FragmentTransaction mTransaction;
    private Fragment firstFragment;
    private MessageFragment mMessageFragment;
    private BaiduDeviceFragment mBaiduDeviceFragment;
    private WorkFragment mWorkFragment;
    private FragmentTransaction mFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();
        mTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        init();
        return view;
    }

    private void init() {
        mMessage.setNotificationNumber(0);
        mRg.check(R.id.device);
        //replace(new BaiduDeviceFragment());
        mBaiduDeviceFragment=new BaiduDeviceFragment();
        mTransaction.add(R.id.rl_content, mBaiduDeviceFragment).commit();
        firstFragment=mBaiduDeviceFragment;
        mActivity.setToolBarTittle("设备状态");
        mRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.message:

                mRg.check(R.id.message);
                if(mMessageFragment==null){
                    mMessageFragment = new MessageFragment();
                }
                switchContent(firstFragment,mMessageFragment);
               // replace(new MessageFragment());
                firstFragment=mMessageFragment;
                mActivity.setToolBarTittle("消息");
                break;
            case R.id.device:
                mRg.check(R.id.device);
                if(mBaiduDeviceFragment==null){
                    mBaiduDeviceFragment = new BaiduDeviceFragment();
                }

                switchContent(firstFragment,mBaiduDeviceFragment);
                // replace(new BaiduDeviceFragment());
                firstFragment=mBaiduDeviceFragment;
                mActivity.setToolBarTittle("设备状态");
                break;
            case R.id.work:
                mRg.check(R.id.work);
                if(mWorkFragment==null){
                    mWorkFragment = new WorkFragment();
                }
                switchContent(firstFragment,mWorkFragment);
                firstFragment=mWorkFragment;
                mActivity.setToolBarTittle("工作");
                // replace(new WorkFragment());
                break;

        }
    }

     public void  show(){
             mMessage.setNotificationNumber(1);
     }



    public void replace(Fragment fragment) {


        mTransaction.replace(R.id.rl_content, fragment);
        mTransaction.commit();
    }

    public void switchContent(Fragment from, Fragment to) {
        mFragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过

            mFragmentTransaction.hide(from).add(R.id.rl_content, to)
                    .commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            mFragmentTransaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }



}