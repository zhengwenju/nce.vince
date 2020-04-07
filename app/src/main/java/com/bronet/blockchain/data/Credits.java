package com.bronet.blockchain.data;

import java.util.ArrayList;

/**
 * Created by 18514 on 2019/7/14.
 */

public class Credits {
     private int status;
    private String msg;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    private Result result;
    public class Result{
        private float totalScore;

        public int getUserCredit() {
            return userCredit;
        }

        public void setUserCredit(int userCredit) {
            this.userCredit = userCredit;
        }

        private int userCredit;
        private String updateTime;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;

        public float getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(float totalScore) {
            this.totalScore = totalScore;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public ArrayList<Items> getItems() {
            return items;
        }

        public void setItems(ArrayList<Items> items) {
            this.items = items;
        }

        private ArrayList<Items> items;
    }
    public class Items{
        private int id;
        private String name;
        private String score;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }

        private String totalScore;
    }
}
