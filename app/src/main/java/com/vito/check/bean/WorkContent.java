package com.vito.check.bean;



public class WorkContent {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WorkContent(String content) {
        this.content = content;
    }

    public WorkContent() {
    }

    @Override
    public String toString() {
        return "WorkContent{" +
                "content='" + content + '\'' +
                '}';
    }
}
