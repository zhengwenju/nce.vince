package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/26.
 */

public class Progress {
    private int status;//": 0,
    private String msg;//": "string",

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private int result;//": 0
}
