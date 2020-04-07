package com.bronet.blockchain.ui.my;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.ResAssure;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

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
 * 担保人扫码后是否同意界面
 * Created by vince on 2019/7/31.
 */

public class ConfirmAssureActivity extends BaseActivity {

    private static String TAG="ConfirmAssureActivity";
    private double usdt,nce;
    private int pid;
    private String userid;
    private String username;
    @BindView(R.id.db_quote_tv)
    TextView dbQuoteTv;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    @BindView(R.id.btn_accept)
    TextView btnAcceptImage;
    @BindView(R.id.btn_reject)
    TextView btnRejectImage;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirmassure;
    }
    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
        Bundle bundle =getIntent().getExtras();
        usdt = bundle.getDouble(Const.USDT);
        nce = bundle.getDouble(Const.NCE);
        username= bundle.getString(Const.USERNAME);
        pid = bundle.getInt(Const.PID);
        userid = bundle.getString(Const.UID);
        if(!TextUtils.isEmpty(username)) {
            tipTv.setText("是否愿意为您朋友(" + username + ")担保？");
        }else {
            tipTv.setText("是否愿意为您朋友担保？");
        }
        dbQuoteTv.setText("额度："+String.valueOf(usdt)+"USDT≈"+nce+"NCE");
    }
    @Override
    protected void setEvent() {

    }
    @OnClick({R.id.btn_accept, R.id.btn_reject,R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                showPop();
                break;
            case R.id.btn_reject:
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    PopupWindow popWindow;
    @SuppressLint("WrongConstant")
    private void showPop() {
        if(Const.IS_FROZEN){
            DialogUtil.showFrozen(this,Const.FROZEN_CONTENT);
            return;
        }
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
        initB(inflate);
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
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }
    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }
    private void initB (View inflate) {
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
                InputMethodManager imm = (InputMethodManager) ConfirmAssureActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)ConfirmAssureActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
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
                    ToastUtil.show(ConfirmAssureActivity.this,ConfirmAssureActivity.this.getString(R.string.d20));
                }else{
                    //支付密码
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    AssureAccept(name.getText().toString());
                }
            }
        });
    }
    public class Data{
        int pid;
        String userid;
        double bzMoney;
        double bzUsdMoney;
        String password;
    }
    private void AssureAccept(String pwd){
        Data data = new Data();
        data.bzMoney=nce;
        data.pid=pid;
        data.userid=ConstantUtil.ID;
        data.password = AESUtil.encry(pwd);
        data.bzUsdMoney=usdt;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .AssureAccept(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResAssure>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull ResAssure assure) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmAssureActivity.this, R.style.FullScreenDialog);
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
                        TextView content = (TextView)window.findViewById(R.id.content);
                        content.setText(assure.getMsg());
                        // 自定义图片的资源
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                finish();
                                dialog.dismiss();
                            }
                        });

                        if(popWindow!=null){
                            popWindow.dismiss();
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e===>"+e.getMessage());
                        if(popWindow!=null){
                            popWindow.dismiss();
                        }

                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}
