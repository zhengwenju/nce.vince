package com.bronet.blockchain.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.data.Model1;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.fragment.ContractFragment;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.ui.login.PwdManagerActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.itheima.wheelpicker.WheelPicker;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//划转
public class TransferActivity extends BaseActivity {

    @BindView(R.id.transfer_profit_ev)
    EditText transferProfitView;
    @BindView(R.id.transfer_btn)
    TextView transferBtn;
    @BindView(R.id.profit_qutoe_tv)
    TextView profitQutoeTv;
    @BindView(R.id.bz_tv)
    TextView bz_tv;
    PopupWindow popWindow;
    private int KitingCointype = 0;//划转币种
    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initView() {


        Constants.activityList.add(this);
    }

    @Override
    protected void initData() {
        RecommendReward();
    }

    @Override
    protected void setEvent() {
        if (transferBtn != null) {
            transferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(transferProfitView.getText().toString())) {
                        DialogUtil.show(TransferActivity.this, getString(R.string.transf_Profit_tip));
                        return;
                    }
                    if (KitingCointype == 0) {
                        DialogUtil.show(TransferActivity.this, getString(R.string.invest20));
                        return;
                    }
                    showPop(2, 3, -1);

                }
            });
        }
    }


    //收益余额
    private void RecommendReward() {
        ApiStore.createApi(ApiService.class)
                .RecommendReward(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ContractNum contractRules) {
                        try {
                            if (profitQutoeTv != null) {
                                if (contractRules.getStatus() == 0) {
                                    BigDecimal eth = new BigDecimal(contractRules.getResult().getEth());
                                    BigDecimal nce = new BigDecimal(contractRules.getResult().getNce());
                                    if (eth.compareTo(BigDecimal.ZERO) > 0 && nce.compareTo(BigDecimal.ZERO) > 0) {
                                        profitQutoeTv.setText(getString(R.string.Profit_Banlance) + "：" + "ETH:" + eth + "  NCE:" + nce);
                                    } else if (eth.compareTo(BigDecimal.ZERO) > 0 && nce.compareTo(BigDecimal.ZERO) <= 0) {
                                        profitQutoeTv.setText(getString(R.string.Profit_Banlance) + "：" + "ETH:" + eth);
                                    } else if (eth.compareTo(BigDecimal.ZERO) <= 0 && nce.compareTo(BigDecimal.ZERO) > 0) {
                                        profitQutoeTv.setText(getString(R.string.Profit_Banlance) + "：" + "NCE:" + nce);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        JumpUtil.errorHandler(getActivity(),121,e.getMessage(),false);
                        L.test(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.btn_back,R.id.bz_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.bz_tv:
                showPop(1, 0, 0);
                break;
        }
    }
    /**
     * 币种
     *
     * @param inflate
     */
    private void bz(View inflate) {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("ETH");
        list.add("NCE");
        final WheelPicker wp = (WheelPicker) inflate.findViewById(R.id.wp);
        wp.setCyclic(false);
        wp.setData(list);
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                int currentItemPosition = wp.getCurrentItemPosition();
                bz_tv.setText(list.get(currentItemPosition));
                if (currentItemPosition == 0) {
                    KitingCointype = 1;
                } else {
                    KitingCointype = 3;
                }
                transferProfitView.setText("");

            }
        });
    }
    @SuppressLint("WrongConstant")
    private void showPop(int type, int type2, int position) {
        View inflate = null;
        switch (type) {
            case 1:
                inflate = LayoutInflater.from(this).inflate(R.layout.pop3, null);
                bz(inflate);
                break;
            case 2:
                inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
                initB(inflate, type2, position);
                break;
        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        if (type == 1) {
            popWindow.setOutsideTouchable(true);
            popWindow.setTouchable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.showAtLocation(parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            popWindow.setFocusable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWindow.showAtLocation(parent,
                    Gravity.CENTER, 0, 0);
        }
        changeWindowAlfa(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }
    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp =this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }
    private String pwd;
    /**
     * 支付密码
     *
     * @param inflate
     * @param type
     */
    private void initB(View inflate, final int type, final int position) {
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
        name.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
                InputMethodManager imm = (InputMethodManager) TransferActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager)TransferActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
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
                if (TextUtils.isEmpty(name.getText().toString())) {
                    ToastUtil.show(TransferActivity.this, getString(R.string.d20));
                } else {
                    //支付密码
                    switch (type) {
                        case 3:
                            if (!ClickUtils.isFastClick()) {
                                return;
                            }
                            pwd = name.getText().toString();
                            //划转
                            KitingRecommendReward(KitingCointype);
                            break;
                    }

                }
            }
        });
    }

    //划转收益
    private void KitingRecommendReward(int cointype) {
        TransfData data = new TransfData();
        data.userId = ConstantUtil.ID;
        data.paymentPassword = AESUtil.encry(pwd);
        data.coinType = cointype;
        data.qty = transferProfitView.getText().toString();
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test("KitingRecommendReward s=====>>" + s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .KitingRecommendReward(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model1>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model1 reInvests) {
                        popWindow.dismiss();
                        if (!TextUtils.isEmpty(reInvests.getMsg())) {
                            DialogUtil.show(TransferActivity.this, reInvests.getMsg());
                        }
                        if (reInvests.getStatus() == 0) {
                            RecommendReward();
                            transferProfitView.setText("");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(TransferActivity.this, 124, e.getMessage(), false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public class TransfData {
        String userId;
        String paymentPassword;
        String qty;
        int coinType;

    }
}



