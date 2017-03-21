package com.vito.check.NetWork;


import com.vito.check.Http.HUrls;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtil {

    private static OkHttpClient okHttpClient = null;
    public static APIServer apiServer = null;
    private OkHttpClient.Builder mOkHttpBuilder;
    private Retrofit retrofit;

    public RetrofitUtil() {

        initOkHttp();
        initApi();
    }

    protected void initOkHttp() {
        mOkHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(NetConstans.CONNECTTIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetConstans.READTIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NetConstans.WRITETIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        okHttpClient = mOkHttpBuilder.build();

    }


    protected void initApi() {

        retrofit = new Retrofit.Builder()
                .baseUrl(HUrls.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiServer = retrofit.create(APIServer.class);
    }



}
