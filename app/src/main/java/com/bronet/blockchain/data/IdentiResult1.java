package com.bronet.blockchain.data;

import java.math.BigDecimal;
import java.util.List;

public class IdentiResult1 {

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

        private int id;
        private double qty;
        private double gift;
        private double totalQty;
        private String coinName;
        private int selected;
        private double finalPrice;
        private String finalCoinName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQty() {
            String qty2 = new BigDecimal(qty).stripTrailingZeros().toPlainString();  //末尾去0
            return qty2;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }


        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public String getFinalCoinName() {
            return finalCoinName;
        }

        public double getGift() {
            return gift;
        }

        public void setGift(double gift) {
            this.gift = gift;
        }

        public double getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(double totalQty) {
            this.totalQty = totalQty;
        }

        public void setFinalCoinName(String finalCoinName) {
            this.finalCoinName = finalCoinName;
        }

        public String getFormatData(){
            String usdt = new BigDecimal(getQty()).stripTrailingZeros().toPlainString();  //末尾去0
            return usdt+"\n"+getCoinName();
        }
    }
}
