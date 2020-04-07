package com.bronet.blockchain.data;

public class InvestNum {


    /**
     * status : 0
     * msg :
     * result : {"qty":"0.00","usd":"0.00"}
     */

    private int status;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * qty : 0.00
         * usd : 0.00
         */

        private String qty;
        private String usd;

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getUsd() {
            return usd;
        }

        public void setUsd(String usd) {
            this.usd = usd;
        }
    }
}
