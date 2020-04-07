package com.bronet.blockchain.data;

import java.util.List;

public class Candles1000 {


    /**
     * code : 0
     * data : [["string"]]
     * detailMsg : string
     * msg : string
     */

    private int code;
    private String detailMsg;
    private String msg;
    private String[][] data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
