package com.bronet.blockchain.data;

public class NodeInfo {


    /**
     * status : 0
     * msg :
     * result : {"totalReward":"0.00","name":"C1-轻主节点","qty":"100.00枚","ratio":0.002,"multiple":"1.20倍","todayReward":"0.00","incomeReward":"0.00"}
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
         * totalReward : 0.00
         * name : C1-轻主节点
         * qty : 100.00枚
         * ratio : 0.002
         * multiple : 1.20倍
         * todayReward : 0.00
         * incomeReward : 0.00
         */

        private String totalReward;
        private String name;
        private String qty;
        private double ratio;
        private String multiple;
        private String todayReward;
        private String incomeQty;

        public String getTotalReward() {
            return totalReward;
        }

        public void setTotalReward(String totalReward) {
            this.totalReward = totalReward;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

        public String getTodayReward() {
            return todayReward;
        }

        public void setTodayReward(String todayReward) {
            this.todayReward = todayReward;
        }

        public String getIncomeReward() {
            return incomeQty;
        }

        public void setIncomeReward(String incomeReward) {
            this.incomeQty = incomeReward;
        }
    }
}
