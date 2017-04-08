package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vito.check.Adapter.ReportDayAdapter;
import com.vito.check.Adapter.ReportWeekAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.DayReport;
import com.vito.check.bean.WeekReport;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/21.
 */

public class ReportActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rb_paidan)
    RadioButton mRbday;
    @BindView(R.id.rb_daishenhe)
    RadioButton mRbWeek;
    @BindView(R.id.rg_gp)
    RadioGroup mRgGp;
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
    @BindView(R.id.ll_day)
    LinearLayout mLlDay;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    @BindView(R.id.xjNumWeek_week)
    TextView mXjNumWeek1;
    @BindView(R.id.dispatchNumWeek)
    TextView mDispatchNumWeek;
    @BindView(R.id.processNumWeek)
    TextView mProcessNumWeek;
    @BindView(R.id.week_topOnlineRate)
    TextView mWeekTopOnlineRate;
    @BindView(R.id.ll_week)
    LinearLayout mLlWeek;
    @BindView(R.id.lv_report)
    ListView mLvReport;
    @BindView(R.id.ll_listview)
    LinearLayout mLlListview;
    private String mToken;
    private String mRole;
    private String xjName = "";
    private ReportDayAdapter mReportDayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("报表", true);
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
        mRole = SpUtils.getString(getApplicationContext(), "role", "");
        init();
        mRgGp.setOnCheckedChangeListener(this);
    }

    private void init() {
        if (mRole.equals("manager")) {
            mRbday.setChecked(true);
            mLlListview.setVisibility(View.VISIBLE);
            getDayReport(xjName);
        } else {
            mRbday.setChecked(true);
            getDayReport(xjName);
        }
    }

    private void getDayReport(final String xjName) {
        mLlDay.setVisibility(View.GONE);
        mLlWeek.setVisibility(View.GONE);
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<DayReport> dayReport = ApiWrapper.getInstance().getDayReport(mToken, xjName);
        addSubscription(dayReport, new Subscriber<DayReport>() {
            @Override
            public void onCompleted() {
                if (!mRole.equals("manager")) {
                    mLlProgress.setVisibility(View.GONE);
                    mLlDay.setVisibility(View.VISIBLE);
                } else {
                    mLlProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("report", e.getMessage());
                return;
            }

            @Override
            public void onNext(DayReport dayReport) {
                List<DayReport.ContentBean> content = dayReport.getContent();
                Log.d("report", content.get(0).toString());
                if (content != null) {
                    if (!mRole.equals("manager")) {
                        DayReport.ContentBean dayreport = content.get(0);
                        mDate.setText(dayreport.getDate());
                        mFirstSignin.setText(dayreport.getFirstSignin());
                        mLastSignin.setText(dayreport.getLastSignin());
                        mUserfulNum.setText(dayreport.getXjNumToday() + "");
                        mOrderNum.setText(dayreport.getProcessNumToday() + "");
                        mTopOnline.setText(dayreport.getTopOnline());
                        mXjNumWeek.setText(dayreport.getXjNumWeek() + "");
                        mProcessWeek.setText(dayreport.getProcessWeek() + "");
                    } else {
                        mReportDayAdapter = new ReportDayAdapter(content, getApplicationContext());
                        mLvReport.setAdapter(mReportDayAdapter);
                        mReportDayAdapter.setOnClickDay(new ReportDayAdapter.OnClickDayListener() {
                            @Override
                            public void onClickDay(DayReport.ContentBean mOrderBean) {
                                Intent check = new Intent(getApplicationContext(), DayReportActivity.class);
                                check.putExtra("daybean", mOrderBean);
                                startActivity(check);
                            }
                        });
                    }

                }

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_report;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_paidan:
                if (mRole.equals("manager")) {
                    mLlDay.setVisibility(View.GONE);
                    mLlWeek.setVisibility(View.GONE);
                    getDayReport(xjName);

                } else {
                    getDayReport(xjName);
                    mLlWeek.setVisibility(View.GONE);
                }

                break;
            case R.id.rb_daishenhe:
                if (mRole.equals("manager")) {
                    getWeekReport(xjName);
                    mLlWeek.setVisibility(View.GONE);
                    mLlDay.setVisibility(View.GONE);
                } else {
                    getWeekReport(xjName);
                    mLlDay.setVisibility(View.GONE);
                }

                break;
        }
    }

    private void getWeekReport(final String xjName) {
        mLlDay.setVisibility(View.GONE);
        mLlWeek.setVisibility(View.GONE);
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<WeekReport> weekReport = ApiWrapper.getInstance().getWeekReport(mToken, xjName);
        addSubscription(weekReport, new Subscriber<WeekReport>() {
            @Override
            public void onCompleted() {
                if (!mRole.equals("manager")) {
                    mLlProgress.setVisibility(View.GONE);
                    mLlWeek.setVisibility(View.VISIBLE);
                } else {
                    mLlProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeekReport weekReport) {
                List<WeekReport.ContentBean> content = weekReport.getContent();
                if (content != null) {
                    if (!mRole.equals("manager")) {
                        WeekReport.ContentBean contentBean = content.get(0);
                        mXjNumWeek1.setText(contentBean.getXjNumWeek() + "");
                        mDispatchNumWeek.setText(contentBean.getDispatchNumWeek() + "");
                        mProcessNumWeek.setText(contentBean.getProcessNumWeek() + "");
                        mWeekTopOnlineRate.setText(contentBean.getTopOnlineRate());
                    } else {
                        ReportWeekAdapter reportWeekAdapter = new ReportWeekAdapter(content, getApplicationContext());
                        mLvReport.setAdapter(reportWeekAdapter);
                        reportWeekAdapter.setOnClickWeek(new ReportWeekAdapter.OnClickWeekListener() {
                            @Override
                            public void onClickWeek(WeekReport.ContentBean mOrderBean) {
                                Intent check=new Intent(getApplicationContext(), WeekReportActivity.class);
                                check.putExtra("weekbean",mOrderBean);
                                startActivity(check);
                            }
                        });

                    }

                }


            }
        });
    }

}
