package com.vito.check.bean;


/**
 * Created by xk on 2017/3/21.
 */

public class SendOrder {


    /**
     * content : token fault
     * success : false
     */

    private String content;
    private boolean success;

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
