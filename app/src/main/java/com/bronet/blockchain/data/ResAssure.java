package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/26.
 */

public class ResAssure {
    private int status;
    private String msg;

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

    @Override
    public String toString() {
        return "ResAssure{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
