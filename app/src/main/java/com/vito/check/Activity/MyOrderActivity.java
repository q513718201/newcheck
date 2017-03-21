package com.vito.check.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vito.check.Adapter.MyAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.MyOrder;
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
    private String state = "";
    private String mToken;
    private List<MyOrder.ContentBean> mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的派单", true);
        mToken = SpUtils.getString(this, "token", "");
        mRgGp.setOnCheckedChangeListener(this);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_myorder;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_paidan:
                state = "派单中";
                break;
            case R.id.rb_daishenhe:
                state = "待审核";
                break;
            case R.id.rb_jiedan:
                state = "已结单";
                break;

        }
        if (mContent != null) {
            mContent.clear();
        }
        Observable<MyOrder> myOrders = ApiWrapper.getInstance().getMyOrders(mToken, state);
        addSubscription(myOrders, new Subscriber<MyOrder>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyOrder myOrder) {
                mContent = myOrder.getContent();
                Log.d("order", mContent.toString());
                mLv.setAdapter(new MyAdapter(mContent, getApplicationContext()));
            }
        });
    }


}


