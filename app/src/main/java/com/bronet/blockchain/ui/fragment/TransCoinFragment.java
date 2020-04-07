package com.bronet.blockchain.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.TransMenuAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yc on 2018/11/26.
 */

public class TransCoinFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    static NewTransactionFragment newTransactionFragment;
    @BindView(R.id.back_iv)
    ImageView backIv;

    private TransMenuAdapter adapter;
    ArrayList<TransCoins.ResultBean> list=new ArrayList<>();
    @Override
    protected void initEvent() {

    }
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_coin_menu;
    }
    @Override
    protected void initData() {

    }
    public static Fragment getInstance(NewTransactionFragment newTransactionFragment) {
        TransCoinFragment.newTransactionFragment = newTransactionFragment;
        return new HomeFragment();
    }
    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TransMenuAdapter(R.layout.item_tran_menu,list);
        recyclerView.setAdapter(adapter);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTransactionFragment.drawerLayout.closeDrawers();
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TransCoins.ResultBean bean = (TransCoins.ResultBean)adapter.getItem(position);
                newTransactionFragment.drawerLayout.closeDrawers();
                newTransactionFragment.checkCoin(bean);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        EntrustChange();

    }
    //委托货币兑换列表
    private void EntrustChange() {
        ApiStore.createApi(ApiService.class)
                .EntrustChange()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransCoins>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransCoins tradeNce) {
                        List<TransCoins.ResultBean> result = tradeNce.getResult();

//                        List<TransCoins.ResultBean> result = new ArrayList<>();
//                        TransCoins.ResultBean bean = new TransCoins.ResultBean();
//                        bean.setInCoinName("BTC");
//                        bean.setInCoinType(5);
//                        bean.setOutCoinName("USTC");
//                        bean.setOutCoinType(8);
//                        result.add(bean);
//
//                        TransCoins.ResultBean bean2 = new TransCoins.ResultBean();
//                        bean2.setInCoinName("AAA");
//                        bean2.setInCoinType(34);
//                        bean2.setOutCoinName("BBB");
//                        bean2.setOutCoinType(55);
//                        result.add(bean2);
//
//                        TransCoins.ResultBean bean3 = new TransCoins.ResultBean();
//                        bean3.setInCoinName("CCC");
//                        bean3.setInCoinType(12);
//                        bean3.setOutCoinName("DDD");
//                        bean3.setOutCoinType(122);
//                        result.add(bean3);



                        adapter.setNewData(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
