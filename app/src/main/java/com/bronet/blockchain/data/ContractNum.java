package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/15.
 */

public class ContractNum {

    /**
     * status : 0
     * msg : string
     * result : {"eth":"string","nce":"string"}
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
         * eth : string
         * nce : string
         */

        private String eth;
        private String nce;
        private String usdt;
        public String getEth() {
            return eth;
        }

        public void setEth(String eth) {
            this.eth = eth;
        }

        public String getNce() {
            return nce;
        }

        public void setNce(String nce) {
            this.nce = nce;
        }

        public String getUsdt() {
            return usdt;
        }

        public void setUsdt(String usdt) {
            this.usdt = usdt;
        }
    }
}
