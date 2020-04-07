package com.bronet.blockchain.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TypeList;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

public class JournalismItemAdapter extends BaseQuickAdapter<TypeList.Data, BaseViewHolder> {
    private AssetsAdapter.OnItemAssetsClickListener mOnItemAssetsClickListener;

    public void OnItemAssetsClickListener(AssetsAdapter.OnItemAssetsClickListener mOnItemAssetsClickListener){
        this.mOnItemAssetsClickListener =mOnItemAssetsClickListener;
    }


    public interface OnItemAssetsClickListener{
        void onItemAssetsClick( int position);


    }

    public JournalismItemAdapter(@LayoutRes int layoutResId, @Nullable List<TypeList.Data> data) {
            super(layoutResId, data);
            }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TypeList.Data data) {
            baseViewHolder.setText(R.id.data,data.getTitle());
        Glide.with(mContext).load(data.getImgUrl()).into((ImageView)baseViewHolder.getView(R.id.image));
            baseViewHolder.setText(R.id.summary,data.getSummary());



        ImageView imageView = baseViewHolder.getView(R.id.image_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemAssetsClickListener.onItemAssetsClick(baseViewHolder.getAdapterPosition());
            }
        });

    }


}

