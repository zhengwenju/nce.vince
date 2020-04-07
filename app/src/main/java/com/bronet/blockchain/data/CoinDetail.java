package com.bronet.blockchain.data;

import java.util.List;

/**
 * Created by 墨 · 烧 on 2019/7/18.
 */

public class CoinDetail {

    /**
     * status : 0
     * msg : string
     * result : [{"typeId":0,"coinName":"string"}]
     */

    private int status;
    private String msg;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * typeId : 0
         * coinName : string
         */

        private int typeId;
        private String coinName;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }
    }
}
