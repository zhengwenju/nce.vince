package com.bronet.blockchain.ui.historicalfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.GridLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MyTradeListAdapter;
import com.bronet.blockchain.adapter.MyTradeListSaleAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.MyTradeList;
import com.bronet.blockchain.data.MyTradeListSale;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.Const;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YearFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    int period=1;
    private int buysellrecord;
    private TransCoins.ResultBean bean;
    private TextView title;
    private MyTradeListSaleAdapter buyInRecordAdapter;
    private MyTradeListAdapter sellOutRecordAdapter;
    private List<MyTradeList.ResultBean.ItemsBean> sellOutResult;
    private  List<MyTradeListSale.ResultBean.ItemsBean> buyInResult;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        Bundle extras = getActivity().getIntent().getExtras();
        buysellrecord = extras.getInt(Const.BUYSELLRECORD);
        bean = (TransCoins.ResultBean)extras.getSerializable(Const.BUYSELLRECORDBEAN);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        if(buysellrecord ==0){//买入记录相关功能
            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
            buyInRecordAdapter = new MyTradeListSaleAdapter(R.layout.my_buyin_item, buyInResult,bean);
            recyclerView.setAdapter(buyInRecordAdapter);
            BuyRecrod();
        }else if(buysellrecord ==1){ //卖出记录相关功能
            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
            sellOutRecordAdapter = new MyTradeListAdapter(R.layout.my_sellout_item, sellOutResult,bean);
            recyclerView.setAdapter(sellOutRecordAdapter);

            SellOutRecord();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.year_list;
    }
    //卖出记录
    private void SellOutRecord() {
        ApiStore.createApi(ApiService.class)
                .MyTradeListBuy(bean.getInCoinType(),bean.getOutCoinType(),ConstantUtil.ID, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyTradeList>() {



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MyTradeList myTradeList) {

                        if (myTradeList.getStatus()==0){
                            sellOutResult = myTradeList.getResult().getItems();
                            sellOutRecordAdapter.setNewData(sellOutResult);

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
    //买入记录
    private void BuyRecrod() {
        ApiStore.createApi(ApiService.class)
                .MyTradeListSale(bean.getInCoinType(),bean.getOutCoinType(),ConstantUtil.ID, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyTradeListSale>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MyTradeListSale myTradeListSale) {
                        if (myTradeListSale.getStatus() == 0) {
                            buyInResult = myTradeListSale.getResult().getItems();
                            buyInRecordAdapter.setNewData(buyInResult);
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
}
