package com.bronet.blockchain.data;

public class TransferAgreement {

    /**
     * status : 0
     * msg :
     * result : 转让：
     ·账户为对方的NID编号,请仔细核对,以免转错账户。
     ·如果对方拒绝接受或您撤销, 您还享有该等认筹权益。
     ·对方确认后可以在“我的转让”中查询详情, 认筹份额转给对方后,您将不再享有该等权益。
     */

    private int status;
    private String msg;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
