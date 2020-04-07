package com.bronet.blockchain.data;

import java.util.List;

public class MutualModelList {


    /**
     * status : 0
     * msg : string
     * result : [{"id":0,"nickName":"string","nId":"string","helpTypeMsg":"string","appTime":"2019-10-23T07:34:09.300Z","status":0,"statusMsg":"string","auditTime":"string"}]
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
         * id : 0
         * nickName : string
         * nId : string
         * helpTypeMsg : string
         * appTime : 2019-10-23T07:34:09.300Z
         * status : 0
         * statusMsg : string
         * auditTime : string
         */

        private int id;
        private String nickName;
        private String nId;
        private String helpTypeMsg;
        private String appTime;
        private int status;
        private String statusMsg;
        private String auditTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNId() {
            return nId;
        }

        public void setNId(String nId) {
            this.nId = nId;
        }

        public String getHelpTypeMsg() {
            return helpTypeMsg;
        }

        public void setHelpTypeMsg(String helpTypeMsg) {
            this.helpTypeMsg = helpTypeMsg;
        }

        public String getAppTime() {
            return appTime;
        }

        public void setAppTime(String appTime) {
            this.appTime = appTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }
    }
}
