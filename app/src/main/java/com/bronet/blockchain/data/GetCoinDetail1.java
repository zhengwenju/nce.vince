package com.bronet.blockchain.data;

public class GetCoinDetail1 {

    /**
     * code : 0
     * data : {"symbol":"eth_usdt","last":"143.06","changePercentage":"-0.16%","dayHigh":"148.00","dayLow":"138.83","volume":"393584.25"}
     * detailMsg :
     * msg :
     */

    private int code;
    private DataBean data;
    private String detailMsg;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * symbol : eth_usdt
         * last : 143.06
         * changePercentage : -0.16%
         * dayHigh : 148.00
         * dayLow : 138.83
         * volume : 393584.25
         */

        private String symbol;
        private String last;
        private String changePercentage;
        private String dayHigh;
        private String dayLow;
        private String volume;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getChangePercentage() {
            return changePercentage;
        }

        public void setChangePercentage(String changePercentage) {
            this.changePercentage = changePercentage;
        }

        public String getDayHigh() {
            return dayHigh;
        }

        public void setDayHigh(String dayHigh) {
            this.dayHigh = dayHigh;
        }

        public String getDayLow() {
            return dayLow;
        }

        public void setDayLow(String dayLow) {
            this.dayLow = dayLow;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }
    }
}
