package com.bronet.blockchain.data;

import java.util.List;

public class TransferNodeLog {

    /**
     * status : 0
     * msg : string
     * result : [{"id":0,"qty":"string","balanceQty":"string","createTime":"2019-12-20T09:20:20.125Z"}]
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
         * id : 0
         * qty : string
         * balanceQty : string
         * createTime : 2019-12-20T09:20:20.125Z
         */

        private int id;
        private String qty;
        private String balanceQty;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getBalanceQty() {
            return balanceQty;
        }

        public void setBalanceQty(String balanceQty) {
            this.balanceQty = balanceQty;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
