package com.bronet.blockchain.data;

public class TransferNodeReward {


    /**
     * status : 1
     * msg : 节点收益余额不足
     * result :
     */

    private int status;
    private String msg;
    private String result;

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
}
