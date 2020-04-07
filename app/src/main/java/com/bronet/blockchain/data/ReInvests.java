package com.bronet.blockchain.data;

import com.bronet.blockchain.util.L;

/**
 * Created by 墨 · 烧 on 2019/7/18.
 */

public class ReInvests {
     int status;

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public String getMsg () {
        return msg;
    }

    public void setMsg (String msg) {
        this.msg = msg;
    }

    String msg;

    @Override
    public String toString() {
        return "ReInvests{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
