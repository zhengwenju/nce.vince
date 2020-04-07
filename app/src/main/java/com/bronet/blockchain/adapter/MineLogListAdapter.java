package com.bronet.blockchain.adapter;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.NodeLog;
import com.bronet.blockchain.data.UserMineLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MineLogListAdapter  extends BaseQuickAdapter<UserMineLog.ResultBean, BaseViewHolder> {
    public MineLogListAdapter(int layoutResId, @Nullable List<UserMineLog.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserMineLog.ResultBean item) {
        helper.setText(R.id.prize_time, item.getFuel());
        helper.setText(R.id.prize_consume, item.getCreateTime());
        helper.setText(R.id.prize_num, item.getQty());
    }
}
