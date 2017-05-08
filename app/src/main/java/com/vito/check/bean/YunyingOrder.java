package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/4/9.
 */
public class YunyingOrder {


    /**
     * content : [{"id":3,"devNo":"45","faultType":"硬件","urgency":"紧急","finishTime":"24","type":null,"description":"45","userInfo":"45","devAddress":"45","situation":null,"doImage":null,"sendTo":"xj003","state":"派单中","isChecked":null,"startDate":"2017-04-28 12:22:08","finishDate":null,"processDate":null,"ownerBranch":"运营部","sendToBranch":"工程部","sendToNickName":"蔺伟东","owner":"zhoucy","ownerNickName":"周成彦","phone":"545","transfor":null,"transforNickName":null}]
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
         * id : 3
         * devNo : 45
         * faultType : 硬件
         * urgency : 紧急
         * finishTime : 24
         * type : null
         * description : 45
         * userInfo : 45
         * devAddress : 45
         * situation : null
         * doImage : null
         * sendTo : xj003
         * state : 派单中
         * isChecked : null
         * startDate : 2017-04-28 12:22:08
         * finishDate : null
         * processDate : null
         * ownerBranch : 运营部
         * sendToBranch : 工程部
         * sendToNickName : 蔺伟东
         * owner : zhoucy
         * ownerNickName : 周成彦
         * phone : 545
         * transfor : null
         * transforNickName : null
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
        private String doImage;
        private String sendTo;
        private String state;
        private String isChecked;
        private String startDate;
        private String finishDate;
        private String processDate;
        private String ownerBranch;
        private String sendToBranch;
        private String sendToNickName;
        private String owner;
        private String ownerNickName;
        private String phone;
        private String transfor;
        private String transforNickName;

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

        public String getDoImage() {
            return doImage;
        }

        public void setDoImage(String doImage) {
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

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getProcessDate() {
            return processDate;
        }

        public void setProcessDate(String processDate) {
            this.processDate = processDate;
        }

        public String getOwnerBranch() {
            return ownerBranch;
        }

        public void setOwnerBranch(String ownerBranch) {
            this.ownerBranch = ownerBranch;
        }

        public String getSendToBranch() {
            return sendToBranch;
        }

        public void setSendToBranch(String sendToBranch) {
            this.sendToBranch = sendToBranch;
        }

        public String getSendToNickName() {
            return sendToNickName;
        }

        public void setSendToNickName(String sendToNickName) {
            this.sendToNickName = sendToNickName;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOwnerNickName() {
            return ownerNickName;
        }

        public void setOwnerNickName(String ownerNickName) {
            this.ownerNickName = ownerNickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTransfor() {
            return transfor;
        }

        public void setTransfor(String transfor) {
            this.transfor = transfor;
        }

        public String getTransforNickName() {
            return transforNickName;
        }

        public void setTransforNickName(String transforNickName) {
            this.transforNickName = transforNickName;
        }
    }
}
