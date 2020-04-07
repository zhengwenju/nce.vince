package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/26.
 */

public class Attendance {
    private int status;//": 0,
    private String msg;//签到成功!",

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;//": ""
}
