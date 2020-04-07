package com.bronet.blockchain.adapter;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/26.
 */

public class Feedback {
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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    int status;
    String msg;
    private ArrayList<Data> data;
    int pageSize;
    int pageNo;
    int totalPage;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    int totalCount;
    public class Data{
        private int id;//": 0,
        private String userAvatar;//": "string",
        private String feedback;//": "string",
        private String fujian;//": "string",
        private String sysAvatar;//": "string",
        private String answer;//": "string",
        private String createTime;//": "2019-07-26T03:03:28.903Z",
        private int typeId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public String getSysAvatar() {
            return sysAvatar;
        }

        public void setSysAvatar(String sysAvatar) {
            this.sysAvatar = sysAvatar;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        private String updateTime;//": "2019-07-26T03:03:28.903Z"

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", userAvatar='" + userAvatar + '\'' +
                    ", feedback='" + feedback + '\'' +
                    ", fujian='" + fujian + '\'' +
                    ", sysAvatar='" + sysAvatar + '\'' +
                    ", answer='" + answer + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    '}';
        }
    }
}
