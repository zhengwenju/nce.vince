package com.bronet.blockchain.data;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by vince on 2019/7/6.
 */

public class Assure {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Result> getResult() {
        return result;
    }
    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    ArrayList<Result> result;
    public class Result{
        private int id;
        private Double usdt;
        private Double nce;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Double getUsdt() {
            return usdt;
        }

        public void setUsdt(Double usdt) {
            this.usdt = usdt;
        }

        public Double getNce() {
            return nce;
        }

        public void setNce(Double nce) {
            this.nce = nce;
        }

        public String getFormatData(){
            String usdt = new BigDecimal(getUsdt()).stripTrailingZeros().toPlainString();  //末尾去0
            return usdt+"USDT\n≈"+getNce()+"NCE";
        }
        public String getFormatData2(){
            return String.valueOf(getNce());
        }
    }
}
