package com.bronet.blockchain.data;

import java.util.List;

public class InvestLevelList {


    /**
     * status : 0
     * msg : string
     * result : [{"id":0,"qty":0}]
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

        private int id;
        private int minQty;
        private int maxQty;
        private String level;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMinQty() {
            return minQty;
        }

        public void setMinQty(int minQty) {
            this.minQty = minQty;
        }

        public int getMaxQty() {
            return maxQty;
        }

        public void setMaxQty(int maxQty) {
            this.maxQty = maxQty;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
