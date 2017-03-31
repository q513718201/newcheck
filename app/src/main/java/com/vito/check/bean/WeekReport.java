package com.vito.check.bean;

/**
 * Created by xk on 2017/3/29.
 */

public class WeekReport {

    /**
     * id : null
     * nickName : 王彦锋
     * xjNumWeek : 43
     * dispatchNumWeek : 0
     * processNumWeek : 0
     * topOnlineRate : 34.35%
     */

    private Object id;
    private String nickName;
    private int xjNumWeek;
    private int dispatchNumWeek;
    private int processNumWeek;
    private String topOnlineRate;
    @Override
    public String toString() {
        return "WeekReport{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", xjNumWeek=" + xjNumWeek +
                ", dispatchNumWeek=" + dispatchNumWeek +
                ", processNumWeek=" + processNumWeek +
                ", topOnlineRate='" + topOnlineRate + '\'' +
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
