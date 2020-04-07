package com.bronet.blockchain.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MineTranAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.CApp;
import com.bronet.blockchain.data.IdeResult;
import com.bronet.blockchain.data.ReceModel;
import com.bronet.blockchain.data.TransferAgreement;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.Action;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

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
 * 认筹转让
 */

public class IdentifyChipsFragment extends BaseFragment {

    @BindView(R.id.nid_etv)
    EditText nidEtv;
    @BindView(R.id.qty_etv)
    EditText qtyEtv;
    @BindView(R.id.input_price)
    EditText mInputPrice;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.radioBtn)
    CheckBox checkBox;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @BindView(R.id.my_tran_tv)
    TextView myTranTv;


    @BindView(R.id.protacol_tv)
    TextView protacol_tv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PopupWindow popWindow;
    private boolean isCheck=false;
    private MineTranAdapter adapter;
    private List<ReceModel.ResultBean> result;

    @Override
    protected void initEvent() {
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
        if(L.isDebug){
//            nidEtv.setText("CND3A8AA");
        }
        IsRenChou();
        TransferAgreement();
        Receiver();

    }
    public void setData(){
        qtyEtv.setText(String.valueOf(Const.IDENTI_NCE));
    }

    @Override
    protected void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MineTranAdapter(R.layout.item_mytrans, result);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ldentifychips_fragment;
    }

    @OnClick({ R.id.nid_etv, R.id.qty_etv, R.id.input_price, R.id.btn_ok,R.id.content_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nid_etv:

                break;
            case R.id.qty_etv:
                break;
            case R.id.input_price:
                break;
            case R.id.btn_ok:
                String id = nidEtv.getText().toString();
                if(TextUtils.isEmpty(id)){
                    ToastUtil.show(getActivity(), getString(R.string.invest16));
                    return;
                }
//                if(!isCheck){
//                    ToastUtil.show(getActivity(), getString(R.string.invest31));
//                    return;
//                }
                if(Const.IS_FROZEN){
                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
                    return;
                }
                showPop(2);
                break;
            case R.id.content_tv:
                break;
        }
    }

    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate = null;
        switch (type) {
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
                    //支付密码
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    Transfer(name.getText().toString());
                }
            }


        });
    }

    //转让
    private void Transfer(String pwd) {
        Data data = new Data();
        data.userid = ConstantUtil.ID;
        data.buyerNid = nidEtv.getText().toString();
        data.qty=Integer.valueOf(qtyEtv.getText().toString());
        data.password= AESUtil.encry(pwd);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .Transfer(body)
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
                                mBtnOk.setBackgroundColor(Color.parseColor("#FF1976D2"));
                                mBtnOk.setText(getString(R.string.invest32));
                                mBtnOk.setEnabled(true);
                            }else if(cpp.getResult()==3){
                                mBtnOk.setBackgroundColor(Color.parseColor("#FF8F8E8E"));
                                mBtnOk.setText(getString(R.string.invest34));
                                mBtnOk.setEnabled(false);
                            }else if(cpp.getResult()==1){
                                mBtnOk.setBackgroundColor(Color.parseColor("#FF8F8E8E"));
                                mBtnOk.setText(getString(R.string.invest25));
                                mBtnOk.setEnabled(false);
                            }else if(cpp.getResult()==2){
                                mBtnOk.setBackgroundColor(Color.parseColor("#FF8F8E8E"));
                                mBtnOk.setText(getString(R.string.invest30));
                                mBtnOk.setEnabled(false);
                            }
//                            if(cpp.getResult()==1||cpp.getResult()==2){
//
//                            }
                        }

//                        if (cpp.getStatus() == 0) {
//                            if(cpp.getResult()==0){
//                                mBtnOk.setBackgroundColor(Color.parseColor("#FF1976D2"));
//                                mBtnOk.setEnabled(true);
//                            }else {
//                                mBtnOk.setBackgroundColor(Color.parseColor("#FF8F8E8E"));
//                                mBtnOk.setEnabled(false);
//                            }
//                            mBtnOk.setText(cpp.getMsg());
//                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //转让协议
    private void TransferAgreement() {
        ApiStore.createApi(ApiService.class)
                .TransferAgreement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransferAgreement>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransferAgreement transferAgreement) {
                        if (transferAgreement.getStatus() == 0) {
                            protacol_tv.setText(transferAgreement.getResult());
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
    //获取接收人信息
    private void Receiver() {
        ApiStore.createApi(ApiService.class)
                .Receiver(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReceModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReceModel receModel) {
                        if (receModel.getStatus() == 0) {
                            List<ReceModel.ResultBean> data = new ArrayList<>();
                            data.add(receModel.getResult());
                            adapter.setNewData(data);
                            myTranTv.setVisibility(View.VISIBLE);
                        }else {
                            myTranTv.setVisibility(View.GONE);
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
        String buyerNid;
        int qty;
        String password;

    }
}
