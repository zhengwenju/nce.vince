package com.bronet.blockchain.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.GetH24List;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by 18514 on 2019/6/27.
 */

public class Home2Adapter extends BaseQuickAdapter< GetH24List.Data ,BaseViewHolder> {
    public Home2Adapter(@LayoutRes int layoutResId, @Nullable List< GetH24List.Data> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper,  GetH24List.Data item) {
        String[] words = item.getSymbol().split("/");
        helper.setText(R.id.last,item.getLast());
        helper.setText(R.id.name,words[0]);
        helper.setText(R.id.name2,"/"+words[1]);
        helper.setText(R.id.gain,item.getChangePercentage());
        helper.getView(R.id.gain).setSelected(item.getChangePercentage().toString().substring(0,1).equals("+")?true:false);
        Log.d("###", "convert: "+item.getImgUrl());

        ImageView image = helper.getView(R.id.image1);
        ImageView image2 = helper.getView(R.id.image2);
        TextView  gain = helper.getView(R.id.gain);
        ImageView rise = helper.getView(R.id.rise);
        ImageView fall = helper.getView(R.id.fall);

        if (item.getChangePercentage().contains("+")){
            image.setVisibility(View.VISIBLE);
            image2.setVisibility(View.GONE);
            rise.setVisibility(View.VISIBLE);
            fall.setVisibility(View.GONE);
            gain.setTextColor(Color.GREEN);
        }else {
            image.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
            fall.setVisibility(View.VISIBLE);
            rise.setVisibility(View.GONE);
            gain.setTextColor(Color.RED);
        }
    }
}
