package com.bronet.blockchain.data;

import java.util.List;

/**
 * Created by 18514 on 2019/7/15.
 */

public class TranEntrusts {


    /**
     * status : 0
     * msg :
     * result : [{"id":1,"createTime":" 09-04: 14:09:52","qty":3,"balance":2,"range":451,"finalPrice":"903.10874828","statusMsg":"撤回","status":1},{"id":2,"createTime":" 09-04: 14:12:18","qty":3,"balance":2,"range":451,"finalPrice":"903.10874828","statusMsg":"撤回","status":1},{"id":3,"createTime":" 09-04: 14:42:57","qty":30,"balance":20,"range":451,"finalPrice":"9031.0874828","statusMsg":"买入","status":0},{"id":4,"createTime":" 09-04: 14:43:22","qty":10,"balance":8,"range":451,"finalPrice":"3612.43499312","statusMsg":"买入","status":0},{"id":5,"createTime":" 09-04: 15:43:59","qty":2,"balance":1,"range":451,"finalPrice":"451.55437414","statusMsg":"买入","status":0},{"id":6,"createTime":" 09-04: 18:08:14","qty":24,"balance":23,"range":451,"finalPrice":"10385.75060522","statusMsg":"买入","status":0},{"id":7,"createTime":" 09-04: 20:11:39","qty":1,"balance":1,"range":451,"finalPrice":"451.55437414","statusMsg":"买入","status":0},{"id":9,"createTime":" 09-04: 22:48:16","qty":1,"balance":1,"range":451,"finalPrice":"451.55437414","statusMsg":"买入","status":0},{"id":10,"createTime":" 09-04: 22:55:41","qty":2,"balance":2,"range":451,"finalPrice":"903.10874828","statusMsg":"买入","status":0},{"id":11,"createTime":" 09-05: 20:48:45","qty":2383.37777369,"balance":2383.37777369,"range":451,"finalPrice":"1076224.6589377745083766","statusMsg":"撤回","status":1},{"id":12,"createTime":" 09-06: 09:47:41","qty":1787.53333026,"balance":1787.53333026,"range":451,"finalPrice":"807168.4941999442234764","statusMsg":"撤回","status":1}]
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
         * id : 1
         * createTime :  09-04: 14:09:52
         * qty : 3
         * balance : 2
         * range : 451
         * finalPrice : 903.10874828
         * statusMsg : 撤回
         * status : 1
         */

        private int id;
        private String createTime;
        private int qty;
        private int balance;
        private int range;
        private String finalPrice;
        private String statusMsg;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getRange() {
            return range;
        }

        public void setRange(int range) {
            this.range = range;
        }

        public String getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(String finalPrice) {
            this.finalPrice = finalPrice;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
