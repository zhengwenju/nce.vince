package com.bronet.blockchain.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vince on 2019/7/6.
 */

public class TransCoins {


    /**
     * status : 0
     * msg : string
     * result : [{"inCoinType":0,"inCoinName":"string","outCoinType":0,"outCoinName":"string"}]
     */

    private int status;
    private String msg;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * inCoinType : 0
         * inCoinName : string
         * outCoinType : 0
         * outCoinName : string
         */

        private int inCoinType;
        private String inCoinName;
        private int outCoinType;
        private String outCoinName;

        public int getInCoinType() {
            return inCoinType;
        }

        public void setInCoinType(int inCoinType) {
            this.inCoinType = inCoinType;
        }

        public String getInCoinName() {
            return inCoinName;
        }

        public void setInCoinName(String inCoinName) {
            this.inCoinName = inCoinName;
        }

        public int getOutCoinType() {
            return outCoinType;
        }

        public void setOutCoinType(int outCoinType) {
            this.outCoinType = outCoinType;
        }

        public String getOutCoinName() {
            return outCoinName;
        }

        public void setOutCoinName(String outCoinName) {
            this.outCoinName = outCoinName;
        }
    }
}
