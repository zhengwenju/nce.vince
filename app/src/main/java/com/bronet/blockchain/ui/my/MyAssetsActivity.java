package com.bronet.blockchain.ui.my;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.AssetsAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Assets;
import com.bronet.blockchain.data.AssetsFromCoin;
import com.bronet.blockchain.data.GetCoinDetail1;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.data.VerifyPwd;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.ApiStore2;
import com.bronet.blockchain.ui.CBActivity;
import com.bronet.blockchain.ui.WithdrawMoneyActivity;
import com.bronet.blockchain.ui.help.HelpDataActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.bronet.blockchain.view.MyRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyAssetsActivity extends BaseActivity {
    @BindView(R.id.btn_recharge)
    LinearLayout btnRecharge;
    @BindView(R.id.btn_extract)
    LinearLayout btnExtract;
    /*@BindView(R.id.btn_bb)
    TextView btnBb;
    @BindView(R.id.btn_hy)
    TextView btnHy;*/
    @BindView(R.id.rv)
    MyRecyclerView rv;
    @BindView(R.id.totalAssets)
    TextView totalAssets;
    @BindView(R.id.assets)
    TextView assets1;
    @BindView(R.id.name)
    TextView name1;
    @BindView(R.id.hidden_pwd)
    ImageView mHiddenPwd;
    @BindView(R.id.display_pwd)
    ImageView mDisplayPwd;
    Assets.Result result;
    Unbinder unbinder;
    AssetsAdapter assetsAdapter;
    StringBuffer datas = new StringBuffer();
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    private ArrayList<com.bronet.blockchain.data.Assets.Items> items;


    Unbinder unbinder1;
    public static String TAG = "AssetsFragment";
    @BindView(R.id.rise)
    ImageView mRise;
    @BindView(R.id.fall)
    ImageView mFall;
    @BindView(R.id.image1)
    ImageView mImage1;
    @BindView(R.id.image2)
    ImageView mImage2;
    @BindView(R.id.Percentage)
    TextView Percentage;
    @BindView(R.id.usd)
    TextView usd;
    @BindView(R.id.totalQuantity)
    TextView totalQuantity;
    @BindView(R.id.availableQuantity)
    TextView availableQuantity;
    @BindView(R.id.FrozenQuantity)
    TextView FrozenQuantity;
    private String totalAssets1;
    private PopupWindow popWindow;
    private String name;
    private String banlance;
    private String usd1;
    int coinId=3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_assets;
    }


    private String cointype="";
    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(MyAssetsActivity.this, LoginActivity.class);
            return;
        }
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MyAssetsActivity.this);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(gridLayoutManager);
        assetsAdapter = new AssetsAdapter(R.layout.item_a, items);
        rv.setAdapter(assetsAdapter);


        assetsAdapter.OnItemAssetsClickListener(new AssetsAdapter.OnItemAssetsClickListener() {



            @Override
            public void onItemAssetsClick(int position) {

                assetsAdapter.setThisPosition(position);
                assetsAdapter.notifyDataSetChanged();

                banlance = result.getItems().get(position).getBanlance();
                name = result.getItems().get(position).getCoinType();
                usd1 = result.getItems().get(position).getUsd();
                if (position == 0) {
                    assets1.setText(banlance);
                    name1.setText(name);
                    CoinDetail(name);
                    usd.setText("≈ "+"$"+usd1);
                    usd.setVisibility(View.VISIBLE);
                    cointype="ETH";
                } else if (position == 1) {
                    assets1.setText(MyAssetsActivity.this.banlance);
                    name1.setText(name);
                    usd.setVisibility(View.VISIBLE);
                    usd.setText("≈ "+"$"+usd1);
                    mRise.setVisibility(View.GONE);
                    mFall.setVisibility(View.GONE);
                    mImage1.setVisibility(View.GONE);
                    mImage2.setVisibility(View.GONE);
                    Percentage.setVisibility(View.GONE);
                    cointype="NCE";
                }else if (position==2){
                    assets1.setText(banlance);
                    name1.setText(name);
                    usd.setVisibility(View.GONE);
                    mRise.setVisibility(View.GONE);
                    mFall.setVisibility(View.GONE);
                    mImage1.setVisibility(View.GONE);
                    mImage2.setVisibility(View.GONE);
                    Percentage.setVisibility(View.GONE);
                    cointype="USDT";
                }
            }


        });


        totalAssets.setText("*****");
        mHiddenPwd.setBackgroundResource(R.drawable.eye_close_btn);
        mHiddenPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(2);
            }
        });


    }

    private void showPop(int type) {
        View inflate = null;
        switch (type) {

            case 2:
                inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
                initB(inflate);
                break;
        }
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
                    ToastUtil.show(MyAssetsActivity.this, MyAssetsActivity.this.getString(R.string.d20));
                } else {
                    if (!ClickUtils.isFastClick()) {
                        return;
                    }
                    VerifyPwd(name.getText().toString());
                }
            }


        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }

    private void VerifyPwd(String name) {
        Data data = new Data();
        data.userid = ConstantUtil.ID;
        data.password = AESUtil.encry(name);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .VerifyPwd(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VerifyPwd>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull VerifyPwd verifyPwd) {
                        if (verifyPwd.getStatus() == 0) {
                            if (!Const.isShowNum) {
                                totalAssets.setText("≈ "+"$"+totalAssets1);
                                mHiddenPwd.setBackgroundResource(R.drawable.eye_open_btn);
                                popWindow.dismiss();
                            }
                        } else {
                            ToastUtil.show(MyAssetsActivity.this, verifyPwd.getMsg());
                            totalAssets.setText("*****");
                            mHiddenPwd.setBackgroundResource(R.drawable.eye_close_btn);
                            popWindow.dismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取币种详情
    private void CoinDetail(String coinName) {
        ApiStore2.createApi(ApiService.class)
                .CoinDetail1(coinName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetCoinDetail1>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GetCoinDetail1 getCoinDetail1) {
                        GetCoinDetail1.DataBean data = getCoinDetail1.getData();
                        String changePercentage = data.getChangePercentage();
                        Percentage.setText(changePercentage);
                        if (data.getChangePercentage().contains("+")){
                            mImage1.setVisibility(View.VISIBLE);
                            mImage2.setVisibility(View.GONE);
                            mRise.setVisibility(View.VISIBLE);
                            mFall.setVisibility(View.GONE);
                            Percentage.setVisibility(View.VISIBLE);
                            Percentage.setTextColor(Color.GREEN);
                        }else {
                            mImage1.setVisibility(View.GONE);
                            mImage2.setVisibility(View.VISIBLE);
                            mRise.setVisibility(View.GONE);
                            mFall.setVisibility(View.VISIBLE);
                            Percentage.setVisibility(View.VISIBLE);
                            Percentage.setTextColor(Color.RED);
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
    protected void initData() {

		getAssets1();
        RechargeWarning();

        Znumber();


    }




    @Override
    protected void setEvent() {

    }

    @OnClick({R.id.btn_recharge, R.id.btn_extract, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge:
                //充币
                if (!ClickUtils.isFastClick()) {
                    return;
                }

                if(cointype.equals("NCE")){
                    ToastUtil.show(MyAssetsActivity.this, "请选择ETH或者USDT币种进行充币");
                    return;
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("cointype",cointype);
                JumpUtil.overlay(MyAssetsActivity.this, CBActivity.class,bundle1);
                break;
            case R.id.btn_extract:
                if (!ClickUtils.isFastClick()) {
                    return;
                }
                //提币
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.ITEMS, Assets);
                bundle.putString(Const.FROMTAG, TAG);
                JumpUtil.overlay(MyAssetsActivity.this, WithdrawMoneyActivity.class, bundle);
                break;
            case R.id.btn_back:
                finish();
                break;
            /*case R.id.btn_hy:
                break;*/
        }
    }

    private void Znumber() {
        ApiStore.createApi(ApiService.class)
                .AssetsFromCoin(ConstantUtil.ID,coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AssetsFromCoin>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AssetsFromCoin assetsFromCoin) {
                        if (assetsFromCoin.getStatus() == 0) {
                            String banlance = assetsFromCoin.getResult().getBanlance();
                            String freeze = assetsFromCoin.getResult().getFreeze();
                            String totalAssets = assetsFromCoin.getResult().getTotalAssets();
                             totalQuantity.setText(totalAssets);
                             availableQuantity.setText(banlance);
                             FrozenQuantity.setText(freeze);
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


    private void RechargeWarning() {
        ApiStore.createApi(ApiService.class)
                .RechargeWarning()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeBuyWarning>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TradeBuyWarning tradeBuyWarning) {
                        if (tradeBuyWarning.getStatus() == 0) {
                            datas.append(tradeBuyWarning.getResult());
                            WithdrawWarning();
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

    private void WithdrawWarning() {
        ApiStore.createApi(ApiService.class)
                .WithdrawWarning(1)
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
                                datas.append("\n\n").append(tradeBuyWarning.getResult());
                                if (data != null) {
                                    data.setText(datas.toString());
                                }
                            }
                        } catch (Exception ex) {
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
    public void onResume() {
        super.onResume();

    }

    ArrayList<Assets.Items> Assets = new ArrayList<>();

    public void getAssets1() {
        ApiStore.createApi(ApiService.class)
                .Assets(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Assets>() {




                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Assets assets) {
                        if (assets.getStatus() == 0) {
                            try {
                                result = assets.getResult();
                                totalAssets1 = result.getTotalAssets();

                                items = result.getItems();
                                String banlance1 = items.get(0).getBanlance();
                                String coinType1 = items.get(0).getCoinType();
                                String usd1 = items.get(0).getUsd();
                                assets1.setText(banlance1);
                                name1.setText(coinType1);
                                usd.setText("≈ "+"$"+usd1);
                                CoinDetail(coinType1);
                                if (items != null) {
                                    assetsAdapter.setNewData(items);
                                    Assets.clear();
                                    Assets.addAll(items);
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(MyAssetsActivity.this, 115, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void newList() {
        if (assetsAdapter != null) {
            getAssets();
        }
    }

    public class Data {
        String userid;
        String password;
    }
}
