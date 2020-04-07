package com.bronet.blockchain.data;

import com.bronet.blockchain.type.MultipleItem;

import java.util.List;

public class Appointment {

    /**
     * status : 0
     * msg :
     * result : [{"id":1,"bzMoney":10,"qty":"1.00","status":"预约成功","createTime":"2019-07-31 17:22:26"}]
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

    public  class ResultBean extends MultipleItem {
        /**
         * id : 1
         * bzMoney : 10
         * qty : 1.00
         * status : 预约成功
         * createTime : 2019-07-31 17:22:26
         */

        private int id;
        private int bzMoney;
        private String qty;
        private String status;
        private String createTime;

        public ResultBean(int itemType) {
            super(itemType);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBzMoney() {
            return bzMoney;
        }

        public void setBzMoney(int bzMoney) {
            this.bzMoney = bzMoney;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
