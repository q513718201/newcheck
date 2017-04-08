package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/4/5.
 */
public class OnlineRate {


    /**
     * content : [{"owner":"陈浴","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"雍福平","topOnlineRate":"31.76%","nowOnlineRate":"31.76%","devNum":340},{"owner":"蔺伟东","topOnlineRate":"32.62%","nowOnlineRate":"32.62%","devNum":374},{"owner":"王彦锋","topOnlineRate":"29.97%","nowOnlineRate":"29.97%","devNum":357},{"owner":"梁耀安","topOnlineRate":"28.10%","nowOnlineRate":"28.10%","devNum":420},{"owner":"陈智俊","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"赵生录","topOnlineRate":"40.21%","nowOnlineRate":"40.21%","devNum":378},{"owner":"刘洋","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"陈轩","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"史豪杰","topOnlineRate":"34.85%","nowOnlineRate":"34.85%","devNum":264},{"owner":"赵国锋","topOnlineRate":"32.59%","nowOnlineRate":"32.59%","devNum":270},{"owner":"陈海龙","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"柴长进","topOnlineRate":"11.96%","nowOnlineRate":"11.96%","devNum":92},{"owner":"刘兵","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"李建强","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"孙建刚","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0},{"owner":"林耀强","topOnlineRate":"29.19%","nowOnlineRate":"29.19%","devNum":322},{"owner":"陈沐","topOnlineRate":"无数据","nowOnlineRate":"无数据","devNum":0}]
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

    public static class ContentBean {
        @Override
        public String toString() {
            return "ContentBean{" +
                    "owner='" + owner + '\'' +
                    ", topOnlineRate='" + topOnlineRate + '\'' +
                    ", nowOnlineRate='" + nowOnlineRate + '\'' +
                    ", devNum=" + devNum +
                    '}';
        }

        /**
         * owner : 陈浴
         * topOnlineRate : 无数据
         * nowOnlineRate : 无数据
         * devNum : 0
         */

        private String owner;
        private String topOnlineRate;
        private String nowOnlineRate;
        private int devNum;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getTopOnlineRate() {
            return topOnlineRate;
        }

        public void setTopOnlineRate(String topOnlineRate) {
            this.topOnlineRate = topOnlineRate;
        }

        public String getNowOnlineRate() {
            return nowOnlineRate;
        }

        public void setNowOnlineRate(String nowOnlineRate) {
            this.nowOnlineRate = nowOnlineRate;
        }

        public int getDevNum() {
            return devNum;
        }

        public void setDevNum(int devNum) {
            this.devNum = devNum;
        }
    }
}
