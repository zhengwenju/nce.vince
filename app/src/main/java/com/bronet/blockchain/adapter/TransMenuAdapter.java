package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TransCoins;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TransMenuAdapter extends BaseQuickAdapter<TransCoins.ResultBean,BaseViewHolder>{


    private OnItemChildClickListener mOnItemChildClickListener;

    public void OnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener){
        this.mOnItemChildClickListener =mOnItemChildClickListener;
    }
    public interface OnItemChildClickListener{
        public void onItemClick(View view, int position);

    }
    public TransMenuAdapter(int layoutResId, @Nullable List<TransCoins.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, final TransCoins.ResultBean item) {
        helper.setText(R.id.name,item.getInCoinName()+"/"+item.getOutCoinName());
    }
}
