package com.vito.check.Activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Adapter.AllUserAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.SendOrder;
import com.vito.check.util.DensityUtils;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

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
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout mRlPhone;
    @BindView(R.id.tv_address1)
    TextView mTvAddress1;
    @BindView(R.id.tv_desc_title)
    TextView mTvDescTitle;
    @BindView(R.id.et_chose)
    EditText mEtChose;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    private MyOrder.ContentBean mOrderBean;
    private String mToken;
    private PopupWindow mPopupWindow1;
    private List<AllUsers.ContentBean> allUsersContent;
    private ListView lv_name;
    private String XjName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("派单", true);
        mToken = SpUtils.getString(this, "token", "");
        mOrderBean = (MyOrder.ContentBean) getIntent().getSerializableExtra("orderBean");
        if (mOrderBean != null) {
            init();
        }

        mLoginBtn.setOnClickListener(this);
        mEtChose.setOnClickListener(this);
    }

    private void init() {
        mTvDeviceNo.setText(mOrderBean.getDevNo());
        mTvAddress.setText(mOrderBean.getAddress());
        mTvDesc.setText(mOrderBean.getDescription());


    }

    @Override
    public int getLayout() {
        return R.layout.activity_sendordercheck;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:
                String DeviceNo = mTvDeviceNo.getText().toString();
                String Address = mTvAddress.getText().toString();
                String Desc = mTvDesc.getText().toString();
                String phone = etPhone.getText().toString();
                String SendTo = mEtChose.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(SendTo)) {
                    Toast.makeText(getApplicationContext(), "派发人不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                 mLlProgress.setVisibility(View.VISIBLE);
                Observable<SendOrder> obsevable = ApiWrapper.getInstance().sendOrder(mToken, DeviceNo, Desc, Address, phone, XjName);
                addSubscription(obsevable, new Subscriber<SendOrder>() {
                    @Override
                    public void onCompleted() {
                        mLlProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendOrder sendOrder) {
                        String content = sendOrder.getContent();
                        Log.d("order", content);
                        boolean success = sendOrder.isSuccess();
                        Log.d("order", success + "");
                        if (success) {
                            Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                            exit();
                        } else {
                            Toast.makeText(getApplicationContext(), "派发失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;

            case R.id.et_chose:
                showPopWindow1();
                break;
        }


    }

    private void showPopWindow1() {
        if (mPopupWindow1 == null) {
            //弹出PopupWindow
            int width = mEtChose.getWidth();//PopupWindow宽度和编辑框一致
            int height = DensityUtils.dp2px(this, 180);
            mPopupWindow1 = new PopupWindow(width, height);
        }
        View view = View.inflate(this, R.layout.user_pop_window, null);
        lv_name = (ListView) view.findViewById(R.id.lv);
        getUserData();
        lv_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                XjName = allUsersContent.get(i).getLogin_name();
                mEtChose.setText(allUsersContent.get(i).getNick_name());
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
        addSubscription(users, new Subscriber<AllUsers>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllUsers allUsers) {
                allUsersContent = allUsers.getContent();
                if (allUsersContent.size() != 0) {
                    lv_name.setAdapter(new AllUserAdapter(allUsersContent, getApplicationContext()));
                } else {
                    Toast.makeText(getApplicationContext(), "未能查询到巡检人员", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
