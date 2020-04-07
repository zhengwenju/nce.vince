package com.bronet.blockchain.data;

import java.util.List;

public class TradeBuyList {


    /**
     * status : 0
     * msg :
     * result : {"qtyCoinName":"NCE","balanceCoinName":"NCE","finalPriceCoinName":"ETH","range":0.0021691,"items":[{"id":160,"createTime":" 12-17: 16:57:41","qty":"10000.00","balance":"7000.00","finalPrice":"15.1837","statusMsg":"撤回","status":1,"exchangeItems":[{"id":224,"qty":"3000.00","fee":"0.00","createTime":"2019-12-17 16:58:14","finalPrice":"6.5073","range":"0.0021691"}]},{"id":159,"createTime":" 12-09: 14:23:19","qty":"50.00","balance":"0.00001","finalPrice":"0.00000002","statusMsg":"已撤回","status":2,"exchangeItems":[{"id":209,"qty":"49.99999","fee":"0.00","createTime":"2019-12-09 14:23:38","finalPrice":"0.10845497","range":"0.0021691"}]},{"id":158,"createTime":" 12-09: 13:49:57","qty":"50.00","balance":"0.0001","finalPrice":"0.00000021","statusMsg":"已撤回","status":2,"exchangeItems":[{"id":205,"qty":"49.9999","fee":"0.00","createTime":"2019-12-09 13:50:40","finalPrice":"0.10845478","range":"0.0021691"}]},{"id":142,"createTime":" 10-16: 17:44:49","qty":"1.00","balance":"0.00","finalPrice":"0.00","statusMsg":"已完成","status":2,"exchangeItems":[{"id":179,"qty":"1.00","fee":"0.00","createTime":"2019-10-17 16:21:20","finalPrice":"0.0021691","range":"0.0021691"}]}]}
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
         * qtyCoinName : NCE
         * balanceCoinName : NCE
         * finalPriceCoinName : ETH
         * range : 0.0021691
         * items : [{"id":160,"createTime":" 12-17: 16:57:41","qty":"10000.00","balance":"7000.00","finalPrice":"15.1837","statusMsg":"撤回","status":1,"exchangeItems":[{"id":224,"qty":"3000.00","fee":"0.00","createTime":"2019-12-17 16:58:14","finalPrice":"6.5073","range":"0.0021691"}]},{"id":159,"createTime":" 12-09: 14:23:19","qty":"50.00","balance":"0.00001","finalPrice":"0.00000002","statusMsg":"已撤回","status":2,"exchangeItems":[{"id":209,"qty":"49.99999","fee":"0.00","createTime":"2019-12-09 14:23:38","finalPrice":"0.10845497","range":"0.0021691"}]},{"id":158,"createTime":" 12-09: 13:49:57","qty":"50.00","balance":"0.0001","finalPrice":"0.00000021","statusMsg":"已撤回","status":2,"exchangeItems":[{"id":205,"qty":"49.9999","fee":"0.00","createTime":"2019-12-09 13:50:40","finalPrice":"0.10845478","range":"0.0021691"}]},{"id":142,"createTime":" 10-16: 17:44:49","qty":"1.00","balance":"0.00","finalPrice":"0.00","statusMsg":"已完成","status":2,"exchangeItems":[{"id":179,"qty":"1.00","fee":"0.00","createTime":"2019-10-17 16:21:20","finalPrice":"0.0021691","range":"0.0021691"}]}]
         */

        private String qtyCoinName;
        private String balanceCoinName;
        private String finalPriceCoinName;
        private double range;
        private List<ItemsBean> items;


        public String getQtyCoinName() {
            return qtyCoinName;
        }

        public void setQtyCoinName(String qtyCoinName) {
            this.qtyCoinName = qtyCoinName;
        }

        public String getBalanceCoinName() {
            return balanceCoinName;
        }

        public void setBalanceCoinName(String balanceCoinName) {
            this.balanceCoinName = balanceCoinName;
        }

        public String getFinalPriceCoinName() {
            return finalPriceCoinName;
        }

        public void setFinalPriceCoinName(String finalPriceCoinName) {
            this.finalPriceCoinName = finalPriceCoinName;
        }

        public double getRange() {
            return range;
        }

        public void setRange(double range) {
            this.range = range;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 160
             * createTime :  12-17: 16:57:41
             * qty : 10000.00
             * balance : 7000.00
             * finalPrice : 15.1837
             * statusMsg : 撤回
             * status : 1
             * exchangeItems : [{"id":224,"qty":"3000.00","fee":"0.00","createTime":"2019-12-17 16:58:14","finalPrice":"6.5073","range":"0.0021691"}]
             */

            private int id;
            private String createTime;
            private String qty;
            private String balance;
            private String finalPrice;
            private String statusMsg;
            private int status;
            private boolean isOpen;

            public boolean isOpen() {
                return isOpen;
            }

            public void setOpen(boolean open) {
                isOpen = open;
            }

            private List<ExchangeItemsBean> exchangeItems;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getFinalPrice() {
                return finalPrice;
            }

            public void setFinalPrice(String finalPrice) {
                this.finalPrice = finalPrice;
            }

            public String getStatusMsg() {
                return statusMsg;
            }

            public void setStatusMsg(String statusMsg) {
                this.statusMsg = statusMsg;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<ExchangeItemsBean> getExchangeItems() {
                return exchangeItems;
            }

            public void setExchangeItems(List<ExchangeItemsBean> exchangeItems) {
                this.exchangeItems = exchangeItems;
            }

            public static class ExchangeItemsBean {
                /**
                 * id : 224
                 * qty : 3000.00
                 * fee : 0.00
                 * createTime : 2019-12-17 16:58:14
                 * finalPrice : 6.5073
                 * range : 0.0021691
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
    }
}
