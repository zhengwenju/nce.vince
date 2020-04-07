package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/26.
 */

public class Type {
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

    public ArrayList<Data> getResult() {
        return result;
    }

    public void setResult(ArrayList<Data> result) {
        this.result = result;
    }

    private ArrayList<Data>result;
    public class Data{
        private int id;//": 0,
        private String imgUrl;//": "string",

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;//": "string"
    }
}
