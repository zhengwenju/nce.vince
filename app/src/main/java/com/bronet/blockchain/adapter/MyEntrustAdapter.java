package com.bronet.blockchain.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.TradeBuyList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyEntrustAdapter extends BaseQuickAdapter<TradeBuyList.ResultBean.ItemsBean,BaseViewHolder> {
    private OnItemEntrustClickListener mOnItemEntrustClickListener;
     RecyclerView recyclerView;
    private List<TradeBuyList.ResultBean.ItemsBean.ExchangeItemsBean> exchangeItems;
    private RelativeLayout linear;
    private OnItemChildClickListener mOnItemChildClickListener;

    public void OnItemEntrustClickListener(OnItemEntrustClickListener mOnItemEntrustClickListener){
        this.mOnItemEntrustClickListener =mOnItemEntrustClickListener;
    }
    public interface OnItemEntrustClickListener{
         void onItemWithdrawClick(View view, int position);


    }
    public void OnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener){
        this.mOnItemChildClickListener =mOnItemChildClickListener;
    }
    public interface OnItemChildClickListener{


        void onItemShowClick(int position);
    }





    public MyEntrustAdapter(int layoutResId, @Nullable List<TradeBuyList.ResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, TradeBuyList.ResultBean.ItemsBean item) {
        helper.setText(R.id.time,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.num,String.valueOf(item.getQty()));
        helper.setText(R.id.Snum,String.valueOf(item.getBalance()));
        helper.setText(R.id.Zprice,String.valueOf(item.getFinalPrice()));
        helper.setText(R.id.withdraw, String.valueOf(item.getStatusMsg()));
        final TextView withdraw = helper.getView(R.id.withdraw);
       LinearLayout aa = helper.getView(R.id.aa);
       aa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mOnItemChildClickListener.onItemShowClick(helper.getAdapterPosition()       );
           }
       });


        linear = helper.getView(R.id.linear);
        recyclerView = helper.getView(R.id.recyclerView);
        if (!item.isOpen()) {
            helper.setGone(R.id.linear, false);
            recyclerView = helper.getView(R.id.recyclerView);
        } else if (item.isOpen()){
            helper.setGone(R.id.linear, true);

        }


        exchangeItems = item.getExchangeItems();
        Log.d(TAG, "convert: "+item.getExchangeItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        MyTwoListAdapter myTwoListAdapter = new MyTwoListAdapter(R.layout.item_myent, exchangeItems);
        recyclerView.setAdapter(myTwoListAdapter);


        if(item.getStatus()==1){
            withdraw.setBackgroundResource(R.drawable.btn_blue_bg);  //可点击
        }else {
            withdraw.setBackgroundResource(R.drawable.btn_blue_tr_bg); //显示状态
        }


        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemEntrustClickListener.onItemWithdrawClick(withdraw, helper.getAdapterPosition());
            }
        });




    }
}
