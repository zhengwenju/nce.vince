package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/26.
 */

public class Info {

    private int status;//": 0,

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getResult() {
        return result;
    }

    public void setResult(Data result) {
        this.result = result;
    }

    private Data result;
    public class Data {
        private int id;//": 0,
        private String title;//": "string",
        private String content;//": "string",
        private String readCount;//": 0,
        private String author;//": "string",
        private String avatar;//": "string",
        private String updateTime;//": "string"

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReadCount() {
            return readCount;
        }

        public void setReadCount(String readCount) {
            this.readCount = readCount;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
