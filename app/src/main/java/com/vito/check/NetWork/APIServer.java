package com.vito.check.NetWork;

import com.vito.check.bean.Device;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.Regist;
import com.vito.check.bean.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xk on 2017/3/14.
 */

public interface APIServer {

    //登录
    @POST("login.do")
    @FormUrlEncoded
    Observable<User> UserLogin(@Field("userName") String username, @Field("password") String pwd);

    //注册
    @POST("register.do")
    @FormUrlEncoded
    Observable<Regist> UserRegist(@Field("userName") String username, @Field("nickName") String nickName,
                            @Field("password") String pwd, @Field("role") String role);

    //获取在线离线设备
    @GET("getDevInfo.do")
    Observable<Device> getDevices(@Query("token") String token,@Query("isOnline") String isOnline);

    //获取全部设备
    @GET("getDevInfo.do")
    Observable<Device> getAllDevices(@Query("token")String token);

    //获取是否巡检设备
    @GET("getDevInfo.do")
    Observable<Device> getCheckedDevices(@Query("token") String token,@Query("isChecked") String isChecked);

    //查询自己的派单信息
    @GET("getMyDispatch.do")
    Observable<MyOrder> getMyOrders(@Query("token") String token, @Query("state") String state);
}
