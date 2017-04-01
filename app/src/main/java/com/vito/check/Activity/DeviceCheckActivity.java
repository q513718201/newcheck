package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vito.check.Adapter.PhotoAdapter;
import com.vito.check.Adapter.RecyclerItemClickListener;
import com.vito.check.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by xk on 2017/3/10.
 */

public class DeviceCheckActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.login_btn)
    Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("日常巡检",true);
        mBtn.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_devicecheck;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                Toast.makeText(getApplicationContext(), "提交了", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
