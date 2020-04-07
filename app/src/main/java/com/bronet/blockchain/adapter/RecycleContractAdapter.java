package com.bronet.blockchain.adapter;//package com.bronet.blockchain.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bronet.blockchain.R;
//import com.bronet.blockchain.data.Appointment;
//import com.bronet.blockchain.data.DetailsBean;
//import com.bronet.blockchain.data.InvestList;
//import com.bronet.blockchain.Model.ApiService;
//import com.bronet.blockchain.type.MultipleItem;
//import com.bronet.blockchain.util.L;
//import com.bronet.blockchain.util.NetWorkManager;
//import com.bronet.blockchain.view.FloorCountDownLib.Center;
//import com.bronet.blockchain.view.FloorCountDownLib.ICountDownCenter;
//import com.bronet.blockchain.view.FloorCountDownLib.TimeBean;
//import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
//import static com.bronet.blockchain.type.MultipleItem.TYPE3;
//
///**
// * Created by 18514 on 2019/7/14.
// */
//
//public class RecycleContractAdapter extends RecyclerView.Adapter<RecycleContractAdapter.ViewHolder>{
//    private List<InvestList.Result> playerList;
//    public RecycleContractAdapter(List<InvestList.Result> playerList) {
//        this.playerList = playerList;
//    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_c,parent,false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView imageView;
//        private TextView textView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.item_imgv);
//            textView = itemView.findViewById(R.id.item_tv);
//        }
//    }
//    //在onBindViewHolder（）中完成对数据的填充
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
//        holder.imageView.setImageResource(playerList.get(position).getImgID());
//        holder.textView.setText(playerList.get(position).getPlayerName());
//    }
//    //这个方法很简单了，返回playerList中的子项的个数
//    @Override
//    public int getItemCount() {
//        return playerList.size();
//    }
//}