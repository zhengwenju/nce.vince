package com.bronet.blockchain.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.WMAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.WithdrawLog;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 18514 on 2019/7/16.
 */

public class WithdrawMoneyListActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private ArrayList<WithdrawLog.Result> result;
    ArrayList<WithdrawLog.Result> list = new ArrayList<>();
    WMAdapter wmAdapter;
    private int pageSize=10;
    private int pageNo=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wml;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        wmAdapter = new WMAdapter(R.layout.item_wm, list);
        rv.setAdapter(wmAdapter);

        smartRefreshLayout.setEnableRefresh(true);//启用刷新
        smartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
                pageNo=1;
                Withdraw(pageNo,pageSize);
                smartRefreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                pageNo++;
                Withdraw(pageNo,pageSize);
                smartRefreshLayout.finishLoadMore();

            }
        });
    }


    @Override
    protected void initData() {
        Withdraw(pageNo,pageSize);

    }

    private void Withdraw(int pageNo,int pageSize) {
        ApiStore.createApi(ApiService.class)
                .WithdrawLog(ConstantUtil.ID,pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WithdrawLog>() {



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WithdrawLog withdrawLog) {
                        if (withdrawLog.getStatus()==0){
                           list.addAll(withdrawLog.getResult());
                            List<WithdrawLog.Result> resultBeans = Util.ridRepeat3(list);
                            wmAdapter.setNewData(resultBeans);
                            /*list.addAll(myTradeListSale.getResult());
                            List<PrizeRecordModel.ResultBean> resultBeans = Util.ridRepeat2(list);
                            prizeRecordAdapter.setNewData(resultBeans);*/
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


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
