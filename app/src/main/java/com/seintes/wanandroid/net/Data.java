package com.seintes.wanandroid.net;

/**
 * @Description 数据类
 * @Author xmhaz
 * @Time 2023/12/1 14:49
 */
public class Data<T> {
    private int errorCode;
    private String errorMsg;
    private T data;

    public int getCode() {
        return errorCode;
    }

    public void setCode(int code) {
        this.errorCode = code;
    }

    public String getMessage() {
        return errorMsg;
    }

    public void setMessage(String message) {
        this.errorMsg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

