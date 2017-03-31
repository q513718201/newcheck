package com.vito.check.bean;

/**
 * Created by xk on 2017/3/29.
 */

public class DayReport {

    /**
     * id : null
     * nickName : 王彦锋
     * date : 2017-03-29 16:50:43
     * firstSignin : 2017-03-29 08:45:46
     * lastSignin : 2017-03-29 09:40:49
     * xjNumToday : 10
     * processNumToday : 0
     * topOnline : 34.35%
     * xjNumWeek : 43
     * processWeek : 0
     */

    private Object id;
    private String nickName;
    private String date;
    private String firstSignin;
    private String lastSignin;
    private int xjNumToday;
    private int processNumToday;
    private String topOnline;
    private int xjNumWeek;
    private int processWeek;

    @Override
    public String toString() {
        return "DayReport{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", date='" + date + '\'' +
                ", firstSignin='" + firstSignin + '\'' +
                ", lastSignin='" + lastSignin + '\'' +
                ", xjNumToday=" + xjNumToday +
                ", processNumToday=" + processNumToday +
                ", topOnline='" + topOnline + '\'' +
                ", xjNumWeek=" + xjNumWeek +
                ", processWeek=" + processWeek +
                '}';
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
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
