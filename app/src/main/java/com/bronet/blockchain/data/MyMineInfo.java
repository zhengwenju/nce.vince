package com.bronet.blockchain.data;

public class MyMineInfo {


    /**
     * status : 0
     * msg :
     * result : {"id":3,"name":"微型矿机","hashrate":0.003,"amount":500,"balanceDays":"333天","multiple":"1.40倍","incomeQty":"0.00NCE","todayReward":"0.00","totalReward":"0.00"}
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
         * id : 3
         * name : 微型矿机
         * hashrate : 0.003
         * amount : 500
         * balanceDays : 333天
         * multiple : 1.40倍
         * incomeQty : 0.00NCE
         * todayReward : 0.00
         * totalReward : 0.00
         */

        private int id;
        private String name;
        private double hashrate;
        private int amount;
        private String balanceDays;
        private String multiple;
        private String incomeQty;
        private String todayReward;
        private String totalReward;
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getHashrate() {
            return hashrate;
        }

        public void setHashrate(double hashrate) {
            this.hashrate = hashrate;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBalanceDays() {
            return balanceDays;
        }

        public void setBalanceDays(String balanceDays) {
            this.balanceDays = balanceDays;
        }

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

        public String getIncomeQty() {
            return incomeQty;
        }

        public void setIncomeQty(String incomeQty) {
            this.incomeQty = incomeQty;
        }

        public String getTodayReward() {
            return todayReward;
        }

        public void setTodayReward(String todayReward) {
            this.todayReward = todayReward;
        }

        public String getTotalReward() {
            return totalReward;
        }

        public void setTotalReward(String totalReward) {
            this.totalReward = totalReward;
        }
    }
}
