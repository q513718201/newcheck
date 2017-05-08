package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/5/7.
 */

public class Gonggao {

    /**
     * content : [{"id":6,"content":"公告测试","dateTime":"2017-05-07 12:19:12","owner":"张云峰","days":30}]
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
         * content : 公告测试
         * dateTime : 2017-05-07 12:19:12
         * owner : 张云峰
         * days : 30
         */

        private int id;
        private String content;
        private String dateTime;
        private String owner;
        private int days;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }
    }
}
