package com.vito.check.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vito.check.Activity.MyOrderActivity;
import com.vito.check.MainActivity;
import com.vito.check.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xk on 2017/3/16.
 */

public class MessageFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.card_my)
    CardView mCardMy;
//    @BindView(R.id.card_new)
//    CardView mCardNew;
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
        mCardMy.setOnClickListener(this);
       // mCardNew.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_my:
                startActivity(new Intent(mActivity, MyOrderActivity.class));
                break;
//            case R.id.card_new:
//                break;
        }
    }
}
