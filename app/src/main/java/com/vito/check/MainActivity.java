package com.vito.check;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.vito.check.Activity.BaseActivity;

import com.vito.check.Fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * AMapV2地图中介绍自定义可旋转的定位图标
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_content)
    FrameLayout mMainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message = getIntent().getStringExtra("message");
        ButterKnife.bind(this);
        initFragment();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_content,new MainFragment());
        transaction.commit();
    }

  public void  setToolBarTittle(String name){
      mTv_toolbar.setText(name);
  }

}
