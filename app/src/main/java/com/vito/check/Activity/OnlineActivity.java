package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vito.check.Adapter.OnLineManagerAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.OnlineRate;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by admin on 2017/4/5.
 */

public class OnlineActivity extends BaseActivity {
    @BindView(R.id.now)
    TextView mNow;
    @BindView(R.id.top)
    TextView mTop;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    @BindView(R.id.ll_normall)
    LinearLayout mLlNormall;
    @BindView(R.id.manager_online)
    LinearLayout mManagerOnline;
    @BindView(R.id.lv_manager)
    ListView mLvManager;
    private String mToken;
    private String mRole;
    private String xjName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("在线率", true);
        init();
    }

    private void init() {
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
        mRole = SpUtils.getString(getApplicationContext(), "role", "");
        if (!mRole.equals("manager")) {
            loadData();
        } else {
            mLlNormall.setVisibility(View.GONE);
            mManagerOnline.setVisibility(View.VISIBLE);
            loadData();
        }
    }

    private void loadData() {
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<OnlineRate> onlineRate = ApiWrapper.getInstance().getOnlineRate(mToken, xjName);
        addSubscription(onlineRate, new Subscriber<OnlineRate>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("online", e.getMessage());
                return;
            }

            @Override
            public void onNext(OnlineRate onlineRate) {
                List<OnlineRate.ContentBean> content = onlineRate.getContent();
                Log.d("online", "获取数量" + content.size() + "");
                Log.d("online", content.get(0).toString());
                if (!mRole.equals("manager")) {
                    OnlineRate.ContentBean contentBean = content.get(0);
                    mNow.setText(contentBean.getNowOnlineRate());
                    mTop.setText(contentBean.getTopOnlineRate());
                } else {
                    OnLineManagerAdapter onLineManagerAdapter = new OnLineManagerAdapter(content, getApplicationContext());
                    mLvManager.setAdapter(onLineManagerAdapter);
                }
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_online;
    }
}
