package com.bronet.blockchain.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.CenterActivit;
import com.bronet.blockchain.data.Type;
import com.bronet.blockchain.ui.help.HelpDataActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

public class ActivityCenterAdapter extends BaseQuickAdapter<CenterActivit.ResultBean, BaseViewHolder> {
    BaseActivity activity;
   /* private OnItemAssetsClickListener mOnItemAssetsClickListener;

    public void OnItemAssetsClickListener(OnItemAssetsClickListener mOnItemAssetsClickListener){
        this.mOnItemAssetsClickListener =mOnItemAssetsClickListener;
    }


    public interface OnItemAssetsClickListener{
        void onItemAssetsClick( int position);


    }*/

    public ActivityCenterAdapter(@LayoutRes int layoutResId, @Nullable List<CenterActivit.ResultBean> data, BaseActivity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CenterActivit.ResultBean s) {

        Glide.with(mContext).load(s.getImgUrl()).into((ImageView) baseViewHolder.getView(R.id.image));

        PercentLinearLayout imageView = baseViewHolder.getView(R.id.rebinding_tv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = s.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id",id);
                JumpUtil.overlay(activity, HelpDataActivity.class,bundle);
            }
        });



    }
}
