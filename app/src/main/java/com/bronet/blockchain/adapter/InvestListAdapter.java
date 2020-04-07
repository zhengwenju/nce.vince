package com.bronet.blockchain.adapter;//package com.bronet.blockchain.adapter;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.TextView;
//
//import com.bronet.blockchain.R;
//import com.bronet.blockchain.data.InvestLevelList;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import java.util.List;
//
//public class InvestListAdapter extends BaseQuickAdapter<InvestLevelList.ResultBean,BaseViewHolder> {
//    private ItemOnclick itemOnclick;
//
//    public void ItemOnclick(ItemOnclick itemOnclick){
//        this.itemOnclick=itemOnclick;
//    }
//    public interface ItemOnclick{
//        public void onCallBack();
//    }
//    public InvestListAdapter(int layoutResId, @Nullable List<InvestLevelList.ResultBean> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, InvestLevelList.ResultBean item) {
//        helper.setText(R.id.nceText,String.valueOf(item.getQty()));
//
// //       TextView nceText = helper.getView(R.id.nceText);
//    }
//}
