package com.bronet.blockchain.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.MainActivity;
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
 *重置密码(添加申请)
 * Created by 18514 on 2019/6/25.
 */

public class ResetPwdActivity extends BaseActivity {

    @BindView(R.id.parent_id_ev)
    EditText etParentId;

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm)
    EditText etConfirm;
    @BindView(R.id.btn_ok)
    Button btnOk;
    int type = 1;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;

    @BindView(R.id.time_tv2)
    TextView timeTv;
    @BindView(R.id.content_tv2)
    TextView content_tv2;

    @BindView(R.id.input_content_rl2)
    LinearLayout input_content_rl2;

    @BindView(R.id.input_content_rl)
    LinearLayout input_content_rl;
    @BindView(R.id.hidden_pwd)
    ImageView mHiddenPwd;
    @BindView(R.id.display_pwd)
    ImageView mDisplayPwd;
    @BindView(R.id.hidden_pwd_img)
    ImageView mHiddenPwdImg;
    @BindView(R.id.display_pwd_img)
    ImageView mDisplayPwdImg;



    private boolean isSubmit=true;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pwd;
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
        WaitAudit();
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
    //是否存在互助申请
    private void WaitAudit() {
        ApiStore.createApi(ApiService.class)
                .WaitAudit(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Model10 model) {
                        try {
                            if(model.getStatus()==0){
                                //已提交过的
                                btnOk.setText(getString(R.string.mutual55));
                                timeTv.setText(model.getResult());
                                content_tv2.setText(model.getMsg());
                                input_content_rl.setVisibility(View.GONE);
                                input_content_rl2.setVisibility(View.VISIBLE);
                                isSubmit=false;
                            }else {
                                //未提交过
                                input_content_rl.setVisibility(View.VISIBLE);
                                input_content_rl2.setVisibility(View.GONE);
                                btnOk.setText(getString(R.string.aa9));
                                etParentId.setEnabled(true);
                                etPassword.setEnabled(true);
                                etConfirm.setEnabled(true);
                                timeTv.setVisibility(View.GONE);
                                isSubmit=true;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        JumpUtil.errorHandler(ResetPwdActivity.this,406,e.getMessage(),true);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //撤消提交
    private void ReCall() {
        ApiStore.createApi(ApiService.class)
                .ReCall(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Model10 model) {
                        try {
                            if(model.getStatus()==0){ //撤消成功
                                WaitAudit();
                            }else {
                                ToastUtil.show(ResetPwdActivity.this, model.getMsg());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        JumpUtil.errorHandler(ResetPwdActivity.this,406,e.getMessage(),true);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
    @OnClick({R.id.btn_ok, R.id.btn_back, R.id.hidden_pwd, R.id.display_pwd, R.id.hidden_pwd_img, R.id.display_pwd_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:

                if(isSubmit) {
                    String parentId = etParentId.getText().toString();
                    String pwd = etPassword.getText().toString();
                    String rpwd = etConfirm.getText().toString();

                    if (TextUtils.isEmpty(parentId)) {
                        ToastUtil.show(this, getString(R.string.basic_data24));
                        return;
                    }

                    if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(rpwd)) {
                        ToastUtil.show(this, getString(R.string.login26));
                        return;
                    }

                    boolean letterDigit = Util.isLetterDigit(pwd);
                    if (!letterDigit) {
                        ToastUtil.show(this, getString(R.string.login26));
                        return;
                    }
                    if (!pwd.equals(rpwd)) {
                        ToastUtil.show(this, getString(R.string.login27));
                        return;
                    }
                    MutualAdd(parentId, pwd, rpwd);
                }else {
                    etParentId.setEnabled(false);
                    etPassword.setEnabled(false);
                    etConfirm.setEnabled(false);
                    showPopConfirm();
                }
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
    private PopupWindow popWindow;
    @SuppressLint("WrongConstant")
    private void showPopConfirm() {
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop16, null);
        TextView textView =inflate.findViewById(R.id.pop_quote_tv);
        TextView btn_yes =inflate.findViewById(R.id.btn_yes);
        TextView btn_no =inflate.findViewById(R.id.btn_no);
        textView.setText("您确定撤回提交信息?");
        btn_yes.setText("确定");
        btn_no.setText("取消");
        initBConfirm(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);

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
    private void initBConfirm(View inflate) {
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow!=null) {
                    popWindow.dismiss();
                }
                ReCall();
            }
        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
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

    //重置密码
    private void MutualAdd(String parentId,String newPwd,String rePwd) {
        Data data = new Data();
        data.userId=ConstantUtil.ID;
        data.parentNid= AESUtil.encry(parentId);
        data.newPwd=AESUtil.encry(newPwd);
        data.rePwd=AESUtil.encry(rePwd);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .MutualAdd(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {

                        try {
                            if (tradeNce.getStatus() == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPwdActivity.this, R.style.FullScreenDialog);
                                final AlertDialog dialog = builder.create();
                                dialog.show();
                                dialog.getWindow().clearFlags(
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                                dialog.getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                                Window window = dialog.getWindow();
                                window.setContentView(R.layout.alter);
                                View btnYes = window.findViewById(R.id.btn_yes);
                                TextView content = (TextView) window.findViewById(R.id.content);
                                content.setText(tradeNce.getMsg());
                                // 自定义图片的资源
                                btnYes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                ToastUtil.show(ResetPwdActivity.this, tradeNce.getMsg());
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

//
//                        ToastUtil.show(ResetPwdActivity.this, tradeNce.getMsg());
//                        if(tradeNce.getStatus()==0) {
////                            finish();
//                            etParentId.setText("");
//                            etPassword.setText("");
//                            etConfirm.setText("");
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

    public class Data {
        String userId;
        String rePwd;
        String newPwd;
        String parentNid;
    }

}
