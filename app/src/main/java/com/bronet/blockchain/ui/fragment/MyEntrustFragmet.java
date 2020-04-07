package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
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

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MyEntrustAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.RecallList;
import com.bronet.blockchain.data.TradeBuyList;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//我的委托
public class MyEntrustFragmet extends BaseFragment {

    public static int fromflag=0; //默认是买入0  卖出是1

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.loding)
    ProgressBar loding;
    private View view;
    private MyEntrustAdapter myEntrustAdapter;
    private PopupWindow popWindow;
    private int id;
    private List<TradeBuyList.ResultBean.ItemsBean> result;

    private TransCoins.ResultBean bean;

    public void setData(TransCoins.ResultBean bean_){
        bean=bean_;
        if(bean!=null) {


            if (Const.TransactionFromData == 0) {
                TradeBuyMyList(bean.getInCoinType(), bean.getOutCoinType());
            } else {
                TradeSellMyList(bean.getInCoinType(), bean.getOutCoinType());
            }
        }
    }
    @Override
    protected void initEvent() {

    }
    //    private String quote;
//    public void setQuote(String quote_){
//        this.quote=quote_;
//    }
    @Override
    protected void initData() {
//        TradeBuyMyList();
    }
    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(getActivity(), LoginActivity.class);
            return;
        }
        /*ArrayList<TranEntrusts.ResultBean> list=new ArrayList<>();
        TranEntrusts.ResultBean resultBean = new TranEntrusts.ResultBean();
        resultBean.setCreateTime("2019/09/09");
        resultBean.setQty(2000);
        resultBean.setBalance(1400);
        resultBean.setFinalPrice(1000);
        resultBean.setStatusMsg("1");
        resultBean.setStatus(0);
        list.add(resultBean);*/
        LinearLayoutManager gridLayoutManager  = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        myEntrustAdapter = new MyEntrustAdapter(R.layout.myentrus_item, result);
        mRecyclerView.setAdapter(myEntrustAdapter);
        myEntrustAdapter.OnItemEntrustClickListener(new MyEntrustAdapter.OnItemEntrustClickListener() {



            @Override
            public void onItemWithdrawClick(View view, int position) {
                TradeBuyList.ResultBean.ItemsBean bean = myEntrustAdapter.getItem(position);
                if(bean !=null) {
                    id = bean.getId();
                    if (bean.getStatus()==2){
                        return;
                    }
                    if(Const.IS_FROZEN){
                        DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
                        return;
                    }
                    showPop(1);
                }
            }


        });
    }



    @Override
    protected int setLayoutId() {
        return R.layout.myentrust_fragmet;
    }
    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate = null;
        switch (type) {
            case 1:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
                initB(inflate);
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
                    if(Const.TransactionFromData==0) {
                        BuyReCall(name.getText().toString());
                    }else {
                        SellReCall(name.getText().toString());
                    }
                }
            }


        });
    }
    //买入我的委托列表
    void TradeBuyMyList(int inCoinType, int outCoinType) {
        ApiStore.createApi(ApiService.class)
                .TradeBuyMyList(ConstantUtil.ID, inCoinType,outCoinType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyList>() {

                    @Override

                    public void onSubscribe(@NonNull Disposable d) {
//                        if(loding!=null)
//                            loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull TradeBuyList tradeBuyMyList) {
                        try {
                            if(result!=null) {
                                result.clear();
                                myEntrustAdapter.setNewData(result);
                            }
                            if(tradeBuyMyList.getStatus()==0){
                                if(tradeBuyMyList.getResult()!=null) {
                                    result = tradeBuyMyList.getResult().getItems();
                                    myEntrustAdapter.setNewData(result);
                                    myEntrustAdapter.OnItemChildClickListener(new MyEntrustAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemShowClick(int position) {
                                            if (result.get(position).isOpen()) {

                                                result.get(position).setOpen(false);

                                            } else {

                                                result.get(position).setOpen(true);


                                            }
                                            myEntrustAdapter.setNewData(result);
                                        }
                                    });
                                }
                            }

                            TradeBuyList.ResultBean result = tradeBuyMyList.getResult();
                                iMyEntrust.updataMyTitle(tradeBuyMyList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
//                        if(loding!=null)
//                            loding.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：买入我的委托列表异常:" + e.getMessage());
                        JumpUtil.errorHandler(getActivity(),132,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void setIMyEntrust(IMyEntrust iMyEntrust){
        this.iMyEntrust=iMyEntrust;
    }

    private IMyEntrust iMyEntrust;

    //卖出我的委托列表
    void TradeSellMyList(int inCoinType, int outCoinType) {
        ApiStore.createApi(ApiService.class)
                .TradeSellMyList(ConstantUtil.ID, outCoinType,inCoinType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyList>() {




                    @Override

                    public void onSubscribe(@NonNull Disposable d) {
//                        if(loding!=null)
//                            loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull TradeBuyList tradeBuyMyList) {
                        try {
                            if(result!=null) {
                                result.clear();
                                myEntrustAdapter.setNewData(result);
                            }
                            result = tradeBuyMyList.getResult().getItems();
//                            if (tradeBuyMyList.getStatus() == 0) {
//                                myEntrustAdapter.setNewData(MyEntrustFragmet.this.result);
//                            }
                            myEntrustAdapter.setNewData(MyEntrustFragmet.this.result);
                                iMyEntrust.updataMyTitle(tradeBuyMyList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
//                        if(loding!=null)
//                            loding.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：合约明细异常" + e.getMessage());
                        JumpUtil.errorHandler(getActivity(),133,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    //买入撤回
    private void BuyReCall(String password) {
        MyEntData data = new MyEntData();
        data.id=id;
        data.userid=ConstantUtil.ID;
        data.password= AESUtil.encry(password);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .BuyReCall(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecallList>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull RecallList recall) {
                        ToastUtil.show(getActivity(), recall.getMsg());
                        Log.i("------->", "onNext: "+recall.getMsg());
                        popWindow.dismiss();
//                        if(Const.TransactionFromData==0) {
//                            TradeBuyMyList(bean.getInCoinType(), bean.getOutCoinType());
//                        }else {
//                            TradeSellMyList(bean.getInCoinType(), bean.getOutCoinType());
//                        }
                        iMyEntrust.refresh();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),134,e.getMessage(),false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //卖出撤回
    private void SellReCall(String password) {
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
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull RecallList recall) {
                        ToastUtil.show(getActivity(), recall.getMsg());
                        Log.i("------->", "onNext: "+recall.getMsg());
                        popWindow.dismiss();
//                        if(Const.TransactionFromData==0) {
//                            TradeBuyMyList(bean.getInCoinType(), bean.getOutCoinType());
//                        }else {
//                            TradeSellMyList(bean.getInCoinType(), bean.getOutCoinType());
//                        }
                        iMyEntrust.refresh();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),135,e.getMessage(),false);
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

    public interface IMyEntrust{
        void refresh();
        void updataMyTitle(TradeBuyList buyList);
    }
}
