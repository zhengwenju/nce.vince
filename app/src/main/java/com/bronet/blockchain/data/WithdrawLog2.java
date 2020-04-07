package com.bronet.blockchain.data;

import java.util.List;

/**
 * Created by 18514 on 2019/7/18.
 */

public class WithdrawLog2 {


    /**
     * status : 0
     * msg : string
     * result : [{"id":0,"tradeType":0,"tradeTypeMsg":"string","startBalance":"string","endBalance":"string","startTransferQty":"string","ethQty":"string","nceQty":"string","rate":"string","endTransferQty":"string","createTime":"2019-11-02T07:13:09.414Z"}]
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
        public ResultBean(String startBalance, String endBalance, String ethQty, String rate, String createTime) {
            this.startBalance = startBalance;
            this.endBalance = endBalance;
            this.ethQty = ethQty;
            this.rate = rate;
            this.createTime = createTime;
        }

        /**
         * id : 0
         * tradeType : 0
         * tradeTypeMsg : string
         * startBalance : string
         * endBalance : string
         * startTransferQty : string
         * ethQty : string
         * nceQty : string
         * rate : string
         * endTransferQty : string
         * createTime : 2019-11-02T07:13:09.414Z
         */



        private int id;
        private int tradeType;
        private String tradeTypeMsg;
        private String startBalance;
        private String endBalance;
        private String startTransferQty;
        private String ethQty;
        private String nceQty;
        private String rate;
        private String endTransferQty;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeTypeMsg() {
            return tradeTypeMsg;
        }

        public void setTradeTypeMsg(String tradeTypeMsg) {
            this.tradeTypeMsg = tradeTypeMsg;
        }

        public String getStartBalance() {
            return startBalance;
        }

        public void setStartBalance(String startBalance) {
            this.startBalance = startBalance;
        }

        public String getEndBalance() {
            return endBalance;
        }

        public void setEndBalance(String endBalance) {
            this.endBalance = endBalance;
        }

        public String getStartTransferQty() {
            return startTransferQty;
        }

        public void setStartTransferQty(String startTransferQty) {
            this.startTransferQty = startTransferQty;
        }

        public String getEthQty() {
            return ethQty;
        }

        public void setEthQty(String ethQty) {
            this.ethQty = ethQty;
        }

        public String getNceQty() {
            return nceQty;
        }

        public void setNceQty(String nceQty) {
            this.nceQty = nceQty;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getEndTransferQty() {
            return endTransferQty;
        }

        public void setEndTransferQty(String endTransferQty) {
            this.endTransferQty = endTransferQty;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
