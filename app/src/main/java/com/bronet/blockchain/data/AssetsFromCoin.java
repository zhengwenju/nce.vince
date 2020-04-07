package com.bronet.blockchain.data;

public class AssetsFromCoin {

    /**
     * status : 0
     * msg :
     * result : {"totalAssets":"99994.35073823","banlance":"99911.38523219","freeze":"82.96550604"}
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
         * totalAssets : 99994.35073823
         * banlance : 99911.38523219
         * freeze : 82.96550604
         */

        private String totalAssets;
        private String banlance;
        private String freeze;

        public String getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(String totalAssets) {
            this.totalAssets = totalAssets;
        }

        public String getBanlance() {
            return banlance;
        }

        public void setBanlance(String banlance) {
            this.banlance = banlance;
        }

        public String getFreeze() {
            return freeze;
        }

        public void setFreeze(String freeze) {
            this.freeze = freeze;
        }
    }
}
