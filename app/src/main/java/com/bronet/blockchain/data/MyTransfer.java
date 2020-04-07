package com.bronet.blockchain.data;

import java.util.List;

public class MyTransfer {


    /**
     * status : 0
     * msg :
     * result : [{"id":4,"userid":6477,"userName":"xxxssss","qty":"1000.00","coinName":"NCE","createTime":"2019-08-28 14:26:52","statusMsg":"成功","status":3},{"id":5,"userid":6479,"userName":"dddxxxs","qty":"1000.00","coinName":"NCE","createTime":"2019-08-28 14:26:52","statusMsg":"操作","status":0},{"id":6,"userid":6496,"userName":"呃呃呃呃呃呃呃","qty":"1000.00","coinName":"NCE","createTime":"2019-08-28 14:26:52","statusMsg":"已拒绝","status":1},{"id":7,"userid":6521,"userName":"admin23","qty":"1000.00","coinName":"NCE","createTime":"2019-08-28 14:26:52","statusMsg":"待支付","status":2}]
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
         * id : 4
         * userid : 6477
         * userName : xxxssss
         * qty : 1000.00
         * coinName : NCE
         * createTime : 2019-08-28 14:26:52
         * statusMsg : 成功
         * status : 3
         */

        private int id;
        private String userName;
        private double finalPrice;
        private double nceQty;
        private String coinName;
        private String createTime;
        private String statusMsg;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }



        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        public String getCoinName() {
            return coinName;
        }

        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }


        public double getNceQty() {
            return nceQty;
        }

        public void setNceQty(double nceQty) {
            this.nceQty = nceQty;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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
