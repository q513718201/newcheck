package com.vito.check.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vito.check.Contact;
import com.vito.check.MainActivity;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.User;
import com.vito.check.util.SpUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/9.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tl_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_phone)
    EditText mEt_phone;
    @BindView(R.id.et_psw)
    EditText mEt_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTv_toolbar.setText("登录");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                final String phone = mEt_phone.getText().toString();
                final String pwd = mEt_pwd.getText().toString();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(phone, pwd);
                break;

        }
    }

    private void login(final String phone, final String pwd) {
        Observable<User> login = ApiWrapper.getInstance().login(phone, pwd);
        addSubscription(login, new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                String content = user.getContent();
                boolean success = user.isSuccess();
                if (success) {
                    SpUtils.putString(getApplicationContext(), "username", phone);
                    SpUtils.putString(getApplicationContext(), "pwd", pwd);
                    SpUtils.putString(getApplicationContext(), "token", content);
                    Contact.isLogin = true;
                    Log.e("login", content);

                    String[] sArray = phone.split(",");
                    Set<String> tagSet = new LinkedHashSet<String>();
                    for (String sTagItme : sArray) {
                        tagSet.add(sTagItme);
                    }
                    JPushInterface.setTags(getApplicationContext(), tagSet, null);

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

    }


}
