package com.bronet.blockchain.data;

import java.util.List;

public class Mine {

    /**
     * status : 0
     * msg :
     * result : [{"id":1,"name":"微型矿机","amount":"500$","hashrate":0.003,"output":"4.20枚","period":"333天","multiple":"1.40倍","fuelRatio":0.1},{"id":2,"name":"中型矿机","amount":"1500$","hashrate":0.003,"output":"15.30枚","period":"333天","multiple":"1.70倍","fuelRatio":0.15},{"id":3,"name":"大型矿机","amount":"5000$","hashrate":0.003,"output":"60.00枚","period":"333天","multiple":"2.00倍","fuelRatio":0.15}]
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
         * name : 微型矿机
         * amount : 500$
         * hashrate : 0.003
         * output : 4.20枚
         * period : 333天
         * multiple : 1.40倍
         * fuelRatio : 0.1
         */

        private int id;
        private String name;
        private String amount;
        private double hashrate;
        private String output;
        private String period;
        private String multiple;
        private double fuelRatio;
        private int buyStatus;

        public int getBuyStatus() {
            return buyStatus;
        }

        public void setBuyStatus(int buyStatus) {
            this.buyStatus = buyStatus;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public double getHashrate() {
            return hashrate;
        }

        public void setHashrate(double hashrate) {
            this.hashrate = hashrate;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

        public double getFuelRatio() {
            return fuelRatio;
        }

        public void setFuelRatio(double fuelRatio) {
            this.fuelRatio = fuelRatio;
        }
    }
}
