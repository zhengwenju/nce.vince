package com.bronet.blockchain.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/6/27.
 */

public class TitleAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {
    int type=-1;
    int SS=1;
    private int thisPosition=-1;
    private TextView textView;
    public int getthisPosition() {
        return thisPosition;
    }
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }
    public TitleAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name,item);
        helper.getView(R.id.name).setSelected(helper.getItemViewType()==SS?true:false);
        textView = helper.getView(R.id.name);
        if (helper.getLayoutPosition() == getthisPosition()) {
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15f);
        } else {
            textView.setTextColor(Color.parseColor("#FF757575"));
            textView.setTextSize(14f);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position==type?SS:super.getItemViewType(position);
    }

    public void setType(int type,RecyclerView titRv) {
        if (this.type==type){
            return;
        }else {
            TextView vo = (TextView) getViewByPosition(titRv, this.type, R.id.name);
            if (vo!=null){
                vo.setSelected(false);
            }
            TextView v = (TextView) getViewByPosition(titRv, type, R.id.name);
            v.setSelected(true);
            this.type = type;
        }
    }
}
