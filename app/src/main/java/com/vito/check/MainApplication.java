package com.vito.check;

import android.app.Application;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xk on 2017/3/9.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        JPushInterface.setAlias(this,"111",null);
//        String a="11111";
//        // ","隔开的多个 转换成 Set
//        String[] sArray = a.split(",");
//        Set<String> tagSet = new LinkedHashSet<String>();
//        for (String sTagItme : sArray) {
//            tagSet.add(sTagItme);
//        }
//        JPushInterface.setTags(this,tagSet,null);
    }
}
