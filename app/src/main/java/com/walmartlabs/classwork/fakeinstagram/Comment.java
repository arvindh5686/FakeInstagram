package com.walmartlabs.classwork.fakeinstagram;

/**
 * Created by abalak5 on 10/11/15.
 */
public class Comment {
    private String text;
    private String userName;

    public Comment(String text, String userName) {
        this.text = text;
        this.userName = userName;
    }

    public String getText() {
        return text;

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
