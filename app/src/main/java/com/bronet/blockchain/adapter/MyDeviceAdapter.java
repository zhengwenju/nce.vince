package com.bronet.blockchain.adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.DeviceModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyDeviceAdapter extends BaseQuickAdapter<DeviceModel.ResultBean,BaseViewHolder> {

    private OnItemEntrustClickListener mOnItemEntrustClickListener;

    public void OnItemEntrustClickListener(OnItemEntrustClickListener mOnItemEntrustClickListener){
        this.mOnItemEntrustClickListener =mOnItemEntrustClickListener;
    }
    public interface OnItemEntrustClickListener{
        public void onItemDeleteDeviceClick(View view, int position);
        public void onItemSetDeviceClick(View view, int position);
    }
    public MyDeviceAdapter(int layoutResId, @Nullable List<DeviceModel.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, DeviceModel.ResultBean item) {
        helper.setText(R.id.device_title,String.valueOf("设备"+item.getIndex()));
        helper.setText(R.id.device_tv,String.valueOf(item.getOsModel()));

//        int isDefault = item.getIsDefault();
//        if(isDefault==0) {
//            helper.setText(R.id.device_tv3, "当前设备");
//            helper.setTextColor(R.id.device_tv3, Color.parseColor("#FF757575"));
//        }else {
//
//        }

        helper.setText(R.id.device_tv3, "当前设备");
        helper.setTextColor(R.id.device_tv3, Color.parseColor("#FF757575"));
        final TextView device_tv2 = helper.getView(R.id.device_tv3);
        device_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemEntrustClickListener.onItemSetDeviceClick(device_tv2, helper.getAdapterPosition());
            }
        });

        final ImageView device_iv2 = helper.getView(R.id.device_iv2);
        device_iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemEntrustClickListener.onItemDeleteDeviceClick(device_iv2, helper.getAdapterPosition());
            }
        });


    }
}
