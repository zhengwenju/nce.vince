package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/6/26.
 */

public class DataBean<T> {
    private String msg;
    private int status;
    private T result;
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
