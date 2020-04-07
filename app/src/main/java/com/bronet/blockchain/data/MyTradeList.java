package com.bronet.blockchain.data;

import java.util.List;

public class MyTradeList {
    /**
     * status : 0
     * msg : string
     * result : {"inCoinName":"string","outCoinName":"string","items":[{"id":0,"qty":"string","fee":"string","createTime":"2020-02-15T14:39:25.543Z","finalPrice":"string","range":"string"}]}
     */

    private int status;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * inCoinName : string
         * outCoinName : string
         * items : [{"id":0,"qty":"string","fee":"string","createTime":"2020-02-15T14:39:25.543Z","finalPrice":"string","range":"string"}]
         */

        private String inCoinName;
        private String outCoinName;
        private List<ItemsBean> items;

        public String getInCoinName() {
            return inCoinName;
        }

        public void setInCoinName(String inCoinName) {
            this.inCoinName = inCoinName;
        }

        public String getOutCoinName() {
            return outCoinName;
        }

        public void setOutCoinName(String outCoinName) {
            this.outCoinName = outCoinName;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 0
             * qty : string
             * fee : string
             * createTime : 2020-02-15T14:39:25.543Z
             * finalPrice : string
             * range : string
             */

            private int id;
            private String qty;
            private String fee;
            private String createTime;
            private String finalPrice;
            private String range;

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

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getFinalPrice() {
                return finalPrice;
            }

            public void setFinalPrice(String finalPrice) {
                this.finalPrice = finalPrice;
            }

            public String getRange() {
                return range;
            }

            public void setRange(String range) {
                this.range = range;
            }
        }
    }

    /**
     * status : 0
     * msg : string
     * result : [{"id":0,"qty":"string","fee":"string","createTime":"2019-09-11T03:47:56.528Z","finalPrice":"string","range":"string"}]
     */


}
