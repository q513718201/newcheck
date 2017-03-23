package com.vito.check.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;

/**
 * Created by xk on 2017/3/23.
 */

public class DialogUtil {
    public static void startRoutePlanDriving(Context context,LatLng position) {
        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(position)
//            .startName("天安门")
//            .endPoint(ptEnd);
                .endName("大雁塔")
                .cityName("西安");

//        RouteParaOption para = new RouteParaOption()
//                .startName("天安门").endName("百度大厦");

//        RouteParaOption para = new RouteParaOption()
//        .startPoint(pt_start).endPoint(pt_end);

        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }
    /**
     * 提示未安装百度地图app或app版本过低
     */
    public static void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(context);
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
