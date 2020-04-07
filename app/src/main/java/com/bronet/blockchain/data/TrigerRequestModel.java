package com.bronet.blockchain.data;

import java.util.List;

public class TrigerRequestModel {
    private String userId;
    private List<BetlistBean> betlist;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<BetlistBean> getBetlist() {
        return betlist;
    }

    public void setBetlist(List<BetlistBean> betlist) {
        this.betlist = betlist;
    }

    public static class BetlistBean {
        /**
         * qty : 0
         * num : 0
         */

        private int qty;
        private int num;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
