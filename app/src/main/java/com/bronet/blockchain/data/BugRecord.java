package com.bronet.blockchain.data;

import java.util.List;

/**
 * Created by 18514 on 2019/7/15.
 */

public class BugRecord {


    /**
     * status : 0
     * msg : string
     * result : [{"inCoinName":"string","qty":"string","outCoinName":"string","price":"string"}]
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

    public static class ResultBean {
        /**
         * inCoinName : string
         * qty : string
         * outCoinName : string
         * price : string
         */

        private String inCoinName;
        private String qty;
        private String outCoinName;
        private String price;

        public String getInCoinName() {
            return inCoinName;
        }

        public void setInCoinName(String inCoinName) {
            this.inCoinName = inCoinName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getOutCoinName() {
            return outCoinName;
        }

        public void setOutCoinName(String outCoinName) {
            this.outCoinName = outCoinName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
