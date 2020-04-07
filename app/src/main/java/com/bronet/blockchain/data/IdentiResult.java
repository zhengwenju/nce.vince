package com.bronet.blockchain.data;

import java.math.BigDecimal;
import java.util.List;

public class IdentiResult {

    /**
     * status : 0
     * msg :
     * result : [{"id":1,"nceQty":1000,"nceName":"NCE","ethQty":2.0775556,"ethName":"ETH"},{"id":2,"nceQty":5000,"nceName":"NCE","ethQty":10.38777802,"ethName":"ETH"},{"id":3,"nceQty":10000,"nceName":"NCE","ethQty":20.77555603,"ethName":"ETH"},{"id":4,"nceQty":20000,"nceName":"NCE","ethQty":41.55111206,"ethName":"ETH"}]
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
         * nceQty : 1000
         * nceName : NCE
         * ethQty : 2.0775556
         * ethName : ETH
         */

        private int id;
        private double nceQty;
        private String nceName;
        private double ethQty;
        private String ethName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getNceQty() {
            return nceQty;
        }

        public void setNceQty(double nceQty) {
            this.nceQty = nceQty;
        }

        public String getNceName() {
            return nceName;
        }

        public void setNceName(String nceName) {
            this.nceName = nceName;
        }

        public double getEthQty() {
            return ethQty;
        }

        public void setEthQty(double ethQty) {
            this.ethQty = ethQty;
        }

        public String getEthName() {
            return ethName;
        }

        public void setEthName(String ethName) {
            this.ethName = ethName;
        }

        public String getFormatData(){
            return getNceQty()+"\n"+getNceName();
        }
    }
}
