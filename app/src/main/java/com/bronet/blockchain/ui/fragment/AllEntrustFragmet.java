package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.EntrustAdapter;
import com.bronet.blockchain.adapter.MyEntrustAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.RecallList;
import com.bronet.blockchain.data.TradeBuyList;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.data.TransSell;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

////全部委托
public class AllEntrustFragmet extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.loding)
    ProgressBar loding;
    private View view;
    private EntrustAdapter entrustAdapter;
    private PopupWindow popWindow;
    private String finalPrice;
    private String balance;
    private String qty;
    private BigDecimal range;
    private TextView mBalance;
    private TextView mPrice;
    private com.bronet.blockchain.ui.MyEditText num;
    private  TextView buyprice_ev;
    private EditText name;
    private int id;

    private TextView pop_quote_tv,pop_total_tv;
    private int pageSize=10;
    private int pageNo=1;

    private double nceFee;
    private IAllEntrust iAllEntrust;

    private MyViewPager vp;
    private Activity activity;

    public void AllEntrustFragmet(Activity activity, MyViewPager vp) {
        this.activity = activity;
        this.vp = vp;
    }



    public void setIAllEntrust(IAllEntrust iAllEntrust){
        this.iAllEntrust=iAllEntrust;
    }

    private TransCoins.ResultBean bean;
    public void setData(TransCoins.ResultBean bean_){
        L.test("setData===========》》》"+Const.TransactionFromData);
        if(bean_!=null) {
            bean = bean_;
            if (Const.TransactionFromData == 0) {
                if (buyAlllist != null) {
                    buyAlllist.clear();
                }

                TradeBuyAllList(bean.getInCoinType(), bean.getOutCoinType(), pageNo, pageSize, "", 0, -1);
            } else {
                if (sellAlllist != null) {
                    sellAlllist.clear();
                }
                TradeSellAllList(bean.getInCoinType(), bean.getOutCoinType(), pageNo, pageSize, "", 0, -1);
            }
            Banlance(bean.getInCoinType());
            Banlance(bean.getOutCoinType());
        }

    }
    public void setDataPage(TransCoins.ResultBean bean_, int pageNo, int pageSize, String orderName, int orderyb){
        bean=bean_;
        if(Const.TransactionFromData==0) {
            TradeBuyAllList(bean.getInCoinType(), bean.getOutCoinType(),pageNo,pageSize,orderName,orderyb,1);
        }else {
            TradeSellAllList(bean.getInCoinType(), bean.getOutCoinType(),pageNo,pageSize,orderName,orderyb,1);
        }
    }
    public void setDataSort(TransCoins.ResultBean bean_, int pageNo, int pageSize, String orderName, int orderyb){
        bean=bean_;
        if(Const.TransactionFromData==0) {
            TradeBuyAllList(bean.getInCoinType(), bean.getOutCoinType(),pageNo,pageSize,orderName,orderyb,-1);
        }else {
            TradeSellAllList(bean.getInCoinType(), bean.getOutCoinType(),pageNo,pageSize,orderName,orderyb,-1);
        }
    }



    @Override
    protected void initEvent() {
    }


    @Override
    protected void initData() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        ExchangeNceFee();//兑换手续费

    }

    public void clearData(){
        if(Const.TransactionFromData==0) {
            buyAlllist.clear();
        }else {
            sellAlllist.clear();
        }
    }

    private String outQuote;
    private String inQuote;
    private int showflag=0;
    private void GetRange(int inCoreType,int outCoreType) {
//        loding.setVisibility(View.VISIBLE);
        ApiStore.createApi(ApiService.class)
                .Range(inCoreType,outCoreType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NceEthRange>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NceEthRange nceEthRange) {
//                        loding.setVisibility(View.GONE);
                        if (nceEthRange.getStatus() == 0) {
                            range = nceEthRange.getResult();
                        }
                        if (Const.TransactionFromData == 0) {
                            showPop(1); //买入
                        } else {
                            showPop(3); //卖出
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),105,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {
//                        loding.setVisibility(View.GONE);
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
                            if(code==bean.getOutCoinType()){
                                outQuote = banlance.getResult();
                            }else if (code==bean.getInCoinType()) {
                                inQuote = banlance.getResult();
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),106,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        LinearLayoutManager gridLayoutManager  = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        entrustAdapter = new EntrustAdapter(R.layout.entrus_item, buyAlllist);
        mRecyclerView.setAdapter(entrustAdapter);
        entrustAdapter.OnItemEntrustClickListener(new MyEntrustAdapter.OnItemEntrustClickListener() {
            @Override
            public void onItemWithdrawClick(View view, int position) {
//                if(Const.IS_FROZEN){
//                    DialogUtil.show(getActivity(),Const.FROZEN_CONTENT);
//                    return;
//                }

                if(!ClickUtils.isFastClick()){
                    return;
                }
                TradeBuyList.ResultBean.ItemsBean rbean = entrustAdapter.getItem(position);
                if(rbean !=null) {
                    id =rbean.getId();
                    qty = rbean.getQty();
                    balance = rbean.getBalance();
                    finalPrice = rbean.getFinalPrice();
                    if (rbean.getStatus()==1){
                        showPop(2);//撤回
                    }else if (rbean.getStatus()==0){
//                        if(Const.TransactionFromData==0) {
//                            GetRange(bean.getInCoinType(),bean.getOutCoinType());
//                        }else {
//                            GetRange(bean.getOutCoinType(),bean.getInCoinType());
//                        }no

                        GetRange(bean.getInCoinType(),bean.getOutCoinType());
                    }
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {

        return R.layout.entrust_fragmet;
    }
    @SuppressLint("WrongConstant")
    private void showPop(int type) {
//        if(Const.IS_FROZEN){
//            DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
//            return;
//        }
        View inflate = null;
        switch (type) {
            case 1:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop12, null); //买入
                Buy(inflate);
                break;
            case 2:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
                price(inflate);
                break;
            case 3:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop13, null);//卖出
                Sell(inflate);
                break;

        }
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

    private void price(View inflate) {
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

                }
                //撤回
                if(Const.TransactionFromData==0) {
                    SellReCall(name.getText().toString());
                }else {
                    BuyReCall(name.getText().toString());
                }
            }
        });
    }




    private void ExchangeNceFee() {
        ApiStore.createApi(ApiService.class)
                .ExchangeNceFee()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 CoinDetails_) {
                        nceFee = Double.valueOf(CoinDetails_.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),107,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void Buy(View inflate) {
        name = inflate.findViewById(R.id.name);
        mBalance = inflate.findViewById(R.id.mBalance);
        mPrice = inflate.findViewById(R.id.finPrice);
        BigDecimal bd=new BigDecimal(balance);
        if(range!=null) {
            BigDecimal data = bd.multiply(range);
            mBalance.setText(String.valueOf(balance)+"≈"+Util.mDecimal8Format(data)+" "+bean.getOutCoinName()); //
        }
        mPrice.setText(outQuote);//我的剩余资产
        num = inflate.findViewById(R.id.number);
        pop_total_tv = inflate.findViewById(R.id.pop_total_tv);
        pop_quote_tv = inflate.findViewById(R.id.pop_quote_tv);
        final TextView unitTv1=  inflate.findViewById(R.id.unit_tv1);
        final TextView unitTv2=  inflate.findViewById(R.id.unit_tv2);
        final TextView unitTv3=  inflate.findViewById(R.id.unit_tv3);
        final TextView input_price_etv = inflate.findViewById(R.id.input_price_etv);
        final TextView all_tv = inflate.findViewById(R.id.all_tv);

        all_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num.setText(balance);
            }
        });

        pop_quote_tv.setText(getString(R.string.transf9)+"("+bean.getInCoinName()+"):");
        pop_total_tv.setText(getString(R.string.transf10)+"("+bean.getOutCoinName()+"):");


        unitTv1.setText(bean.getInCoinName());
        unitTv2.setText(bean.getOutCoinName());
        unitTv3.setText(bean.getInCoinName());

        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(num.getText().toString())){
                    ToastUtil.show(getActivity(),getActivity().getString(R.string.d27));
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString())){
                    ToastUtil.show(getActivity(),getActivity().getString(R.string.d20));
                    return;
                }
                if(range!=null) {
                    BigDecimal bd = new BigDecimal(num.getText().toString());
                    BigDecimal total = bd.multiply(range);
                    BigDecimal intputNum = new BigDecimal(num.getText().toString());
                    Double buypriceCount = Double.valueOf(input_price_etv.getText().toString());
                    Double asserts = Double.valueOf(outQuote);
                    if (buypriceCount > asserts) {
                        ToastUtil.show(getActivity(), getString(R.string.transf2));
                        return;
                    } else {
                        if (!ClickUtils.isFastClick()) {
                            return;
                        }
                        BuyInlist(name.getText().toString(), total, intputNum);
                    }
                }else {
                    ToastUtil.show(getActivity(),getString(R.string.server_exception));
                }
            }
        });

        if(num!=null) {
            num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        if(!TextUtils.isEmpty(num.getText().toString())) {
                            BigDecimal bd=new BigDecimal(num.getText().toString());
                            input_price_etv.setText(String.valueOf(Util.mDecimal8Format(bd.multiply(range))));
                        }else {
                            input_price_etv.setText("0.0");
                        }

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    private void Sell(View inflate) {
        if(range!=null) {
            name = inflate.findViewById(R.id.name);
            num = inflate.findViewById(R.id.number);

            mBalance = inflate.findViewById(R.id.mBalance);
            mPrice = inflate.findViewById(R.id.finPrice);

            buyprice_ev = inflate.findViewById(R.id.input_price_etv);

            final com.bronet.blockchain.ui.MyTextView sxfTv = inflate.findViewById(R.id.sxf_tv);
            BigDecimal bd = new BigDecimal(balance);

            final BigDecimal data = bd.divide(range, RoundingMode.FLOOR);
            mBalance.setText(String.valueOf(balance) + "≈" + Util.mDecimal8Format(data) + " NCE"); //

            mPrice.setText(inQuote); //我的剩余资产


            pop_total_tv = inflate.findViewById(R.id.pop_total_tv);
            pop_quote_tv = inflate.findViewById(R.id.pop_quote_tv);

            final TextView unitTv1 = inflate.findViewById(R.id.unit_tv1);
            final TextView unitTv2 = inflate.findViewById(R.id.unit_tv2);
            final TextView unitTv3 = inflate.findViewById(R.id.unit_tv3);
            final TextView all_tv = inflate.findViewById(R.id.all_tv);

            all_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    num.setText(Util.mDecimal8Format(data));
                }
            });


            pop_quote_tv.setText(getString(R.string.transf8) + "(" + bean.getOutCoinName() + ")");
            pop_total_tv.setText(getString(R.string.transf10) + "(" + bean.getInCoinName() + ")");

            unitTv1.setText(bean.getInCoinName());
            unitTv2.setText(bean.getOutCoinName());
            unitTv3.setText(bean.getInCoinName());


            inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();
                }
            });

            inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (TextUtils.isEmpty(num.getText().toString())) {
                        ToastUtil.show(getActivity(), getActivity().getString(R.string.d28));
                        return;
                    }
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        ToastUtil.show(getActivity(), getActivity().getString(R.string.d20));
                        return;
                    }
                    BigDecimal bd = new BigDecimal(num.getText().toString());
                    BigDecimal total = bd.multiply(range);
                    Double inputTotal = Double.valueOf(num.getText().toString());
                    Double asserts = Double.valueOf(inQuote);

                    if (inputTotal > asserts) {
                        ToastUtil.show(getActivity(), getString(R.string.transf2));
                        return;
                    } else {
                        if (!ClickUtils.isFastClick()) {
                            return;
                        }
                        SellOutList(name.getText().toString(), total);
                    }
                }
            });


            num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        if (!TextUtils.isEmpty(num.getText().toString())) {
                            String data = num.getText().toString();
                            BigDecimal bd = new BigDecimal(data);
                            buyprice_ev.setText(String.valueOf(Util.mDecimal8Format(bd.multiply(range))));

                            //手续费
                            BigDecimal bd2 = new BigDecimal(Double.valueOf(num.getText().toString()) * nceFee);
                            sxfTv.setText(String.valueOf(Util.mDecimal8Format(bd2)));

                        } else {
                            buyprice_ev.setText("0.0");
                            sxfTv.setText("0.0");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }else {
            ToastUtil.show(getActivity(),getString(R.string.server_exception));
        }
    }

    private  List<TradeBuyList.ResultBean.ItemsBean> buyAlllist=new ArrayList<>();
    private  List<TradeBuyList.ResultBean.ItemsBean> sellAlllist=new ArrayList<>();
    /**
     *
     * @param inCoinType
     * @param outCoinType
     * @param pageNo
     * @param pageSize
     * @param orderName
     * @param orderby
     * @param tag 是否为分页数据
     */
    private void TradeBuyAllList(int inCoinType, int outCoinType,final int pageNo,int pageSize,String orderName,int orderby,final int tag) {
        ApiStore.createApi(ApiService.class)
                .TradeBuyAllList(ConstantUtil.ID,outCoinType,inCoinType,pageNo,pageSize,orderName,orderby)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyList>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        if(loding!=null)
//                            loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull TradeBuyList tradeBuyList) {
                        try {
                            L.test("响应  买入  全部委托");
//                            if (tradeBuyList.getStatus() == 0) {
//                                entrustAdapter.setNewData(AllEntrustFragmet.this.result);
//                            }
                            if(tag==-1){ //不分页数据
                                buyAlllist.clear();
                                entrustAdapter.setNewData(buyAlllist);
                            }
                            TradeBuyList.ResultBean result = tradeBuyList.getResult();
                            if(result!=null&&result.getItems().size()>0) {  //result!=null&&result.getItems()==null&&
                                buyAlllist.addAll(result.getItems());
                                List<TradeBuyList.ResultBean.ItemsBean> resultBeans1 = Util.ridRepeat(buyAlllist);
                                entrustAdapter.setNewData(resultBeans1);
                            }
//                            else {
//                                entrustAdapter.setNewData(resultBeans);
//                            }
                            iAllEntrust.updataTitle(tradeBuyList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
//                        if(loding!=null)
//                            loding.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),108,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     *
     * @param inCoinType
     * @param outCoinType
     * @param pageNo
     * @param pageSize
     * @param orderName
     * @param orderby
     * @param tag 是否为分页数据
     */

    //卖出全部委托
    private void TradeSellAllList(int inCoinType, int outCoinType, int pageNo, int pageSize, String orderName, int orderby, final int tag) {
        ApiStore.createApi(ApiService.class)
                .TradeSellAllList(ConstantUtil.ID,inCoinType,outCoinType,pageNo,pageSize,orderName,orderby)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        if(loding!=null)
//                            loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull TradeBuyList tradeBuyList) {
                        try {
                            if(tag==-1){
                                sellAlllist.clear();
                                entrustAdapter.setNewData(sellAlllist);
                            }
                            TradeBuyList.ResultBean result = tradeBuyList.getResult();
                            if(result!=null&&result.getItems().size()>0) {
                                sellAlllist.addAll(result.getItems());
                                List<TradeBuyList.ResultBean.ItemsBean> resultBeans1 = Util.ridRepeat(sellAlllist);
                                entrustAdapter.setNewData(resultBeans1);
                            }
                            iAllEntrust.updataTitle(tradeBuyList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
//                        if(loding!=null)
//                            loding.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),109,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void BuyInlist(String password, BigDecimal finalPrice, BigDecimal intputNum) {
        if(TextUtils.isEmpty(ConstantUtil.ID)){
            ToastUtil.show(getActivity(),"账号为空");
            return;
        }
        BuyData data = new BuyData();
        data.id=id;
        data.userid=ConstantUtil.ID;
        data.password= AESUtil.encry(password);
        data.inCoinType=bean.getInCoinType();
        data.outCoinType=bean.getOutCoinType();
        data.qty=intputNum;
        data.range=range;
        data.finalPrice=finalPrice;
        data.inIp=Util.getIPAddress(getActivity());
        String deviceId = Util.getAndroidId(getActivity());
        String netType = Util.getNetType(getActivity());
        String osModel = Util.getOsModel();
        String appVersion=Util.getAppVersion(getActivity());
//        data.fee=Double.valueOf(num.getText().toString())*nceFee;
        data.isSimulator= Util.isMonitor(getActivity());
        data.osModel=osModel;
        data.deviceId = deviceId;
        data.netType=netType;
        data.appVersion=appVersion;
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data.outIp= Const.outIpAddressArray[0];
        }else {
            data.outIp=" ";
        }

        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data.ipAddress=Const.outIpAddressArray[1];
        }else {
            data.ipAddress=" ";
        }


        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .BuyInlist(body)
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

                        clearData();
                        iAllEntrust.refresh();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),110,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void SellOutList(String password, BigDecimal finalPrice) {
//        loding.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(ConstantUtil.ID)){
            ToastUtil.show(getActivity(),"账号为空");
            return;
        }
        BuyData data = new BuyData();
        data.id=id;
        data.userid=ConstantUtil.ID;
        data.password= AESUtil.encry(password);
        data.outCoinType=bean.getOutCoinType();
        data.inCoinType=bean.getInCoinType();
        data.qty=new BigDecimal(num.getText().toString());
        data.range=range;
        data.fee=Double.valueOf(num.getText().toString())*nceFee;
        data.finalPrice=finalPrice;
        data.isSimulator= Util.isMonitor(getActivity());
        data.inIp=Util.getIPAddress(getActivity());
        String deviceId = Util.getAndroidId(getActivity());
        String netType = Util.getNetType(getActivity());
        String osModel = Util.getOsModel();
        String appVersion=Util.getAppVersion(getActivity());

        data.osModel=osModel;
        data.deviceId = deviceId;
        data.netType=netType;
        data.appVersion=appVersion;
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data.outIp= Const.outIpAddressArray[0];
        }else {
            data.outIp=" ";
        }

        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data.ipAddress=Const.outIpAddressArray[1];
        }else {
            data.ipAddress=" ";
        }


        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .SellInList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransSell>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransSell tradeNce) {
//                        loding.setVisibility(View.GONE);
                        ToastUtil.show(getActivity(), tradeNce.getMsg());
                        popWindow.dismiss();
                        clearData();
                        iAllEntrust.refresh();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        loding.setVisibility(View.GONE);
                        JumpUtil.errorHandler(getActivity(),111,e.getMessage(),false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {
//                        loding.setVisibility(View.GONE);

                    }
                });
    }
    //买入撤回
    private void BuyReCall(String password) {
        if(TextUtils.isEmpty(ConstantUtil.ID)){
            ToastUtil.show(getActivity(),"账号为空");
            return;
        }
        MyEntData data = new MyEntData();
        data.id=id;
        data.userid=ConstantUtil.ID;
        data.password=AESUtil.encry(password);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .BuyReCall(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecallList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RecallList recall) {
                        ToastUtil.show(getActivity(), recall.getMsg());
                        Log.i("------->", "onNext: "+recall.getMsg());
                        popWindow.dismiss();
                        clearData();
                        iAllEntrust.refresh();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),112,e.getMessage(),false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //卖出撤回
    private void SellReCall(String password) {
        if(TextUtils.isEmpty(ConstantUtil.ID)){
            ToastUtil.show(getActivity(),"账号为空");
            return;
        }
        MyEntData data = new MyEntData();
        data.id=id;
        data.userid=ConstantUtil.ID;
        data.password=AESUtil.encry(password);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .SellReCall(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecallList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RecallList recall) {
                        ToastUtil.show(getActivity(), recall.getMsg());
                        Log.i("------->", "onNext: "+recall.getMsg());
                        popWindow.dismiss();
                        clearData();
                        iAllEntrust.refresh();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),113,e.getMessage(),false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public class MyEntData {
        int id;
        String userid;
        String password;
    }




    public class BuyData {
        int id;
        String userid;
        String password;
        int inCoinType;
        int outCoinType;
        BigDecimal qty;
        BigDecimal range;
        BigDecimal finalPrice;
        double fee;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String inIp;
        String isSimulator;
    }

    public interface IAllEntrust{
        void refresh();
        void updataTitle(TradeBuyList result);


    }
}
