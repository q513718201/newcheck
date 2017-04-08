package com.vito.check.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.SendOrder;
import com.vito.check.util.SpUtils;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/10.
 */

public class DeviceCheckActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.login_btn)
    Button mBtn;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.et6)
    EditText et6;
    @BindView(R.id.et7)
    EditText et7;
    @BindView(R.id.et8)
    EditText et8;
    @BindView(R.id.et9)
    EditText et9;
    @BindView(R.id.et10)
    EditText et10;
    @BindView(R.id.et11)
    EditText et11;
    @BindView(R.id.et12)
    EditText et12;
    @BindView(R.id.et13)
    EditText et13;
    @BindView(R.id.et14)
    EditText et14;
    @BindView(R.id.et15)
    EditText et15;
    @BindView(R.id.et16)
    EditText et16;
    @BindView(R.id.et17)
    EditText et17;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("日常巡检", true);
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
        mBtn.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_devicecheck;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                String s1 = et1.getText().toString();
                String s2 = et2.getText().toString();
                String s3 = et3.getText().toString();
                String s4 = et4.getText().toString();
                String s5 = et5.getText().toString();
                String s6 = et6.getText().toString();
                String s7 = et7.getText().toString();
                String s8 = et8.getText().toString();
                String s9 = et9.getText().toString();
                String s10 = et10.getText().toString();
                String s11 = et11.getText().toString();
                String s12 = et12.getText().toString();
                String s13 = et13.getText().toString();
                String s14 = et14.getText().toString();
                String s15 = et15.getText().toString();
                String s16 = et16.getText().toString();
                String s17 = et17.getText().toString();
                if(!s1.equals("")&&!s2.equals("")&&!s3.equals("")&&!s4.equals("")&&
                        !s5.equals("")&&!s6.equals("")&&!s7.equals("")&&!s8.equals("")&&
                        !s9.equals("")&&!s10.equals("")&&!s11.equals("")&&!s12.equals("")&&
                        !s13.equals("")&&!s14.equals("")&&!s15.equals("")&&!s16.equals("")&&
                        !s17.equals("")){
                    Observable<SendOrder> sendOrderObservable = ApiWrapper.getInstance().addDailyCheck(mToken, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17);
                    addSubscription(sendOrderObservable, new Subscriber<SendOrder>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(SendOrder sendOrder) {
                            Toast.makeText(getApplicationContext(), "已提交", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "请检查任何选项不能为空", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


}
