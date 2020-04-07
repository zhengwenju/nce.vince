package com.bronet.blockchain.data;

import java.util.List;

public class TradeBuyMyList {

    /**
     * status : 0
     * msg :
     * result : [{"id":1,"createTime":" 09-04: 14:09:52","qty":"3.00","balance":"2.00","range":447.1085381,"finalPrice":"894.2170762","statusMsg":"撤回","status":1},{"id":2,"createTime":" 09-04: 14:12:18","qty":"3.00","balance":"2.00","range":447.1085381,"finalPrice":"894.2170762","statusMsg":"撤回","status":1},{"id":11,"createTime":" 09-05: 20:48:45","qty":"2383.37777369","balance":"2383.37777369","range":447.1085381,"finalPrice":"1065628.552134568542589","statusMsg":"撤回","status":1},{"id":12,"createTime":" 09-06: 09:47:41","qty":"1787.53333026","balance":"1787.53333026","range":447.1085381,"finalPrice":"799221.414097573092906","statusMsg":"撤回","status":1}]
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
         * qty : 3.00
         * balance : 2.00
         * range : 447.1085381
         * finalPrice : 894.2170762
         * statusMsg : 撤回
         * status : 1
         */

        private int id;
        private String createTime;
        private String qty;
        private String balance;
        private double range;
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

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public double getRange() {
            return range;
        }

        public void setRange(double range) {
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
