package com.bronet.blockchain.data;

import com.bronet.blockchain.type.MultipleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 18514 on 2019/7/15.
 */

public class InvestList {
    int status;
    String msg;
    ArrayList<Result> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }


    public class Result extends MultipleItem {
        int id;
        String qty;
        boolean isOpen;
        String expireTime;
        String freezeTime;
        String updateTime;
        String autoStatusMsg;
        String coinName;
        int status;
        String createTime;
        String bzMoney;
        String userName;
        String isInvest;
        String statusMsg;
        double micFee;
        private String qtyCoinName;
        private String feeCoinName;
        String typeName;
        String bzCoinName;


        public String getFreezeTime() {
            return freezeTime;
        }

        public void setFreezeTime(String freezeTime) {
            this.freezeTime = freezeTime;
        }

        public String getBzCoinName() {
            return bzCoinName;
        }

        public void setBzCoinName(String bzCoinName) {
            this.bzCoinName = bzCoinName;
        }

        private ArrayList<DetailsBean.ResultBean.ItemsBean> items;

        public String getQtyCoinName() {
            return qtyCoinName;
        }

        public void setQtyCoinName(String qtyCoinName) {
            this.qtyCoinName = qtyCoinName;
        }

        public String getFeeCoinName() {
            return feeCoinName;
        }

        public void setFeeCoinName(String feeCoinName) {
            this.feeCoinName = feeCoinName;
        }

        public double getMicFee() {
            return micFee;
        }

        public void setMicFee(double micFee) {
            this.micFee = micFee;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Result(int itemType) {
            super(itemType);
        }

        public String getAutoStatusMsg() {
            return autoStatusMsg;
        }

        public void setAutoStatusMsg(String autoStatusMsg) {
            this.autoStatusMsg = autoStatusMsg;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getbzMoney() {
            return bzMoney;
        }

        public void setbzMoney(String bzMoney) {
            this.bzMoney = bzMoney;
        }

        public String getisInvest() {
            return isInvest;
        }

        public void setisInvest(int bzMoney) {
            this.isInvest = isInvest;
        }

        public String getuserName() {
            return userName;
        }

        public void setuserName(String userName) {
            this.userName = userName;
        }

        public ArrayList<DetailsBean.ResultBean.ItemsBean> getItems() {
            return items;
        }

        public void setItems(ArrayList<DetailsBean.ResultBean.ItemsBean> items) {
            this.items = items;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return "Result{" +
                    ", qty='" + qty + '\'' +
                    ", expireTime='" + expireTime + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", autoStatusMsg='" + autoStatusMsg + '\'' +
                    ", coinName='" + coinName + '\'' +
                    ", statusMsg='" + statusMsg + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", bzMoney=" + bzMoney +
                    ", isInvest='" + isInvest + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof Result)) return false;
//            Result result = (Result) o;
//            return
//                    isOpen() == result.isOpen() &&
//
//                    Double.compare(result.getMicFee(), getMicFee()) == 0 &&
//                    getQty().equals(result.getQty()) &&
//                    getExpireTime().equals(result.getExpireTime()) &&
//                    getFreezeTime().equals(result.getFreezeTime()) &&
//                    getUpdateTime().equals(result.getUpdateTime()) &&
//                    getAutoStatusMsg().equals(result.getAutoStatusMsg()) &&
//                    getCoinName().equals(result.getCoinName()) &&
//                    getCreateTime().equals(result.getCreateTime()) &&
//                    bzMoney.equals(result.bzMoney) &&
//                    userName.equals(result.userName) &&
//                    isInvest.equals(result.isInvest) &&
//                    getStatusMsg().equals(result.getStatusMsg()) &&
//                    getQtyCoinName().equals(result.getQtyCoinName()) &&
//                    getFeeCoinName().equals(result.getFeeCoinName()) &&
//                    getTypeName().equals(result.getTypeName()) &&
//                    getBzCoinName().equals(result.getBzCoinName()) &&
//                    getItems().equals(result.getItems());
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash( getQty(), isOpen(), getExpireTime(), getFreezeTime(), getUpdateTime(), getAutoStatusMsg(), getCoinName(), getCreateTime(), bzMoney, userName, isInvest, getStatusMsg(), getMicFee(), getQtyCoinName(), getFeeCoinName(), getTypeName(), getBzCoinName(), getItems());
//        }
    }
}
