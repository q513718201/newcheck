package com.vito.check.NetWork;

import com.vito.check.bean.AddressModify;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.DayReport;
import com.vito.check.bean.Device;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.OnlineRate;
import com.vito.check.bean.Regist;
import com.vito.check.bean.SendOrder;
import com.vito.check.bean.User;
import com.vito.check.bean.WeekReport;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Observable<Device> getDevices(@Query("token") String token, @Query("isOnline") String isOnline, @Query("xjName") String xjName, @Query("devNo") String devNo);

    //获取全部设备
    @GET("getDevInfo.do")
    Observable<Device> getAllDevices(@Query("token") String token, @Query("xjName") String xjName, @Query("devNo") String devNo);

    //获取是否巡检设备
    @GET("getDevInfo.do")
    Observable<Device> getCheckedDevices(@Query("token") String token, @Query("isChecked") String isChecked, @Query("xjName") String xjName, @Query("devNo") String devNo);

    //获取派单设备
    @GET("getDispatchDevInfos.do")
    Observable<Device> getOrderDevices(@Query("token") String token);

    //查询自己的派单信息
    @GET("findMyDispatch.do")
    Observable<MyOrder> getMyOrders(@Query("token") String token, @Query("day") int day, @Query("state") String state);


    //查询所有巡检人员信息
    @GET("getUser.do")
    Observable<AllUsers> getAllUsers(@Query("token") String token);


    //工程部派单
    @POST("startDailyCheckFromApp.do")
    @FormUrlEncoded
    Observable<SendOrder> sendOrder(@Field("token") String token, @Field("devNo") String devNo,
                                    @Field("description") String description, @Field("address") String address,
                                    @Field("phone") String phone, @Field("sendTo") String sendTo);

    //完成工程部派单
    @POST("finishDailyCheckEngineer.do")
    Observable<SendOrder> finishEngineerOrder(@Body RequestBody Body);


    //完成运营部派单
    @POST("finishDispatch.do")
    @Multipart
    Call<String> finishyunyingOrder(@Part("token") String token, @Part("id") String id,
                                    @Part("processInfo") String processInfo, @PartMap Map<String, RequestBody> params);


    //查看在线率
    @GET("getOnlineRate.do")
    Observable<OnlineRate> getOnlineRate(@Query("token") String token, @Query("xjName") String xjName);

    //日报表查询
    @GET("getTodayReport.do")
    Observable<DayReport> getDayReport(@Query("token") String token, @Query("xjName") String xjName);

    //周报表查询
    @GET("getWeekReport.do")
    Observable<WeekReport> getWeekReport(@Query("token") String token, @Query("xjName") String xjName);

    //修改设备地址
    @GET("updateDevPosition.do")
    Observable<AddressModify> addressModify(@Query("token") String token, @Query("devNo") String devNo,
                                            @Query("lat") double lat, @Query("lng") double lng,
                                            @Query("address") String address);

    //日常巡检上传
    @POST("addDailyCheck.do")
    @FormUrlEncoded
    Observable<SendOrder> addDailyCheck(@Field("token") String token, @Field("devNo") String devNo,
                                        @Field("safeLeve") String safeLeve, @Field("inoutDoor") String inoutDoor,
                                        @Field("awning") String awning, @Field("outLook") String outLook,
                                        @Field("screen") String screen, @Field("touchScreen") String touchScreen,
                                        @Field("netStat") String netStat, @Field("updateResult") String updateResult,
                                        @Field("softwareVersion") String softwareVersion, @Field("payResult") String payResult,
                                        @Field("nonCardReader") String nonCardReader, @Field("unionPayCardReader") String unionPayCardReader,
                                        @Field("gasCardReader") String gasCardReader, @Field("keyPad") String keyPad,
                                        @Field("printer") String printer, @Field("coinReader") String coinReader);
}
