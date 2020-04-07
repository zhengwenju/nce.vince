package com.bronet.blockchain.data;

import com.bronet.blockchain.type.MultipleItem;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/15.
 */

public class TranMenu {

    /**
     * status : 0
     * msg : string
     * result : {"userName":"string","qty":0,"statusMsg":"string","status":0,"createTime":"2019-08-30T03:22:07.699Z"}
     */


    private int status;
    private String msg;
    private ReceModel.ResultBean result;

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

    public ReceModel.ResultBean getResult() {
        return result;
    }

    public void setResult(ReceModel.ResultBean result) {
        this.result = result;
    }



}
