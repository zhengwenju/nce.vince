package com.bronet.blockchain.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Assets;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.CoinDataModel;
import com.bronet.blockchain.data.CoinDetail;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.data.Withdrawal2;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.fragment.AssetsFragment;
import com.bronet.blockchain.ui.fragment.TransactionFragment;
import com.bronet.blockchain.ui.scancode.CommonScanActivity;
import com.bronet.blockchain.ui.scancode.utils.Constant;
import com.bronet.blockchain.util.Action;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.itheima.wheelpicker.WheelPicker;
import com.zhy.android.percent.support.PercentRelativeLayout;

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
 * 提币
 * Created by 18514 on 2019/7/16.
 */

public class WithdrawMoneyActivity extends BaseActivity {
    PercentRelativeLayout btnBack;
    @BindView(R.id.btn_jl)
    TextView btnJl;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.type_all)
    TextView type_all;

    @BindView(R.id.tip_tv)
    TextView tipTv;

    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.number2)
    TextView number2;
    @BindView(R.id.number3)
    TextView number3;
    @BindView(R.id.btn_ok)
    Button btnOk;
    PopupWindow popWindow;
    @BindView(R.id.unit_num_tv)
    TextView unitNumTv;

    @BindView(R.id.unit_sxf_tv)
    TextView unitSxfTv;

    @BindView(R.id.quto_num_tv)
    TextView qutoNumTv;
    @BindView(R.id.record_iv)
    ImageView recordIv;



    @BindView(R.id.btn_choose_coin_type)
    TextView btnChooseCoinType;

    @BindView(R.id.link_name_linearlayout)
    LinearLayout linkNameLinearlayout;

    @BindView(R.id.scrollview)
    com.bronet.blockchain.view.MyScrollView scrollview;


    ArrayList<Assets.Items> items = null;
    double eth = 0;
    String fromTag;
    ArrayList<String> coins = new ArrayList<>();

    private String EthLimitNum ="0";
    private String USDTLimitNum ="0";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wm;
    }

    @Override
    protected void initView() {

    }

    private  Thread t;
    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        fromTag = bundle.getString(Const.FROMTAG);
        if (fromTag.equals(AssetsFragment.TAG)) {
            items = (ArrayList<Assets.Items>) bundle.getSerializable(Const.ITEMS);
            if (items != null) {
                for (Assets.Items c : items) {
                    coins.add(c.getCoinType());
                }
            }
        } else if (fromTag.equals(TransactionFragment.TAG)) {
            String buyQuota = bundle.getString(Const.ETH);
            eth = Double.valueOf(buyQuota);
        }


        if (L.isDebug) {
            address.setText(Const.TB_ADDRESS);
        }

        if (Const.outIpAddressArray == null) {
            t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    Const.outIpAddressArray = Util.getOutNetIP(WithdrawMoneyActivity.this, 1);
                }
            };
            t.start();
        }

    }

    @Override
    protected void onPause() {
        if(t!=null) {
            t.interrupt();
        }
        super.onPause();
    }

    private String conntQuote="0";
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
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

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
    protected void setEvent() {
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(currentCoin.equals(STR_ETH)) {
                        if (thisBean != null) {
                            number3.setText(String.valueOf(Float.valueOf(number.getText().toString()) - Float.valueOf(number2.getText().toString())) + thisBean.getCoinName());
                        }
                    }else if(currentCoin.equals(STR_USDT)) {
                        if (thisBean != null) {
                            number3.setText(String.valueOf(Float.valueOf(number.getText().toString()) - Float.valueOf(number2.getText().toString())) + thisBean.getCoinName());
                        }
                    }
                } catch (Exception ex) {
                    number3.setText("");
                    ex.printStackTrace();
                }
            }
        });

        btnChooseCoinType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取币种
                BaseList();
            }
        });
    }

    @OnClick({R.id.btn_back, R.id.btn_jl, R.id.btn_ok, R.id.unit_num_tv, R.id.btn_code, R.id.type_all,R.id.record_iv})
    public void onViewClicked(View view) {
        String cointype = unitNumTv.getText().toString();
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_jl:
                JumpUtil.overlay(activity, WithdrawMoneyListActivity.class);
                break;
            case R.id.record_iv:
                JumpUtil.overlay(activity, WithdrawAdjustLogListActivity.class);
                break;
            case R.id.unit_num_tv:
//                showPop(1);
                break;
            case R.id.btn_ok:
                String count = number.getText().toString();
                if (TextUtils.isEmpty(count)) {
                    ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest14));
                    return;
                }
                try {
//                    if (Double.parseDouble(count) > Double.parseDouble(conntQuote)) {
//                        ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.transf54));
//                        return;
//                    }

                    //可提币数量不足
                    if(currentCoin !=null) {
                        if (currentCoin.equals(STR_ETH)) {
                            if (Double.parseDouble(count) > Double.parseDouble(EthLimitNum)) {
                                ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.transf54));
                                return;
                            }
                        } else if (currentCoin.equals(STR_USDT)) {
                            if (Double.parseDouble(count) > Double.parseDouble(USDTLimitNum)) {
                                ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.transf54));
                                return;
                            }
                        }
                    }else {
                        ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.choose_cointype));
                        return;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                if (!TextUtils.isEmpty(count)) {
                    if(currentCoin.equals(STR_ETH)) {
                        //最小提币数量为1
                        if (Double.parseDouble(count) < 1) {
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.zc3));
                            return;
                        }
                        //单笔提币数量最大为10
                        if (Double.parseDouble(count) > 10) {
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.zc4));
                            return;
                        }
                    }else if(currentCoin.equals(STR_USDT)){
                        //最小提币数量为2
                        if (Double.parseDouble(count) < 2) {
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.zc4));
                            return;
                        }
                        //单笔提币数量最大为500
                        if (Double.parseDouble(count) > 500) {
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.zc5));
                            return;
                        }
                    }
                }
                if (TextUtils.isEmpty(address.getText().toString())) {
                    ToastUtil.show(activity, activity.getString(R.string.d26));
                    return;
                }

                if(currentCoin.equals(STR_ETH)) {
                    if (fromTag.equals(AssetsFragment.TAG)) {
                        if (!coins.contains(cointype)) {
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest12));
                            return;
                        }
                    } else if (fromTag.equals(TransactionFragment.TAG)) {
                        if (!cointype.equals("ETH")) { //交易界面，目前只支持ETH提币
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest12));
                            return;
                        }
                    }
                }else if(currentCoin.equals(STR_USDT)){
                    if (fromTag.equals(AssetsFragment.TAG)) {
                        if (!coins.contains(cointype)) {
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest12));
                            return;
                        }
                    } else if (fromTag.equals(TransactionFragment.TAG)) {
                        if (!cointype.equals("USDT")) { //交易界面，目前只支持ETH提币
                            ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest12));
                            return;
                        }
                    }
                }
                TodayWithdrawLimit();
                break;
            case R.id.btn_code:
                start(1);
                break;
            case R.id.type_all:

                if (fromTag.equals(AssetsFragment.TAG)) {
                    if (!coins.contains(cointype)) {
                        ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest12));
                        return;
                    }

                    for (Assets.Items item : items) {
                        if (item.getCoinType().equals(cointype)) {
                            double eth1 = Double.valueOf(item.getBanlance());
                            int eth2 = (int) eth1;
                            number.setText(String.valueOf(eth2));
                            return;
                        }
                    }
                } else if (fromTag.equals(TransactionFragment.TAG)) {
                    if (!cointype.contains("ETH")) { //交易界面，目前只支持ETH提币
                        ToastUtil.show(WithdrawMoneyActivity.this, getString(R.string.invest12));
                        return;
                    }
                    int eth1 = (int) eth;
                    number.setText(String.valueOf(eth1));
                }


                break;
        }
    }

    private void start(int i) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent intent = new Intent(activity, CommonScanActivity.class);
            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
//            intent.putExtra("type", i);
            startActivityForResult(intent, 11);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10) {
            String code = data.getStringExtra("code");
            address.setText(code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == 0) {
            Intent intent = new Intent(activity, CommonScanActivity.class);
            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
            startActivity(intent);
        }

    }

    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate = null;
        switch (type) {
            case 1:
//                inflate = LayoutInflater.from(activity).inflate(R.layout.pop3, null);
//                bz(inflate);
                break;
            case 2:
                inflate = LayoutInflater.from(activity).inflate(R.layout.pop4, null);
                initB(inflate);
                break;
            case 3:
                inflate = LayoutInflater.from(activity).inflate(R.layout.pop3, null);
                bz2(inflate);

                break;
        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
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
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 支付密码
     *
     * @param inflate
     */
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
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
                    ToastUtil.show(activity, activity.getString(R.string.d20));
                } else {
                    //支付密码
                    if (!ClickUtils.isFastClick()) {
                        return;
                    }
                    withdrawal(name.getText().toString());
                }
            }
        });
    }

    private void withdrawal(String paymentPassword) {
        String deviceid = Util.getAndroidId(this);
        String netType = Util.getNetType(this);
        String inIp = Util.getIPAddress(this);
        Data data = new Data();
        data.coinId = currentCoinId;
        data.userId = ConstantUtil.ID;
        data.qty = number.getText().toString();
        data.toAddress = address.getText().toString();
        data.paymentPassword = AESUtil.encry(paymentPassword);

        data.deviceId = deviceid;
        data.netType = netType;
        data.isSimulator = Util.isMonitor(this);
        data.appVersion = Util.getAppVersion(this);
        data.inIp = inIp;
//        data.imei=Util.imei;

        if (Const.outIpAddressArray != null && Const.outIpAddressArray.length > 1) {
            data.ipAddress = Const.outIpAddressArray[1];
        } else {
            data.ipAddress = " ";
        }
        if (Const.outIpAddressArray != null && Const.outIpAddressArray.length > 0) {
            data.outIp = Const.outIpAddressArray[0];
        } else {
            data.outIp = " ";
        }
        String osModel = Util.getOsModel();
        data.osModel = osModel;
        try {
            if (osModel.toLowerCase().contains("unknow")) {
                ToastUtil.show(this, "无法识别手机");
                return;
            }
            if (data.outIp.contains("识别")) {
                ToastUtil.show(this, "无法识别地址");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test("withdrawal s===>>>" + s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .withdrawal(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Withdrawal2>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull final Withdrawal2 withdrawal) {
//                        if (withdrawal.getStatus() == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.FullScreenDialog);
                            final AlertDialog dialog = builder.create();
//                            dialog.setCancelable(false);
                            dialog.show();
                            dialog.getWindow().clearFlags(
                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                            | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                            dialog.getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            final Window window = dialog.getWindow();
                            window.setContentView(R.layout.alter3);

                            View btnYes = window.findViewById(R.id.btn_yes);
                            View contentTv = window.findViewById(R.id.lookfor_tv);
                            if(withdrawal.getStatus()==0){
                                contentTv.setVisibility(View.GONE);
                            }else {
                                contentTv.setVisibility(View.VISIBLE);
                                contentTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                       JumpUtil.overlay(WithdrawMoneyActivity.this,WithdrawMoneyLimitExplainActivity.class);
                                    }
                                });
                            }
                            TextView content = (TextView) window.findViewById(R.id.content);
                            content.setText(withdrawal.getMsg());
                            // 自定义图片的资源
                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    dialog.dismiss();
                                    if(withdrawal.getStatus()==0) {
                                        finish();
                                        Intent intent = new Intent();
                                        intent.setAction(Action.WithMoneyAction);
                                        sendBroadcast(intent);
                                    }
                                }
                            });
//                        }else {
//
//                        }
                        if (popWindow != null)
                            popWindow.dismiss();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("withdrawal Throwable:" + e.getMessage());
                        JumpUtil.errorHandler(WithdrawMoneyActivity.this, 101, e.getMessage(), true);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void TodayWithdrawLimit() {
        WithData data3 = new WithData();
        data3.userId = ConstantUtil.ID;
        data3.qty = number.getText().toString();
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data3);
        L.test("s==========>>>" + s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .TodayWithdrawLimit(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Model10 model) {
                        if (model != null) {
                            if (model.getStatus() == 1) {
                                DialogUtil.show(WithdrawMoneyActivity.this, model.getMsg());

                            } else if (model.getStatus() == 0) {
                                showPop(2);
                            }
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

    private void WithdrawWarning(int coinId) {
        scrollview.setVisibility(View.VISIBLE);
        ApiStore.createApi(ApiService.class)
                .WithdrawWarning(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyWarning>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TradeBuyWarning tradeBuyWarning) {
                        try {
                            if (tradeBuyWarning.getStatus() == 0) {
                                if (!TextUtils.isEmpty(tradeBuyWarning.getResult())) {
                                    if (tipTv != null) {
                                        tipTv.setText(tradeBuyWarning.getResult());
                                    }
                                }
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(WithdrawMoneyActivity.this, 102, e.getMessage(), true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void withdrawAddressList(int coinid) {
        ApiStore.createApi(ApiService.class)
                .withdrawAddressList(ConstantUtil.ID, coinid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CoinDataModel tradeBuyWarning) {
                        if (tradeBuyWarning.getStatus() == 0) {
                            showPopCoin(tradeBuyWarning);
                        } else {
                            address.setText("");
                        }
                        number.setText("");


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(WithdrawMoneyActivity.this, 103, e.getMessage(), true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //    /**
//     * 币种
//     *
//     * @param inflate
//     */
//    private void bz(View inflate) {
//        final ArrayList<String> list = new ArrayList<String>();
//        list.add("BTC");
//        list.add("ETH");
//        final WheelPicker wp = (WheelPicker) inflate.findViewById(R.id.wp);
//        wp.setCyclic(false);
//        wp.setData(list);
//        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popWindow.dismiss();
//            }
//        });
//        inflate.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popWindow.dismiss();
//                int currentItemPosition = wp.getCurrentItemPosition();
//                type_eth.setText(list.get(currentItemPosition));
//                number.setText("");
//            }
//        });
//    }
    private CoinDetail.ResultBean thisBean;

    private String currentCoin=null;
    private String currentCoinId="1";
    private static String STR_USDT="USDT";
    private static String STR_ETH="ETH";
    /**
     * 币种
     *
     * @param inflate
     */
    private void bz2(View inflate) {
        final List<CoinDetail.ResultBean> result = CoinDetails.getResult();
        final List<String> list = new ArrayList<>();
        for (CoinDetail.ResultBean bean : result) {
            list.add(bean.getCoinName());
        }
//        List<CoinDetail.ResultBean> result = CoinDetails.getResult();
//        String coinName = result.get(0).getCoinName();

        final WheelPicker wp = (WheelPicker) inflate.findViewById(R.id.wp);
        wp.setCyclic(false);
        wp.setData(list); //Collections.singletonList(coinName)
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

                unitNumTv.setText("");
                unitSxfTv.setText("");
                qutoNumTv.setText("");
                tipTv.setText("");
                number.setText("");
                number2.setText("");

                int currentItemPosition = wp.getCurrentItemPosition();
                CoinDetail.ResultBean bean = result.get(currentItemPosition);
                withdrawAddressList(bean.getTypeId());
                unitNumTv.setText(bean.getCoinName());
                unitSxfTv.setText(bean.getCoinName());
                thisBean = bean;
                Banlance(bean.getTypeId());

                if(bean.getCoinName().equals("USDT")){
                    linkNameLinearlayout.setVisibility(View.VISIBLE);
                    WithdrawWarning(4);
                    AmountLimit("USDT");
                    number2.setText(String.valueOf(1));
                    currentCoin =STR_USDT;
                    number.setHint(getString(R.string.zc5));
                    currentCoinId="4";
                }else if(bean.getCoinName().equals("ETH")){
                    linkNameLinearlayout.setVisibility(View.GONE);
                    WithdrawWarning(1);
                    AmountLimit("ETH");
                    number2.setText(String.valueOf(0.01));
                    currentCoin =STR_ETH;
                    number.setHint(getString(R.string.zc3));
                    currentCoinId="1";
                }
                recordIv.setVisibility(View.VISIBLE);
            }
        });
    }

    public class Data {
        String userId;
        String coinId;
        String toAddress;
        String qty;
        String paymentPassword;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String inIp;
        String isSimulator;
    }


    public class WithData {
        String userId;
        String qty;
    }


    private CoinDetail CoinDetails;

    private void BaseList() {
        ApiStore.createApi(ApiService.class)
                .BaseList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CoinDetail CoinDetails_) {
                        CoinDetails = CoinDetails_;
                        showPop(3);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(WithdrawMoneyActivity.this, 104, e.getMessage(), true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @SuppressLint("WrongConstant")
    private void showPopCoin(CoinDataModel bean) {
        View inflate = null;
        inflate = LayoutInflater.from(activity).inflate(R.layout.pop15, null);
        choosebz(inflate, bean);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);
        changeWindowAlfa(0.5f);
        popWindow.showAtLocation(parent,
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }

    private void choosebz(View inflate, CoinDataModel bean) {
        final TextView tv1 = inflate.findViewById(R.id.tv1);
        final TextView tv2 = inflate.findViewById(R.id.tv2);
        tv1.setText(bean.getResult().getAddress1());
        tv2.setText(bean.getResult().getAddress2());

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
                address.setText(tv1.getText().toString());
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
                address.setText(tv2.getText().toString());
            }
        });

    }



    //可用提币额度限制
    private void AmountLimit(String coid) {
        ApiStore.createApi(ApiService.class)
                .AmountLimit(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ContractNum model) {
                        try {
                            if(model.getStatus()==0){ //撤消成功
                                EthLimitNum = model.getResult().getEth();
                                USDTLimitNum = model.getResult().getUsdt();
                                String NceLimitNum = model.getResult().getNce();
                                if(coid.equals("ETH")) {
                                    qutoNumTv.setText(NceLimitNum + "NCE\n≈" + EthLimitNum + "ETH");
                                }else if(coid.equals("USDT")){
                                    qutoNumTv.setText(NceLimitNum + "NCE\n≈" + USDTLimitNum + "USDT");
                                }
                            }else {

                                qutoNumTv.setText("转出额度：0.00");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
