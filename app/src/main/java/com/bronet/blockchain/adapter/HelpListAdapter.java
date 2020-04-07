package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TypeList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/26.
 */

public class HelpListAdapter extends BaseQuickAdapter<TypeList.Data,BaseViewHolder> {
    public HelpListAdapter(@LayoutRes int layoutResId, @Nullable List<TypeList.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TypeList.Data data) {
        baseViewHolder.setText(R.id.data,data.getTitle());
    }
}
