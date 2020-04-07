package com.bronet.blockchain.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.Tickers;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/6/27.
 */

public class QuotationAdapter extends BaseQuickAdapter<Tickers.Data ,BaseViewHolder> {
    public QuotationAdapter(@LayoutRes int layoutResId, @Nullable List< Tickers.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tickers.Data item) {
        String[] words = item.getSymbol().split("/");
        helper.setText(R.id.name,words[0]);
        helper.setText(R.id.name2,"/"+words[1]);
        helper.setText(R.id.price,item.getLast());
        helper.setText(R.id.gain,item.getChangePercentage());
        helper.getView(R.id.gain).setSelected(item.getChangePercentage().toString().substring(0,1).equals("+")?true:false);
        helper.setText(R.id.number,String.valueOf(item.getMarketFrom()));
        helper.setText(R.id.money,item.getVolume());
        TextView gain = helper.getView(R.id.gain);
        ImageView rise = helper.getView(R.id.rise);
        ImageView fall = helper.getView(R.id.fall);

        if (item.getChangePercentage().contains("+")){
            rise.setVisibility(View.VISIBLE);
            fall.setVisibility(View.GONE);
            gain.setTextColor(Color.GREEN);
        }else {
            fall.setVisibility(View.VISIBLE);
            rise.setVisibility(View.GONE);
            gain.setTextColor(Color.RED);
        }
    }
}
