package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.IdentiAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.CApp;
import com.bronet.blockchain.data.IdeResult;
import com.bronet.blockchain.data.IdentiResult1;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.XYIdentiActivity;
import com.bronet.blockchain.util.Action;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

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
 * 认筹
 * Created by 18514 on 2019/6/27.
 */

public class IdentiFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.eth_num)
    EditText mEthnum;
    @BindView(R.id.reduce_btn)
    Button mReducebtn;
    @BindView(R.id.bz_input)
    EditText mBzinput;
    @BindView(R.id.add_btn)
    Button mAddbtn;
    @BindView(R.id.edi_price)
    EditText mEdiprice;
    @BindView(R.id.radioBtn)
    CheckBox checkBox;
    @BindView(R.id.purchase_btn)
    Button mPurchase;
    @BindView(R.id.tip_tv)
    TextView tipTv;

    @BindView(R.id.bz2)
    TextView bz2;
    @BindView(R.id.loding)
    ProgressBar loding;

    @BindView(R.id.balance_tv)
    TextView balanceTv;

    private double ethQty_=-1;
    private double nceQty_=-1;
    private double nceEhtRange_=-1;

    private IdentiAdapter identiAdapter;
    private View view;
    private PopupWindow popWindow;
    private List<IdentiResult1.ResultBean> result;
    private boolean isCheck=false;

    public void reset(Intent intent){

    }
    @Override
    protected void initEvent() {

//       identiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                identiAdapter.setThisPosition(i);
//                identiAdapter.notifyDataSetChanged();
//                IdentiResult.ResultBean item = identiAdapter.getItem(i);
//                nceQty_ =item.getNceQty();
//                RenChouMeal(item.getId());
//            }
//        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkBox.setButtonDrawable(R.mipmap.checked);
                }else {
                    checkBox.setButtonDrawable(R.mipmap.check);
                }
                isCheck=b;
            }
        });


    }

    @Override
    protected void initData() {

        IsRenChou();
        RenChouMealList();
        Banlance(1); //1 ETH
        RenChouTip();
    }



    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        identiAdapter = new IdentiAdapter(R.layout.item_ident, result);
        recyclerView.setAdapter(identiAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_identi;
    }

    public static Fragment getInstance() {
        return new IdentiFragment();
    }

//    int num=0;
    @OnClick({R.id.recyclerView, R.id.eth_num, R.id.reduce_btn, R.id.add_btn, R.id.bz_input, R.id.edi_price,R.id.purchase_btn,R.id.content_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerView:
                break;
            case R.id.eth_num:
                break;
            case R.id.reduce_btn:
               /* mEthnum.setText(String.valueOf(--num));
                if(num<0){
                    ToastUtil.show(getActivity(), getString(R.string.invest22));
                    return;
                }*/
                break;
            case R.id.add_btn:
                //mEthnum.setText(String.valueOf(++num));
                break;
            case R.id.bz_input:
                break;
            case R.id.edi_price:
                break;
            case R.id.purchase_btn:
                if(nceQty_<0){
                    ToastUtil.show(getActivity(), getString(R.string.invest24));
                    return;
                }
                String num1 = mEthnum.getText().toString();
                if(TextUtils.isEmpty(num1)){
                    ToastUtil.show(getActivity(), getString(R.string.invest22));
                    return;
                }

                String price = mEdiprice.getText().toString();
                if(TextUtils.isEmpty(price)){
                    ToastUtil.show(getActivity(), getString(R.string.invest21));
                    return;
                }

                if(!isCheck){
                    ToastUtil.show(getActivity(), getString(R.string.invest23));
                    return;
                }
                if(Const.IS_FROZEN){
                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
                    return;
                }
                showPop(2);
                break;
                case R.id.content_tv:
                    JumpUtil.overlay(getActivity(), XYIdentiActivity.class);
                    break;
        }
    }

    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate = null;
        switch (type) {
            case 1:
                /*inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop3, null);
                bz(inflate);
                break;*/
            case 2:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
                initB(inflate);
                break;
        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        if (type==1) {
            popWindow.setOutsideTouchable(true);
            popWindow.setTouchable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.showAtLocation(parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }else{
            popWindow.setFocusable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWindow.showAtLocation(parent,
                    Gravity.CENTER, 0, 0);
        }
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
                }else{
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    Identi(name.getText().toString());
                }
            }


        });
    }


    //认筹套餐列表
    private void RenChouMealList() {
        Const.IDENTI_NCE=0;
        ApiStore.createApi(ApiService.class)
                .RenChouMealList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IdentiResult1>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull IdentiResult1 identiResult) {
                        try {
                            nceEhtRange_=Double.valueOf(identiResult.getMsg());

                            List<IdentiResult1.ResultBean> resultBeans = identiResult.getResult();
                            if (identiResult.getStatus() == 0) {
                                identiAdapter.setNewData(resultBeans);
                            }

                            for(IdentiResult1.ResultBean resultBean:resultBeans){
                                if(resultBean.getSelected()==1){
                                    nceQty_=resultBean.getTotalQty();
                                    Const.IDENTI_NCE=(int)nceQty_;
                                    ethQty_=resultBean.getFinalPrice();
                                    mEdiprice.setText(String.valueOf(ethQty_));
                                    mBzinput.setText(resultBean.getFinalCoinName());
                                    bz2.setText(resultBean.getFinalCoinName());
                                    break;
                                }
                            }

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(loding!=null) {
                            loding.setVisibility(View.GONE);
                        }
                    }
                });
    }


    //帐号余额
    private void Banlance(int code) {
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
                            String result = banlance.getResult();
                            balanceTv.setText(result);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //认筹
    private void Identi(String pwd) {
        Data data = new Data();
        data.userid=ConstantUtil.ID;
        data.ethQty=ethQty_;
        data.nceQty=nceQty_;
        data.nceEhtRange=nceEhtRange_;
        data.password= AESUtil.encry(pwd);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .RenChou(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IdeResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull IdeResult reInvests) {
                        popWindow.dismiss();
                        if (!TextUtils.isEmpty(reInvests.getMsg())) {
                            ToastUtil.show(getActivity(), reInvests.getMsg());
                        }
                        if (reInvests.getStatus() == 0) {

                            getActivity().finish();
                            Intent intent = new Intent();
                            intent.setAction(Action.WithMoneyAction);
                            getActivity().sendBroadcast(intent);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void IsRenChou() {
        ApiStore.createApi(ApiService.class)
                .IsRenChou(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CApp>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull CApp cpp) {
                        if (cpp.getStatus() == 0) {
                            if(cpp.getResult()==0){
                                mPurchase.setBackgroundColor(Color.parseColor("#FF1976D2"));
                                mPurchase.setEnabled(true);
                            }else {
                                mPurchase.setBackgroundColor(Color.parseColor("#FF8F8E8E"));
                                mPurchase.setEnabled(false);
                            }
                            mPurchase.setText(cpp.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //认筹协议
    private void RenChouTip() {
        ApiStore.createApi(ApiService.class)
                .RenChouTip()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IdeResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull IdeResult ideResult) {
                        if (ideResult.getStatus() == 0) {
                            String result =ideResult.getResult();
                            tipTv.setText(result);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class Data{
        String userid;
        double ethQty;
        double nceQty;
        double nceEhtRange;
        String password;

    }
}
