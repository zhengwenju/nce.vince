package com.bronet.blockchain.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
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
 *修改密码
 * Created by 18514 on 2019/6/25.
 */

public class ModifyPwdActivity extends BaseActivity {

    @BindView(R.id.old_password_ev)
    EditText oldPasswordEv;

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm)
    EditText etConfirm;
    @BindView(R.id.btn_ok)
    Button btnOk;
    int type = 1;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.hidden_pwd)
    ImageView mHiddenPwd;
    @BindView(R.id.display_pwd)
    ImageView mDisplayPwd;
    @BindView(R.id.hidden_pwd_img)
    ImageView mHiddenPwdImg;
    @BindView(R.id.display_pwd_img)
    ImageView mDisplayPwdImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initView() {
    }
    private String[] outIpAddressArray=null;
    @Override
    protected void initData() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }

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

    @OnClick({R.id.btn_ok, R.id.btn_back, R.id.hidden_pwd, R.id.display_pwd, R.id.hidden_pwd_img, R.id.display_pwd_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String oldpwd =oldPasswordEv.getText().toString();
                String pwd =etPassword.getText().toString();
                String rpwd = etConfirm.getText().toString();

                if(TextUtils.isEmpty(oldpwd)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(rpwd)){
                    ToastUtil.show(this,getString(R.string.login26));
                    return;
                }

                boolean letterDigit = Util.isLetterDigit(pwd);
                if(!letterDigit){
                    ToastUtil.show(this,getString(R.string.login26));
                    return;
                }
                if(!pwd.equals(rpwd)){
                    ToastUtil.show(this,getString(R.string.login27));
                    return;
                }
                modifyPwd(oldpwd,pwd,rpwd);
                break;
            case R.id.btn_back:
                finish();
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

    //修改密码
    private void modifyPwd(String oldPwd,String newPwd,String rePwd) {
        Data data = new Data();
        data.userid=ConstantUtil.ID;
        data.oldPwd= AESUtil.encry(oldPwd);
        data.newPwd=AESUtil.encry(newPwd);
        data.rePwd=AESUtil.encry(rePwd);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .EditPassword(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPwdActivity.this, R.style.FullScreenDialog);
                        final AlertDialog dialog = builder.create();
                        dialog.setCancelable(false);
                        dialog.show();
                        dialog.getWindow().clearFlags(
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                        dialog.getWindow().setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                        Window window = dialog.getWindow();
                        window.setContentView(R.layout.alter);
                        View btnYes = window.findViewById(R.id.btn_yes);
                        TextView content = (TextView)window.findViewById(R.id.content);
                        content.setText(tradeNce.getMsg());
                        // 自定义图片的资源
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class Data {
        String userid;
        String oldPwd;
        String newPwd;
        String rePwd;

    }
}
