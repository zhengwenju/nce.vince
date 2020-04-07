package com.bronet.blockchain.data;

import java.util.List;

public class MyMineList {

    /**
     * status : 0
     * msg :
     * result : [{"id":2,"name":"微型矿机","hashrate":0.003,"multiple":"1.40倍","output":"4.20000000枚","fuelRatio":0.1,"period":"333天","createTime":"2020-01-19 18:40:46"},{"id":3,"name":"微型矿机","hashrate":0.003,"multiple":"1.40倍","output":"4.20000000枚","fuelRatio":0.1,"period":"333天","createTime":"2020-01-20 09:22:57"}]
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
         * id : 2
         * name : 微型矿机
         * hashrate : 0.003
         * multiple : 1.40倍
         * output : 4.20000000枚
         * fuelRatio : 0.1
         * period : 333天
         * createTime : 2020-01-19 18:40:46
         */

        private int id;
        private String name;
        private double hashrate;
        private String multiple;
        private String output;
        private double fuelRatio;
        private String period;
        private String createTime;

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

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

        public double getFuelRatio() {
            return fuelRatio;
        }

        public void setFuelRatio(double fuelRatio) {
            this.fuelRatio = fuelRatio;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
