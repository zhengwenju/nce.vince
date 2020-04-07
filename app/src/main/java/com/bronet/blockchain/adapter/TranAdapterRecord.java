package com.bronet.blockchain.adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.MyTransfer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TranAdapterRecord extends BaseQuickAdapter<MyTransfer.ResultBean,BaseViewHolder>{

    private OnItemChildClickListener mOnItemChildClickListener;

    public void OnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener){
        this.mOnItemChildClickListener =mOnItemChildClickListener;
    }
    public interface OnItemChildClickListener{
        public void onItemAcceptClick(View view, int position);
        public void onItemRefuseClick(View view, int position);

    }

    public TranAdapterRecord(int layoutResId, @Nullable List<MyTransfer.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, final MyTransfer.ResultBean item) {
        helper.setText(R.id.name,item.getUserName());
        helper.setText(R.id.time,item.getCreateTime());
        helper.setText(R.id.num,String.valueOf(item.getNceQty()));
        helper.setText(R.id.num_Bz,"/"+item.getCoinName());
      // helper.setText(R.id.successful,String.valueOf(item.getStatus()));
        if (item.getStatus() == 0) {
            helper.setVisible(R.id.accept,true);
            helper.setVisible(R.id.refuse,true);
            helper.setGone(R.id.successful,true);
        } else {
            helper.setVisible(R.id.accept,false);
            helper.setVisible(R.id.refuse,false);
            helper.setVisible(R.id.successful,true);
            helper.setText(R.id.successful, item.getStatusMsg());
            helper.setTextColor(R.id.successful,Color.parseColor("#FF8A8A8A"));
        }
        final TextView accept = helper.getView(R.id.accept);
        final TextView refuse = helper.getView(R.id.refuse);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChildClickListener.onItemAcceptClick(accept, helper.getAdapterPosition());
            }
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChildClickListener.onItemRefuseClick(refuse, helper.getAdapterPosition());
            }
        });
    }
}
