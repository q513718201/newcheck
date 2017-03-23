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
    private long lastClickTime = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();
        init();
        return view;
    }

    private void init() {
        mMessage.setNotificationNumber(1);
        mRg.check(R.id.device);
        replace(new BaiduDeviceFragment());
        mRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (isFastDoubleClick()) return;
        switch (checkedId) {
            case R.id.message:
                if (mMessage.isHavePoint()){
                    mMessage.setNotificationNumber(0);
                }else {
                    mMessage.setNotificationNumber(1);
                }
                mRg.check(R.id.message);
                replace(new MessageFragment());
                break;
            case R.id.device:
                mRg.check(R.id.device);
                replace(new BaiduDeviceFragment());
                break;
            case R.id.work:
                mRg.check(R.id.work);
                replace(new WorkFragment());
                break;

        }
    }


    public void replace(Fragment fragment) {

        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rl_content, fragment);
        fragmentTransaction.commit();
    }

    /**防止过快点击造成多次事件*/
    public boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > 500) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }

}