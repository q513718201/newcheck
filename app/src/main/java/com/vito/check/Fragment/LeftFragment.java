package com.vito.check.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Activity.LoginActivity;
import com.vito.check.Contact;
import com.vito.check.EventBus.SendMessage;
import com.vito.check.MainActivity;
import com.vito.check.R;
import com.vito.check.View.CircleImageView;
import com.vito.check.bean.User;
import com.vito.check.util.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xk on 2017/3/15.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.tv_nick)
    TextView mTvNick;
    @BindView(R.id.iv_image)
    CircleImageView mIvImage;
    private MainActivity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
        mContext = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        ButterKnife.bind(this, view);
        init();
        mIvImage.setOnClickListener(this);
        return view;

    }


    private void init() {
        String nick = SpUtils.getString(mContext, "nick", "");
        Log.e("nick",nick);
        if(Contact.isLogin){
            mTvNick.setText(nick);
        }else{
            mTvNick.setText("点击登录");
        }
    }

    @Override
    public void onClick(View v) {

        if(Contact.isLogin){
            Toast.makeText(mContext,"账号已登录",Toast.LENGTH_SHORT).show();
            return;
        }else{
            //在左侧点击要关闭drawer
            Intent i=new Intent(mContext, LoginActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
