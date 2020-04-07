package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.data.TradeNce;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.ExchangeRecordActivity;
import com.bronet.blockchain.ui.WithdrawMoneyActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 交易
 * Created by 18514 on 2019/6/27.
 */

public class TransactionFragment extends BaseFragment {
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.input1)
    EditText input1;
    @BindView(R.id.type1)
    TextView type1;
    @BindView(R.id.current_number)
    TextView currentNumber;
    @BindView(R.id.btn_withdraw_money)
    TextView btnWithdrawMoney;
    @BindView(R.id.exchange)
    TextView exchange;
    @BindView(R.id.input2)
    EditText input2;
    @BindView(R.id.type2)
    TextView type2;
    @BindView(R.id.btn_ok)
    Button btnOk;
    String[] name = {"买入", "卖出"};
    PopupWindow popWindow;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.ivs1)
    ImageView ivs1;
    @BindView(R.id.ivs2)
    ImageView ivs2;

//    @BindView(R.id.tiao)
//    TextView tiao;
    private String[] outIpAddressArray=null;

    Unbinder unbinder;
    int position = 0;
    int type;
    float result;
    String buyQuota;
    boolean Number;
    StringBuffer data = new StringBuffer();
    @BindView(R.id.datas)
    TextView datas;
    public static String TAG="TransactionFragment";

    @Override
    protected void initEvent() {
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                TransactionFragment.this.position = position;
                if (position == 0) {
                    type1.setText("ETH");
                    type2.setText("NCE");
                    iv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.GONE);
                    ivs1.setVisibility(View.VISIBLE);
                    ivs2.setVisibility(View.GONE);
                    btnWithdrawMoney.setVisibility(View.VISIBLE);

                    input1.setText("");

                    type = 2;
                    Banlance(1);
                } else {
                    type1.setText("NCE");
                    type2.setText("ETH");
                    btnWithdrawMoney.setVisibility(View.GONE);
                    iv2.setVisibility(View.VISIBLE);
                    iv1.setVisibility(View.GONE);
                    ivs2.setVisibility(View.VISIBLE);
                    ivs1.setVisibility(View.GONE);

                    input2.setText("");
                    Banlance(3);

                    type = 1;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        input1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Number = true;
            }
        });
        input2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Number = false;
            }
        });
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Number) {
                    if (!TextUtils.isEmpty(input1.getText().toString())) {
                        if (type == 2) {
                            Float aFloat = Float.valueOf(input1.getText().toString());
                            float v = aFloat * result;
                            input2.setText(String.valueOf(v));
                        } else {
                            Float aFloat = Float.valueOf(input1.getText().toString());
                            float v = aFloat / result;
                            input2.setText(String.valueOf(v));
                        }
                    } else {
                        input2.setText(String.valueOf(0));
                    }
                }
            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!Number) {
                    if (!TextUtils.isEmpty(input2.getText().toString())) {
                        if (type == 2) {
                            Float aFloat = Float.valueOf(input2.getText().toString());
                            float v = aFloat / result;
                            input1.setText(String.valueOf(v));
                        } else {
                            Float aFloat = Float.valueOf(input2.getText().toString());
                            float v = aFloat * result;
                            input1.setText(String.valueOf(v));
                        }
                    } else {
                        input1.setText(String.valueOf(0));
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        type1.setText("ETH");
        type2.setText("NCE");
        ivs2.setVisibility(View.GONE);
        type = 2;
        TradeBuyWarning();

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                outIpAddressArray = Util.getOutNetIP(getActivity(),1);
                L.test("TransactionFragment v=======>>>outIpAddressArray==========>>"+ Arrays.toString(outIpAddressArray));
            }
        };t.start();
    }

    private void TradeSellWarning() {
        ApiStore.createApi(ApiService.class)
                .TradeSellWarning()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyWarning>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TradeBuyWarning tradeBuyWarning) {
                        if (tradeBuyWarning.getStatus() == 0) {
                            data.append("\n\n").append(tradeBuyWarning.getResult());
                            datas.setText(data.toString());
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

    private void TradeBuyWarning() {
        ApiStore.createApi(ApiService.class)
                .TradeBuyWarning()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyWarning>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TradeBuyWarning tradeBuyWarning) {
                        if (tradeBuyWarning.getStatus() == 0) {
                            data.append(tradeBuyWarning.getResult());
                            TradeSellWarning();
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

    @Override
    public void onResume() {
        super.onResume();
        if(type==2) {
            Banlance(1);
        }else {
            Banlance(3);
        }
    }

    private void GetPrice() {
        ApiStore.createApi(ApiService.class)
                .NceEthRange()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NceEthRange>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NceEthRange nceEthRange) {
                        if (nceEthRange.getStatus() == 0) {
                            result = nceEthRange.getResult().floatValue();
                            exchange.setText(getResources().getString(R.string.transaction2) + "1:" + String.valueOf(result));

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
                            currentNumber.setText(getResources().getString(R.string.transaction1) + result);
                            buyQuota=banlance.getResult();
                            GetPrice();
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

    @Override
    protected void initView() {
        tl1.setTabData(name);

//        tiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             JumpUtil.overlay(getActivity(), TiaoActivity.class);
//            }
//        });


    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_transaction;
    }

    public static Fragment getInstance() {
        return new TransactionFragment();
    }

    @OnClick({R.id.btn, R.id.type1, R.id.btn_withdraw_money, R.id.type2, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                //兑换记录
                JumpUtil.overlay(getActivity(), ExchangeRecordActivity.class);
                break;
            case R.id.type1:
                if (position == 0) {
                    showPop();
                }
                break;
            case R.id.btn_withdraw_money:
                if(!TextUtils.isEmpty(buyQuota)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.ETH, buyQuota);
                    bundle.putString(Const.FROMTAG, TAG);
                    JumpUtil.overlay(getActivity(), WithdrawMoneyActivity.class, bundle);
                }
                break;
            case R.id.type2:
                if (position == 1) {
                    showPop();
                }
                break;
            case R.id.btn_ok:
                //兑换
                if(Const.IS_FROZEN){
                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
                    return;
                }
                showPop2();
                break;
        }
    }
    //  case R.id.type1:
//            if (position == 0) {
//        showPop();
//    }
//                break;
//            case R.id.type2:
//            if (position == 1) {
//        showPop();
//    }
//                break;
//            case R.id.btn_withdraw_money:
//            //提币
//            JumpUtil.overlay(getActivity(), WithdrawMoneyActivity.class);
//                break;
//            case R.id.btn_ok:
//    //兑换
//    showPop2();
//                break;
//            case R.id.btn_jlx:
//            //兑换记录
//            JumpUtil.overlay(getActivity(), ExchangeRecordActivity.class);
//                break;
    @SuppressLint("WrongConstant")
    private void showPop2() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
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
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
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
        name.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
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
                    ToastUtil.show(getActivity(), getActivity().getString(R.string.d20));
                } else {
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    //兑换
                    TradeNce(name.getText().toString());
                }
            }
        });
    }
    private String ipAddress;
    private String outIp;
    private void TradeNce(String password) {
        if(outIpAddressArray!=null&&outIpAddressArray.length>0) {
            outIp = outIpAddressArray[0];
        }else {
            outIp = " ";
        }
        if(outIpAddressArray!=null&&outIpAddressArray.length>1) {
            ipAddress = outIpAddressArray[1];
        }else {
            ipAddress = " ";
        }
        if(outIpAddressArray==null){
            ToastUtil.show(getActivity(),"正在加载数据,请稍候");
            return;
        }

        String osModel = Util.getOsModel();
        try {
            if(osModel.toLowerCase().contains("unknow")){
                ToastUtil.show(getActivity(),"无法识别手机");
                return;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        String deviceid =Util.getAndroidId(getActivity());
        String netType = Util.getNetType(getActivity());

        Data data = new Data();
        data.setUserid(ConstantUtil.ID);
        data.setEthQty(type == 2 ? input1.getText().toString() : input2.getText().toString());
        data.setQty(type != 2 ? input1.getText().toString() : input2.getText().toString());
        data.setTradeType(type == 2 ? 0 : 1);
        data.setPassword(AESUtil.encry(password));
        data.setAppVersion(Util.getAppVersion(getActivity()));

        data.setIpAddress(ipAddress);
        data.setNetType(netType);
        data.setOsModel(osModel);
        data.setOutIp(outIp);
        data.setDeviceId(deviceid);

        data.setNceEhtRange(result);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test("v==============>>TradeNce==========>>s:"+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .TradeNce(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeNce>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TradeNce tradeNce) {
                        ToastUtil.show(getActivity(), tradeNce.getMsg());
                        if (tradeNce.getStatus() == 0) {
                            if(type==2){
                                Banlance(1);
                            }else {
                                Banlance(3);
                            }
                            popWindow.dismiss();
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

    private void showPop() {

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop3, null);
        bz(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
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

    /**
     * 币种
     *
     * @param inflate
     */
    private void bz(View inflate) {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("ETH");
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
                if (currentItemPosition == 0) {
                    type = 2;
                } else {
                    type = 1;
                }
                if (position == 0) {
                    type1.setText(list.get(currentItemPosition));
                } else {
                    type2.setText(list.get(currentItemPosition));
                }
                if(type==2){
                    Banlance(1);
                }else {
                    Banlance(3);
                }
            }
        });
    }




    public class Data {
        String userid;
        String password;
        String qty;//": 0,
        String ethQty;//": 0,
        double nceEhtRange;
        String appVersion;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;


        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getOsModel() {
            return osModel;
        }

        public void setOsModel(String osModel) {
            this.osModel = osModel;
        }

        public String getOutIp() {
            return outIp;
        }

        public void setOutIp(String outIp) {
            this.outIp = outIp;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getNetType() {
            return netType;
        }

        public void setNetType(String netType) {
            this.netType = netType;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getEthQty() {
            return ethQty;
        }

        public void setEthQty(String ethQty) {
            this.ethQty = ethQty;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        int tradeType;//": 0

        public double getNceEhtRange() {
            return nceEhtRange;
        }

        public void setNceEhtRange(double nceEhtRange) {
            this.nceEhtRange = nceEhtRange;
        }
    }
}
