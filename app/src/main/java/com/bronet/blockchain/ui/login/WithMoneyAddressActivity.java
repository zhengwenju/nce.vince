package com.bronet.blockchain.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.CoinDataModel;
import com.bronet.blockchain.data.CoinDetail;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.ui.scancode.CommonScanActivity;
import com.bronet.blockchain.ui.scancode.utils.Constant;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 提币地址
 * Created by 18514 on 2019/6/25.
 */

public class WithMoneyAddressActivity extends BaseActivity {

    @BindView(R.id.with_coin_ev10)
    EditText with_coin_ev10;

    @BindView(R.id.title_tv)
    TextView titleTv;


    @BindView(R.id.with_coin_ev20)
    EditText with_coin_ev20;

    @BindView(R.id.choose_coin_tv)
    TextView choose_coin_tv;

    @BindView(R.id.choose_coin_llayout)
    LinearLayout chooseCoinLlayout;

    @BindView(R.id.btn_ok)
    Button btnOk;
    int type = 1;
    @BindView(R.id.btn_back)
    RelativeLayout back;

    @BindView(R.id.close1)
    ImageView close1;

    @BindView(R.id.close2)
    ImageView close2;

    @BindView(R.id.qrcode1)
    ImageView qrcode1;

    @BindView(R.id.qrcode2)
    ImageView qrcode2;
    @BindView(R.id.back_iv)
    ImageView back_iv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_money_address;
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
        titleTv.setText(R.string.assets23);
    }
    private String[] outIpAddressArray=null;
    @Override
    protected void initData() {

        withdrawAddressList(1);
    }

    @Override
    protected void setEvent() {
        chooseCoinLlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseList();
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                with_coin_ev10.setText("");
            }
        });
        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                with_coin_ev20.setText("");
            }
        });

        qrcode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan(1);
            }
        });
        qrcode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan(2);
            }
        });
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startScan(int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent intent = new Intent(activity, CommonScanActivity.class);
            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
            startActivityForResult(intent, requestCode);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            String code = data.getStringExtra("code");
            if (requestCode == 1) {
                with_coin_ev10.setText(code);
            } else if (requestCode == 2) {
                with_coin_ev20.setText(code);
            }
        }
    }

    //增加提币地址
    private void SaveWithdrawAddress(String pwd) {
        Data1 data = new Data1();
        data.userid=ConstantUtil.ID;
        data.password= AESUtil.encry(pwd);
        data.coinId=coinid;
        data.id1=id1;
        data.address1=with_coin_ev10.getText().toString(); //"0x41ca01dfbc480b4ddfcf044585c297b96bd74586"
        data.id2=id2;
        data.address2=with_coin_ev20.getText().toString();

        String deviceid = Util.getAndroidId(this);
        String osModel = Util.getOsModel();
        String netType = Util.getNetType(this);

        String inIp = Util.getIPAddress(this);

        data.osModel =osModel;
        data.deviceId=deviceid;
        data.appVersion= Util.getAppVersion(this);
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

        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .SaveWithdrawAddress(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {
                        ToastUtil.show(WithMoneyAddressActivity.this, tradeNce.getMsg());
                        if(tradeNce.getStatus()==0) {
                           withdrawAddressList(1);
                        }
                        if(popWindow!=null){
                            popWindow.dismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(WithMoneyAddressActivity.this,148,e.getMessage(),true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
                        CoinDetails=CoinDetails_;
                        showPop(1);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(WithMoneyAddressActivity.this,149,e.getMessage(),true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnClick({R.id.btn_ok, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if(Const.IS_FROZEN){
                    DialogUtil.showFrozen(this,Const.FROZEN_CONTENT);
                    return;
                }
                showPop(2);
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    public void onBackPressed() {
        if (ConstantUtil.TYPE) {
            JumpUtil.overlay(activity, MainActivity.class);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    //买入
    private void modifyPwd(String oldPwd,String newPwd,String rePwd) {
//        Data data = new Data();
//        data.userid=Integer.valueOf(ConstantUtil.ID);
//        data.oldPwd=oldPwd;
//        data.newPwd=newPwd;
//        data.rePwd=rePwd;
//        String s = new Gson().toJson(data);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
//        ApiStore.createApi(ApiService.class)
//                .EditPassword(body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Recall>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Recall tradeNce) {
//                        ToastUtil.show(WithMoneyAddressActivity.this, tradeNce.getMsg());
//                        if(tradeNce.getStatus()==0) {
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    public class Data {
        int userid;
        String oldPwd;
        String newPwd;
        String rePwd;
    }

    public class Data1 {
        String userid;
        String password;
        int coinId;
        int id1;
        String address1;
        int id2;
        String address2;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String inIp;
    }

    private PopupWindow popWindow;
    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate = null;
        switch (type) {
            case 1:
                inflate = LayoutInflater.from(activity).inflate(R.layout.pop3, null);
                bz(inflate);
                break;
            case 2:
                inflate = LayoutInflater.from(activity).inflate(R.layout.pop4, null);
                initB(inflate);
                break;
        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
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

    /**
     * 支付密码
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
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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


                if (TextUtils.isEmpty(name.getText().toString())){
                    ToastUtil.show(activity,activity.getString(R.string.d20));
                }else{
                    //支付密码
                    if(!ClickUtils.isFastClick()){
                        return;
                    }

                    SaveWithdrawAddress(name.getText().toString());
                }
            }
        });
    }


    private int coinid=1;
    private int id1;
    private int id2;
    /**
     * 币种
     * @param inflate
     */
    private void bz(View inflate) {
        final List<CoinDetail.ResultBean> result = CoinDetails.getResult();
        final List<String> list = new ArrayList<>();
        for(CoinDetail.ResultBean bean:result){
            list.add(bean.getCoinName());
        }
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

				with_coin_ev10.setText("");
				with_coin_ev20.setText("");

                int currentItemPosition = wp.getCurrentItemPosition();
                CoinDetail.ResultBean bean = result.get(currentItemPosition);

                choose_coin_tv.setText(bean.getCoinName());
                coinid = bean.getTypeId();
                withdrawAddressList(bean.getTypeId());


            }
        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    private void withdrawAddressList(int coinid) {
        ApiStore.createApi(ApiService.class)
                .withdrawAddressList(ConstantUtil.ID,coinid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull CoinDataModel tranEntrusts) {
//                        ToastUtil.show(WithMoneyAddressActivity.this,tranEntrusts.getMsg());
                        if (tranEntrusts.getStatus() == 0) {

                            String s1=tranEntrusts.getResult().getAddress1();
                            String s2 =tranEntrusts.getResult().getAddress2();
                            with_coin_ev10.setText(s1);
                            with_coin_ev20.setText(s2);

                            id1 =tranEntrusts.getResult().getId1();
                            id2 =tranEntrusts.getResult().getId2();
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(WithMoneyAddressActivity.this,150,e.getMessage(),true);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}
