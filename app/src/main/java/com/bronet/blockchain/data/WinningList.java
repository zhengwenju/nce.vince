package com.bronet.blockchain.data;

import java.util.List;

public class WinningList {

    /**
     * status : 0
     * msg : string
     * result : ["string"]
     */

    private int status;
    private String msg;
    private List<String> result;

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

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
