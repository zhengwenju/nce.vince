package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.WithdrawLog2;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/16.
 */

public class WMAdjustLogAdapter extends BaseQuickAdapter<WithdrawLog2.ResultBean,BaseViewHolder> {
    public WMAdjustLogAdapter(@LayoutRes int layoutResId, @Nullable List<WithdrawLog2.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WithdrawLog2.ResultBean s) {
        baseViewHolder.setText(R.id.start_quto_num_tv,mContext.getString(R.string.startBalance)+"："+s.getStartTransferQty()+" NCE");
        baseViewHolder.setText(R.id.end_quto_num_tv,mContext.getString(R.string.endBalance)+"："+s.getEndTransferQty()+" NCE");
        baseViewHolder.setText(R.id.tb_num_tv,mContext.getString(R.string.ethQty)+"："+s.getEthQty()+" ETH");
        baseViewHolder.setText(R.id.rate_tv,mContext.getString(R.string.rate)+"："+s.getRate());
        baseViewHolder.setText(R.id.createTime_tv,s.getCreateTime());
        if(s.getTradeType()==0){ //合约到期
            baseViewHolder.setText(R.id.type_tv,"合约到期");
            baseViewHolder.setVisible(R.id.tb_num_tv,false);
            baseViewHolder.setVisible(R.id.rate_tv,false);
        }else {  //提币
            baseViewHolder.setText(R.id.type_tv,"提币");
            baseViewHolder.setVisible(R.id.tb_num_tv,true);
            baseViewHolder.setVisible(R.id.rate_tv,true);
        }
    }
}
