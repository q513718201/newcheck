package com.vito.check.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vito.check.EventBus.SendMessage;
import com.vito.check.NetWork.ApiWrapper;
import com.vito.check.R;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.Device;
import com.vito.check.util.SpUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xk on 2017/3/24.
 */
public class AllUserActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;
    private String mToken;
    private HomeAdapter mAdapter;
    private List<AllUsers.ContentBean> mContent;

    @Override
    public int getLayout() {
        return R.layout.activity_alluser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("巡检人员信息", true);
        mToken = SpUtils.getString(this, "token", "");
        initData();
    }

    private void initData() {
//        RequestQueue requestQueue = NoHttp.newRequestQueue();
//        Request<String> request = NoHttp.createStringRequest("http://118.89.45.113:20001/test/android/getUser.do", RequestMethod.GET);
//
//        request.add("token", mToken);
//        requestQueue.add(0, request, new SimpleResponseListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                super.onSucceed(what, response);
//                Log.d("wozuile",response.get());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                super.onFailed(what, response);
//                Log.d("wozuile",response.get());
//            }
//        });

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
                mContent = allUsers.getContent();
                mRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mAdapter = new HomeAdapter();
                mRv.setAdapter(mAdapter);

            }
        });
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from
                    (AllUserActivity.this).inflate(R.layout.item_alluser, parent,
                    false);
           final MyViewHolder view = new MyViewHolder(inflate);
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String no= view.tv_no.getText().toString();

                }
            });
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_name.setText("姓名：" + mContent.get(position).getNick_name());
            holder.tv_no.setText("工号：" + mContent.get(position).getLogin_name());

        }

        @Override
        public int getItemCount() {
            return mContent.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_name;
            TextView tv_no;

            public MyViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_no = (TextView) view.findViewById(R.id.tv_no);


            }
        }
    }


}
