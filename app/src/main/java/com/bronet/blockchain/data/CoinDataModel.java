package com.bronet.blockchain.data;

public class CoinDataModel {


    /**
     * status : 0
     * msg : string
     * result : {"id1":0,"address1":"string","id2":0,"address2":"string"}
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
         * id1 : 0
         * address1 : string
         * id2 : 0
         * address2 : string
         */

        private int id1;
        private String address1;
        private int id2;
        private String address2;

        public int getId1() {
            return id1;
        }

        public void setId1(int id1) {
            this.id1 = id1;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public int getId2() {
            return id2;
        }

        public void setId2(int id2) {
            this.id2 = id2;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }
    }
}
