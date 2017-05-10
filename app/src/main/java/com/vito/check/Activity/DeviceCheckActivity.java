package com.vito.check.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.Adapter.PhotoAdapter;
import com.vito.check.Adapter.RecyclerItemClickListener;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.SendOrder;
import com.vito.check.util.ImageToBase64;
import com.vito.check.util.SpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/10.
 */

public class DeviceCheckActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.login_btn)
    Button mBtn;
    @BindView(R.id.deviceno)
    EditText mDeviceno;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.tv_safe)
    TextView mTvSafe;
    @BindView(R.id.tv_shineiwai)
    TextView mTvShineiwai;
    @BindView(R.id.shifouyouyupeng)
    TextView mShifouyouyupeng;
    @BindView(R.id.xujiaxupeng)
    TextView mXujiaxupeng;
    @BindView(R.id.caozuoxitong)
    TextView mCaozuoxitong;
    @BindView(R.id.neicun)
    TextView mNeicun;
    @BindView(R.id.zhijia)
    TextView mZhijia;
    @BindView(R.id.waixing)
    TextView mWaixing;
    @BindView(R.id.xianshiping)
    TextView mXianshiping;
    @BindView(R.id.chumoping)
    TextView mChumoping;
    @BindView(R.id.wangluo)
    TextView mWangluo;
    @BindView(R.id.gengxin)
    TextView mGengxin;
    @BindView(R.id.banbenhao)
    EditText mBanben;
    @BindView(R.id.jiaofei)
    TextView mJiaofei;
    @BindView(R.id.feijiechuduka)
    TextView mFeijiechuduka;
    @BindView(R.id.yinlian)
    TextView mYinlian;
    @BindView(R.id.ranqi)
    TextView mRanqi;
    @BindView(R.id.mima)
    TextView mMima;
    @BindView(R.id.dayinji)
    TextView mDayinji;
    @BindView(R.id.shibiqi)
    TextView mShibiqi;
    @BindView(R.id.ll_xunjianjieguo)
    LinearLayout mLlXunjianjieguo;
    @BindView(R.id.ll_safe)
    LinearLayout mLlSafe;
    @BindView(R.id.ll_shineiwai)
    LinearLayout mLlShineiwai;
    @BindView(R.id.ll_shifouyouxupeng)
    LinearLayout mLlShifouyouxupeng;
    @BindView(R.id.ll_xujiaxupeng)
    LinearLayout mLlXujiaxupeng;
    @BindView(R.id.ll_caozuo)
    LinearLayout mLlCaozuo;
    @BindView(R.id.ll_neicun)
    LinearLayout mLlNeicun;
    @BindView(R.id.ll_zhijia)
    LinearLayout mLlZhijia;
    @BindView(R.id.ll_waixing)
    LinearLayout mLlWaixing;
    @BindView(R.id.ll_xianshiping)
    LinearLayout mLlXianshiping;
    @BindView(R.id.ll_chumoping)
    LinearLayout mLlChumoping;
    @BindView(R.id.ll_wangluo)
    LinearLayout mLlWangluo;
    @BindView(R.id.ll_gengxin)
    LinearLayout mLlGengxin;
    @BindView(R.id.ll_banben)
    LinearLayout mLlBanben;
    @BindView(R.id.ll_jiaofei)
    LinearLayout mLlJiaofei;
    @BindView(R.id.ll_feijiechouduka)
    LinearLayout mLlFeijiechouduka;
    @BindView(R.id.ll_yinlian)
    LinearLayout mLlYinlian;
    @BindView(R.id.ll_ranqi)
    LinearLayout mLlRanqi;
    @BindView(R.id.ll_mima)
    LinearLayout mLlMima;
    @BindView(R.id.ll_dayinji)
    LinearLayout mLlDayinji;
    @BindView(R.id.ll_shibiqi)
    LinearLayout mLlShibiqi;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    private String mToken;
    private PopupWindow mPopupWindow = new PopupWindow(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private int shifou = 0;
    private int isok = 0;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private String mS1;
    private String mS2;
    private String mImage;
    private File mFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("日常巡检", true);
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
        mBtn.setOnClickListener(this);
        initData();
        initRecycleView();
//        <!--android:focusable="true"-->
//        <!--android:focusableInTouchMode="true"-->
    }
    private void initRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(DeviceCheckActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(DeviceCheckActivity.this);
                        }
                    }
                }));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }


    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    load();
                    break;
            }
        }
    };

    private void load() {
        String path = mFile.getAbsolutePath();
        String mName = path.substring(path.length() - 4, path.length());
        Log.d("junjunsb",mImage+mName);
        Observable<SendOrder> sendOrderObservable = ApiWrapper.getInstance().addDailyCheck(mToken, mS1, mTvResult.getText().toString(), mTvShineiwai.getText().toString(), mTvSafe.getText().toString(), mZhijia.getText().toString(), mShifouyouyupeng.getText().toString()
                , mXujiaxupeng.getText().toString(), mCaozuoxitong.getText().toString(), mNeicun.getText().toString(), mWaixing.getText().toString(), mXianshiping.getText().toString(), mChumoping.getText().toString()
                , mWangluo.getText().toString(), mGengxin.getText().toString(), mS2, mJiaofei.getText().toString(), mYinlian.getText().toString(), mFeijiechuduka.getText().toString(), mRanqi.getText().toString(), mDayinji.getText().toString()
                , mMima.getText().toString(), mShibiqi.getText().toString(),mImage+mName);
        addSubscription(sendOrderObservable, new Subscriber<SendOrder>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onNext(SendOrder sendOrder) {
                if(sendOrder.isSuccess()){
                    Toast.makeText(getApplicationContext(),"提交成功",Toast.LENGTH_LONG).show();
                    exit();
                }else{
                    Toast.makeText(getApplicationContext(),"提交失败",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initData() {
        String ll_xunjianjieguo = SpUtils.getString(getApplicationContext(), "ll_xunjianjieguo", "");
        String ll_shineiwai = SpUtils.getString(getApplicationContext(), "ll_shineiwai", "");
        String ll_safe = SpUtils.getString(getApplicationContext(), "ll_safe", "");
        String ll_zhijia = SpUtils.getString(getApplicationContext(), "ll_zhijia", "");
        String ll_shifouyouxupeng = SpUtils.getString(getApplicationContext(), "ll_shifouyouxupeng", "");
        String ll_xujiaxupeng = SpUtils.getString(getApplicationContext(), "ll_xujiaxupeng", "");
        String ll_caozuo = SpUtils.getString(getApplicationContext(), "ll_caozuo", "");
        String ll_neicun = SpUtils.getString(getApplicationContext(), "ll_neicun", "");
        String ll_waixing = SpUtils.getString(getApplicationContext(), "ll_waixing", "");
        String ll_xianshiping = SpUtils.getString(getApplicationContext(), "ll_xianshiping", "");
        String ll_chumoping = SpUtils.getString(getApplicationContext(), "ll_chumoping", "");
        String ll_yinlian = SpUtils.getString(getApplicationContext(), "ll_yinlian", "");
        String ll_feijiechouduka = SpUtils.getString(getApplicationContext(), "ll_feijiechouduka", "");
        String ll_ranqi = SpUtils.getString(getApplicationContext(), "ll_ranqi", "");
        String ll_dayinji = SpUtils.getString(getApplicationContext(), "ll_dayinji", "");
        String ll_mima = SpUtils.getString(getApplicationContext(), "ll_mima", "");
        String ll_wangluo = SpUtils.getString(getApplicationContext(), "ll_wangluo", "");
        String ll_gengxin = SpUtils.getString(getApplicationContext(), "ll_gengxin", "");
        String ll_jiaofei = SpUtils.getString(getApplicationContext(), "ll_jiaofei", "");
        String ll_shibiqi = SpUtils.getString(getApplicationContext(), "ll_shibiqi", "");
        if(!TextUtils.isEmpty(ll_xunjianjieguo)){
            mTvResult.setText(ll_xunjianjieguo);
        }
        if(!TextUtils.isEmpty(ll_shineiwai)){
            mTvShineiwai.setText(ll_shineiwai);
        }
        if(!TextUtils.isEmpty(ll_safe)){
            mTvSafe.setText(ll_safe);
        }
        if(!TextUtils.isEmpty(ll_zhijia)){
            mZhijia.setText(ll_zhijia);
        }
        if(!TextUtils.isEmpty(ll_shifouyouxupeng)){
            mShifouyouyupeng.setText(ll_shifouyouxupeng);
        }
        if(!TextUtils.isEmpty(ll_xujiaxupeng)){
            mXujiaxupeng.setText(ll_xujiaxupeng);
        }

        if(!TextUtils.isEmpty(ll_caozuo)){
            mCaozuoxitong.setText(ll_caozuo);
        }
        if(!TextUtils.isEmpty(ll_neicun)){
            mNeicun.setText(ll_neicun);
        }

        if(!TextUtils.isEmpty(ll_waixing)){
            mWaixing.setText(ll_waixing);
        }
        if(!TextUtils.isEmpty(ll_xianshiping)){
            mXianshiping.setText(ll_xianshiping);
        }
        if(!TextUtils.isEmpty(ll_chumoping)){
            mChumoping.setText(ll_chumoping);
        }
        if(!TextUtils.isEmpty(ll_yinlian)){
            mYinlian.setText(ll_yinlian);
        }
        if(!TextUtils.isEmpty(ll_feijiechouduka)){
            mFeijiechuduka.setText(ll_feijiechouduka);
        }
        if(!TextUtils.isEmpty(ll_ranqi)){
            mRanqi.setText(ll_ranqi);
        }
        if(!TextUtils.isEmpty(ll_dayinji)){
            mDayinji.setText(ll_dayinji);
        }
        if(!TextUtils.isEmpty(ll_mima)){
            mMima.setText(ll_mima);
        }
        if(!TextUtils.isEmpty(ll_wangluo)){
            mWangluo.setText(ll_wangluo);
        }
        if(!TextUtils.isEmpty(ll_gengxin)){
            mGengxin.setText(ll_gengxin);
        }

        if(!TextUtils.isEmpty(ll_jiaofei)){
            mJiaofei.setText(ll_jiaofei);
        }
        if(!TextUtils.isEmpty(ll_shibiqi)){
            mShibiqi.setText(ll_shibiqi);
        }


    }

    @Override
    public int getLayout() {
        return R.layout.activity_devicecheck;
    }

    @OnClick({R.id.login_btn, R.id.ll_banben, R.id.ll_caozuo, R.id.ll_chumoping, R.id.ll_dayinji, R.id.ll_feijiechouduka, R.id.ll_gengxin
            , R.id.ll_jiaofei, R.id.ll_neicun, R.id.ll_mima, R.id.ll_ranqi, R.id.ll_safe, R.id.ll_shibiqi, R.id.ll_shifouyouxupeng
            , R.id.ll_shineiwai, R.id.ll_waixing, R.id.ll_wangluo, R.id.ll_xianshiping, R.id.ll_xujiaxupeng, R.id.ll_yinlian
            , R.id.ll_zhijia, R.id.ll_xunjianjieguo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                mS1 = mDeviceno.getText().toString();
                mS2 = mBanben.getText().toString();
                if (mS1.equals("") || mTvResult.getText().equals("请选择(必填)") || mTvSafe.getText().equals("请选择(必填)") || mTvShineiwai.getText().equals("请选择(必填)")
                        || mShifouyouyupeng.getText().equals("请选择(必填)") || mXujiaxupeng.getText().equals("请选择(必填)") || mCaozuoxitong.getText().equals("请选择(必填)") || mNeicun.getText().equals("请选择(必填)")
                        || mZhijia.getText().equals("请选择(必填)") || mWaixing.getText().equals("请选择(必填)") || mXianshiping.getText().equals("请选择(必填)") || mChumoping.getText().equals("请选择(必填)")
                        || mWangluo.getText().equals("请选择(必填)") || mGengxin.getText().equals("请选择(必填)") || mJiaofei.getText().equals("请选择(必填)") || mFeijiechuduka.getText().equals("请选择(必填)")
                        || mYinlian.getText().equals("请选择(必填)") || mRanqi.getText().equals("请选择(必填)") || mMima.getText().equals("请选择(必填)") || mDayinji.getText().equals("请选择(必填)")
                        || mShibiqi.getText().equals("请选择(必填)")) {
                    Toast.makeText(getApplicationContext(), "填写项不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                mLlProgress.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (selectedPhotos.size() != 0) {
                            mFile = new File(selectedPhotos.get(0));
                            Log.d("image", mFile.length() / 1024 + "");
                            long l = System.currentTimeMillis();
                            mImage = ImageToBase64.bitmaptoString( decodeSampledBitmapFromResource(selectedPhotos.get(0),300,500));
                            long l1 = System.currentTimeMillis();
                            Log.d("image", l - l1 + "");
                            mHandler.sendEmptyMessage(1);
                        }

                    }
                }).start();
                break;
            case R.id.ll_xunjianjieguo:
                showPop1();
                break;
            case R.id.xunjianzhengchang:
                mTvResult.setText("设备正常");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_xunjianjieguo","设备正常");
                break;
            case R.id.yichuli:
                mTvResult.setText("故障已处理");
                SpUtils.putString(getApplicationContext(),"ll_xunjianjieguo","故障已处理");
                mPopupWindow.dismiss();
                break;
            case R.id.weichuli:
                mTvResult.setText("故障未处理");
                SpUtils.putString(getApplicationContext(),"ll_xunjianjieguo","故障未处理");
                mPopupWindow.dismiss();
                break;
            case R.id.xietiao:
                mTvResult.setText("需市场部协调");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_xunjianjieguo","需市场部协调");
                break;

            case R.id.ll_shineiwai:
                showPop2();
                break;
            case R.id.shinei:
                mTvShineiwai.setText("室内");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_shineiwai","室内");
                break;
            case R.id.shiwai:
                mTvShineiwai.setText("室外");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_shineiwai","室外");
                break;

            case R.id.ll_safe:
                showPop3();
                break;
            case R.id.anquan:
                mTvSafe.setText("安全");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_safe","安全");
                break;
            case R.id.yiban:
                mTvSafe.setText("一般");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_safe","一般");
                break;
            case R.id.weixian:
                mTvSafe.setText("危险");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_safe","危险");
                break;
            case R.id.gaowei:
                mTvSafe.setText("高危");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_safe","高危");
                break;

            case R.id.ll_zhijia:
                showPop4();
                shifou = 1;
                break;
            case R.id.ll_shifouyouxupeng:
                showPop4();
                shifou = 2;
                break;
            case R.id.ll_xujiaxupeng:
                showPop4();
                shifou = 3;
                break;
            case R.id.shi:
                if (shifou == 1) {
                    mZhijia.setText("是");
                    SpUtils.putString(getApplicationContext(),"ll_zhijia","是");
                }
                if (shifou == 2) {
                    mShifouyouyupeng.setText("是");
                    SpUtils.putString(getApplicationContext(),"ll_shifouyouxupeng","是");
                }
                if (shifou == 3) {
                    mXujiaxupeng.setText("是");
                    SpUtils.putString(getApplicationContext(),"ll_xujiaxupeng","是");
                }
                mPopupWindow.dismiss();
                break;
            case R.id.fou:
                if (shifou == 1) {
                    mZhijia.setText("否");
                    SpUtils.putString(getApplicationContext(),"ll_zhijia","否");
                }
                if (shifou == 2) {
                    mShifouyouyupeng.setText("否");
                    SpUtils.putString(getApplicationContext(),"ll_shifouyouxupeng","否");
                }
                if (shifou == 3) {
                    mXujiaxupeng.setText("否");
                    SpUtils.putString(getApplicationContext(),"ll_xujiaxupeng","否");
                }
                mPopupWindow.dismiss();
                break;


            case R.id.ll_caozuo:
                showPop5();
                break;
            case R.id.wei32:
                mCaozuoxitong.setText("32位Windows7");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_caozuo","32位Windows7");
                break;
            case R.id.wei64:
                mCaozuoxitong.setText("64位Windows7");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_caozuo","64位Windows7");
                break;
            case R.id.android:
                mCaozuoxitong.setText("安卓");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_caozuo","安卓");
                break;

            case R.id.ll_neicun:
                showPop6();
                break;
            case R.id.g2:
                mNeicun.setText("2G");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_neicun","2G");
                break;
            case R.id.g4:
                mNeicun.setText("4G");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_neicun","4G");
                break;


            case R.id.ll_waixing:
                showPop7();
                break;
            case R.id.waixingzhengchang:
                mWaixing.setText("正常");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_waixing","正常");
                break;
            case R.id.posun:
                mWaixing.setText("破损");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_waixing","破损");
                break;


            case R.id.ll_xianshiping:
                showPop8();
                isok = 1;
                break;
            case R.id.ll_chumoping:
                showPop8();
                isok = 2;
                break;
            case R.id.ll_yinlian:
                showPop8();
                isok = 3;
                break;
            case R.id.ll_feijiechouduka:
                showPop8();
                isok = 4;
                break;
            case R.id.ll_ranqi:
                showPop8();
                isok = 5;
                break;
            case R.id.ll_dayinji:
                showPop8();
                isok = 6;
                break;
            case R.id.zhengchang:
                switch (isok) {
                    case 1:
                        mXianshiping.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_xianshiping","正常");
                        break;
                    case 2:
                        mChumoping.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_chumoping","正常");
                        break;
                    case 3:
                        mYinlian.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_yinlian","正常");
                        break;
                    case 4:
                        mFeijiechuduka.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_feijiechouduka","正常");
                        break;
                    case 5:
                        mRanqi.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_ranqi","正常");
                        break;
                    case 6:
                        mDayinji.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_dayinji","正常");
                        break;
                    case 7:
                        mMima.setText("正常");
                        SpUtils.putString(getApplicationContext(),"ll_mima","正常");
                        break;
                }
                mPopupWindow.dismiss();
                break;
            case R.id.guzhang:
                switch (isok) {
                    case 1:
                        mXianshiping.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_xianshiping","故障");
                        break;
                    case 2:
                        mChumoping.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_chumoping","故障");
                        break;
                    case 3:
                        mYinlian.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_yinlian","故障");
                        break;
                    case 4:
                        mFeijiechuduka.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_feijiechouduka","故障");
                        break;
                    case 5:
                        mRanqi.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_ranqi","故障");
                        break;
                    case 6:
                        mDayinji.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_dayinji","故障");
                        break;
                    case 7:
                        mMima.setText("故障");
                        SpUtils.putString(getApplicationContext(),"ll_mima","故障");
                        break;
                }
                mPopupWindow.dismiss();
                break;

            case R.id.ll_wangluo:
                showPop9();
                break;
            case R.id.wangluozhengchang:
                mWangluo.setText("正常");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_wangluo","正常");
                break;
            case R.id.shujukaguzhang:
                mWangluo.setText("数据卡故障");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_wangluo","数据卡故障");
                break;
            case R.id.luyouqi:
                mWangluo.setText("路由器故障");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_wangluo","路由器故障");
                break;


            case R.id.ll_gengxin:
                showPop10();
                break;
            case R.id.zidonggengxin:
                mGengxin.setText("自动更新");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_gengxin","自动更新");
                break;
            case R.id.shoudonggengxin:
                mGengxin.setText("手动更新");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_gengxin","手动更新");
                break;
            case R.id.shibai:
                mGengxin.setText("更新失败");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_gengxin","更新失败");
                break;

            case R.id.ll_jiaofei:
                showPop11();
                break;
            case R.id.jiaofeichenggong:
                mJiaofei.setText("缴费成功");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_jiaofei","缴费成功");
                break;
            case R.id.jiaofeishibai:
                mJiaofei.setText("缴费失败");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_jiaofei","缴费失败");
                break;
            case R.id.weiceshi:
                mJiaofei.setText("未测试");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_jiaofei","未测试");
                break;

            case R.id.ll_mima:
                showPop8();
                isok = 7;
                break;
            case R.id.ll_shibiqi:
                showPop12();
                break;
            case R.id.shibiqizhengchang:
                mShibiqi.setText("正常");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_shibiqi","正常");
                break;
            case R.id.shibiqiguzhang:
                mShibiqi.setText("故障");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_shibiqi","故障");
                break;
            case R.id.weianzhuang:
                mShibiqi.setText("未安装");
                mPopupWindow.dismiss();
                SpUtils.putString(getApplicationContext(),"ll_shibiqi","未安装");
                break;

        }

    }

    private void showPop12() {
        View view = View.inflate(getApplicationContext(), R.layout.shibiqi_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.shibiqizhengchang);
        TextView tv2 = (TextView) view.findViewById(R.id.shibiqiguzhang);
        TextView tv3 = (TextView) view.findViewById(R.id.weianzhuang);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        showLocation();
    }

    private void showPop11() {
        View view = View.inflate(getApplicationContext(), R.layout.jiaofei_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.jiaofeichenggong);
        TextView tv2 = (TextView) view.findViewById(R.id.jiaofeishibai);
        TextView tv3 = (TextView) view.findViewById(R.id.weiceshi);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        showLocation();

    }

    private void showPop10() {
        View view = View.inflate(getApplicationContext(), R.layout.gengxin_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.zidonggengxin);
        TextView tv2 = (TextView) view.findViewById(R.id.shoudonggengxin);
        TextView tv3 = (TextView) view.findViewById(R.id.shibai);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        showLocation();
    }

    private void showPop9() {
        View view = View.inflate(getApplicationContext(), R.layout.wangluo_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.wangluozhengchang);
        TextView tv2 = (TextView) view.findViewById(R.id.shujukaguzhang);
        TextView tv3 = (TextView) view.findViewById(R.id.luyouqi);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        showLocation();

    }

    private void showPop8() {
        View view = View.inflate(getApplicationContext(), R.layout.zhengchangguzhang_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.zhengchang);
        TextView tv2 = (TextView) view.findViewById(R.id.guzhang);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        showLocation();

    }

    private void showPop7() {
        View view = View.inflate(getApplicationContext(), R.layout.waixing_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.waixingzhengchang);
        TextView tv2 = (TextView) view.findViewById(R.id.posun);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        showLocation();

    }

    private void showPop6() {
        View view = View.inflate(getApplicationContext(), R.layout.neicun_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.g2);
        TextView tv2 = (TextView) view.findViewById(R.id.g4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        showLocation();

    }

    private void showPop5() {
        View view = View.inflate(getApplicationContext(), R.layout.caozuo_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.wei32);
        TextView tv2 = (TextView) view.findViewById(R.id.wei64);
        TextView tv3 = (TextView) view.findViewById(R.id.android);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        showLocation();

    }

    private void showPop4() {
        View view = View.inflate(getApplicationContext(), R.layout.shifou_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.shi);
        TextView tv2 = (TextView) view.findViewById(R.id.fou);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        showLocation();
    }

    private void showPop3() {
        View view = View.inflate(getApplicationContext(), R.layout.anquandengji_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.anquan);
        TextView tv2 = (TextView) view.findViewById(R.id.yiban);
        TextView tv3 = (TextView) view.findViewById(R.id.weixian);
        TextView tv4 = (TextView) view.findViewById(R.id.gaowei);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        showLocation();
    }

    private void showPop2() {
        View view = View.inflate(getApplicationContext(), R.layout.shineiwai_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.shinei);
        TextView tv2 = (TextView) view.findViewById(R.id.shiwai);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        showLocation();
    }

    private void showPop1() {
        View view = View.inflate(getApplicationContext(), R.layout.xunjianjieguo_pop, null);
        //设置PopupWindow里面的View
        mPopupWindow.setContentView(view);
        TextView tv1 = (TextView) view.findViewById(R.id.xunjianzhengchang);
        TextView tv2 = (TextView) view.findViewById(R.id.yichuli);
        TextView tv3 = (TextView) view.findViewById(R.id.weichuli);
        TextView tv4 = (TextView) view.findViewById(R.id.xietiao);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        showLocation();
    }

    private void showLocation() {
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //弹出mPopupWindow, 在mEdit下显示
        mPopupWindow.showAtLocation(DeviceCheckActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片  
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度  
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height>reqHeight||width>reqWidth){
            // 计算出实际宽高和目标宽高的比率  
            final int heightRatio=Math.round((float)height/(float)reqHeight);
            final int widthRatio=Math.round((float)width/(float)reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
            // 一定都会大于等于目标的宽和高。  
            inSampleSize=heightRatio<widthRatio?heightRatio:widthRatio;
        }
        return inSampleSize;
    }
}
