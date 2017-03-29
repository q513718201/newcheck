package com.vito.check.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Contact;
import com.vito.check.MainActivity;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.User;
import com.vito.check.util.SpUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Subscriber;


/**
 * des:启动页
 * Created by xsf
 * on 2016.09.15:16
 */
public class SplashActivity extends BaseActivity {

    private ImageView ivLogo;
    private TextView tvName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }
    public void initView() {
        ivLogo = (ImageView)findViewById(R.id.iv_logo);
       tvName=  (TextView)findViewById(R.id.tv_name);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.6f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.6f, 1.3f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(tvName, alpha, scaleX, scaleY);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha, scaleX, scaleY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(2000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                String username = SpUtils.getString(getApplicationContext(), "username", "");
                String pwd = SpUtils.getString(getApplicationContext(), "pwd", "");
                if(username==""&&pwd==""){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else{
                    login(username,pwd);
                }



            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    @Override
    public int getLayout() {
        return  R.layout.act_splash;
    }

    private void login(final String phone, final String pwd) {
        Observable<User> login = ApiWrapper.getInstance().login(phone, pwd);
        addSubscription(login, new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(getApplicationContext(),  "自动登录失败，请重新登陆！" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(User user) {
                String content = user.getContent();
                boolean success = user.isSuccess();
                if(success){
                    SpUtils.putString(getApplicationContext(), "username", phone);
                    SpUtils.putString(getApplicationContext(), "pwd", pwd);
                    SpUtils.putString(getApplicationContext(), "token", content);
                    Contact.isLogin = true;
                    Log.e("login",content);
                    String[] sArray = phone.split(",");
                    Set<String> tagSet = new LinkedHashSet<String>();
                    for (String sTagItme : sArray) {
                        tagSet.add(sTagItme);
                    }
                    JPushInterface.setTags(getApplicationContext(), tagSet, null);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "自动登录成功" , Toast.LENGTH_SHORT).show();

                }else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Toast.makeText(getApplicationContext(),  "自动登录失败，请重新登陆！" , Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });

    }
}
