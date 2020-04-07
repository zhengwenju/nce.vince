package com.bronet.blockchain.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.Assets;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/15.
 */

public class AssetsAdapter extends BaseQuickAdapter<Assets.Items,BaseViewHolder> {

    private OnItemAssetsClickListener mOnItemAssetsClickListener;

    public void OnItemAssetsClickListener(OnItemAssetsClickListener mOnItemAssetsClickListener){
        this.mOnItemAssetsClickListener =mOnItemAssetsClickListener;
    }


    public interface OnItemAssetsClickListener{
        void onItemAssetsClick( int position);


    }

    private int thisPosition=0;
    public int getthisPosition() {
        return thisPosition;
    }
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }



    public AssetsAdapter(@LayoutRes int layoutResId, @Nullable List<Assets.Items> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Assets.Items items) {
        baseViewHolder.setText(R.id.name,items.getCoinType());
        Glide.with(mContext).load(items.getImgUrl()).into((ImageView)baseViewHolder.getView(R.id.image));


        LinearLayout aa = baseViewHolder.getView(R.id.aa);

            if (baseViewHolder.getLayoutPosition() == getthisPosition()) {
                aa.setBackgroundResource(R.drawable.blue_shape5);
            }else {
                aa.setBackgroundResource(R.drawable.blue_shape2);
            }



        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemAssetsClickListener.onItemAssetsClick(baseViewHolder.getAdapterPosition());
            }
        });


    }
}
