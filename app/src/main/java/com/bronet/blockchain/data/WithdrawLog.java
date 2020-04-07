package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/18.
 */

public class WithdrawLog {
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    ArrayList<Result>result;
    public class Result{
        private int id;//": 0,
        private String coinName;//": "string",
        private String  qty;//": "string",
        private String  status;//": "string",

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        private String  updateTime;//": "2019-07-18T01:23:05.600Z"
    }
}
