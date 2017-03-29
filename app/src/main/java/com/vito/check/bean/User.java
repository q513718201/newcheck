package com.vito.check.bean;

/**
 * Created by xk on 2017/3/14.
 */

public class User {


    /**
     * content : baijw-2a3233b0-1cd5-4510-8400-c1b1706ce9ee
     * success : true
     */

    private String content;
    private boolean success;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
