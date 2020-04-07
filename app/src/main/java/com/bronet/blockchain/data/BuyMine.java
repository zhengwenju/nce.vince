package com.bronet.blockchain.data;

public class BuyMine {
    /**
     * status : 0
     * msg : string
     * result : {}
     */

    private int status;
    private String msg;
    private AppNode.ResultBean result;

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

    public AppNode.ResultBean getResult() {
        return result;
    }

    public void setResult(AppNode.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
    }
}
