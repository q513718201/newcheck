package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */

public class MyOrder {

    /**
     * content : [{"id":6,"devNo":"LZD0010","faultType":"硬件","urgency":"紧急","finishTime":"24","type":"坏了","description":"屏幕碎了","userInfo":"白江伟","devAddress":"兰大","situation":"搞定了","doImage":null,"sendTo":"18393918636","state":"已结单","isChecked":"1","startDate":1489757754000,"finishDate":0}]
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
         * id : 6
         * devNo : LZD0010
         * faultType : 硬件
         * urgency : 紧急
         * finishTime : 24
         * type : 坏了
         * description : 屏幕碎了
         * userInfo : 白江伟
         * devAddress : 兰大
         * situation : 搞定了
         * doImage : null
         * sendTo : 18393918636
         * state : 已结单
         * isChecked : 1
         * startDate : 1489757754000
         * finishDate : 0
         */

        private int id;
        private String devNo;
        private String faultType;
        private String urgency;
        private String finishTime;
        private String type;
        private String description;
        private String userInfo;
        private String devAddress;
        private String situation;

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", devNo='" + devNo + '\'' +
                    ", faultType='" + faultType + '\'' +
                    ", urgency='" + urgency + '\'' +
                    ", finishTime='" + finishTime + '\'' +
                    ", type='" + type + '\'' +
                    ", description='" + description + '\'' +
                    ", userInfo='" + userInfo + '\'' +
                    ", devAddress='" + devAddress + '\'' +
                    ", situation='" + situation + '\'' +
                    ", doImage=" + doImage +
                    ", sendTo='" + sendTo + '\'' +
                    ", state='" + state + '\'' +
                    ", isChecked='" + isChecked + '\'' +
                    ", startDate=" + startDate +
                    ", finishDate=" + finishDate +
                    '}';
        }

        private Object doImage;
        private String sendTo;
        private String state;
        private String isChecked;
        private long startDate;
        private int finishDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDevNo() {
            return devNo;
        }

        public void setDevNo(String devNo) {
            this.devNo = devNo;
        }

        public String getFaultType() {
            return faultType;
        }

        public void setFaultType(String faultType) {
            this.faultType = faultType;
        }

        public String getUrgency() {
            return urgency;
        }

        public void setUrgency(String urgency) {
            this.urgency = urgency;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(String userInfo) {
            this.userInfo = userInfo;
        }

        public String getDevAddress() {
            return devAddress;
        }

        public void setDevAddress(String devAddress) {
            this.devAddress = devAddress;
        }

        public String getSituation() {
            return situation;
        }

        public void setSituation(String situation) {
            this.situation = situation;
        }

        public Object getDoImage() {
            return doImage;
        }

        public void setDoImage(Object doImage) {
            this.doImage = doImage;
        }

        public String getSendTo() {
            return sendTo;
        }

        public void setSendTo(String sendTo) {
            this.sendTo = sendTo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(String isChecked) {
            this.isChecked = isChecked;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public int getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(int finishDate) {
            this.finishDate = finishDate;
        }
    }
}
