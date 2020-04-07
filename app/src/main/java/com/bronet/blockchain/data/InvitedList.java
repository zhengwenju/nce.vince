package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/6.
 */

public class InvitedList {
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

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }

    private Result[] result;
    public class Result{
        private int id;//": 0,
        private String username;//": "string",
        private String avatar;//": "string",
        private int lockStatus;//": 0,
        private int memberLevel;//": 0,

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(int lockStatus) {
            this.lockStatus = lockStatus;
        }

        public int getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(int memberLevel) {
            this.memberLevel = memberLevel;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        private String registerTime;//": "2019-07-12T01:24:33.514Z"
    }
}
