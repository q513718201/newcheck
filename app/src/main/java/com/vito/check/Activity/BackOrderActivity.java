package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.vito.check.Adapter.OrderBackAdapter;
import com.vito.check.Adapter.YunyingAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.SendOrder;
import com.vito.check.bean.YunyingOrder;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by admin on 2017/4/13.
 */
public class BackOrderActivity extends BaseActivity {

    @BindView(R.id.lv_back)
    ListView mLvBack;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    private List<YunyingOrder.ContentBean> mYunyingOrderContent;
    private YunyingOrder.ContentBean mYunyingBean;
    private OrderBackAdapter mOrderBackAdapter;
    private String mToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToken = SpUtils.getString(this, "token", "");
        setTitle("您派出去的单", true);

        mYunyingBean = (YunyingOrder.ContentBean) getIntent().getSerializableExtra("yunyingBean");

        mLlProgress.setVisibility(View.VISIBLE);
       getBackOrder();
    }

    private void getBackOrder() {
        if(mYunyingOrderContent!=null){
            mYunyingOrderContent.clear();
        }
        Observable<YunyingOrder> backOrders = ApiWrapper.getInstance().getBackOrders(mToken);
        addSubscription(backOrders, new Subscriber<YunyingOrder>() {
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
                if (yunyingOrder.isSuccess()) {
                    if (mYunyingOrderContent.size() == 0) {
                        Toast.makeText(getApplicationContext(), "未能查到您派单", Toast.LENGTH_SHORT).show();
                    }
                    mOrderBackAdapter = new OrderBackAdapter(mYunyingOrderContent, getApplicationContext());
                    mLvBack.setAdapter(mOrderBackAdapter);
                    if (mOrderBackAdapter != null) {
                        mOrderBackAdapter.setOnBack(new OrderBackAdapter.OnBackClick() {
                            @Override
                            public void back(YunyingOrder.ContentBean mOrderBean) {
                                int id = mOrderBean.getId();
                                backOrder(id);
                            }
                        });
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "账号在其他地方登陆，请重新登陆", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    exit();
                }
            }
        });
    }

    private void backOrder(int id) {
        Observable<SendOrder> sendOrderObservable = ApiWrapper.getInstance().BackOrders(mToken, id);
        addSubscription(sendOrderObservable, new Subscriber<SendOrder>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SendOrder sendOrder) {

                if (sendOrder.isSuccess()) {
                    Toast.makeText(getApplicationContext(), sendOrder.getContent(), Toast.LENGTH_SHORT).show();
                    getBackOrder();
                }

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.backactivity;
    }
}
