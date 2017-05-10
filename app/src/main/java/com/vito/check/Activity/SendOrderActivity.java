package com.vito.check.Activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Adapter.AllUserAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.SendOrder;
import com.vito.check.bean.YunyingOrder;
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

    @BindView(R.id.tv_desc)
    EditText mTvDesc;

    @BindView(R.id.login_btn)
    Button mLoginBtn;

    @BindView(R.id.tv_phone)
    EditText etPhone;

    @BindView(R.id.et_chose)
    EditText mEtChose;

    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    @BindView(R.id.et_type)
    LinearLayout mEtType;

    @BindView(R.id.tv_type)
    TextView mTvType;

    @BindView(R.id.et_chengdu)
    LinearLayout mEtChengdu;
    @BindView(R.id.tv_chengdu)
    TextView mTvChengdu;

    @BindView(R.id.et_time)
    LinearLayout mEtTime;
    @BindView(R.id.tv_time)
    TextView mTvTime;


    @BindView(R.id.et_usermsg)
    EditText mEtUsermsg;
    @BindView(R.id.tv_dept)
    TextView mTvDept;
    @BindView(R.id.et_dept)
    LinearLayout mEtDept;


    private YunyingOrder.ContentBean mOrderBean;
    private String mToken;
    private PopupWindow mPopupWindow1;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow2;
    private PopupWindow mPopupWindow3;
    private PopupWindow mPopupWindow4;

    private List<AllUsers.ContentBean> allUsersContent;
    private ListView lv_name;
    private String xjName = "";
    private AllUserAdapter mAllUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("派单", true);
        mToken = SpUtils.getString(this, "token", "");
        mOrderBean = (YunyingOrder.ContentBean) getIntent().getSerializableExtra("orderBean");
        if (mOrderBean != null) {
            init();
        }

        mLoginBtn.setOnClickListener(this);
        mEtChose.setOnClickListener(this);
        mEtType.setOnClickListener(this);
        mEtChengdu.setOnClickListener(this);
        mEtTime.setOnClickListener(this);
        mEtDept.setOnClickListener(this);

    }

    private void init() {
        mTvDeviceNo.setText(mOrderBean.getDevNo());
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

                String Desc = mTvDesc.getText().toString();
                String phone = etPhone.getText().toString();
                String SendTo = mEtChose.getText().toString();
                String faultType = mTvType.getText().toString();
                String chengdu = mTvChengdu.getText().toString();
                String time = mTvTime.getText().toString();
                String usermsg = mEtUsermsg.getText().toString();

                if (TextUtils.isEmpty(SendTo) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(DeviceNo) || TextUtils.isEmpty(Desc) || TextUtils.isEmpty(chengdu) || TextUtils.isEmpty(usermsg)
                       || TextUtils.isEmpty(faultType) || TextUtils.isEmpty(time)

                        ) {
                    Toast.makeText(getApplicationContext(), "信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLlProgress.setVisibility(View.VISIBLE);
                Observable<SendOrder> obsevable = ApiWrapper.getInstance().paidan(mToken, DeviceNo, faultType,
                        chengdu, time, usermsg,
                        "", "", Desc,
                        phone, xjName);
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
            case R.id.et_type:
                showPopWindow();
                break;
            case R.id.et_chengdu:
                showPopWindow2();
                break;
            case R.id.et_time:
                showPopWindow3();
                break;
            case R.id.yingjian:
                mTvType.setText("移机");
                mPopupWindow.dismiss();
                break;
            case R.id.ruanjian:
                mTvType.setText("拆机");
                mPopupWindow.dismiss();
                break;
            case R.id.fangong:
                mTvType.setText("返工");
                mPopupWindow.dismiss();
                break;

            case R.id.jinji:
                mTvChengdu.setText("紧急(一天)");
                mPopupWindow2.dismiss();
                break;
            case R.id.yiban:
                mTvChengdu.setText("一般(二天)");
                mPopupWindow2.dismiss();
                break;
            case R.id.zhengchang:
                mTvChengdu.setText("正常(三天)");
                mPopupWindow2.dismiss();
                break;
            case R.id.yanshi:
                mTvChengdu.setText("延时(七天)");
                mPopupWindow2.dismiss();
                break;
            case R.id.ersi:
                mTvTime.setText("24");
                mPopupWindow3.dismiss();
                break;
            case R.id.siba:
                mTvTime.setText("48");
                mPopupWindow3.dismiss();
                break;
            case R.id.qier:
                mTvTime.setText("72");
                mPopupWindow3.dismiss();
                break;
            case R.id.et_dept:
                showPopWindow4();
                break;
            case R.id.gongchengbu:
                mTvDept.setText("工程部");
                xjName="wh001";
                mEtChose.setText("陈智俊");
                mPopupWindow4.dismiss();
                break;
            case R.id.yunyingbu:
                mTvDept.setText("运营部");
                mPopupWindow4.dismiss();
                break;
        }


    }

    private void showPopWindow4() {
        if (mPopupWindow4 == null) {
            //弹出PopupWindow
            mPopupWindow4 = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        View view = View.inflate(this, R.layout.dept_pop_window, null);
        TextView tv1 = (TextView) view.findViewById(R.id.gongchengbu);
        TextView tv2 = (TextView) view.findViewById(R.id.yunyingbu);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        //设置PopupWindow里面的View
        mPopupWindow4.setContentView(view);
        mPopupWindow4.setFocusable(true);
        //让PopupWindow能够消失
        mPopupWindow4.setOutsideTouchable(true);
        mPopupWindow4.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow4.showAtLocation(SendOrderActivity.this.findViewById(R.id.rl_sendorder), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void showPopWindow3() {
        if (mPopupWindow3 == null) {
            //弹出PopupWindow
            mPopupWindow3 = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        View view = View.inflate(this, R.layout.time_pop_window, null);
        TextView tv1 = (TextView) view.findViewById(R.id.ersi);
        TextView tv2 = (TextView) view.findViewById(R.id.siba);
        TextView tv3 = (TextView) view.findViewById(R.id.qier);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //设置PopupWindow里面的View
        mPopupWindow3.setContentView(view);
        mPopupWindow3.setFocusable(true);
        //让PopupWindow能够消失
        mPopupWindow3.setOutsideTouchable(true);
        mPopupWindow3.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow3.showAtLocation(SendOrderActivity.this.findViewById(R.id.rl_sendorder), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    private void showPopWindow2() {
        if (mPopupWindow2 == null) {
            //弹出PopupWindow
            mPopupWindow2 = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        View view = View.inflate(this, R.layout.chengdu_pop_window, null);
        TextView tv1 = (TextView) view.findViewById(R.id.jinji);
        TextView tv2 = (TextView) view.findViewById(R.id.yiban);
        TextView tv3 = (TextView) view.findViewById(R.id.zhengchang);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //设置PopupWindow里面的View
        mPopupWindow2.setContentView(view);
        mPopupWindow2.setFocusable(true);
        //让PopupWindow能够消失
        mPopupWindow2.setOutsideTouchable(true);
        mPopupWindow2.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow2.showAtLocation(SendOrderActivity.this.findViewById(R.id.rl_sendorder), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void showPopWindow() {
        if (mPopupWindow == null) {
            //弹出PopupWindow
            mPopupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        View view = View.inflate(this, R.layout.type_pop_window, null);
        TextView tv1 = (TextView) view.findViewById(R.id.yingjian);
        TextView tv2 = (TextView) view.findViewById(R.id.ruanjian);
        TextView tv3 = (TextView) view.findViewById(R.id.fangong);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        //让PopupWindow能够消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow.showAtLocation(SendOrderActivity.this.findViewById(R.id.rl_sendorder), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
        //设置PopupWindow里面的View
        mPopupWindow1.setContentView(view);
        mPopupWindow1.setFocusable(true);
        //让PopupWindow能够消失
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow1.showAsDropDown(mEtChose);
    }

    private void getUserData() {
        Observable<AllUsers> users = ApiWrapper.getInstance().getpaidanUsers(mToken);
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
                    mAllUserAdapter = new AllUserAdapter(allUsersContent, getApplicationContext());
                    lv_name.setAdapter(mAllUserAdapter);

                    mAllUserAdapter.setOnClickDay(new AllUserAdapter.OnClickUserListener() {
                        @Override
                        public void onClickUser(AllUsers.ContentBean bean) {
                            xjName = bean.getLogin_name();
                            mEtChose.setText(bean.getNick_name());
                            mPopupWindow1.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "未能查询到巡检人员", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}
