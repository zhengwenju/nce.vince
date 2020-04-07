package com.bronet.blockchain.data;

import java.util.List;

public class PrizeRecordModel {

    /**
     * status : 0
     * msg : string
     * result : [{"qty":"string","rewardQty":"string","createTime":"2019-10-08T07:45:50.785Z"}]
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
         * qty : string
         * rewardQty : string
         * createTime : 2019-10-08T07:45:50.785Z
         */

        private String qty;
        private String rewardQty;
        private String createTime;

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getRewardQty() {
            return rewardQty;
        }

        public void setRewardQty(String rewardQty) {
            this.rewardQty = rewardQty;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
