package com.vito.check.data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.vito.check.Activity.LoginActivity;
import com.vito.check.util.SpUtils;

/**
 * Created by admin on 2017/3/19.
 */

public class DialogUtils {
//    public static void dialog(final Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage("请您开启定位权限");
//        builder.setTitle("提示");
//        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Intent localIntent = new Intent();
//                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (Build.VERSION.SDK_INT >= 9) {
//                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                    localIntent.setData(Uri.fromParts("package", "com.vito.check", null));
//                } else if (Build.VERSION.SDK_INT <= 8) {
//                    localIntent.setAction(Intent.ACTION_VIEW);
//                    localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
//                    localIntent.putExtra("com.android.settings.ApplicationPkgName", "com.vito.check");
//                }
//                context.startActivity(localIntent);
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//    }
public static void dialog(final Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage("是否要退出账号");
    builder.setTitle("提示");
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            SpUtils.putString(context, "username", "");
            SpUtils.putString(context, "pwd", "");
            SpUtils.putString(context, "token", "");
            Toast.makeText(context,"注销成功请重新登陆",Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context,LoginActivity.class));

        }
    });
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    builder.create().show();
}
}
