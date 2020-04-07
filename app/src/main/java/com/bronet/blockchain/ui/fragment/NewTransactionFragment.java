package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MyFragmentPagerAdapter;
import com.bronet.blockchain.adapter.SellRecordAdapter;
import com.bronet.blockchain.adapter.BuyRecordAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.TradeBuyList;
import com.bronet.blockchain.data.TradeList;
import com.bronet.blockchain.data.TradeSellList;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.data.TransSell;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.HistoricalRecordActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.MyViewPager;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 新交易
 * Created by 18514 on 2019/6/27.
 */

public class NewTransactionFragment extends BaseFragment implements AllEntrustFragmet.IAllEntrust, MyEntrustFragmet.IMyEntrust {
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.per_tv1)
    TextView perTv1;
    @BindView(R.id.per_tv2)
    TextView perTv2;
    @BindView(R.id.per_tv3)
    TextView perTv3;
    @BindView(R.id.per_tv4)
    TextView perTv4;
    @BindView(R.id.per_rel1)
    RelativeLayout perRel1;
    @BindView(R.id.per_rel2)
    RelativeLayout perRel2;
    @BindView(R.id.per_rel3)
    RelativeLayout perRel3;
    @BindView(R.id.per_rel4)
    RelativeLayout perRel4;
    @BindView(R.id.wt_tv1)
    TextView wtTv1;
    @BindView(R.id.wt_tv2)
    TextView wtTv2;
    @BindView(R.id.wt_tv3)
    TextView wtTv3;
    @BindView(R.id.qty_etv)
    EditText qtyEtv;
    @BindView(R.id.buyIn_btn)
    Button BuyInBtn;
    @BindView(R.id.coin_Label_tv)
    TextView coinBabelTv;
    @BindView(R.id.balance_tv)
    TextView balanceTv;
    @BindView(R.id.buy_record_tv1)
    TextView buyRecordTv1;
    @BindView(R.id.buy_record_tv2)
    TextView buyRecordTv2;
    @BindView(R.id.choose_coin_tv)
    TextView chooseCoinTv;
    @BindView(R.id.ldentifyC)
    RadioButton mLdentifyC;
    @BindView(R.id.myTransfer)
    RadioButton mMyTransfer;
    /*@BindView(R.id.fram_bill)
    FrameLayout mFramBill;*/
    @BindView(R.id.linear_time)
    LinearLayout linear_time;
    @BindView(R.id.linear_num)
    LinearLayout linear_num;
    @BindView(R.id.linear_Surplus)
    LinearLayout linear_Surplus;
    @BindView(R.id.linear_Total)
    LinearLayout linear_Total;
    @BindView(R.id.loding)
    ProgressBar loding;
    private AllEntrustFragmet allEntrustFragmet;
    private MyEntrustFragmet myEntrustFragmet;
    @BindView(R.id.menu_coin_iv)
    ImageView menuCoinIv;
    @BindView(R.id.sell_recyclerView)
    RecyclerView sellRecyclerView;
    @BindView(R.id.buy_recyclerView)
    RecyclerView buyRecyclerView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    public static String TAG="NewTransactionFragment";
    private String conntQuote;
    @BindView(R.id.btn_Purchase)
    Button mBtnPurchase;
    @BindView(R.id.btn_Sell_out)
    Button mBtnSellOut;
    @BindView(R.id.Sellout_btn)
    Button SelloutBtn;
    @BindView(R.id.rate_tv)
    TextView rateTv;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.upper)
    ImageView upper;
    @BindView(R.id.lower)
    ImageView lower;
    @BindView(R.id.upper_num)
    ImageView upper_num;
    @BindView(R.id.lower_num)
    ImageView lower_num;
    @BindView(R.id.upper_Surplus)
    ImageView upper_Surplus;
    @BindView(R.id.lower_Surplus)
    ImageView lower_Surplus;
    @BindView(R.id.lower_Total)
    ImageView lower_Total;
    @BindView(R.id.historicalRecord)
    TextView mHistoricalRecord;
    @BindView(R.id.viewPager)
    MyViewPager viewPager;

    private int pageSize=10;
    private int pageNo=1;
    private List<TradeSellList.ResultBean> sellRecords;  //卖出记录
    private List<TradeList.ResultBean> buyRecords;       //买入记录
    private BuyRecordAdapter buyRecordAdapter;
    private SellRecordAdapter sellRecordAdapter;

    private int buySellRecord=0;
    private int flag=iOperaBuySell.BUY;
    private TransCoins.ResultBean thisBean;
    int tag = 1;

    @Override
    protected void initData() {

        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        initFragment();
        //委托货币兑换列表
        EntrustChange();

    }

    //切换交易币种
    public void checkCoin(TransCoins.ResultBean bean){
        thisBean=bean;
        chooseCoinTv.setText(bean.getInCoinName()+"/"+bean.getOutCoinName());
        coinBabelTv.setText(bean.getOutCoinName());
        Banlance(bean.getOutCoinType());
        buyRecordTv1.setText(getString(R.string.transf7)+"("+bean.getOutCoinName()+")");
        buyRecordTv2.setText(getString(R.string.transf4)+"("+bean.getInCoinName()+")");

        inCoinType=bean.getInCoinType();
        outCoinType=bean.getOutCoinType();

        inCoinName=bean.getInCoinName();
        outCoinName=bean.getOutCoinName();


        qtyEtv.setText("");

        allEntrustFragmet.setData(bean);
        myEntrustFragmet.setData(bean);

        GetRange(outCoinType,inCoinType);


        if (sellRecords != null) {
            sellRecords.clear();
        }
        if (buyRecords != null) {
            buyRecords.clear();
        }

        //交易记录（卖出记录）
        sellRecordList(inCoinType,outCoinType);
        //交易记录（买入记录）
        buyRecordList(inCoinType,outCoinType);
//        monitorBuyClick();
        if(Const.TransactionFromData==1){
            mBtnSellOut.performClick();
        }

    }
    //
    public void monitorBuyClick(){
        if(mBtnPurchase!=null){
//            mBtnPurchase.performClick();
//            EntrustChange();
        }
        if(mLdentifyC!=null){
            mLdentifyC.performClick();
        }
    }
    @Override
    protected int setLayoutId() {
        return R.layout.activity_new_trans_vf;
    }
    @Override
    protected void initEvent() {
        qtyEtv.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        qtyEtv.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 9) {
                        return "";
                    }
                }
                return null;
            }
        }});
        qtyEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                if (s.length() == 1 && s.toString().equals(".")) {
                    qtyEtv.setText("");
                    return;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        smartRefreshLayout.setEnableRefresh(true);//启用刷新
        smartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo=1;
                //账号余额
                if(Const.TransactionFromData==0){//切换到买入
                    Banlance(outCoinType);
                    GetRange(outCoinType,inCoinType);
                }else {                         //切换到卖出
                    Banlance(inCoinType);
                    GetRange(inCoinType,outCoinType);
                }

                if(Const.DelagateFromData==0){
                    //全部委托
                    pageNo=1;
                    allEntrustFragmet.setData(thisBean);
                }else {
                    //我的委托
                    myEntrustFragmet.setData(thisBean);
                }

                if (sellRecords != null) {
                    sellRecords.clear();
                }
                if (buyRecords != null) {
                    buyRecords.clear();
                }
                //交易记录（卖出记录）
                sellRecordList(inCoinType,outCoinType);
                //交易记录（买入记录）
                buyRecordList(inCoinType,outCoinType);

                smartRefreshLayout.finishRefresh();

                Const.SORT_ORDERNAME =null;
                Const.SORT_ORDERBY_NUM=0;


            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                pageNo++;
                if(!TextUtils.isEmpty(Const.SORT_ORDERNAME)){
                    allEntrustFragmet.setDataPage(thisBean,pageNo,pageSize,Const.SORT_ORDERNAME,Const.SORT_ORDERBY_NUM);
                    smartRefreshLayout.finishLoadMore();
                }else {
                    allEntrustFragmet.setDataPage(thisBean,pageNo,pageSize,"",0);
                    smartRefreshLayout.finishLoadMore();
                }

            }
        });
        //切换到买入
        mBtnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnSellOut.setBackgroundResource(R.drawable.blue_shape8);
                mBtnSellOut.setTextColor(Color.RED);
                mBtnPurchase.setBackgroundResource(R.drawable.blue_shape7);
                mBtnPurchase.setTextColor(Color.WHITE);
                /*sellBtn.setBackgroundResource(R.drawable.blue_shape7);
                sellBtn.setTextColor(Color.WHITE);*/
                BuyInBtn.setVisibility(View.VISIBLE);
                SelloutBtn.setVisibility(View.GONE);
//                rateTv.setTextColor(Color.GREEN);

                Banlance(outCoinType);

                coinBabelTv.setText(outCoinName);
                clearRecord();
                flag=iOperaBuySell.BUY;

                Const.TransactionFromData=0;

                if(Const.DelagateFromData==0){
                    pageNo=1; //全部委托
                    allEntrustFragmet.setData(thisBean);
                }else {       //我的委托
                    myEntrustFragmet.setData(thisBean);
                }

//                wtTv1.setText(getString(R.string.transf4)+thisBean.getOutCoinName());
//                wtTv2.setText(getString(R.string.transf5)+thisBean.getOutCoinName());
//                wtTv3.setText(getString(R.string.transf6)+thisBean.getInCoinName());

                mHistoricalRecord.setText(getString(R.string.transf11));
                buySellRecord=0;

                GetRange(outCoinType,inCoinType);
                mLdentifyC.setText(getString(R.string.transf13));
            }
        });
        //切换到卖出
        mBtnSellOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnSellOut.setBackgroundResource(R.drawable.blue_shape9);
                mBtnSellOut.setTextColor(Color.WHITE);
                mBtnPurchase.setBackgroundResource(R.drawable.blue_shape10);
                mBtnPurchase.setTextColor(Color.parseColor("#00f2c6"));
                /*sellBtn.setBackgroundResource(R.drawable.blue_shape9);
                sellBtn.setTextColor(Color.WHITE);*/
                BuyInBtn.setVisibility(View.GONE);
                SelloutBtn.setVisibility(View.VISIBLE);
//                rateTv.setTextColor(Color.RED);

                clearRecord();
                flag=iOperaBuySell.SELL;

                Const.TransactionFromData=1;

                if(Const.DelagateFromData==0){
                    pageNo=1;
                    allEntrustFragmet.setData(thisBean);
                }else {
                    myEntrustFragmet.setData(thisBean);
                }



                Banlance(inCoinType);
                coinBabelTv.setText(inCoinName);

//                wtTv1.setText(getString(R.string.transf4)+thisBean.getInCoinName());
//                wtTv2.setText(getString(R.string.transf5)+thisBean.getInCoinName());
//                wtTv3.setText(getString(R.string.transf6)+thisBean.getOutCoinName());

                mHistoricalRecord.setText(getString(R.string.transf12));
                buySellRecord=1;

                GetRange(inCoinType,outCoinType);

                mLdentifyC.setText(getString(R.string.transf14));
            }
        });

        linear_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Const.DelagateFromData==1){
                    return;
                }
                pageNo=1;
                if (tag==1){
                    upper.setImageResource(R.drawable.c);
                    lower.setImageResource(R.drawable.a);
                    tag = 0;
                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"createTime",0);
                }else{
                    upper.setImageResource(R.drawable.b);
                    lower.setImageResource(R.drawable.d);
                    tag = 1;
                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"createTime",1);
                }
                Const.SORT_ORDERBY_NUM =tag;
                Const.SORT_ORDERNAME ="createTime";
            }
        });
        linear_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Const.DelagateFromData==1){
                    return;
                }
                pageNo=1;
                if (tag==1){
                    lower_num.setImageResource(R.drawable.d);
                    upper_num.setImageResource(R.drawable.b);

                    tag = 0;
                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"qty",0);
                }else{
                    lower_num.setImageResource(R.drawable.a);
                    upper_num.setImageResource(R.drawable.c);
                    tag = 1;
                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"qty",1);
                }
                Const.SORT_ORDERBY_NUM =tag;
                Const.SORT_ORDERNAME ="qty";
            }
        });
        linear_Surplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Const.DelagateFromData==1){
                    return;
                }
                pageNo=1;
                if (tag==1){
                    lower_Surplus.setImageResource(R.drawable.d);
                    upper_Surplus.setImageResource(R.drawable.b);
                    tag = 0;
                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"balance",0);
                }else{
                    lower_Surplus.setImageResource(R.drawable.a);
                    upper_Surplus.setImageResource(R.drawable.c);
                    tag = 1;
                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"balance",1);

                }
                Const.SORT_ORDERBY_NUM =tag;
                Const.SORT_ORDERNAME ="balance";
            }
        });
//        linear_Total.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pageNo=1;
//                if (tag==1){
//                    upper_Total.setImageResource(R.drawable.c);
//                    lower_Total.setImageResource(R.drawable.a);
//                    tag = 0;
//                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"finalPrice",0);
//                }else{
//                    upper_Total.setImageResource(R.drawable.b);
//                    lower_Total.setImageResource(R.drawable.d);
//                    tag = 1;
//                    allEntrustFragmet.setDataSort(thisBean,pageNo,pageSize,"finalPrice",1);
//                }
//            }
//        });
        mHistoricalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Const.BUYSELLRECORD,buySellRecord);
                bundle.putSerializable(Const.BUYSELLRECORDBEAN,thisBean);
                JumpUtil.overlay(getActivity(), HistoricalRecordActivity.class,bundle);
            }
        });
    }


    private void refreshData(){
        //全部委托
        allEntrustFragmet.setData(thisBean);
        //我的委托
        myEntrustFragmet.setData(thisBean);

        if (sellRecords != null) {
            sellRecords.clear();
        }
        if (buyRecords != null) {
            buyRecords.clear();
        }
        //卖出记录
        sellRecordList(inCoinType,outCoinType);
        //买入记录
        buyRecordList(inCoinType,outCoinType);

        clearRecord();

        if(Const.TransactionFromData==0){
            Banlance(outCoinType);
            GetRange(outCoinType,inCoinType);
        }else {
            Banlance(inCoinType);
            GetRange(inCoinType,outCoinType);
        }
    }
    private void clearRecord(){
        perRel1.setBackgroundResource(R.drawable.blue_shape21);
        perRel2.setBackgroundResource(R.drawable.blue_shape21);
        perRel3.setBackgroundResource(R.drawable.blue_shape21);
        perRel4.setBackgroundResource(R.drawable.blue_shape21);
        perTv1.setTextColor(Color.parseColor("#FF757575"));
        perTv2.setTextColor(Color.parseColor("#FF757575"));
        perTv3.setTextColor(Color.parseColor("#FF757575"));
        perTv4.setTextColor(Color.parseColor("#FF757575"));
        qtyEtv.setText("");
    }
    private String outCoinName;
    private String inCoinName;
    private int inCoinType;
    private int outCoinType;

    @Override
    public void refresh() {
        refreshData();
    }

    @Override
    public void updataTitle(TradeBuyList tradeBuyList) {
        TradeBuyList.ResultBean result = tradeBuyList.getResult();
        L.test("2QWER"+result);
        if(result!=null) {
            wtTv1.setText(getString(R.string.transf4) + result.getQtyCoinName());
            wtTv2.setText(getString(R.string.transf5) + result.getBalanceCoinName());
            wtTv3.setText(getString(R.string.transf6) + result.getFinalPriceCoinName());
        }

    }

    @Override
    public void updataMyTitle(TradeBuyList buyList) {
        TradeBuyList.ResultBean result = buyList.getResult();
        if(result!=null) {
            wtTv1.setText(getString(R.string.transf4) + result.getQtyCoinName());
            wtTv2.setText(getString(R.string.transf5) + result.getBalanceCoinName());
            wtTv3.setText(getString(R.string.transf6) + result.getFinalPriceCoinName());
        }
    }

    public interface iOperaBuySell{
        int BUY=1;
        int SELL=2;
    }


    private void initFragment() {
        mLdentifyC.setSelected(true);
        //获取布局管理器
        //Fragment
        List<Fragment> list = new ArrayList<>();
        allEntrustFragmet = new AllEntrustFragmet();
        myEntrustFragmet = new MyEntrustFragmet();
        list.add(allEntrustFragmet);
        list.add(myEntrustFragmet);
        allEntrustFragmet.setIAllEntrust(this);
        myEntrustFragmet.setIMyEntrust(this);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(myFragmentPagerAdapter);

        final int currentSelectedPosition = viewPager.getCurrentItem();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                viewPager.resetHeight(position);
                if (position==0){
                    //设置选中
                    pageNo=1;
                    Const.DelagateFromData=0;
                    mLdentifyC.setSelected(true);
                    mMyTransfer.setSelected(false);
                    allEntrustFragmet.setData(thisBean);
                    smartRefreshLayout.setEnableLoadMore(true);
                    upper.setVisibility(View.VISIBLE);
                    lower.setVisibility(View.VISIBLE);
                    upper_num.setVisibility(View.VISIBLE);
                    lower_num.setVisibility(View.VISIBLE);
                    upper_Surplus.setVisibility(View.VISIBLE);
                    lower_Surplus.setVisibility(View.VISIBLE);
                    mLdentifyC.setTextSize(15);
                    mLdentifyC.setTextColor(Color.parseColor("#ffffff"));
                    mMyTransfer.setTextSize(13);
                    mMyTransfer.setTextColor(Color.parseColor("#999999"));


                }else if (position==1){
                    viewPager.resetHeight(position);
                    Const.DelagateFromData=1;
                    mLdentifyC.setSelected(false);
                    mMyTransfer.setSelected(true);
                    myEntrustFragmet.setData(thisBean);
                    smartRefreshLayout.setEnableLoadMore(false);
                    upper.setVisibility(View.GONE);
                    lower.setVisibility(View.GONE);
                    upper_num.setVisibility(View.GONE);
                    lower_num.setVisibility(View.GONE);
                    upper_Surplus.setVisibility(View.GONE);
                    lower_Surplus.setVisibility(View.GONE);
                    mLdentifyC.setTextSize(13);
                    mLdentifyC.setTextColor(Color.parseColor("#999999"));
                    mMyTransfer.setTextSize(15);
                    mMyTransfer.setTextColor(Color.parseColor("#ffffff"));
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.resetHeight(0);
    }


    @OnClick({R.id.ldentifyC, R.id.myTransfer})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ldentifyC: //全部委托
                viewPager.setCurrentItem(0);
                pageNo=1;
                Const.DelagateFromData=0;
                mLdentifyC.setSelected(true);
                mMyTransfer.setSelected(false);
                allEntrustFragmet.setData(thisBean);
                smartRefreshLayout.setEnableLoadMore(true);
                upper.setVisibility(View.VISIBLE);
                lower.setVisibility(View.VISIBLE);
                upper_num.setVisibility(View.VISIBLE);
                lower_num.setVisibility(View.VISIBLE);
                upper_Surplus.setVisibility(View.VISIBLE);
                mLdentifyC.setTextSize(15);
                mLdentifyC.setTextColor(Color.parseColor("#ffffff"));
                mMyTransfer.setTextSize(13);
                mMyTransfer.setTextColor(Color.parseColor("#999999"));
                break;
            case R.id.myTransfer: //我的委托
                viewPager.setCurrentItem(1);
                Const.DelagateFromData=1;
                mLdentifyC.setSelected(false);
                mMyTransfer.setSelected(true);
                myEntrustFragmet.setData(thisBean);
                smartRefreshLayout.setEnableLoadMore(false);
                upper.setVisibility(View.GONE);
                lower.setVisibility(View.GONE);
                upper_num.setVisibility(View.GONE);
                lower_num.setVisibility(View.GONE);
                upper_Surplus.setVisibility(View.GONE);
                lower_Surplus.setVisibility(View.GONE);
                mLdentifyC.setTextSize(13);
                mLdentifyC.setTextColor(Color.parseColor("#999999"));
                mMyTransfer.setTextSize(15);
                mMyTransfer.setTextColor(Color.parseColor("#ffffff"));
                break;
        }

    }


    @Override
    public void onResume() {
        Const.SORT_ORDERNAME =null;
        Const.SORT_ORDERBY_NUM=0;
        super.onResume();
        monitorBuyClick();
    }

    @Override
    public void onPause() {
        super.onPause();
//        Const.TransactionFromData=0;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        LinearLayoutManager gridLayoutManager  = new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        sellRecyclerView.setLayoutManager(gridLayoutManager);
        sellRecordAdapter = new SellRecordAdapter(R.layout.sell_out_record_item, sellRecords);
        sellRecyclerView.setAdapter(sellRecordAdapter);

        LinearLayoutManager gridLayoutManager1  = new LinearLayoutManager(getActivity());
        gridLayoutManager1.setOrientation(GridLayout.VERTICAL);
        buyRecyclerView.setLayoutManager(gridLayoutManager1);
        buyRecordAdapter = new BuyRecordAdapter(R.layout.buy_in_record_item, buyRecords);
        buyRecyclerView.setAdapter(buyRecordAdapter);



        chooseCoinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        menuCoinIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        TransCoinFragment.getInstance(this);

        perTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyEtv.setText(String.valueOf(Double.valueOf(conntQuote)*1/4));

                if(flag==iOperaBuySell.SELL){
                    perRel1.setBackgroundResource(R.drawable.blue_shape22);
                }else {
                    perRel1.setBackgroundResource(R.drawable.blue_shape20);
                }
                perTv1.setTextColor(Color.WHITE);

                perRel2.setBackgroundResource(R.drawable.blue_shape21);
                perRel3.setBackgroundResource(R.drawable.blue_shape21);
                perRel4.setBackgroundResource(R.drawable.blue_shape21);
                perTv2.setTextColor(Color.parseColor("#FF757575"));
                perTv3.setTextColor(Color.parseColor("#FF757575"));
                perTv4.setTextColor(Color.parseColor("#FF757575"));


            }
        });

        perTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyEtv.setText(String.valueOf(Double.valueOf(conntQuote)*2/4));

                if(flag==iOperaBuySell.SELL){
                    perRel2.setBackgroundResource(R.drawable.blue_shape22);
                }else {
                    perRel2.setBackgroundResource(R.drawable.blue_shape20);
                }
                perTv2.setTextColor(Color.WHITE);

                perRel1.setBackgroundResource(R.drawable.blue_shape21);
                perRel3.setBackgroundResource(R.drawable.blue_shape21);
                perRel4.setBackgroundResource(R.drawable.blue_shape21);
                perTv1.setTextColor(Color.parseColor("#FF757575"));
                perTv3.setTextColor(Color.parseColor("#FF757575"));
                perTv4.setTextColor(Color.parseColor("#FF757575"));
            }
        });

        perTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyEtv.setText(String.valueOf(Double.valueOf(conntQuote)*3/4));

                if(flag==iOperaBuySell.SELL){
                    perRel3.setBackgroundResource(R.drawable.blue_shape22);
                }else {
                    perRel3.setBackgroundResource(R.drawable.blue_shape20);
                }
                perTv3.setTextColor(Color.WHITE);

                perRel1.setBackgroundResource(R.drawable.blue_shape21);
                perRel2.setBackgroundResource(R.drawable.blue_shape21);
                perRel4.setBackgroundResource(R.drawable.blue_shape21);
                perTv1.setTextColor(Color.parseColor("#FF757575"));
                perTv2.setTextColor(Color.parseColor("#FF757575"));
                perTv4.setTextColor(Color.parseColor("#FF757575"));
            }
        });

        perTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyEtv.setText(String.valueOf(Double.valueOf(conntQuote)));


                if(flag==iOperaBuySell.SELL){
                    perRel4.setBackgroundResource(R.drawable.blue_shape22);
                }else {
                    perRel4.setBackgroundResource(R.drawable.blue_shape20);
                }
                perTv4.setTextColor(Color.WHITE);

                perRel1.setBackgroundResource(R.drawable.blue_shape21);
                perRel2.setBackgroundResource(R.drawable.blue_shape21);
                perRel3.setBackgroundResource(R.drawable.blue_shape21);
                perTv1.setTextColor(Color.parseColor("#FF757575"));
                perTv2.setTextColor(Color.parseColor("#FF757575"));
                perTv3.setTextColor(Color.parseColor("#FF757575"));
            }
        });

        //买入事件
        BuyInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(Const.IS_FROZEN){
//                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
//                    return;
//                }

                if(TextUtils.isEmpty(qtyEtv.getText().toString())){
                    ToastUtil.show(getActivity(),getString(R.string.transf1));
                    return;
                }
                Double inputNum = Double.valueOf(qtyEtv.getText().toString());
                Double conntQuoteD = Double.valueOf(conntQuote);
                if(inputNum>conntQuoteD){
                    ToastUtil.show(getActivity(),getString(R.string.transf2));
                    return;
                }
                showPop();
            }
        });

        //卖出事件
        SelloutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(Const.IS_FROZEN){
//                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
//                    return;
//                }

                if(TextUtils.isEmpty(qtyEtv.getText().toString())){
                    ToastUtil.show(getActivity(),getString(R.string.transf1));
                    return;
                }
                Double inputNum = Double.valueOf(qtyEtv.getText().toString());
                Double conntQuoteD = Double.valueOf(conntQuote);
                if(inputNum>conntQuoteD){
                    ToastUtil.show(getActivity(),getString(R.string.transf2));
                    return;
                }
                showPop();
            }
        });

        mLdentifyC.setTextSize(15);
        mLdentifyC.setTextColor(Color.parseColor("#ffffff"));
        mMyTransfer.setTextSize(13);
        mMyTransfer.setTextColor(Color.parseColor("#999999"));
    }



    public static Fragment getInstance() {
        return new NewTransactionFragment();
    }
    private PopupWindow popWindow;

    @SuppressLint("WrongConstant")
    private void showPop() {
        View inflate = null;
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
        initB(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);
        changeWindowAlfa(0.5f);
        popWindow.showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }
    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgcolor;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }
    private void initB(View inflate) {
        final EditText name = inflate.findViewById(R.id.name);
        final ImageView hidden_pwd = inflate.findViewById(R.id.hidden_pwd);
        final ImageView display_pwd = inflate.findViewById(R.id.display_pwd);
        hidden_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                display_pwd.setVisibility(View.VISIBLE);
                hidden_pwd.setVisibility(View.GONE);
                name.setSelection(name.getText().length());
            }
        });
        display_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setTransformationMethod(PasswordTransformationMethod.getInstance());
                display_pwd.setVisibility(View.GONE);
                hidden_pwd.setVisibility(View.VISIBLE);
                name.setSelection(name.getText().length());
            }
        });

        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())){
                    ToastUtil.show(getActivity(),getActivity().getString(R.string.d20));
                    return;
                }else{
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    if(flag==iOperaBuySell.SELL){
                        UserSellOnSell(name.getText().toString());
                    }else if(flag==iOperaBuySell.BUY){
                        UserBuyOnSell(name.getText().toString());
                    }
                }
            }


        });
    }

    //买入 上架销售
    private void UserBuyOnSell(String password) {
        if(TextUtils.isEmpty(ConstantUtil.ID)){
            return;
        }
        BuyOpenrData data = new BuyOpenrData();
        data.userId=ConstantUtil.ID;
        data.outCoinType=outCoinType;

        data.inCoinType=inCoinType;
        String deviceid = Util.getAndroidId(getActivity());
        String osModel = Util.getOsModel();
        String netType = Util.getNetType(getActivity());
        String inIp = Util.getIPAddress(getActivity());
        data.osModel =osModel;
        data.deviceId=deviceid;
        data.appVersion= Util.getAppVersion(getActivity());
        data.inIp=inIp;

        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data.ipAddress = Const.outIpAddressArray[1];
        }else {
            data.ipAddress = " ";
        }
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data.outIp = Const.outIpAddressArray[0];
        }else {
            data.outIp = " ";
        }
        data.netType=netType;
        data.isSimulator= Util.isMonitor(getActivity());
        data.password= AESUtil.encry(password);
        String str =qtyEtv.getText().toString();
        if(!Util.isData(str)){
            ToastUtil.show(getActivity(),"请输入正确的数字");
            return;
        }
        data.qty=Double.valueOf(str);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .UserBuyOnSell(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransSell>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransSell tradeNce) {
                        ToastUtil.show(getActivity(), tradeNce.getMsg());
                        popWindow.dismiss();
                        refreshData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),136,e.getMessage(),false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //卖出 上架NCE
    private void UserSellOnSell(String password) {
        SellOpenrData data = new SellOpenrData();
        data.userId=ConstantUtil.ID;

        String deviceid = Util.getAndroidId(getActivity());
        String osModel = Util.getOsModel();
        String netType = Util.getNetType(getActivity());
        String inIp = Util.getIPAddress(getActivity());
        data.osModel =osModel;
        data.deviceId=deviceid;
        data.appVersion= Util.getAppVersion(getActivity());

        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data.ipAddress = Const.outIpAddressArray[1];
        }else {
            data.ipAddress = " ";
        }
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data.outIp = Const.outIpAddressArray[0];
        }else {
            data.outIp = " ";
        }
        data.netType=netType;
        data.inIp=inIp;
        data.isSimulator= Util.isMonitor(getActivity());
        data.inCoinType=inCoinType;
        data.outCoinType=outCoinType;
        data.password=AESUtil.encry(password);;
        data.qty=Double.valueOf(qtyEtv.getText().toString());
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .UserSellOnSell(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransSell>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransSell tradeNce) {
                        ToastUtil.show(getActivity(), tradeNce.getMsg());
                        popWindow.dismiss();
                        refreshData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),137,e.getMessage(),false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //帐号余额
    private void Banlance(final int code) {
        ApiStore.createApi(ApiService.class)
                .Banlance(ConstantUtil.ID, String.valueOf(code))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Banlance>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Banlance banlance) {
                        if (banlance.getStatus() == 0) {
                            try {
                                conntQuote = banlance.getResult();
                                if(balanceTv!=null) {
                                    if (code == inCoinType) {
                                        balanceTv.setText(conntQuote + " " + inCoinName);
                                    } else if (code == outCoinType) {
                                        balanceTv.setText(conntQuote + " " + outCoinName);
                                    }
                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),138,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //委托货币兑换列表
    public void EntrustChange() {
        ApiStore.createApi(ApiService.class)
                .EntrustChange()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransCoins>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        if(loding!=null)
//                            loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull TransCoins tradeNce) {
                        try {
                            List<TransCoins.ResultBean> result = tradeNce.getResult();
                            thisBean =result.get(0);
                            if(thisBean!=null) {
                                TransCoins.ResultBean fristResultBean = result.get(0);
                                inCoinName = fristResultBean.getInCoinName();
                                outCoinName = fristResultBean.getOutCoinName();
                                inCoinType = fristResultBean.getInCoinType();
                                outCoinType = fristResultBean.getOutCoinType();
                                chooseCoinTv.setText(inCoinName + "/" + outCoinName);
//                            wtTv1.setText(getString(R.string.transf4)+ outCoinName);
//                            wtTv2.setText(getString(R.string.transf5)+ outCoinName);
//                            wtTv3.setText(getString(R.string.transf6)+ inCoinName);
                                coinBabelTv.setText(outCoinName);
                                buyRecordTv1.setText(getString(R.string.transf7) + "(" + outCoinName + ")");
                                buyRecordTv2.setText(getString(R.string.transf4) + "(" + inCoinName + ")");

                                Banlance(outCoinType);

                                if (Const.DelagateFromData == 0) {
                                    allEntrustFragmet.setData(fristResultBean);
                                } else {
                                    myEntrustFragmet.setData(fristResultBean);
                                }

                                if (sellRecords != null) {
                                    sellRecords.clear();
                                }
                                if (buyRecords != null) {
                                    buyRecords.clear();
                                }
                                //卖出记录
                                sellRecordList(inCoinType, outCoinType);
                                //买入记录
                                buyRecordList(inCoinType, outCoinType);
                                //获取汇率
                                GetRange(outCoinType, inCoinType);
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
//                        if(loding!=null)
//                            loding.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),139,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //卖出记录
    private void sellRecordList(int inCoinType,int outCoinType) {
        ApiStore.createApi(ApiService.class)
                .TradeSellList(inCoinType,outCoinType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeSellList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull TradeSellList tradeSellList) {

                        try {
                            sellRecords = tradeSellList.getResult();
                            if (tradeSellList.getStatus() == 0) {
                                sellRecordAdapter.setNewData(sellRecords);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),140,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //买入记录
    private void buyRecordList(int inCoinType,int outCoinType) {
        ApiStore.createApi(ApiService.class)
                .TradeBuyList(inCoinType,outCoinType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeList>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull TradeList tradeList) {

                        try {
                            buyRecords = tradeList.getResult();
                            if (tradeList.getStatus() == 0) {
                                buyRecordAdapter.setNewData(buyRecords);
                            }


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),141,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void GetRange(int inCoreType,int outCoreType) {
        ApiStore.createApi(ApiService.class)
                .Range(inCoreType,outCoreType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NceEthRange>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        if(loding!=null)
//                            loding.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onNext(@NonNull NceEthRange nceEthRange) {
                        if (nceEthRange.getStatus() == 0) {
                            try {
                                if(rateTv!=null) {
                                    if (Const.TransactionFromData == 0) {
                                        rateTv.setText("1" + outCoinName + " ：" + nceEthRange.getResult() + inCoinName);
                                    } else {
                                        rateTv.setText("1" + inCoinName + " ：" + nceEthRange.getResult() + outCoinName);
                                    }
                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }



                        }
//                        if(loding!=null)
//                            loding.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),142,e.getMessage(),false);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public class SellOpenrData {
        String userId;
        String password;
        int inCoinType;
        int outCoinType;
        double qty;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String inIp;
        String isSimulator;
    }

    public class BuyOpenrData {
        String userId;
        String password;
        int outCoinType;
        double qty;
        int inCoinType;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String inIp;
        String isSimulator;
    }


}

