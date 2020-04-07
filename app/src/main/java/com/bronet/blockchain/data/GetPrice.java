package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/18.
 */

public class GetPrice {
    private int code;//": 0,
    private String btc2usd;
    private String eth2usd;
    private String btc2rbm;
    private String deceth2usd;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBtc2usd() {
        return btc2usd;
    }

    public void setBtc2usd(String btc2usd) {
        this.btc2usd = btc2usd;
    }

    public String getEth2usd() {
        return eth2usd;
    }

    public void setEth2usd(String eth2usd) {
        this.eth2usd = eth2usd;
    }

    public String getBtc2rbm() {
        return btc2rbm;
    }

    public void setBtc2rbm(String btc2rbm) {
        this.btc2rbm = btc2rbm;
    }

    public String getDeceth2usd() {
        return deceth2usd;
    }

    public void setDeceth2usd(String deceth2usd) {
        this.deceth2usd = deceth2usd;
    }

    public String getEth2rbm() {
        return eth2rbm;
    }

    public void setEth2rbm(String eth2rbm) {
        this.eth2rbm = eth2rbm;
    }

    private String eth2rbm;
}
