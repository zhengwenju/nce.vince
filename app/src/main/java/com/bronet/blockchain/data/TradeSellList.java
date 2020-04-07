package com.bronet.blockchain.data;

import java.util.List;

public class TradeSellList {

    /**
     * status : 0
     * msg :
     * result : [{"inCoinName":"NCE","qty":"5100.00","outCoinName":"ETH","price":"336.648291272"},{"inCoinName":"NCE","qty":"5100.00","outCoinName":"ETH","price":"331.9212364"},{"inCoinName":"NCE","qty":"5100.00","outCoinName":"ETH","price":"419.2151353"},{"inCoinName":"NCE","qty":"10200.00","outCoinName":"ETH","price":"419.271249395"},{"inCoinName":"NCE","qty":"5100.00","outCoinName":"ETH","price":"423.13361994"},{"inCoinName":"NCE","qty":"10200.00","outCoinName":"ETH","price":"423.16978729"},{"inCoinName":"NCE","qty":"10000.00","outCoinName":"ETH","price":"429.33747408"},{"inCoinName":"NCE","qty":"20000.00","outCoinName":"ETH","price":"429.28498032"},{"inCoinName":"NCE","qty":"1000.00","outCoinName":"ETH","price":"467.2688638"},{"inCoinName":"NCE","qty":"1000.00","outCoinName":"ETH","price":"467.25932095"},{"inCoinName":"NCE","qty":"20000.00","outCoinName":"ETH","price":"462.8254775"},{"inCoinName":"NCE","qty":"1.00","outCoinName":"ETH","price":"483.16046142578125"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"299.00","outCoinName":"ETH","price":"0.00"},{"inCoinName":"NCE","qty":"1.00","outCoinName":"ETH","price":"0.00"}]
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
         * inCoinName : NCE
         * qty : 5100.00
         * outCoinName : ETH
         * price : 336.648291272
         */

        private String inCoinName;
        private String qty;
        private String outCoinName;
        private String price;

        public String getInCoinName() {
            return inCoinName;
        }

        public void setInCoinName(String inCoinName) {
            this.inCoinName = inCoinName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getOutCoinName() {
            return outCoinName;
        }

        public void setOutCoinName(String outCoinName) {
            this.outCoinName = outCoinName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
