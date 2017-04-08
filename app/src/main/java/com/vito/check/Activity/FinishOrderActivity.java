package com.vito.check.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.vito.check.util.SpUtils;

import java.io.File;
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
        if (mOrderBean != null) {
            tvDeviceNo.setText(mOrderBean.getId() + "");
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
            Log.i("tangj", "onActivityResult: " + photos);
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        String id = tvDeviceNo.getText().toString();
        String process = tvProcess.getText().toString();
        if (selectedPhotos.size() == 0) {
            Toast.makeText(getApplicationContext(), "长传图片或处理详情不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(selectedPhotos.get(0));
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", mToken)
                .addFormDataPart("id", id)
                .addFormDataPart("processInfo", process)
                .addFormDataPart("doImage", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        mLlProgress.setVisibility(View.VISIBLE);
        Observable<SendOrder> sendOrderObservable = ApiWrapper.getInstance().finishEngineerOrder(requestBody);
        addSubscription(sendOrderObservable, new Subscriber<SendOrder>() {
            @Override
            public void onCompleted() {
                mLlProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

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
}
