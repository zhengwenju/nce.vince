package com.bronet.blockchain.data;

import java.util.List;

public class UserMineLog {

    /**
     * status : 0
     * msg : null
     * result : [{"id":3,"qty":"3.78","fuel":"0.42","createTime":"2020-01-19 16:44:11"},{"id":2,"qty":"3.78","fuel":"0.42","createTime":"2020-01-19 16:44:11"},{"id":1,"qty":"3.78","fuel":"0.42","createTime":"2020-01-19 16:44:11"}]
     */

    private int status;
    private Object msg;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
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
         * id : 3
         * qty : 3.78
         * fuel : 0.42
         * createTime : 2020-01-19 16:44:11
         */

        private int id;
        private String qty;
        private String fuel;
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

        public String getFuel() {
            return fuel;
        }

        public void setFuel(String fuel) {
            this.fuel = fuel;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
