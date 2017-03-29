package com.vito.check.NetWork;


import com.vito.check.bean.AllUsers;
import com.vito.check.bean.Device;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.OnlineRate;
import com.vito.check.bean.Regist;
import com.vito.check.bean.SendOrder;
import com.vito.check.bean.User;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by 郑友宏 on 2016/10/9.
 * 使用 map将数据转换成需要的数据类型
 */

public class ApiWrapper extends RetrofitUtil {

    private static ApiWrapper mInstance;
    public static String url;

    private ApiWrapper() {
        super();
    }

    public static ApiWrapper getInstance() {
        if (mInstance == null) {
            synchronized (ApiWrapper.class) {
                if (mInstance == null) {
                    mInstance = new ApiWrapper();
                }
            }
        }
        return mInstance;
    }


    /**
     * 登录
     */
    public Observable<User> login(String userName, String passwrod) {
        return apiServer.UserLogin(userName,passwrod);

    }

    /**
     * 注册
     */
    public Observable<Regist> regist(String username, String nickName, String pwd,String role) {
        return apiServer.UserRegist(username,nickName,pwd,role);
    }

    /**
     * 获取在线或离线设备
     */
    public Observable<Device> getDevices(String token,String isOnline,String xjName, String devNo) {
        return apiServer.getDevices(token,isOnline,xjName,devNo);
    }

    /**
     * 获取全部设备
     */
    public Observable<Device> getAllDevices(String token,String xjName, String devNo) {
        return apiServer.getAllDevices(token,xjName,devNo);
    }

    /**
     * 获取以巡检设备
     */
    public Observable<Device> getCheckedDevices(String token,String isChecked,String xjName, String devNo) {
        return apiServer.getCheckedDevices(token,isChecked,xjName,devNo);
    }
    /**
     * 看看自己的派单
     */
    public Observable<MyOrder> getMyOrders(String token,int day,String state) {
        return apiServer.getMyOrders(token,day,state);
    }

    /**
     * 查询所有巡检人信息
     */
    public Observable<AllUsers> getAllUsers(String token) {
        return apiServer.getAllUsers(token);
    }

    /**
     * 工程部派单
     */

    public Observable<SendOrder> sendOrder(String token, String devNo, String description, String address, String phone, String sendTo) {
        return apiServer.sendOrder(token,devNo,description,address,phone,sendTo);
    }

    /**
     * 完成工程部派单
     */
    public Observable<SendOrder> finishEngineerOrder(RequestBody Body){

        return apiServer.finishEngineerOrder(Body);
    }

    /**
     * 查询在线率
     */
    public Observable<OnlineRate> getOnlineRate(String token,String xjName){
        return apiServer.getOnlineRate(token,xjName);
    }
}
