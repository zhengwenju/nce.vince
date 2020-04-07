package com.bronet.blockchain.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by 墨 · 烧 on 2019/7/18.
 */

public class NceEthRange {
    int status;
    String msg;

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

    public BigDecimal getResult () {
        return result;
    }

    public void setResult (BigDecimal result) {
        this.result = result;
    }

    BigDecimal result;
}
