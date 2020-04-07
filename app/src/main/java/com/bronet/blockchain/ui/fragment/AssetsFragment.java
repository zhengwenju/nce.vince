package com.bronet.blockchain.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.AssetsAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Assets;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.CBActivity;
import com.bronet.blockchain.ui.WithdrawMoneyActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.view.MyRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 资产
 * Created by 18514 on 2019/6/27.
 */

public class AssetsFragment extends BaseFragment {
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.btn_recharge)
    TextView btnRecharge;
    @BindView(R.id.btn_extract)
    TextView btnExtract;
    /*@BindView(R.id.btn_bb)
    TextView btnBb;
    @BindView(R.id.btn_hy)
    TextView btnHy;*/
    @BindView(R.id.number2)
    TextView number2;
    @BindView(R.id.rv)
    MyRecyclerView rv;
    Unbinder unbinder;
    ArrayList<Assets.Items> list = new ArrayList();
    AssetsAdapter assetsAdapter;
    StringBuffer datas = new StringBuffer();
    @BindView(R.id.data)
    TextView data;
    Unbinder unbinder1;
    public static String TAG="AssetsFragment";
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        RechargeWarning();
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
    public void onResume() {
        super.onResume();
        getAssets();
    }

     ArrayList<Assets.Items> Assets = new ArrayList<>();

    private void getAssets() {
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
                                Assets.Result result = assets.getResult();
                                ArrayList<Assets.Items> items = result.getItems();
                                if(items!=null) {
                                    assetsAdapter.setNewData(items);
                                    Assets.clear();
                                    Assets.addAll(items);
                                }

                                number.setText(getResources().getString(R.string.assets1) + result.getNceToUsd() + getResources().getString(R.string.assets13));
                                number2.setText(getResources().getString(R.string.assets7) + result.getBtcToUsd() + getResources().getString(R.string.assets13));
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(),115,e.getMessage(),false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        assetsAdapter = new AssetsAdapter(R.layout.item_a, list);
        rv.setAdapter(assetsAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_assets;
    }

    public static Fragment getInstance() {
        return new AssetsFragment();
    }

    @OnClick({R.id.btn_recharge, R.id.btn_extract, /*R.id.btn_bb, R.id.btn_hy*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge:
                //充币
                if(!ClickUtils.isFastClick()){
                    return;
                }
                JumpUtil.overlay(getActivity(), CBActivity.class);
                break;
            case R.id.btn_extract:
                if(!ClickUtils.isFastClick()){
                    return;
                }
                //提币
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.ITEMS,Assets);
                bundle.putString(Const.FROMTAG,TAG);
                JumpUtil.overlay(getActivity(), WithdrawMoneyActivity.class,bundle);
                break;
           /* case R.id.btn_bb:
                break;
            case R.id.btn_hy:
                break;*/
        }
    }

    public void newList() {
        if (assetsAdapter != null) {
            getAssets();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
