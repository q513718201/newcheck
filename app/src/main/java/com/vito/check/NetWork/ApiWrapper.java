package com.vito.check.NetWork;


import com.vito.check.bean.Device;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.Regist;
import com.vito.check.bean.User;
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
    public Observable<Device> getDevices(String token,String isOnline) {
        return apiServer.getDevices(token,isOnline);
    }

    /**
     * 获取全部设备
     */
    public Observable<Device> getAllDevices(String token) {
        return apiServer.getAllDevices(token);
    }

    /**
     * 获取以巡检设备
     */
    public Observable<Device> getCheckedDevices(String token,String isChecked) {
        return apiServer.getCheckedDevices(token,isChecked);
    }
    /**
     * 派单
     */
    public Observable<MyOrder> getMyOrders(String token, String state) {
        return apiServer.getMyOrders(token,state);
    }


}
