package com.bronet.blockchain.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.NodeList;
import com.bronet.blockchain.util.L;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NodeListAdapter extends BaseQuickAdapter<NodeList.ResultBean, BaseViewHolder> {
    private OnItemChildClickListener mOnItemChildClickListener;

    private BigDecimal num100=new BigDecimal(100);
    private MathContext mc = new MathContext(2);
    public void OnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    public interface OnItemChildClickListener {
        void onItemReinvestClick(Button purchase, int position);

        void onItemShowClick(int position);
    }


    public NodeListAdapter(int layoutResId, @Nullable List<NodeList.ResultBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NonNull final BaseViewHolder helper, NodeList.ResultBean item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.name1, item.getName());

        BigDecimal tatio = new BigDecimal(item.getRatio());
        helper.setText(R.id.ratio, num100.multiply(tatio, mc)+ "%");
        helper.setText(R.id.multiple, String.valueOf(item.getMultiple()));
        helper.setText(R.id.qty, item.getQty() + "枚");
        helper.setText(R.id.totalQty, item.getTotalQty() + "枚");


        BigDecimal fuelRatio = new BigDecimal(item.getFuelRatio());
        helper.setText(R.id.fuelRatio, num100.multiply(fuelRatio,mc) + "%");

        final Button Purchase = helper.getView(R.id.Purchase);
        Purchase.setOnClickListener(view -> mOnItemChildClickListener.onItemReinvestClick(Purchase, helper.getAdapterPosition()));
        if (item.getBuyStatus() == 0) {
            helper.setGone(R.id.Purchase, true);  //显示
            helper.setText(R.id.Purchase, "买入");
            helper.setEnabled(R.id.Purchase, true);
        } else if (item.getBuyStatus() == 1) {
            helper.setGone(R.id.Purchase, true);  //显示
            helper.setText(R.id.Purchase, "购买成功");
            helper.setEnabled(R.id.Purchase, false);
        } else if (item.getBuyStatus() == 2) {
            helper.setGone(R.id.Purchase, false);  //隐藏
        }

        LinearLayout parentll = helper.getView(R.id.parent_linerlayout);
        parentll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemChildClickListener.onItemShowClick(helper.getAdapterPosition());
            }
        });
    }


}
