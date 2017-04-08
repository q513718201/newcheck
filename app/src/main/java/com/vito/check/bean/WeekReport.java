package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xk on 2017/3/29.
 */

public class WeekReport {

    /**
     * content : [{"id":null,"nickName":"陈浴","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"雍福平","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"31.76%"},{"id":null,"nickName":"蔺伟东","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"32.62%"},{"id":null,"nickName":"王彦锋","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"29.97%"},{"id":null,"nickName":"梁耀安","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"28.10%"},{"id":null,"nickName":"陈智俊","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"赵生录","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"40.21%"},{"id":null,"nickName":"刘洋","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"陈轩","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"史豪杰","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"34.85%"},{"id":null,"nickName":"赵国锋","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"32.59%"},{"id":null,"nickName":"陈海龙","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"柴长进","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"11.96%"},{"id":null,"nickName":"刘兵","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"李建强","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"孙建刚","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"},{"id":null,"nickName":"林耀强","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"29.19%"},{"id":null,"nickName":"陈沐","xjNumWeek":0,"dispatchNumWeek":0,"processNumWeek":0,"topOnlineRate":"0.00"}]
     * success : true
     */

    private boolean success;
    private List<ContentBean> content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        /**
         * id : null
         * nickName : 陈浴
         * xjNumWeek : 0
         * dispatchNumWeek : 0
         * processNumWeek : 0
         * topOnlineRate : 0.00
         */

        private int id;
        private String nickName;
        private int xjNumWeek;
        private int dispatchNumWeek;
        private int processNumWeek;
        private String topOnlineRate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getXjNumWeek() {
            return xjNumWeek;
        }

        public void setXjNumWeek(int xjNumWeek) {
            this.xjNumWeek = xjNumWeek;
        }

        public int getDispatchNumWeek() {
            return dispatchNumWeek;
        }

        public void setDispatchNumWeek(int dispatchNumWeek) {
            this.dispatchNumWeek = dispatchNumWeek;
        }

        public int getProcessNumWeek() {
            return processNumWeek;
        }

        public void setProcessNumWeek(int processNumWeek) {
            this.processNumWeek = processNumWeek;
        }

        public String getTopOnlineRate() {
            return topOnlineRate;
        }

        public void setTopOnlineRate(String topOnlineRate) {
            this.topOnlineRate = topOnlineRate;
        }
    }
}
