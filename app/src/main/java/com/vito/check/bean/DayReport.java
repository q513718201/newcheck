package com.vito.check.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xk on 2017/3/29.
 */

public class DayReport {


    /**
     * content : [{"id":0,"nickName":"陈浴","date":"2017-04-06 17:48:47","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"雍福平","date":"2017-04-06 17:48:48","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"31.76%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"蔺伟东","date":"2017-04-06 17:48:49","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"32.62%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"王彦锋","date":"2017-04-06 17:48:51","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"29.97%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"梁耀安","date":"2017-04-06 17:48:52","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"28.10%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"陈智俊","date":"2017-04-06 17:48:53","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"赵生录","date":"2017-04-06 17:48:55","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"40.21%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"刘洋","date":"2017-04-06 17:48:56","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"陈轩","date":"2017-04-06 17:48:57","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"史豪杰","date":"2017-04-06 17:48:58","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"34.85%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"赵国锋","date":"2017-04-06 17:48:59","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"32.59%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"陈海龙","date":"2017-04-06 17:49:00","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"柴长进","date":"2017-04-06 17:49:02","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"11.96%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"刘兵","date":"2017-04-06 17:49:03","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"李建强","date":"2017-04-06 17:49:04","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"孙建刚","date":"2017-04-06 17:49:05","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"林耀强","date":"2017-04-06 17:49:06","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"29.19%","xjNumWeek":0,"processWeek":0},{"id":0,"nickName":"陈沐","date":"2017-04-06 17:49:07","firstSignin":"无签到数据","lastSignin":"无签到数据","xjNumToday":0,"processNumToday":0,"topOnline":"0.00","xjNumWeek":0,"processWeek":0}]
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

    public static class ContentBean  implements Serializable{
        /**
         * id : 0
         * nickName : 陈浴
         * date : 2017-04-06 17:48:47
         * firstSignin : 无签到数据
         * lastSignin : 无签到数据
         * xjNumToday : 0
         * processNumToday : 0
         * topOnline : 0.00
         * xjNumWeek : 0
         * processWeek : 0
         */

        private int id;
        private String nickName;
        private String date;
        private String firstSignin;
        private String lastSignin;
        private int xjNumToday;
        private int processNumToday;
        private String topOnline;
        private int xjNumWeek;
        private int processWeek;

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFirstSignin() {
            return firstSignin;
        }

        public void setFirstSignin(String firstSignin) {
            this.firstSignin = firstSignin;
        }

        public String getLastSignin() {
            return lastSignin;
        }

        public void setLastSignin(String lastSignin) {
            this.lastSignin = lastSignin;
        }

        public int getXjNumToday() {
            return xjNumToday;
        }

        public void setXjNumToday(int xjNumToday) {
            this.xjNumToday = xjNumToday;
        }

        public int getProcessNumToday() {
            return processNumToday;
        }

        public void setProcessNumToday(int processNumToday) {
            this.processNumToday = processNumToday;
        }

        public String getTopOnline() {
            return topOnline;
        }

        public void setTopOnline(String topOnline) {
            this.topOnline = topOnline;
        }

        public int getXjNumWeek() {
            return xjNumWeek;
        }

        public void setXjNumWeek(int xjNumWeek) {
            this.xjNumWeek = xjNumWeek;
        }

        public int getProcessWeek() {
            return processWeek;
        }

        public void setProcessWeek(int processWeek) {
            this.processWeek = processWeek;
        }
    }
}
