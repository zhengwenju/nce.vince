package com.bronet.blockchain.adapter;

import android.graphics.Color;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.IdentiResult1;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/3.
 */

public class IdentiAdapter extends BaseQuickAdapter<IdentiResult1.ResultBean,BaseViewHolder> {
    private int thisPosition=-1;
    private TextView textView,textView2,textView3;
    private RelativeLayout relativeLayout;
    public int getthisPosition() {
        return thisPosition;
    }
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }
    public IdentiAdapter(@LayoutRes int layoutResId, @Nullable List<IdentiResult1.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, IdentiResult1.ResultBean s) {
        relativeLayout = holder.getView(R.id.rel);
        textView = holder.getView(R.id.tv1);
        textView2 = holder.getView(R.id.tv2);
        textView3 = holder.getView(R.id.tv3);
//        if (holder.getLayoutPosition() == getthisPosition()) {
//            textView.setBackgroundResource(R.mipmap.db_bg_sel);
//            textView.setTextColor(Color.WHITE);
//        } else {
//            textView.setBackgroundResource(R.mipmap.db_bg);
//            textView.setTextColor(Color.BLACK);
//        }

        if(s.getSelected()==0){
            relativeLayout.setBackgroundResource(R.mipmap.db_bg);
            textView.setTextColor(Color.BLACK);
            textView2.setTextColor(Color.BLACK);
            textView3.setTextColor(Color.BLACK);
        }else {
            relativeLayout.setBackgroundResource(R.mipmap.db_bg_sel);
            textView.setTextColor(Color.WHITE);
            textView2.setTextColor(Color.WHITE);
            textView3.setTextColor(Color.WHITE);
        }
        holder.setText(R.id.tv1,String.valueOf(s.getQty()));
        holder.setText(R.id.tv2,String.valueOf(s.getCoinName()));
        holder.setText(R.id.tv3,String.valueOf("+赠送"+s.getGift()));
    }
    public void setDbEdit(EditText dbEdit){
        dbEdit.setText(textView.getText().toString());
    }
}
