package com.bronet.blockchain.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.DetailsBean;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.type.MultipleItem;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.view.FloorCountDownLib.Center;
import com.bronet.blockchain.view.FloorCountDownLib.ICountDownCenter;
import com.bronet.blockchain.view.FloorCountDownLib.TimeBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.bronet.blockchain.type.MultipleItem.TYPE3;

/**
 * Created by 18514 on 2019/7/14.
 */

public class ContractAdapter extends BaseMultiItemQuickAdapter<InvestList.Result,ContractAdapter.ViewHolder> {
    ICountDownCenter countDownCenter;
    View itemView1;
    private ArrayList<DetailsBean.ResultBean.ItemsBean> items;
    private RecyclerView recyclerView;

    public ContractAdapter(OnItemChildClickListener mOnItemChildClickListener, List<InvestList.Result> data, ICountDownCenter countDownCenter) {
        super(data);
        this.countDownCenter = countDownCenter;
        addItemType(MultipleItem.TYPE1, R.layout.item_c);
        addItemType(MultipleItem.TYPE2, R.layout.item_c2);
        addItemType(TYPE3, R.layout.item_appointment);
        addItemType(MultipleItem.TYPE4, R.layout.item_guarantee);
        this.mOnItemChildClickListener = mOnItemChildClickListener;
        addItemType(MultipleItem.TYPE1, R.layout.item_c);
        addItemType(MultipleItem.TYPE2, R.layout.item_c2);
        addItemType(TYPE3, R.layout.item_appointment);
        addItemType(MultipleItem.TYPE4, R.layout.item_guarantee);

    }

    private OnItemChildClickListener mOnItemChildClickListener;




    public interface OnItemChildClickListener {
        void onItemReinvestClick(View view, int position);
        void onItemShowClick(int position);
        void onAppointItemChildClick(int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View itemView = getItemView(R.layout.item_c, parent); //todo
            final ViewHolder baseViewHolder = new ViewHolder(itemView);
            if(Const.IsClosePeriod==0) {
                countDownCenter.addObserver(baseViewHolder);
                countDownCenter.startCountdown();
            }
            final TextView ftTv = itemView.findViewById(R.id.btn_ft);
            ftTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int status = ftTv.getText().toString().equals(mContext.getString(R.string.invest10)) ? 1 : 0;
                    int i =baseViewHolder.getAdapterPosition();
                    L.test(i);
                    mOnItemChildClickListener.onItemReinvestClick(ftTv, baseViewHolder.getAdapterPosition() - 1);
                }
            });
            return baseViewHolder;
        } else if (viewType == 2) {
            View itemView = getItemView(R.layout.item_c2, parent);
            ViewHolder baseViewHolder = new ViewHolder(itemView);
            if(Const.IsClosePeriod==0) {
                countDownCenter.addObserver(baseViewHolder);
                countDownCenter.startCountdown();
            }
            return baseViewHolder;
        } else if (viewType == 3) {
            itemView1 = getItemView(R.layout.item_appointment, parent);
            final ViewHolder baseViewHolder = new ViewHolder(itemView1);
            if(Const.IsClosePeriod==0) {
                countDownCenter.addObserver(baseViewHolder);
                countDownCenter.startCountdown();
            }

            itemView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemChildClickListener.onItemShowClick(baseViewHolder.getAdapterPosition() - 1);
                }
            });

            final TextView textView = itemView1.findViewById(R.id.successful);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    L.test("convert====onClick 点击事件");
                    mOnItemChildClickListener.onAppointItemChildClick(baseViewHolder.getAdapterPosition() - 1);
                }
            });
            return baseViewHolder;

        } else if (viewType == 4) {
            View itemView = getItemView(R.layout.item_guarantee, parent);
            ViewHolder baseViewHolder = new ViewHolder(itemView);
            if(Const.IsClosePeriod==0) {
                countDownCenter.addObserver(baseViewHolder);
                countDownCenter.startCountdown();
            }
            return baseViewHolder;
        }

        return super.onCreateViewHolder(parent, viewType);
    }


    public int type=0;
    /**
     * statusMsg	string
     * 投资状态信息(已复投,已过期,自动复投中,未过期,投资中)
     * <p>
     * status	integer($int32)
     * 状态(0.有复投按钮,1.没有复投按钮)
     * <p>
     * autoStatusMsg	string
     * 自动复投信息(有"取消自动复投"提示的 才可用取消自动复投)
     *
     * @param baseViewHolder
     * @param result
     */
    @Override
    protected void convert(final ViewHolder baseViewHolder, final InvestList.Result result) {
        switch (baseViewHolder.getItemViewType()) {
            case MultipleItem.TYPE1:
                type=baseViewHolder.getItemViewType();
                baseViewHolder.setText(R.id.name, result.getCoinName());
                baseViewHolder.setText(R.id.type, result.getQty());
                baseViewHolder.setText(R.id.time, String.valueOf(result.getCreateTime()));
                baseViewHolder.setText(R.id.type1, result.getStatusMsg());
                baseViewHolder.setText(R.id.name, result.getCoinName());
                baseViewHolder.setText(R.id.type, result.getQty());
                baseViewHolder.setText(R.id.time, String.valueOf(result.getCreateTime()));

                if (result.getStatus() == 0) {
                    baseViewHolder.setVisible(R.id.btn_ft, false);
                } else if (result.getStatus() == 1) {
                    baseViewHolder.setVisible(R.id.btn_ft, true);
                    baseViewHolder.setText(R.id.btn_ft, mContext.getString(R.string.invest11));
                } else if (result.getStatus() == 2||result.getStatus() == 3) {
                    baseViewHolder.setVisible(R.id.btn_ft, true);
                    baseViewHolder.setText(R.id.btn_ft, mContext.getString(R.string.invest10));
                }else if (result.getStatus() == 3) {

                }
                String expireTime = result.getExpireTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = formatter.parse(expireTime);
                    long time = date.getTime();
                    time=time- System.currentTimeMillis();
                    TimeBean timeBean = new TimeBean(time/1000);
                    baseViewHolder.lastBindPositon = baseViewHolder.getAdapterPosition();
                    baseViewHolder.timeBean = timeBean;
                    if(Const.IS_FROZEN){
                        String freezeTime = result.getFreezeTime();
                        Date freezedate = formatter.parse(freezeTime);
                        long freezeTimeL = freezedate.getTime()/1000;
                        long expireTimeL = date.getTime()/1000;
                        baseViewHolder.setText(R.id.time1, getTime(expireTimeL-freezeTimeL));
                        L.test("freezeTime=========>>>");
                    }else {
                        bindCountView(((TextView) baseViewHolder.getView(R.id.time1)), timeBean);
                        L.test("freezeTime=========>>>no");
                    }
                    if (!countDownCenter.containHolder(baseViewHolder)){
                        countDownCenter.addObserver(baseViewHolder);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case MultipleItem.TYPE2:
                baseViewHolder.setText(R.id.number, result.getQty());
                baseViewHolder.setText(R.id.time, String.valueOf(result.getUpdateTime()));
                baseViewHolder.setText(R.id.type_name_tv,result.getTypeName());
                baseViewHolder.setText(R.id.name,result.getQtyCoinName());
                break;
            case TYPE3:
                baseViewHolder.setText(R.id.nameHb, String.valueOf(result.getQtyCoinName()));
                baseViewHolder.setText(R.id.time, String.valueOf(result.getCreateTime()));
                baseViewHolder.setText(R.id.successful, result.getStatusMsg());
                baseViewHolder.setText(R.id.number, result.getQty());
                baseViewHolder.setText(R.id.price, String.valueOf(result.getMicFee()));
                baseViewHolder.setText(R.id.num_Hb,String.valueOf(result.getFeeCoinName()));
                if (!result.isOpen()) {
                    baseViewHolder.setGone(R.id.linear, false);
                    recyclerView = itemView1.findViewById(R.id.recyclerView);
                } else if (result.isOpen()){
                    baseViewHolder.setGone(R.id.linear, true);
                }
                items = result.getItems();
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                baseViewHolder.recyclerView.setLayoutManager(layoutManager);
                SearchAdapter goodsAdapter = new SearchAdapter(R.layout.item_d, items);
                baseViewHolder.recyclerView.setAdapter(goodsAdapter);
                break;
            case MultipleItem.TYPE4:
                baseViewHolder.setText(R.id.time, String.valueOf(result.getCreateTime()));
                baseViewHolder.setText(R.id.state, result.getisInvest());
                baseViewHolder.setText(R.id.state, result.getStatusMsg());
                baseViewHolder.setText(R.id.number,String.valueOf(result.getbzMoney()));
                baseViewHolder.setText(R.id.name_Bz,result.getBzCoinName());
                Log.d(TAG, "result.getStatus():" + result.getStatus());
                baseViewHolder.setText(R.id.name, result.getuserName());

                break;
        }


    }


    public class ViewHolder extends BaseViewHolder implements Observer {
        int lastBindPositon = -1;
        TextView time;
        TimeBean timeBean;
        RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time1);
            recyclerView =view.findViewById(R.id.recyclerView);

        }

        @Override
        public void update(Observable o, Object arg) {

            if (arg != null && arg instanceof Center.PostionFL) {
                Center.PostionFL postionFL = (Center.PostionFL) arg;
                if (lastBindPositon >= postionFL.frist && lastBindPositon <= postionFL.last) {
                    Log.e("lmtlmtupdate", "update" + lastBindPositon);
                    bindCountView(time, timeBean);

                }
            }


        }


        //监控内存，可删除此方法实现
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            Log.e("lmtlmt", "finalize" + lastBindPositon);
        }
    }

    //倒计时展示和结束逻辑
    private static void bindCountView(TextView textView, TimeBean timeBean) {

        if (timeBean == null) return;
        //倒计时结束
        if (timeBean.getRainTime() <= 0) {
            textView.setText("");
            return;
        }

        textView.setText(getTime(timeBean.getRainTime()));

    }

    public static String getTime(long second) {
        String HMStime;
        long s = second % 86400;
        long day = second / 86400;
        second = s;
        long hour = second / 3600;
        long mint = (second % 3600) / 60;
        long sed = second % 60;
        String hourStr = String.valueOf(hour);
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        String mintStr = String.valueOf(mint);
        if (mint < 10) {
            mintStr = "0" + mintStr;
        }
        String sedStr = String.valueOf(sed);
        if (sed < 10) {
            sedStr = "0" + sedStr;
        }
        HMStime = (day > 0 ? day + "天" : "") + hourStr + "时" + mintStr + "分" + sedStr + "秒";
        return HMStime;
    }

}
