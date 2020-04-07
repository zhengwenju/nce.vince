package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/14.
 */

public class UploadFile {
   private String msg;
    private int status;

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

    public ArrayList<String> getFilelist() {
        return filelist;
    }

    public void setFilelist(ArrayList<String> filelist) {
        this.filelist = filelist;
    }

    private ArrayList<String>filelist;
}
