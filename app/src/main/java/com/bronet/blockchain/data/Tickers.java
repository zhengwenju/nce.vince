package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/15.
 */

public class Tickers {
    private int code;
    private ArrayList<Data> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<String> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<String> coins) {
        this.coins = coins;
    }

    private ArrayList<String> coins;
    public class Data{
        private String symbol;//": "LTC/USDK",
        private String last;//": "87.79",
        private String changePercentage;//": "-10.50%",
        private String change;//": "-10.30",
        private String dayLow;//": "84.30",
        private String dayHigh;//": "98.09",
        private String volume;//": "22955.356809",
        private int brokerId;//": 0,
        private String buy;//": "87.67",
        private String close;//": "87.79",
        private String coinVolume;//": "2067282.707851185",
        private String createdDate;//": "1563184105244",
        private String currencyId;//": "0",
        private String high;//": "98.09",
        private String inflows;//": "0",
        private String low;//": "84.30",
        private int marketFrom;//": 1009,
        private String name;//": "",
        private String open;//": "98.09",
        private String outflows;//": "0",
        private int productId;//": 909,

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

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getDayLow() {
            return dayLow;
        }

        public void setDayLow(String dayLow) {
            this.dayLow = dayLow;
        }

        public String getDayHigh() {
            return dayHigh;
        }

        public void setDayHigh(String dayHigh) {
            this.dayHigh = dayHigh;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
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

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getInflows() {
            return inflows;
        }

        public void setInflows(String inflows) {
            this.inflows = inflows;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public int getMarketFrom() {
            return marketFrom;
        }

        public void setMarketFrom(int marketFrom) {
            this.marketFrom = marketFrom;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
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

        private String sell;//": "87.91"
    }
}
