package com.vito.check.Activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Adapter.AllUserAdapter;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.DayReport;
import com.vito.check.bean.WeekReport;
import com.vito.check.util.DensityUtils;
import com.vito.check.util.SpUtils;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/21.
 */

public class ReportActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.rb_paidan)
    RadioButton mRbday;
    @BindView(R.id.rb_daishenhe)
    RadioButton mRbWeek;
    @BindView(R.id.rg_gp)
    RadioGroup mRgGp;
    @BindView(R.id.et_chose)
    EditText mEtChose;
    @BindView(R.id.arrow1)
    ImageView mArrow1;
    @BindView(R.id.rl1)
    RelativeLayout mRl1;
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
    private String mToken;
    private String mRole;
    private String xjName = "";
    private PopupWindow mPopupWindow1;
    private List<AllUsers.ContentBean> allUsersContent;
    private ListView lv_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("报表", true);
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
        mRole = SpUtils.getString(getApplicationContext(), "role", "");
        init();
        mRgGp.setOnCheckedChangeListener(this);
        mArrow1.setOnClickListener(this);
    }

    private void init() {
        if (mRole.equals("manager")) {
            mRbday.setChecked(true);
            mRl1.setVisibility(View.VISIBLE);
            mLlDay.setVisibility(View.GONE);

        } else {
            mRbday.setChecked(true);
            getDayReport(xjName);
        }
    }

    private void getDayReport(String xjName) {
        mLlWeek.setVisibility(View.GONE);
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<DayReport> dayReport = ApiWrapper.getInstance().getDayReport(mToken, xjName);
        addSubscription(dayReport, new Subscriber<DayReport>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
                mLlDay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DayReport dayReport) {
                Log.d("report", dayReport.toString());
                mDate.setText(dayReport.getDate());
                mFirstSignin.setText(dayReport.getFirstSignin());
                mLastSignin.setText(dayReport.getLastSignin());
                mUserfulNum.setText(dayReport.getXjNumToday() + "");
                mOrderNum.setText(dayReport.getProcessNumToday() + "");
                mTopOnline.setText(dayReport.getTopOnline());
                mXjNumWeek.setText(dayReport.getXjNumWeek() + "");
                mProcessWeek.setText(dayReport.getProcessWeek() + "");
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
                if(mRole.equals("manager")){
                    if(!xjName.equals("")){
                        getDayReport(xjName);
                        mLlWeek.setVisibility(View.GONE);
                    }

                }else{
                    getDayReport(xjName);
                    mLlWeek.setVisibility(View.GONE);
                }

                break;
            case R.id.rb_daishenhe:
                if(mRole.equals("manager")){
                    if(!xjName.equals("")){
                        getWeekReport(xjName);
                        mLlWeek.setVisibility(View.GONE);
                    }
                }else{
                    getWeekReport(xjName);
                    mLlDay.setVisibility(View.GONE);
                }

                break;
        }
    }

    private void getWeekReport(String xjName) {
        mLlDay.setVisibility(View.GONE);
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<WeekReport> weekReport = ApiWrapper.getInstance().getWeekReport(mToken, xjName);
        addSubscription(weekReport, new Subscriber<WeekReport>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
                mLlWeek.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeekReport weekReport) {
                Log.d("report", weekReport.toString());
                mXjNumWeek1.setText(weekReport.getXjNumWeek() + "");
                mDispatchNumWeek.setText(weekReport.getDispatchNumWeek() + "");
                mProcessNumWeek.setText(weekReport.getProcessNumWeek() + "");
                mWeekTopOnlineRate.setText(weekReport.getTopOnlineRate());

            }
        });
    }

    @Override
    public void onClick(View v) {
        showPopWindow1();
    }

    private void showPopWindow1() {
        if (mPopupWindow1 == null) {
            //弹出PopupWindow
            int width = mEtChose.getWidth();//PopupWindow宽度和编辑框一致
            int height = DensityUtils.dp2px(this, 180);
            mPopupWindow1 = new PopupWindow(width, height);
        }
        View view = View.inflate(this, R.layout.user_pop_window, null);
        lv_name = (ListView) view.findViewById(R.id.lv);
        getUserData();
        lv_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xjName = allUsersContent.get(i).getLogin_name();
                mEtChose.setText(allUsersContent.get(i).getNick_name());
                mPopupWindow1.dismiss();
                if (mRbday.isChecked()) {
                    getDayReport(xjName);
                }
                if (mRbWeek.isChecked()) {
                    getWeekReport(xjName);

                }

            }
        });
        //设置PopupWindow里面的View
        mPopupWindow1.setContentView(view);


        //让PopupWindow能够消失
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow1.showAsDropDown(mEtChose);
    }

    private void getUserData() {
        Observable<AllUsers> users = ApiWrapper.getInstance().getAllUsers(mToken);
        addSubscription(users, new Subscriber<AllUsers>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AllUsers allUsers) {
                allUsersContent = allUsers.getContent();
                if (allUsersContent.size() != 0) {
                    lv_name.setAdapter(new AllUserAdapter(allUsersContent, getApplicationContext()));
                } else {
                    Toast.makeText(getApplicationContext(), "未能查询到巡检人员", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
