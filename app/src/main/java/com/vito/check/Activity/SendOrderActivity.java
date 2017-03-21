package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vito.check.R;
import com.vito.check.bean.MyOrder;
import com.vito.check.util.SpUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xk on 2017/3/10.
 */

public class SendOrderActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.tv_deviceNo)
    EditText mTvDeviceNo;
    @BindView(R.id.tv_address)
    EditText mTvAddress;
    @BindView(R.id.tv_desc)
    EditText mTvDesc;
    @BindView(R.id.tv_sendPhone)
    EditText mTvSendPhone;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    private MyOrder.ContentBean mOrderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("派单", true);
        mOrderBean = (MyOrder.ContentBean) getIntent().getSerializableExtra("orderBean");
        init();
        mLoginBtn.setOnClickListener(this);
    }

    private void init() {
        mTvDeviceNo.setText(mOrderBean.getDevNo());
        mTvAddress.setText(mOrderBean.getDevAddress());
        mTvDesc.setText(mOrderBean.getDescription());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sendordercheck;
    }


    @Override
    public void onClick(View v) {
        SpUtils.getString(this, "token", "");
        String DeviceNo = mTvDeviceNo.getText().toString();
        String Address = mTvAddress.getText().toString();
        String Desc = mTvDesc.getText().toString();
        String SendPhone = mTvSendPhone.getText().toString();


    }
}
