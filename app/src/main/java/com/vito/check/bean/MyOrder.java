package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */

public class MyOrder {


    /**
     * content : [{"id":33,"devNo":"LZX0000","description":"派单测试,不用理会,谢谢","address":"中山公园","phone":"18888888","processInfo":"可以啦,没有事情啦","doImage":"265bc538-0bed-4201-8628-b8fef50b604bCapture001.png","sendTo":"xj003","startDate":1490427543000,"finishDate":1490430896000,"state":"已结单","owner":"gc001","branch":"工程部"}]
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
         * id : 33
         * devNo : LZX0000
         * description : 派单测试,不用理会,谢谢
         * address : 中山公园
         * phone : 18888888
         * processInfo : 可以啦,没有事情啦
         * doImage : 265bc538-0bed-4201-8628-b8fef50b604bCapture001.png
         * sendTo : xj003
         * startDate : 1490427543000
         * finishDate : 1490430896000
         * state : 已结单
         * owner : gc001
         * branch : 工程部
         */

        private int id;
        private String devNo;
        private String description;
        private String address;
        private String phone;
        private String processInfo;
        private String doImage;
        private String sendTo;
        private long startDate;
        private long finishDate;
        private String state;
        private String owner;
        private String branch;

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", devNo='" + devNo + '\'' +
                    ", description='" + description + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", processInfo='" + processInfo + '\'' +
                    ", doImage='" + doImage + '\'' +
                    ", sendTo='" + sendTo + '\'' +
                    ", startDate=" + startDate +
                    ", finishDate=" + finishDate +
                    ", state='" + state + '\'' +
                    ", owner='" + owner + '\'' +
                    ", branch='" + branch + '\'' +
                    '}';
        }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProcessInfo() {
            return processInfo;
        }

        public void setProcessInfo(String processInfo) {
            this.processInfo = processInfo;
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

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(long finishDate) {
            this.finishDate = finishDate;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }
    }
}
