package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vito.check.Adapter.MyAdapter;
import com.vito.check.Adapter.YunyingAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.YunyingOrder;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/20.
 */

public class MyOrderActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rb_paidan)
    RadioButton mRbPaidan;
    @BindView(R.id.rb_daishenhe)
    RadioButton mRbDaishenhe;
    @BindView(R.id.rb_jiedan)
    RadioButton mRbJiedan;
    @BindView(R.id.rg_gp)
    RadioGroup mRgGp;
    @BindView(R.id.lv)
    ListView mLv;
//    @BindView(R.id.rb_gongchengbu)
//    RadioButton mRbGongchengbu;
//    @BindView(R.id.rb_yunyingbu)
//    RadioButton mRbYunyingbu;
//    @BindView(R.id.rg2)
//    RadioGroup mRg2;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    private String state = "";
    private String mToken;
    private List<MyOrder.ContentBean> mContent;
    private int day = 7;
    private List<YunyingOrder.ContentBean> mYunyingOrderContent;
    private MyAdapter mMyAdapter;
    private YunyingAdapter mYunyingAdapter;
    private String mExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的派单", true);
        mExtra = getIntent().getStringExtra("jingli");
        mToken = SpUtils.getString(this, "token", "");
        mRgGp.setOnCheckedChangeListener(this);
        mRbPaidan.setChecked(true);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_myorder;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_paidan:
                if(mExtra.equals("0")){
                    state = "1";
                }else{
                    state = "派单中";
                }

                break;
            case R.id.rb_daishenhe:
                if(mExtra.equals("0")){
                    state = "2";
                }else{
                    state = "待审核";
                }
                break;
            case R.id.rb_jiedan:
                if(mExtra.equals("0")){
                    state = "3";
                }else{
                    state = "已结单";
                }
                break;
        }
        if(mExtra.equals("0")){
            getYunyingbuOrder();
        }else{
            getAllOrder();
        }

    }

    //获取所有人派单
    private void getAllOrder() {
        mLlProgress.setVisibility(View.VISIBLE);
        if (mYunyingOrderContent != null) {
            mYunyingOrderContent.clear();
        }
        Log.d("hhhh",mToken+"---"+day+"--"+state);
        Observable<YunyingOrder> allOrders = ApiWrapper.getInstance().getAllOrders(mToken, day, state);
        addSubscription(allOrders, new Subscriber<YunyingOrder>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(YunyingOrder yunyingOrder) {
                mYunyingOrderContent = yunyingOrder.getContent();
                if(yunyingOrder.isSuccess()){
                    if (mYunyingOrderContent.size() == 0) {
                        Log.d("hhhh","---"+mYunyingOrderContent.size());
                        Toast.makeText(getApplicationContext(), "未能查到派单", Toast.LENGTH_SHORT).show();
                    }else{
                        mYunyingAdapter = new YunyingAdapter(mYunyingOrderContent, getApplicationContext(),mExtra);
                        mLv.setAdapter(mYunyingAdapter);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "账号在其他地方登陆，请重新登陆", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    exit();
                }


            }
        });
    }

    //获取运营部派单
    private void getYunyingbuOrder() {
        mLlProgress.setVisibility(View.VISIBLE);
        if (mYunyingOrderContent != null) {
            mYunyingOrderContent.clear();
        }
        Observable<YunyingOrder> myOrders = ApiWrapper.getInstance().getYunyingOrders(mToken, day, state);
        addSubscription(myOrders, new Subscriber<YunyingOrder>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(YunyingOrder yunyingOrder) {
                mYunyingOrderContent = yunyingOrder.getContent();
                if(yunyingOrder.isSuccess()){
                    if (mYunyingOrderContent.size() == 0) {
                        Toast.makeText(getApplicationContext(), "未能查到派单", Toast.LENGTH_SHORT).show();
                    }
                    mYunyingAdapter = new YunyingAdapter(mYunyingOrderContent, getApplicationContext(), mExtra);
                    mLv.setAdapter(mYunyingAdapter);
                }else{
                    Toast.makeText(getApplicationContext(), "账号在其他地方登陆，请重新登陆", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                   exit();
                }

            }
        });
    }

    //获取工程部派单
    private void getGongchengOrder() {
        mLlProgress.setVisibility(View.VISIBLE);
        if (mContent != null) {
            mContent.clear();
        }
        Observable<MyOrder> myOrders = ApiWrapper.getInstance().getMyOrders(mToken, day, state);
        addSubscription(myOrders, new Subscriber<MyOrder>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(MyOrder myOrder) {
                mContent = myOrder.getContent();
                if (mContent.size() == 0) {
                    Toast.makeText(getApplicationContext(), "未能查到派单", Toast.LENGTH_SHORT).show();
                }
                Log.d("order", mContent.toString());
                mMyAdapter = new MyAdapter(mContent, getApplicationContext());
                mLv.setAdapter(mMyAdapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mRbPaidan.isChecked()){
            if(mExtra.equals("0")){
                getYunyingbuOrder();
            }else{
                getAllOrder();
            }
        }
        if(mRbDaishenhe.isChecked()){
            if(mExtra.equals("0")){
                getYunyingbuOrder();
            }else{
                getAllOrder();
            }
        }
        if(mRbJiedan.isChecked()){
            if(mExtra.equals("0")){
                getYunyingbuOrder();
            }else{
                getAllOrder();
            }
        }
    }


}


