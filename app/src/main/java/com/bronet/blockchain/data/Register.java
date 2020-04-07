package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/6/26.
 */

public class Register {
    private String msg;
    private int status;
    private Result result;
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public class Result{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        String id;
    }
}
