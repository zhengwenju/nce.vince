package com.bronet.blockchain.adapter;

import android.graphics.Color;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Assure;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/3.
 */

public class AssureAdapter extends BaseQuickAdapter<Assure.Result,BaseViewHolder> {
    private int thisPosition=-1;
    private TextView textView;
    public int getthisPosition() {
        return thisPosition;
    }
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }
    BaseActivity activity;
    public AssureAdapter(@LayoutRes int layoutResId, @Nullable List<Assure.Result> data, BaseActivity activity) {
        super(layoutResId, data);
        this.activity=activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, Assure.Result s) {
        holder.setText(R.id.tv1,s.getFormatData());
        textView = holder.getView(R.id.tv1);
        if (holder.getLayoutPosition() == getthisPosition()) {
            textView.setBackgroundResource(R.mipmap.db_bg_sel);
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setBackgroundResource(R.mipmap.db_bg);
            textView.setTextColor(Color.BLACK);
        }
    }
    public void setDbEdit(EditText dbEdit){
        dbEdit.setText(textView.getText().toString());
    }
}
