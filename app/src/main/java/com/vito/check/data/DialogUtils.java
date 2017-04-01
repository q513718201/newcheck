package com.vito.check.data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vito.check.Activity.LoginActivity;
import com.vito.check.R;
import com.vito.check.util.SpUtils;

/**
 * Created by admin on 2017/3/19.
 */

public class DialogUtils {

    public static void modify(final Context context) {

        View view = View.inflate(context, R.layout.dialog_input, null);
        final EditText et_address = (EditText) view.findViewById(R.id.et_address);

        new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String dev_id = et_address.getText().toString();
                      if(!TextUtils.isEmpty(dev_id)){
                          Toast.makeText(context,"wol;aile",Toast.LENGTH_SHORT).show();
                      }

                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

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
