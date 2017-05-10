package com.vito.check.BroadCast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.vito.check.Activity.GonggaoActivity;
import com.vito.check.Activity.MyOrderActivity;
import com.vito.check.Fragment.MainFragment;
import com.vito.check.MainActivity;
import com.vito.check.util.SpUtils;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;
import cn.jpush.android.service.PushService;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by admin on 2017/3/18.
 */

public class MyBroadCast extends BroadcastReceiver {

    private NotificationManager manager;
    private String mMessage;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            Bundle extras = intent.getExtras();
            String title = extras.getString(JPushInterface.EXTRA_TITLE);
            String message = extras.getString(JPushInterface.EXTRA_MESSAGE);
            Log.d("jpush111", title + "---" + message);
        }
        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            Bundle extras = intent.getExtras();
            String title = extras.getString(JPushInterface.ACTION_NOTIFICATION_RECEIVED);
            mMessage = extras.getString(JPushInterface.EXTRA_ALERT);
            SpUtils.putString(context,"jiguang",mMessage);
            Log.d("jpush112", title + "---" + mMessage);

        }
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String jiguang = SpUtils.getString(context, "jiguang", "");
            Log.d("jpush112","您有新的公告" + jiguang);
            if (jiguang.equals("您有新的公告")) {
                Intent i = new Intent(context, GonggaoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }else{
                Intent i = new Intent(context, MyOrderActivity.class);
                i.putExtra("jingli", "0");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        }

    }
}
