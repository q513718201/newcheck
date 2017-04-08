package com.vito.check.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.vito.check.R;
import com.vito.check.bean.WeekReport;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/6.
 */
public class WeekReportActivity extends BaseActivity {


    @BindView(R.id.xjNumWeek_week)
    TextView mXjNumWeekWeek;
    @BindView(R.id.dispatchNumWeek)
    TextView mDispatchNumWeek;
    @BindView(R.id.processNumWeek)
    TextView mProcessNumWeek;
    @BindView(R.id.week_topOnlineRate)
    TextView mWeekTopOnlineRate;
    @BindView(R.id.tv_weekname)
    TextView mName;
    private WeekReport.ContentBean mWeekbean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("周报表详情", true);
        mWeekbean = (WeekReport.ContentBean) getIntent().getSerializableExtra("weekbean");
        if (mWeekbean != null) {
            mName.setText(mWeekbean.getNickName());
            mXjNumWeekWeek.setText(mWeekbean.getXjNumWeek() + "");
            mDispatchNumWeek.setText(mWeekbean.getDispatchNumWeek() + "");
            mProcessNumWeek.setText(mWeekbean.getProcessNumWeek() + "");
            mWeekTopOnlineRate.setText(mWeekbean.getTopOnlineRate());
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_weekreport;
    }
}
