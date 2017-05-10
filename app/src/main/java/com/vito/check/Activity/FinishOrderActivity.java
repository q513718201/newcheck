package com.vito.check.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vito.check.Adapter.PhotoAdapter;
import com.vito.check.Adapter.RecyclerItemClickListener;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.SendOrder;
import com.vito.check.bean.YunyingOrder;
import com.vito.check.util.ImageToBase64;
import com.vito.check.util.SpUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by admin on 2017/4/5.
 */
public class FinishOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_deviceNo)
    EditText tvDeviceNo;
    @BindView(R.id.tv_process)
    EditText tvProcess;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private String mToken;
    private MyOrder.ContentBean mOrderBean;
    private YunyingOrder.ContentBean mYunyingBean;
    private String mImage;
    private File mFile;
    private String mId;
    private String mProcess;
    private boolean isLoadFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("完成派单", true);
        init();
        initRecycleView();
        loginBtn.setOnClickListener(this);
    }

    private void init() {
        mOrderBean = (MyOrder.ContentBean) getIntent().getSerializableExtra("orderBean");
        mYunyingBean = (YunyingOrder.ContentBean) getIntent().getSerializableExtra("yunyingBean");

        if (mOrderBean != null) {
            tvDeviceNo.setText(mOrderBean.getId() + "");
        }
        if (mYunyingBean != null) {
            tvDeviceNo.setText(mYunyingBean.getId() + "");
        }
        mToken = SpUtils.getString(getApplicationContext(), "token", "");
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
                                    .start(FinishOrderActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(FinishOrderActivity.this);
                        }
                    }
                }));

    }

    @Override
    public int getLayout() {
        return R.layout.activity_finishorder;

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

    @Override
    public void onClick(View view) {

        mId = tvDeviceNo.getText().toString();
        mProcess = tvProcess.getText().toString();
        if (selectedPhotos.size() == 0|| TextUtils.isEmpty(mProcess)) {
            Toast.makeText(getApplicationContext(), "长传图片或处理详情不能为空", Toast.LENGTH_SHORT).show();
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

//        if (mImage == null) {
//            Log.d("aaa","我来了");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    SystemClock.sleep(2000);
//                    if(mImage!=null){
//                        Log.d("aaa","我要演示来了");
//                        mHandler.sendEmptyMessage(0);
//                    }
//
//                }
//            }).start();
//
//        } else {
//            load();
//        }
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


    private void load() {
        String path = mFile.getAbsolutePath();
        String mName = path.substring(path.length() - 4, path.length());
        Log.d("image", mFile.getAbsolutePath());
        Log.d("image", mName);
        Log.d("image", mImage);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", mToken)
                .addFormDataPart("id", mId)
                .addFormDataPart("processInfo", mProcess)
                .addFormDataPart("doImage", mImage + mName)
                .build();
        if (mYunyingBean != null) {
            Observable<SendOrder> sendOrderObservable = ApiWrapper.getInstance().finishyunyingOrder(requestBody);
            addSubscription(sendOrderObservable, new Subscriber<SendOrder>() {
                @Override
                public void onCompleted() {
                    mLlProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable e) {
                    mLlProgress.setVisibility(View.GONE);
                }

                @Override
                public void onNext(SendOrder sendOrder) {
                    if (sendOrder.isSuccess()) {
                        Toast.makeText(getApplicationContext(), sendOrder.getContent(), Toast.LENGTH_SHORT).show();
                        exit();
                    } else {
                        Toast.makeText(getApplicationContext(), sendOrder.getContent(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
//        if (mOrderBean != null) {
//            Observable<SendOrder> sendOrderObservable = ApiWrapper.getInstance().finishEngineerOrder(requestBody);
//            addSubscription(sendOrderObservable, new Subscriber<SendOrder>() {
//                @Override
//                public void onCompleted() {
//                    mLlProgress.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(SendOrder sendOrder) {
//                    if (sendOrder.isSuccess()) {
//                        Toast.makeText(getApplicationContext(), sendOrder.getContent(), Toast.LENGTH_SHORT).show();
//                        exit();
//                    } else {
//                        Toast.makeText(getApplicationContext(), sendOrder.getContent(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
    }
}
