package com.vito.check.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.vito.check.R;
import com.vito.check.bean.DayReport;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/6.
 */
public class DayReportActivity extends BaseActivity {

    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.firstSignin)
    TextView mFirstSignin;
    @BindView(R.id.lastSignin)
    TextView mLastSignin;
    @BindView(R.id.userful_num)
    TextView mUserfulNum;
    @BindView(R.id.order_num)
    TextView mOrderNum;
    @BindView(R.id.topOnline)
    TextView mTopOnline;
    @BindView(R.id.xjNumWeek)
    TextView mXjNumWeek;
    @BindView(R.id.processWeek)
    TextView mProcessWeek;
    @BindView(R.id.tv_name)
    TextView mName;
    private DayReport.ContentBean mDaybean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("日报表详情", true);
        mDaybean = (DayReport.ContentBean) getIntent().getSerializableExtra("daybean");
        if (mDaybean != null) {
            mName.setText(mDaybean.getNickName());
            mDate.setText(mDaybean.getDate());
            mFirstSignin.setText(mDaybean.getFirstSignin());
            mLastSignin.setText(mDaybean.getLastSignin());
            mUserfulNum.setText(mDaybean.getXjNumToday() + "");
            mOrderNum.setText(mDaybean.getProcessNumToday() + "");
            mTopOnline.setText(mDaybean.getTopOnline());
            mXjNumWeek.setText(mDaybean.getXjNumWeek() + "");
            mProcessWeek.setText(mDaybean.getProcessWeek() + "");
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_dayreport;
    }
}
