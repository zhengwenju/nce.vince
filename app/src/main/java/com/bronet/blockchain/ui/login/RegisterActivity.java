package com.bronet.blockchain.ui.login;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.DataBean;
import com.bronet.blockchain.data.Register;
import com.bronet.blockchain.data.RegisterS;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.XYActivity;
import com.bronet.blockchain.ui.scancode.CommonScanActivity;
import com.bronet.blockchain.ui.scancode.utils.Constant;
import com.bronet.blockchain.ui.scancode.zxing.utils.QrcodeManger;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.FileUtils;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 注册
 * Created by 18514 on 2019/6/26.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.rigster_tv2)
    TextView rigster_tv2;

    @BindView(R.id.et_auxiliaries)
    EditText etAuxiliaries;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm)
    EditText etConfirm;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.loding)
    ProgressBar loding;
    @BindView(R.id.hidden_pwd)
    ImageView mHiddenPwd;
    @BindView(R.id.display_pwd)
    ImageView mDisplayPwd;
    @BindView(R.id.hidden_pwd_img)
    ImageView mHiddenPwdImg;
    @BindView(R.id.display_pwd_img)
    ImageView mDisplayPwdImg;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.auxiliaries_et)
    EditText code;
    @BindView(R.id.btn_picture)
    ImageView btnPicture;
    @BindView(R.id.btn_photograph)
    ImageView btnPhotograph;
    int resultCode=11;
    private String name;
    private String pwd;
    private String rpwd;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_r;
    }
    private static final int REQUEST_PERMISSION = 0;
    @Override
    protected void initView() {
        rigster_tv2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        etAuxiliaries.addTextChangedListener(new TextChanged());
        etPassword.addTextChangedListener(new TextChanged());
        etConfirm.addTextChangedListener(new TextChanged());
        etAuxiliaries.setFilters(new InputFilter[]{Util.filterSpeChat, Util.inputFilter, new InputFilter.LengthFilter(16)});
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnOk.setSelected(TextUtils.isEmpty(code.getText().toString()) ? false : true);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (!permissions.isEmpty()) {
//              requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                        REQUEST_PERMISSION);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setEvent() {


    }



    @OnClick({R.id.btn_register, R.id.btn_ok, R.id.rigster_tv2, R.id.hidden_pwd, R.id.display_pwd, R.id.hidden_pwd_img, R.id.display_pwd_img,R.id.btn_picture,R.id.btn_photograph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                finish();
                break;
            case R.id.btn_picture:
                //获取图片二维码
//                start(1);
                showPictures(300);
                break;
            case R.id.btn_photograph:
                //拍照获取二维码
                start(2);
                break;
            case R.id.btn_ok:
                name = etAuxiliaries.getText().toString();
                pwd = etPassword.getText().toString();
                rpwd = etConfirm.getText().toString();
                String s = code.getText().toString();

                boolean letterDigit = Util.isLetterDigit(pwd);
                if (!letterDigit) {
                    DialogUtil.show(this, getString(R.string.login26));
                    return;
                }

                if (!pwd.equals(rpwd)) {
                    DialogUtil.show(this, getString(R.string.login27));
                    return;
                }
                if (s==null){
                    DialogUtil.show(this, getString(R.string.login17));
                    return;
                }
                if (!checkbox.isChecked()){

                    DialogUtil.show(this, getString(R.string.login35));
                    return;
                }


                if (s != null) {
                    loding.setVisibility(View.VISIBLE);
                    FileUtils.writeV10Mesage("提交注册请求码"+s);
                    ApiStore.createApi(ApiService.class)
                            .improveRepair(s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Register>() {
                                @Override
                                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@io.reactivex.annotations.NonNull Register register) {
//                                    FileUtils.writeV10Mesage("\"提交注册返回 getStatus：\"+"+register.getStatus());
//                                    FileUtils.writeV10Mesage("\"提交注册返回 getResult().getId：\"+"+register.getResult().getId());

                                    if (register.getStatus() == 0) {
                                        String result = register.getResult().getId();
                                        Log.d("111", "onNext: "+result);
                                        register(result, s);
                                    }else{
                                        DialogUtil.show(activity,register.getMsg());
                                    }
                                    loding.setVisibility(View.GONE);
                                }



                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                    FileUtils.writeV10Mesage("\"提交注册返回 Throwable e：\"+"+e.getMessage());
                                    loding.setVisibility(View.GONE);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }



                break;
            case R.id.rigster_tv2:
                JumpUtil.overlay(activity, XYActivity.class);
                break;
            case R.id.hidden_pwd:
                etConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mDisplayPwd.setVisibility(View.VISIBLE);
                mHiddenPwd.setVisibility(View.GONE);
                etConfirm.setSelection(etConfirm.getText().length());
                break;
            case R.id.display_pwd:
                etConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mDisplayPwd.setVisibility(View.GONE);
                mHiddenPwd.setVisibility(View.VISIBLE);
                etConfirm.setSelection(etConfirm.getText().length());
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

    public class TextChanged implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(etAuxiliaries.getText().toString()) &&
                    !TextUtils.isEmpty(etPassword.getText().toString()) &&
                    !TextUtils.isEmpty(etConfirm.getText().toString())) {
                btnOk.setSelected(true);
            } else {
                btnOk.setSelected(false);
            }
        }
    }

    private void register(String result, String codes) {
        Data datas = new Data();
        String inIp = Util.getIPAddress(this);
        String deviceid = Util.getAndroidId(this);
        String osModel = Util.getOsModel();
        String netType = Util.getNetType(this);

        datas.setOsModel(osModel);
        datas.setDeviceId(deviceid);
        datas.setInIp(inIp);
        datas.setAppVersion(Util.getAppVersion(this));

        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            datas.setIpAddress(Const.outIpAddressArray[1]);
        }else {
            datas.setIpAddress("");
        }
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            datas.setOutIp(Const.outIpAddressArray[0]);
        }else {
            datas.setOutIp("");
        }
        datas.setNetType(netType);


        datas.setUserName(name);
//        data.setInviteCode("8k8r1u"); htyc63
        datas.setInviteCode(codes);
        datas.setParentId(result);
        datas.setPassword(AESUtil.encry(pwd));
        datas.setRePassword(AESUtil.encry(rpwd));
        datas.isSimulator= Util.isMonitor(this);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(datas);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .register(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean<RegisterS>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull DataBean<RegisterS> registerSDataBean) {
                        if (registerSDataBean.getStatus() == 0) {
                            if (!registerSDataBean.getMsg().equals(null)) {
                                code.setLongClickable(false);
                                DialogUtil.show(activity, registerSDataBean.getMsg());
     //                           data.setText(registerSDataBean.getResult().getMnemonic());
                                resultCode=10;



                                Bundle bundle = new Bundle();
                                bundle.putString("data",registerSDataBean.getResult().getMnemonic());
                                JumpUtil.startForResult(activity,AuxiliariesActivity.class,12,bundle);
                            }
                        }else{
                            DialogUtil.show(activity,registerSDataBean.getMsg());
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

    private void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
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
    private String getRealFilePath(Context c, Uri uri) {
        String result;
        Cursor cursor = c.getContentResolver().query(uri,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = uri.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10) {
            String codes = data.getStringExtra("code");
            code.setText(codes);
        }
        if (resultCode == 11) {
            setResult(10);
            finish();
        }
        if (requestCode == 300) {
            try {
                String photoPath = getRealFilePath(this, data.getData());
                if (photoPath == null) {
//                LogUtil.fussenLog().d("路径获取失败");
                } else {
                    //解析图片
                    prasePhoto(photoPath);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void prasePhoto(String path) {
        AsyncTask myTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                // 解析二维码/条码
                return QrcodeManger.syncDecodeQRCode(path);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (null == s) {
                } else {
                    // 识别出图片二维码/条码，内容为s
                    code.setText(s);
                }
            }
        }.execute(path);
    }


    public class Data {
            String userName;
            String password;
            String rePassword;
            String parentId;

            String deviceId;
            String osModel;
            String outIp;
            String ipAddress;
            String netType;
            String appVersion;
            String inIp;
            String isSimulator;


            public String getInIp() {
                return inIp;
            }

            public void setInIp(String inIp) {
                this.inIp = inIp;
            }

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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getRePassword() {
                return rePassword;
            }

            public void setRePassword(String rePassword) {
                this.rePassword = rePassword;
            }

            String inviteCode;

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }
        }

}
