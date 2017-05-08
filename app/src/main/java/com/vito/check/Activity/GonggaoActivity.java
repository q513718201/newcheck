package com.vito.check.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Adapter.GonggaoAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.Gonggao;
import com.vito.check.bean.SendOrder;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by admin on 2017/5/7.
 */

public class GonggaoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_gonggao)
    EditText mEtGonggao;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    @BindView(R.id.lv_gonggao)
    ListView mLvGonggao;
    @BindView(R.id.ll_xunjian)
    LinearLayout mLlXunjian;
    @BindView(R.id.ll_manager)
    LinearLayout mLlManager;
    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    private String mToken;
    private List<Gonggao.ContentBean> gonggaoContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("公告",true);
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
        String role = SpUtils.getString(getApplicationContext(), "role", "");
        if (role.equals("manager")) {
            mLlXunjian.setVisibility(View.GONE);
            mLlManager.setVisibility(View.VISIBLE);
        } else {
            mLlXunjian.setVisibility(View.VISIBLE);
            mLlManager.setVisibility(View.GONE);
            getGongGaoMsg();
        }
        mLoginBtn.setOnClickListener(this);

    }

    private void getGongGaoMsg() {
        mLlProgress.setVisibility(View.VISIBLE);
        mTvLoading.setText("正在查询...");
        Observable<Gonggao> ggonggao = ApiWrapper.getInstance().getGgonggao(mToken);
        addSubscription(ggonggao, new Subscriber<Gonggao>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Gonggao gonggao) {
                if (gonggao.isSuccess()) {
                    gonggaoContent = gonggao.getContent();
                    if (gonggaoContent.size() == 0) {
                        Toast.makeText(getApplicationContext(), "未有相关公告！", Toast.LENGTH_SHORT).show();
                    } else {
                        mLvGonggao.setAdapter(new GonggaoAdapter(gonggaoContent, getApplicationContext()));
                    }

                }
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.gonggao_activity;
    }

    @Override
    public void onClick(View view) {
        String gonggao = mEtGonggao.getText().toString();
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<SendOrder> gonggao1 = ApiWrapper.getInstance().gonggao(mToken, gonggao);
        addSubscription(gonggao1, new Subscriber<SendOrder>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(SendOrder sendOrder) {
                if (sendOrder.isSuccess()) {
                    Toast.makeText(getApplicationContext(), sendOrder.getContent(), Toast.LENGTH_SHORT).show();
                    exit();
                }
            }
        });

    }
}
