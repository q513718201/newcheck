package com.vito.check.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by xk on 2016/11/21.
 */
public class SpUtils {


    //保存是否开启辅助服务的状态
    public static void putString(Context context, String key, String value){
        //保存数据到sp中，文件私有
        SharedPreferences sp=context.getSharedPreferences("login", context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    //获取保存的数据
    public static String getString(Context context, String key, String defValue){
        SharedPreferences sp=context.getSharedPreferences("login", context.MODE_PRIVATE);
        return sp.getString(key, defValue);

    }

}

