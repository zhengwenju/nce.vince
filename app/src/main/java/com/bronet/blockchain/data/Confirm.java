package com.bronet.blockchain.data;

public class Confirm {

    /**
     * status : 1
     * msg : 操作失败
     */

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
        return "Confirm{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
