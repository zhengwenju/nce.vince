package com.bronet.blockchain.data;

public class CoinTypeResp {


    /**
     * code : 0
     * data : {"bestAskSize":"0.30262869","bestBidSize":"4.36865925","brokerId":0,"buy":"7504.1","change":"+68.9","changePercentage":"+0.93%","close":"7504.6","coinVolume":"134658551.3","createdDate":1578277285082,"currencyId":"0","dayHigh":"7523.5","dayLow":"7319.0","high":"7523.5","indexPrice":"7504.7","inflows":"0","last":"7504.6","lastQty":"0.00190663","low":"7319.0","markPrice":"7504.6","marketFrom":118,"name":{},"open":"7435.7","outflows":"0","productId":20,"sell":"7504.2","symbol":"btc_usdt","volume":"18104.4"}
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
         * bestAskSize : 0.30262869
         * bestBidSize : 4.36865925
         * brokerId : 0
         * buy : 7504.1
         * change : +68.9
         * changePercentage : +0.93%
         * close : 7504.6
         * coinVolume : 134658551.3
         * createdDate : 1578277285082
         * currencyId : 0
         * dayHigh : 7523.5
         * dayLow : 7319.0
         * high : 7523.5
         * indexPrice : 7504.7
         * inflows : 0
         * last : 7504.6
         * lastQty : 0.00190663
         * low : 7319.0
         * markPrice : 7504.6
         * marketFrom : 118
         * name : {}
         * open : 7435.7
         * outflows : 0
         * productId : 20
         * sell : 7504.2
         * symbol : btc_usdt
         * volume : 18104.4
         */

        private String bestAskSize;
        private String bestBidSize;
        private int brokerId;
        private String buy;
        private String change;
        private String changePercentage;
        private String close;
        private String coinVolume;
        private long createdDate;
        private String currencyId;
        private String dayHigh;
        private String dayLow;
        private String high;
        private String indexPrice;
        private String inflows;
        private String last;
        private String lastQty;
        private String low;
        private String markPrice;
        private int marketFrom;
        private NameBean name;
        private String open;
        private String outflows;
        private int productId;
        private String sell;
        private String symbol;
        private String volume;

        public String getBestAskSize() {
            return bestAskSize;
        }

        public void setBestAskSize(String bestAskSize) {
            this.bestAskSize = bestAskSize;
        }

        public String getBestBidSize() {
            return bestBidSize;
        }

        public void setBestBidSize(String bestBidSize) {
            this.bestBidSize = bestBidSize;
        }

        public int getBrokerId() {
            return brokerId;
        }

        public void setBrokerId(int brokerId) {
            this.brokerId = brokerId;
        }

        public String getBuy() {
            return buy;
        }

        public void setBuy(String buy) {
            this.buy = buy;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getChangePercentage() {
            return changePercentage;
        }

        public void setChangePercentage(String changePercentage) {
            this.changePercentage = changePercentage;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getCoinVolume() {
            return coinVolume;
        }

        public void setCoinVolume(String coinVolume) {
            this.coinVolume = coinVolume;
        }

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
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

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getIndexPrice() {
            return indexPrice;
        }

        public void setIndexPrice(String indexPrice) {
            this.indexPrice = indexPrice;
        }

        public String getInflows() {
            return inflows;
        }

        public void setInflows(String inflows) {
            this.inflows = inflows;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getLastQty() {
            return lastQty;
        }

        public void setLastQty(String lastQty) {
            this.lastQty = lastQty;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getMarkPrice() {
            return markPrice;
        }

        public void setMarkPrice(String markPrice) {
            this.markPrice = markPrice;
        }

        public int getMarketFrom() {
            return marketFrom;
        }

        public void setMarketFrom(int marketFrom) {
            this.marketFrom = marketFrom;
        }

        public NameBean getName() {
            return name;
        }

        public void setName(NameBean name) {
            this.name = name;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getOutflows() {
            return outflows;
        }

        public void setOutflows(String outflows) {
            this.outflows = outflows;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getSell() {
            return sell;
        }

        public void setSell(String sell) {
            this.sell = sell;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public static class NameBean {
        }
    }
}
