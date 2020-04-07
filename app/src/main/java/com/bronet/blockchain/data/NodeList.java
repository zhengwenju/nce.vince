package com.bronet.blockchain.data;

import java.util.List;

public class NodeList {


    /**
     * status : 0
     * msg :
     * result : [{"id":1,"name":"C1-轻主节点","qty":100,"ratio":0.002,"totalQty":120,"multiple":1.2,"fuelRatio":0.08},{"id":2,"name":"C2-轻主节点","qty":500,"ratio":0.0025,"totalQty":650,"multiple":1.3,"fuelRatio":0.08},{"id":3,"name":"C3-轻主节点","qty":1000,"ratio":0.003,"totalQty":1400,"multiple":1.4,"fuelRatio":0.1},{"id":4,"name":"C4-轻主节点","qty":5000,"ratio":0.0035,"totalQty":7500,"multiple":1.5,"fuelRatio":0.15},{"id":5,"name":"C5-超级节点","qty":10000,"ratio":0.004,"totalQty":16000,"multiple":1.6,"fuelRatio":0.15}]
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
         * name : C1-轻主节点
         * qty : 100
         * ratio : 0.002
         * totalQty : 120
         * multiple : 1.2
         * fuelRatio : 0.08
         */

        private int id;
        private String name;
        private int qty;
        private double ratio;
        private int totalQty;
        private String multiple;
        private double fuelRatio;
        private int buyStatus;
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

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public int getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(int totalQty) {
            this.totalQty = totalQty;
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

        public int getBuyStatus() {
            return buyStatus;
        }

        public void setBuyStatus(int buyStatus) {
            this.buyStatus = buyStatus;
        }
    }
}
