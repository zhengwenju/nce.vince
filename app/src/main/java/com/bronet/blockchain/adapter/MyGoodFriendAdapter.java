package com.bronet.blockchain.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import android.widget.ImageView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.InvitedList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 18514 on 2019/7/3.
 */

public class MyGoodFriendAdapter extends BaseQuickAdapter<InvitedList.Result,BaseViewHolder> {
    BaseActivity activity;
    public MyGoodFriendAdapter(@LayoutRes int layoutResId, @Nullable List<InvitedList.Result> data, BaseActivity activity) {
        super(layoutResId, data);
        this.activity=activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, InvitedList.Result s) {
        holder.setText(R.id.name,s.getUsername()+s.getId());
        holder.setText(R.id.time,s.getRegisterTime());
        if (!s.getAvatar().equals("")) {
            Glide.with(activity).load(s.getAvatar()).apply(new RequestOptions().circleCrop().error(R.mipmap.dicon_37)).into((ImageView) holder.getView(R.id.iv));
        }
        holder.setText(R.id.type,s.getLockStatus()==0?"正常":"帐户异常");
        holder.setTextColor(R.id.type,activity.getResources().getColor(s.getLockStatus()==0?R.color.green:R.color.red));
        switch (s.getMemberLevel()) {
            case 0:
                holder.setImageResource(R.id.vip,R.mipmap.vip0);
                break;
            case 1:
                holder.setImageResource(R.id.vip,R.mipmap.vip1);
                break;
            case 2:
                holder.setImageResource(R.id.vip,R.mipmap.vip2);
                break;
            case 3:
                holder.setImageResource(R.id.vip,R.mipmap.vip3);
                break;
            case 4:
                holder.setImageResource(R.id.vip,R.mipmap.vip4);
                break;
            case 5:
                holder.setImageResource(R.id.vip,R.mipmap.vip5);
                break;
            case 6:
                holder.setImageResource(R.id.vip,R.mipmap.vip6);
                break;
        }
    }
}
