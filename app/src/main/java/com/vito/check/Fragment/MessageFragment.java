package com.vito.check.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vito.check.Activity.GonggaoActivity;
import com.vito.check.Activity.MyOrderActivity;
import com.vito.check.MainActivity;
import com.vito.check.R;
import com.vito.check.util.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xk on 2017/3/16.
 */

public class MessageFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.card_my)
    CardView mCardMy;
    @BindView(R.id.card_allorder)
    CardView mCardAllorder;
    @BindView(R.id.msg)
    TextView mMsg;
    @BindView(R.id.card_msg)
    CardView mCardMsg;

    private MainActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        String role = SpUtils.getString(mActivity, "role", "");
        if (role.equals("manager")) {
            mCardAllorder.setVisibility(View.VISIBLE);
            mMsg.setText("发布公告");
        }
        mCardMy.setOnClickListener(this);
        mCardAllorder.setOnClickListener(this);
        mCardMsg.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, MyOrderActivity.class);
        switch (v.getId()) {
            case R.id.card_my:
                intent.putExtra("jingli", "0");
                startActivity(intent);
                break;
            case R.id.card_allorder:
                intent.putExtra("jingli", "1");
                startActivity(intent);
                break;
            case R.id.card_msg:
                startActivity(new Intent(mActivity,GonggaoActivity.class));
                break;
        }
    }

}
