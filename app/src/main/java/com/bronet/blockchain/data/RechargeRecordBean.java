package com.bronet.blockchain.data;

import java.util.List;

public class RechargeRecordBean {

    /**
     * status : 0
     * result : [{"id":37007,"coinName":"ETH","qty":"50.00","updateTime":"2019-08-08 11:33:24"},{"id":37008,"coinName":"ETH","qty":"50.00","updateTime":"2019-08-08 11:33:24"},{"id":37009,"coinName":"ETH","qty":"50.00","updateTime":"2019-08-08 11:33:24"}]
     */

    private int status;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 37007
         * coinName : ETH
         * qty : 50.00
         * updateTime : 2019-08-08 11:33:24
         */

        private int id;
        private String coinName;
        private String qty;
        private String updateTime;

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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
