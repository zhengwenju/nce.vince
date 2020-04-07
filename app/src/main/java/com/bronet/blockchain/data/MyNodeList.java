package com.bronet.blockchain.data;

import java.util.List;

public class MyNodeList {


    /**
     * status : 0
     * msg : string
     * result : [{"id":0,"nodeName":"string","ratio":0,"multiple":0,"qty":"string","fuelRatio":0,"totalQty":"string","createTime":"2019-12-20T04:35:58.290Z"}]
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
         * nodeName : string
         * ratio : 0
         * multiple : 0
         * qty : string
         * fuelRatio : 0
         * totalQty : string
         * createTime : 2019-12-20T04:35:58.290Z
         */

        private int id;
        private String nodeName;
        private double ratio;
        private String multiple;
        private String qty;
        private double fuelRatio;
        private String totalQty;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
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

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public double getFuelRatio() {
            return fuelRatio;
        }

        public void setFuelRatio(int fuelRatio) {
            this.fuelRatio = fuelRatio;
        }

        public String getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(String totalQty) {
            this.totalQty = totalQty;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
