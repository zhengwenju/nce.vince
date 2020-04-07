package com.bronet.blockchain.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/15.
 */

public class Assets {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    private Result result;
    public class  Result{
        private String nceToUsd;//": "string",
        private String ethToUsd;//": "string",
        private String btcToUsd;//": "string",
        private String totalInvest;//": "string",
        private String totalAssets;//": "string",

        public String getNceToUsd() {
            return nceToUsd;
        }

        public void setNceToUsd(String nceToUsd) {
            this.nceToUsd = nceToUsd;
        }

        public String getEthToUsd() {
            return ethToUsd;
        }

        public void setEthToUsd(String ethToUsd) {
            this.ethToUsd = ethToUsd;
        }

        public String getBtcToUsd() {
            return btcToUsd;
        }

        public void setBtcToUsd(String btcToUsd) {
            this.btcToUsd = btcToUsd;
        }

        public String getTotalInvest() {
            return totalInvest;
        }

        public void setTotalInvest(String totalInvest) {
            this.totalInvest = totalInvest;
        }

        public String getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(String totalAssets) {
            this.totalAssets = totalAssets;
        }

        public ArrayList<Assets.Items> getItems() {
            return items;
        }

        public void setItems(ArrayList<Assets.Items> items) {
            items = items;
        }

        private ArrayList<Items> items;
    }
    public class Items implements Serializable {
        /**
         * coinId : 1
         * coinType : ETH
         * banlance : 1.77629718
         * imgUrl : http://api.ncecoin.io/images/eth.png
         * freeze : 0.00
         * usd : 327.56439147
         */

        private int coinId;
        private String coinType;
        private String banlance;
        private String imgUrl;
        private String freeze;
        private String usd;

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public String getCoinType() {
            return coinType;
        }

        public void setCoinType(String coinType) {
            this.coinType = coinType;
        }

        public String getBanlance() {
            return banlance;
        }

        public void setBanlance(String banlance) {
            this.banlance = banlance;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getFreeze() {
            return freeze;
        }

        public void setFreeze(String freeze) {
            this.freeze = freeze;
        }

        public String getUsd() {
            return usd;
        }

        public void setUsd(String usd) {
            this.usd = usd;
        }




    }
}
