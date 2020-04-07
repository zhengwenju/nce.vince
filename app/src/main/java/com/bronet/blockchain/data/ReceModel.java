package com.bronet.blockchain.data;

/**
 * Created by vince on 2019/7/6.
 */

public class ReceModel {


    /**
     * status : 0
     * msg : string
     * result : {"userName":"string","qty":0,"statusMsg":"string","status":0,"createTime":"2019-08-30T03:22:07.699Z"}
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
         * userName : string
         * qty : 0
         * statusMsg : string
         * status : 0
         * createTime : 2019-08-30T03:22:07.699Z
         */

        private String userName;
        private int qty;
        private String statusMsg;
        private int status;
        private String createTime;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
