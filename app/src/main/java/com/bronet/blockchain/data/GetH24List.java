package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/3.
 */

public class GetH24List {
    private int code;

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

    private ArrayList<Data> data;
    public static class Data{
        private String symbol;
        private String last;
        private String changePercentage;
        private String change;
        private String dayLow;
        private String dayHigh;
        private String volume;
        private int brokerId;
        private String buy;
        private String close;
        private String coinVolume;
        private long createdDate;
        private String currencyId;
        private String high;
        private String inflows;
        private String low;
        private int marketFrom;
        private String open;
        private String outflows;
        private int productId;
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

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

        private String sell;
    }

}
