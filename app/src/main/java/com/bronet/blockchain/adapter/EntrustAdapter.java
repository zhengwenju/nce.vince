package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TradeBuyList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class EntrustAdapter extends BaseQuickAdapter<TradeBuyList.ResultBean.ItemsBean,BaseViewHolder> {

    private MyEntrustAdapter.OnItemEntrustClickListener mOnItemEntrustClickListener;

    public void OnItemEntrustClickListener(MyEntrustAdapter.OnItemEntrustClickListener mOnItemEntrustClickListener){
        this.mOnItemEntrustClickListener =mOnItemEntrustClickListener;
    }
    public interface OnItemEntrustClickListener{
        public void onItemWithdrawClick(View view, int position);

    }
    public EntrustAdapter(int layoutResId, @Nullable List<TradeBuyList.ResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, TradeBuyList.ResultBean.ItemsBean item) {
        helper.setText(R.id.time,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.num,String.valueOf(item.getQty()));
        helper.setText(R.id.Snum,String.valueOf(item.getBalance()));
        helper.setText(R.id.Zprice,String.valueOf(item.getFinalPrice()));
        helper.setText(R.id.Purchase, String.valueOf(item.getStatusMsg()));
        final TextView Purchase = helper.getView(R.id.Purchase);

        Purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemEntrustClickListener.onItemWithdrawClick(Purchase, helper.getAdapterPosition());
            }
        });
    }
}
