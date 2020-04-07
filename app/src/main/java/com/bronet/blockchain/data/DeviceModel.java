package com.bronet.blockchain.data;

import java.util.List;

public class DeviceModel {


    /**
     * status : 0
     * msg : string
     * result : [{"index":0,"id":0,"osModel":"string","isDefault":0}]
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
         * index : 0
         * id : 0
         * osModel : string
         * isDefault : 0
         */

        private int index;
        private int id;
        private String osModel;
        private int isDefault;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOsModel() {
            return osModel;
        }

        public void setOsModel(String osModel) {
            this.osModel = osModel;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }
}
