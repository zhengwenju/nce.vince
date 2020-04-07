package com.bronet.blockchain.ui.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Login;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.UserInfo;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.fix.AES;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.gson.Gson;

import java.util.Arrays;

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
 * 登录
 * Created by 18514 on 2019/6/25.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.et_auxiliaries)
    EditText etAuxiliaries;
//    @BindView(R.id.et_password)
//    EditText etPassword;
//    @BindView(R.id.et_confirm)
//    EditText etConfirm;
    @BindView(R.id.btn_secret_key_login)
    TextView btnSecretKeyLogin;
    @BindView(R.id.rebinding_tv)
    TextView rebindingTv;

    @BindView(R.id.btn_ok)
    Button btnOk;
    int type = 1; //1是助记词 2是密钥

    @BindView(R.id.loding)
    ProgressBar loding;
    @BindView(R.id.login_tv2)
    TextView mLoginTv;
    @BindView(R.id.checkbox)
    CheckBox checkbox;

    private int errorcode=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etAuxiliaries.addTextChangedListener(new TextChanged());
        mLoginTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        etPassword.addTextChangedListener(new TextChanged());
//        etConfirm.addTextChangedListener(new TextChanged());
    }
    private  Thread t;
    @Override
    protected void initData() {
        if(L.isDebug){
            etAuxiliaries.setText(Const.ZJC);
//            etPassword.setText(Const.PWD);
//            etConfirm.setText(Const.PWD);
        }
//        if(Const.outIpAddressArray==null) {
           t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    Const.outIpAddressArray = Util.getOutNetIP(LoginActivity.this, 1);
                    L.test("LoginActivity result v=======>>>outIpAddressArray==========>>" + Arrays.toString(Const.outIpAddressArray));
                }
            };
            t.start();
//        }
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                errorcode = extras.getInt("error");
                if (errorcode >0) {
                    VerifyUserPolicy();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    protected void setEvent() {

    }

    private void VerifyUserPolicy() {
        Data3 data3 = new Data3();
        data3.userId=ConstantUtil.ID;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data3);
        L.test("s==========>>>"+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .VerifyUserPolicy(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull Model10 model) {
                        if(model!=null) {

                            ConstantUtil.ID = "";
                            SharePreData sharePreData = new SharePreData();
                            sharePreData.add(Const.SP_ID, "");
                            sharePreData.add(Const.SP_TOKEN, "");

                            DialogUtil.show(LoginActivity.this, model.getMsg() + "\n(code:" + errorcode + ")");
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        loding.setVisibility(View.GONE);
                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            finish();
        }
    }

    @OnClick({R.id.btn_register, R.id.btn_secret_key_login, R.id.btn_ok,R.id.rebinding_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                //注册
                JumpUtil.overlay(activity, RegisterActivity.class);
                break;
            case R.id.btn_secret_key_login:
                //秘钥登录
                if (btnSecretKeyLogin.getText().toString().equals(getResources().getString(R.string.login18))) {
                    type = 1;
                    btnSecretKeyLogin.setText(getString(R.string.login5));
                    etAuxiliaries.setHint(getResources().getString(R.string.login2));
                } else {
                    type = 2;
                    etAuxiliaries.setHint(getResources().getString(R.string.login25));
                    btnSecretKeyLogin.setText(getString(R.string.login18));
                }
                break;
            case R.id.btn_ok:

//                if (etPassword.getText().toString().length() != 0 && etPassword.getText().toString().equals(etConfirm.getText().toString())) {

//                    String pwd = etPassword.getText().toString();
//                    boolean letterDigit = Util.isLetterDigit(pwd);
//                    if(!letterDigit){
//                        return;
//                    }
                if (!checkbox.isChecked()){

                    DialogUtil.show(this, getString(R.string.login35));
                    return;
                }

                    SharePreData sharePreData = new SharePreData();
                    sharePreData.add(Const.SP_ID, "");
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    if (type == 1) {
                        login();
                    } else {
                        loginkey();
                    }
//                } else {
//                }
                break;

            case R.id.rebinding_tv:
                if(TextUtils.isEmpty(etAuxiliaries.getText().toString())){
                    DialogUtil.show(this,getString(R.string.login31));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(Const.AUXILIARIES_OR_KEY,etAuxiliaries.getText().toString());
                bundle.putInt(Const.LOGINTYPE,type);
                JumpUtil.overlay(this, RebindActivity.class,bundle);
                break;
        }
    }

    private String ipAddress;
    private String outIp;
    private void loginkey() {
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
        String light = Util.isMonitor(this);
        Data1 data1 = new Data1();
        data1.privateKey = AESUtil.encry(etAuxiliaries.getText().toString());
        data1.osModel =osModel;
        data1.deviceId=deviceid;
        data1.appVersion=Util.getAppVersion(this);

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
        String inIp = Util.getIPAddress(this);
        data1.netType=netType;
        data1.inIp=inIp;
        data1.isSimulator=light;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data1);
        L.test("v==============>>loginkey==========>>s:"+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .loginkey(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Login login) {
                        if(!TextUtils.isEmpty(login.getMsg())) {
                            DialogUtil.show(activity, login.getMsg());
                        }
                        if (login.getStatus() == 0) {
                            //登录成功
                            ConstantUtil.ID = AESUtil.encry(String.valueOf(login.getResult().getId()));
//                            ConstantUtil.PWD =etConfirm.getText().toString();
                            Const.TOKEN ="Bearer "+login.getResult().getToken();
                            if (ConstantUtil.TYPE||errorcode>0) {
                                JumpUtil.overlay(activity, MainActivity.class);
                            }

                            try {
                                SharePreData sharePreData = new SharePreData();
                                sharePreData.add(Const.SP_ID, AES.encrypt(String.valueOf(login.getResult().getId())));
                                sharePreData.add(Const.SP_TOKEN, Const.TOKEN);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                            finish();
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
    protected void onPause() {
        JumpUtil.loginflag=0;
        if(t!=null){
            t.interrupt();
        }
        super.onPause();
    }




    private void login() {
        String osModel = Util.getOsModel();
        try {
            if(osModel.toLowerCase().contains("unknow")){
                DialogUtil.show(this,"无法识别手机");
                return;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Data data = new Data();
        String light = Util.isMonitor(this);
        String deviceid =Util.getAndroidId(this);
        String netType = Util.getNetType(this);
        String inIp = Util.getIPAddress(this);


        data.mnemonickeys = AESUtil.encry(etAuxiliaries.getText().toString());
//        data.password = etPassword.getText().toString();
//        data.rePassword = etConfirm.getText().toString();
        data.osModel =osModel;
        data.deviceId=deviceid;
        data.appVersion=Util.getAppVersion(this);

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
        data.isSimulator=light;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        Log.d("1111", "login: "+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if(loding!=null)
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull Login login) {
                        if(!TextUtils.isEmpty(login.getMsg())){
                            DialogUtil.show(activity, login.getMsg()); //TODO
                        }
                        JumpUtil.loginflag=0;
                        if (login.getStatus() == 0) {
                            L.test("首次登录成功userid========>>>"+String.valueOf(login.getResult().getId()));
                            //登录成功
                            ConstantUtil.ID = AESUtil.encry(String.valueOf(login.getResult().getId()));
//                            ConstantUtil.PWD =etConfirm.getText().toString();
                            Const.TOKEN ="Bearer "+login.getResult().getToken();
                            if (ConstantUtil.TYPE||errorcode>0) {
                                JumpUtil.overlay(activity, MainActivity.class);
                            }

                            try {
                                SharePreData sharePreData = new SharePreData();
                                sharePreData.add(Const.SP_ID, AES.encrypt(String.valueOf(login.getResult().getId())));
                                sharePreData.add(Const.SP_TOKEN, Const.TOKEN);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }


//                            SharePreData sharePreData = new SharePreData();
//                            ConstantUtil.ID= AES.decrypt(sharePreData.getString(Const.SP_ID));
//                            L.test("非首次登录成功userid========>>>"+ConstantUtil.ID);
//                            ConstantUtil.ID= AESUtil.desEncry(ConstantUtil.ID);
//                            createToken();
                            getUserInfo();

                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if(loding!=null)
                        loding.setVisibility(View.GONE);
                    }
                });
    }
//    private void createToken(){
//        Data data = new Data();
//        String s1 = new Gson().toJson(data);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s1);
//        L.test("checkVersion s1====>>>"+s1);
//        ApiStore.createApi(ApiService.class)
//                .CreateToken(body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<TokenModel>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull TokenModel appModel) {
//                        String token = appModel.getType()+" "+appModel.getAccess();
//                        Const.TOKEN = token;
//                        Valus();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                        L.test(e.getMessage());
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }});
//    }
//    private void Valus(){
//        ApiStore.createApi(ApiService.class)
//                .Values()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull Object jsonArray) {
//                        L.test(jsonArray);
//
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                        L.test(e.getMessage());
//                        ConstantUtil.ID=null;
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
    private void getUserInfo(){
        ApiStore.createApi(ApiService.class)
                .info(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull UserInfo userInfo) {
                        if (userInfo.getStatus() == 0) {
                            ConstantUtil.USERNAME=userInfo.getResult().getUsername();
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(LoginActivity.this,301,e.getMessage(),false);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
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

    @Override
    public void onBackPressed() {
        if (ConstantUtil.TYPE) {
            JumpUtil.overlay(activity, MainActivity.class);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public class Data {
        String mnemonickeys;
        String password;
        String rePassword;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;

        String imei;
        String phone;
        String inIp;
        String isSimulator; //1是模拟器,0不是
    }

    public class Data1 {
        String privateKey;
        String password;
        String rePassword;

        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String imei;
        String phone;

        String inIp;
        String isSimulator; //1是模拟器,0不是
    }

    public class Data3 {
        String userId;
    }
}
