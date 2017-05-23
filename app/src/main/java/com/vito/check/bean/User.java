package com.vito.check.bean;

/**
 * Created by xk on 2017/3/14.
 */

public class User {

    /**
     * content : xj003-d8fe8e11-de05-4d5d-9174-dcf6499c3a4f
     * role : xj
     * branch : 工程部
     * nickName : 蔺伟东
     * success : true
     */

    private String content;
    private String branch;
    private String nickName;
    private boolean success;
    private String role;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
