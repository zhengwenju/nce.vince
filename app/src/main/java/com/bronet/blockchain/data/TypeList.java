package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/26.
 */

public class TypeList {
    private int status;//": 0,
    private String msg;//": "string",
    private int pageSize;//": 0,
    private int pageNo;//": 0,
    private int totalPage;//": 0,

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

    private int totalCount;//": 0
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

    public void setResult(ArrayList<Data> data) {
        this.data = data;
    }

    private ArrayList<Data> data;
    public class Data{
        private int id;//": 0,
        private String title;
        private String summary;
        private String imgUrl;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


    }
}
