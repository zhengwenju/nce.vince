package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.ImageView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Type;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/25.
 */

public class HelpAdapter extends BaseQuickAdapter<Type.Data,BaseViewHolder> {
    BaseActivity activity;
    public HelpAdapter(@LayoutRes int layoutResId, @Nullable List<Type.Data> data, BaseActivity activity) {
        super(layoutResId, data);
        this.activity=activity;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Type.Data s) {
        baseViewHolder.setText(R.id.name,s.getName());
        Glide.with(mContext).load(s.getImgUrl()).into((ImageView)baseViewHolder.getView(R.id.iv));
        Log.d("%%%%", "convert: "+s.getImgUrl());
    }
}
