package com.bronet.blockchain.adapter;

import android.view.View;
import android.widget.RelativeLayout;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.MyMineList;
import com.bronet.blockchain.data.MyNodeList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyMinListAdapter extends BaseQuickAdapter<MyMineList.ResultBean, BaseViewHolder> {
    public MyMinListAdapter(int layoutResId, @Nullable List<MyMineList.ResultBean> data) {
        super(layoutResId, data);
    }
    private BigDecimal num100=new BigDecimal(100);
    private MathContext mc = new MathContext(2);
    private OnItemClickListener mOnItemClickListener;
    public void OnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyMineList.ResultBean item) {




        helper.setText(R.id.time, item.getCreateTime());
        helper.setText(R.id.level_title_tv, item.getName());

        BigDecimal tatio = new BigDecimal(item.getHashrate());

        helper.setText(R.id.day_rate_tv, num100.multiply(tatio,mc) + "%");

        helper.setText(R.id.profit_times_tv, item.getMultiple());

        helper.setText(R.id.note_weight_tv, item.getOutput() + "");

        helper.setText(R.id.note_win_total_tv, item.getPeriod() + "");

        BigDecimal fuelRatio = new BigDecimal(item.getFuelRatio());
        helper.setText(R.id.fuel_consume_rate_tv, num100.multiply(fuelRatio,mc) + "%");
        RelativeLayout parentll = helper.getView(R.id.detail_linerlayout);
        parentll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(helper.getAdapterPosition());
            }
        });
    }
}
