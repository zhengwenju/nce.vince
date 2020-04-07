package com.bronet.blockchain.adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.InvestLevelList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ContactChooseAdapter extends BaseQuickAdapter<InvestLevelList.ResultBean, BaseViewHolder> {
    public ContactChooseAdapter(int layoutResId, @Nullable List<InvestLevelList.ResultBean> data) {
        super(layoutResId, data);
    }

    private int thisPosition = -1;

    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public int getthisPosition() {
        return thisPosition;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InvestLevelList.ResultBean item) {
        try {
            helper.setText(R.id.value_tv, String.valueOf(item.getLevel()));
            if (helper.getLayoutPosition() == getthisPosition()) {
                helper.setBackgroundColor(R.id.relativeLayout,Color.parseColor("#4877f6"));
                helper.setTextColor(R.id.value_tv,Color.parseColor("#f4f4f4"));
            } else {
                helper.setBackgroundColor(R.id.relativeLayout,Color.parseColor("#f4f4f4"));
                helper.setTextColor(R.id.value_tv,Color.parseColor("#FF757575"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
