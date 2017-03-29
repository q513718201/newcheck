package com.vito.check.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.vito.check.Activity.AllUserActivity;
import com.vito.check.Activity.DeviceCheckActivity;
import com.vito.check.Activity.FinishOrderActivity;
import com.vito.check.Activity.InspectionRecord;
import com.vito.check.Activity.LoginActivity;
import com.vito.check.Activity.OnlinerateActivity;
import com.vito.check.Activity.ReportActivity;
import com.vito.check.Activity.SendOrderActivity;
import com.vito.check.Adapter.SectionAdapter;
import com.vito.check.MainActivity;
import com.vito.check.R;
import com.vito.check.bean.WorkSelect;
import com.vito.check.data.DataServer;
import com.vito.check.data.DialogUtils;
import com.vito.check.util.SpUtils;

import java.util.List;

/**
 * Created by xk on 2017/3/16.
 */

public class WorkFragment extends Fragment {
    private MainActivity mActivity;
    private View view;
    private RecyclerView mRecyclerView;
    private List<WorkSelect> mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work, container, false);
        initRecycleView();
        return view;
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mData = DataServer.getSampleData();
        Log.i("tangj", "onCreate: " + mData);
        SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mData);
        sectionAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                WorkSelect mySection = mData.get(position);
//                if (mySection.isHeader)
//                    Toast.makeText(mActivity, mySection.header, Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(mActivity, mySection.t.getContent()+"----"+position, Toast.LENGTH_LONG).show();
                switch (position) {
                    case 1:
                        startActivity(InspectionRecord.class);
                        break;
                    case 2:
                        startActivity(ReportActivity.class);
                        break;
                    case 3:
                        startActivity(DeviceCheckActivity.class);
                        break;
                    case 5:
                       startActivity(SendOrderActivity.class);
                        break;
                    case 7:
                        startActivity(OnlinerateActivity.class);
                        break;
                    case 9:
                        dialog();
                        break;
                    case 10:

                        break;
                }
            }

        });
        mRecyclerView.setAdapter(sectionAdapter);
    }

    public void startActivity(Class c) {
        Intent i = new Intent(mActivity, c);
        mActivity.startActivity(i);
    }

    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("是否要退出账号");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SpUtils.putString(mActivity, "username", "");
                SpUtils.putString(mActivity, "pwd", "");
                SpUtils.putString(mActivity, "token", "");
                Toast.makeText(mActivity, "注销成功请重新登陆", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mActivity, LoginActivity.class));
                mActivity.finish();

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
