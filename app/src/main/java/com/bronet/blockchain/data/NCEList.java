package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/26.
 */

public class NCEList {
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

    ArrayList<Result> result;
    public class Result{
        private int id;//": 0,
        private String payCoinName;//": "string",
        private String  qty;//": "string",
        private String  tradeType;//": "string",

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPayCoinName() {
            return payCoinName;
        }

        public void setPayCoinName(String coinName) {
            this.payCoinName = payCoinName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private String  createTime;//": "2019-07-18T01:23:05.600Z"
    }
}
