package com.vito.check.BroadCast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.vito.check.Activity.MyOrderActivity;
import com.vito.check.Fragment.MainFragment;
import com.vito.check.MainActivity;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushService;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by admin on 2017/3/18.
 */

public class MyBroadCast extends BroadcastReceiver {

    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            Bundle extras = intent.getExtras();
            String title = extras.getString(JPushInterface.EXTRA_TITLE);
            String message = extras.getString(JPushInterface.EXTRA_MESSAGE);
            Log.d("jpush", title + "---" + message);
        }

        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            Bundle extras = intent.getExtras();
            String msg = extras.getString(JPushInterface.EXTRA_MESSAGE);
            Log.d("jiguang",msg);
//            MainFragment m=new MainFragment();
//            m.show();


        }
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Intent i = new Intent(context, MyOrderActivity.class);
            Bundle extras = intent.getExtras();
            String msg = extras.getString(JPushInterface.EXTRA_MESSAGE);
            i.putExtra("message", msg);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }
}
