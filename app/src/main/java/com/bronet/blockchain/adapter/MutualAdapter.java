package com.bronet.blockchain.adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.MutualModelList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MutualAdapter extends BaseQuickAdapter<MutualModelList.ResultBean,BaseViewHolder> {

    private OnItemSetStatusClickListener mOnItemEntrustClickListener;

    public void OnItemEntrustClickListener(OnItemSetStatusClickListener mOnItemEntrustClickListener){
        this.mOnItemEntrustClickListener =mOnItemEntrustClickListener;
    }
    public interface OnItemSetStatusClickListener {
        public void onItemSetStatusClick(View view, int position);
    }
    public MutualAdapter(int layoutResId, @Nullable List<MutualModelList.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, MutualModelList.ResultBean item) {
        //(0.待通过,1.未通过,2.已通过)
        if(item.getStatus()==0) {
            helper.setVisible(R.id.unread_iv, true);
            helper.setText(R.id.status_msg_tv, item.getStatusMsg());
            helper.setTextColor(R.id.status_msg_tv, Color.BLUE);
        }else if(item.getStatus()==1) {
            helper.setVisible(R.id.unread_iv, false);
            helper.setText(R.id.status_msg_tv, item.getStatusMsg());
            helper.setTextColor(R.id.status_msg_tv, Color.GRAY);
        }else if(item.getStatus()==2) {
            helper.setVisible(R.id.unread_iv, false);
            helper.setText(R.id.status_msg_tv, item.getStatusMsg());
            helper.setTextColor(R.id.status_msg_tv, Color.GRAY);
        }
        helper.setText(R.id.replay_time_tv,String.valueOf(item.getAppTime()));
        helper.setText(R.id.audit_time_tv,String.valueOf(item.getAuditTime()));
        helper.setText(R.id.reset_pwd_tv,String.valueOf(item.getHelpTypeMsg()));
        helper.setText(R.id.nick_name_tv,String.valueOf(item.getNickName()));
        helper.setText(R.id.nid_tv,String.valueOf(item.getNId()));

        final TextView status_msg_tv = helper.getView(R.id.status_msg_tv);
        status_msg_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemEntrustClickListener.onItemSetStatusClick(status_msg_tv, helper.getAdapterPosition());
            }
        });



    }
}
