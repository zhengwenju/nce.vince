package com.bronet.blockchain.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.ZXingUtils;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentRelativeLayout;

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
 * 充币
 * Created by 18514 on 2019/7/26.
 */

public class CBActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.btn_jl)
    TextView btnJl;
    @BindView(R.id.input)
    TextView input;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tip_tv)
    TextView tip_tv;

    @BindView(R.id.coin_type_tv)
    TextView coin_type_tv;

    @BindView(R.id.link_name_linearlayout)
    LinearLayout link_name_linearlayout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_cb;
    }

    @Override
    protected void initView() {

//        if(Const.IS_FROZEN){
//            iv.setVisibility(View.GONE);
//            btnOk.setVisibility(View.GONE);
//            input.setVisibility(View.GONE);
//        }
        tip_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    private void RechargeWarning(String cointype) {
        int coinId=1;
        if(cointype.equals("ETH")){
            coinId=1;
            coin_type_tv.setText("ETH");
            link_name_linearlayout.setVisibility(View.GONE);
        }else if(cointype.equals("USDT")){
            coinId=4;
            coin_type_tv.setText("USDT");
            link_name_linearlayout.setVisibility(View.VISIBLE);
        }

        ApiStore.createApi(ApiService.class)
                .RechargeWarning(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyWarning>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TradeBuyWarning tradeBuyWarning) {
                        if (tradeBuyWarning.getStatus() == 0) {
                            tip_tv.setText(tradeBuyWarning.getResult());
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
    private String cointype="";
    @Override
    protected void initData() {
        cointype =getIntent().getStringExtra("cointype");

        RechargeWarning(cointype);

        Data data = new Data();
        data.userId = ConstantUtil.ID;
        data.coinId = "1";

        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .recharge(body)
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
                                input.setText(tradeBuyWarning.getResult());
                                Bitmap qrImage = ZXingUtils.createQRImage(tradeBuyWarning.getResult(), 800, 800);
                                iv.setImageBitmap(qrImage);
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
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

    }


    @OnClick({R.id.btn_back, R.id.btn_jl, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_jl:
                JumpUtil.overlay(activity, ExchangeRecordListActivity.class);
                break;
            case R.id.btn_ok:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(input.getText());
                ToastUtil.show(this,getString(R.string.assets24));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public class Data {
        String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }

        String coinId;
    }
}
