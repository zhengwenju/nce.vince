package com.bronet.blockchain.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Login;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.WithdrawMoneyActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.google.gson.Gson;

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
 * 重新绑定
 * Created by 18514 on 2019/6/25.
 */

public class RebindActivity extends BaseActivity {
    @BindView(R.id.et_auxiliaries)
    EditText etAuxiliaries;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.loding)
    ProgressBar loding;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_quote)
    EditText etQuote;
    @BindView(R.id.hidden_pwd_img)
    ImageView mHiddenPwdImg;
    @BindView(R.id.display_pwd_img)
    ImageView mDisplayPwdImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rebind;
    }
    int type = 1; //1是助记词 2是密钥
    private String auxiliariesOrKey;
    @Override
    protected void initView() {
        etAuxiliaries.addTextChangedListener(new TextChanged());
    }
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        auxiliariesOrKey = extras.getString(Const.AUXILIARIES_OR_KEY);
        type =extras.getInt(Const.LOGINTYPE);
        etAuxiliaries.setText(auxiliariesOrKey);
    }

    @Override
    protected void setEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            finish();
        }
    }

    @OnClick({R.id.btn_ok, R.id.btn_back,R.id.hidden_pwd_img, R.id.display_pwd_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
//                if(Const.IS_FROZEN){
//                    DialogUtil.showFrozen(this,Const.FROZEN_CONTENT);
//                    return;
//                }
                if(TextUtils.isEmpty(etPassword.getText().toString())){
                    DialogUtil.show(this,getString(R.string.d20));
                    return;
                }

                if(TextUtils.isEmpty(etQuote.getText().toString())){
                    DialogUtil.show(this,getString(R.string.login32));
                    return;
                }


                if(!ClickUtils.isFastClick()){
                    return;
                }

                if(type==1){
                    Mnemonickeys(etPassword.getText().toString(),etQuote.getText().toString());
                }else if(type==2){
                    PrivateKey(etPassword.getText().toString(),etQuote.getText().toString());
                }

                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.hidden_pwd_img:
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mDisplayPwdImg.setVisibility(View.VISIBLE);
                mHiddenPwdImg.setVisibility(View.GONE);
                etPassword.setSelection(etPassword.getText().length());
                break;
            case R.id.display_pwd_img:
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mDisplayPwdImg.setVisibility(View.GONE);
                mHiddenPwdImg.setVisibility(View.VISIBLE);
                etPassword.setSelection(etPassword.getText().length());
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public class TextChanged implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(etAuxiliaries.getText().toString())) {
                btnOk.setSelected(true);
            } else {
                btnOk.setSelected(false);
            }
        }
    }

    private void PrivateKey(String password,String quote) {
        String osModel = Util.getOsModel();
        try {
            if(osModel.toLowerCase().contains("unknow")){
                DialogUtil.show(this,"无法识别手机");
                return;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


        String deviceid =Util.getAndroidId(this);
        String netType = Util.getNetType(this);

        KeyData data1 = new KeyData();
        data1.privateKey = AESUtil.encry(etAuxiliaries.getText().toString());
        String inIp = Util.getIPAddress(this);
        data1.password= AESUtil.encry(password);
        data1.osModel =osModel;
        data1.deviceId=deviceid;
        data1.appVersion=Util.getAppVersion(this);
        data1.inIp=inIp;
        data1.isSimulator= Util.isMonitor(this);
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data1.ipAddress = Const.outIpAddressArray[1];
        }else {
            data1.ipAddress = " ";
        }
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data1.outIp = Const.outIpAddressArray[0];
        }else {
            data1.outIp = " ";
        }
        data1.netType=netType;


        data1.qty =quote;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data1);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .PrivateKey(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull Login login) {
                        if(!TextUtils.isEmpty(login.getMsg())){
                            DialogUtil.show(activity, login.getMsg());
                        }

                        if (login.getStatus() == 0) {
                            finish();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(RebindActivity.this,146,e.getMessage(),true);
                        loding.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        loding.setVisibility(View.GONE);
                    }
                });
    }


    private void Mnemonickeys(String password,String quote) {
        String osModel = Util.getOsModel();
        try {
            if(osModel.toLowerCase().contains("unknow")){
                DialogUtil.show(this,"无法识别手机");
                return;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        String inIp = Util.getIPAddress(this);
        Data data = new Data();
        String deviceid =Util.getAndroidId(this);
        String netType = Util.getNetType(this);
        data.mnemonickeys = AESUtil.encry(etAuxiliaries.getText().toString());
//        data.password = etPassword.getText().toString();
//        data.rePassword = etConfirm.getText().toString();
        data.osModel =osModel;
        data.deviceId=deviceid;
        data.password=AESUtil.encry(password);
        data.inIp=inIp;
        data.isSimulator= Util.isMonitor(this);
        data.appVersion=Util.getAppVersion(this);
        data.qty =quote;
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

        L.test("v==============>>login==========>>s:"+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .Mnemonickeys(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull Login login) {
                        if(!TextUtils.isEmpty(login.getMsg())){
                            DialogUtil.show(activity, login.getMsg());
                        }
                        if (login.getStatus() == 0) {
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test(e.getMessage());
                        loding.setVisibility(View.GONE);
                        JumpUtil.errorHandler(RebindActivity.this,147,e.getMessage(),true);
                    }

                    @Override
                    public void onComplete() {
                        loding.setVisibility(View.GONE);
                    }
                });
    }

    public class Data {
        String mnemonickeys;
        String password;
        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String qty;
        String inIp;
        String isSimulator;
    }

    public class KeyData {
        String privateKey;
        String password;
        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String qty;
        String inIp;
        String isSimulator;
    }
}
