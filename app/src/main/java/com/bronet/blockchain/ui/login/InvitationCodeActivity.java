package com.bronet.blockchain.ui.login;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.DataBean;
import com.bronet.blockchain.data.Register;
import com.bronet.blockchain.data.RegisterS;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
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

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 获取去邀请码
 * Created by 18514 on 2019/6/26.
 */

public class InvitationCodeActivity extends BaseActivity {
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.et_auxiliaries)
    EditText code;
    @BindView(R.id.btn_picture)
    RelativeLayout btnPicture;
    @BindView(R.id.btn_photograph)
    RelativeLayout btnPhotograph;
    @BindView(R.id.btn_ok)
    Button btnOk;
    String userName, password, rePassword;
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.loding)
    ProgressBar loding;
    int resultCode=11;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_i_c;
    }
    private static final int REQUEST_PERMISSION = 0;
    @Override
    protected void initView() {
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
        userName = getIntent().getExtras().getString("userName");
        password = getIntent().getExtras().getString("password");
        rePassword = getIntent().getExtras().getString("rePassword");
    }

    @Override
    protected void setEvent() {
        data.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(data.getText());
                ToastUtil.show(activity,"已复制");
                return false;
            }
        });
    }

    @OnClick({R.id.btn_register, R.id.btn_picture, R.id.btn_photograph, R.id.btn_ok,R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                //返回登录页面
                setResult(11);
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
                //提交注册
                final String s = code.getText().toString();
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
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void register(String result, String codes) {
        final Data datas = new Data();


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


        datas.setUserName(userName);
//        data.setInviteCode("8k8r1u"); htyc63
        datas.setInviteCode(codes);
        datas.setParentId(result);
        datas.setPassword(AESUtil.encry(password));
        datas.setRePassword(AESUtil.encry(rePassword));
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
                                data.setText(registerSDataBean.getResult().getMnemonic());
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

    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    private void prasePhoto(final String path) {
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
            }catch (Exception ex){
                ex.printStackTrace();
            }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == 0) {
            Intent intent = new Intent(activity, CommonScanActivity.class);
            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        setResult(resultCode);
        super.onBackPressed();
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
