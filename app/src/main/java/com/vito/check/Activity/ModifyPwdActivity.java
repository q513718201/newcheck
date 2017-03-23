package com.vito.check.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vito.check.Http.HUrls;
import com.vito.check.NetWork.APIServer;
import com.vito.check.R;
import com.vito.check.bean.Regist;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by xk on 2017/3/14.
 */

public class ModifyPwdActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_nick)
    EditText mEtNick;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd1)
    EditText mEtPwd1;
    @BindView(R.id.login_regist)
    Button mLoginRegist;
    @BindView(R.id.rg_gp)
    RadioGroup mRgGp;
    @BindView(R.id.rb_yxb)
    RadioButton mRbYxb;
    private String part = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        mRgGp.setOnCheckedChangeListener(this);
        mLoginRegist.setOnClickListener(this);
        mRbYxb.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_gcb:
                part = "工程部";
                Toast.makeText(this, part, Toast.LENGTH_LONG).show();
                break;
            case R.id.rb_scb:
                part = "市场部";
                Toast.makeText(this, part, Toast.LENGTH_LONG).show();
                break;
            case R.id.rb_yxb:
                part = "运行部";
                Toast.makeText(this, part, Toast.LENGTH_LONG).show();
                break;

        }

    }

    @Override
    public void onClick(View v) {
        String userName = mEtUsername.getText().toString();
        String nickName = mEtNick.getText().toString();
        String pwd = mEtPwd.getText().toString();
        String pwd1 = mEtPwd1.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(nickName)){
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pwd1)){
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pwd.equals(pwd1)){
            Toast.makeText(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HUrls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServer iRegist = retrofit.create(APIServer.class);
        Observable<Regist> observable = iRegist.UserRegist(userName, nickName, pwd, part);




        }


}
