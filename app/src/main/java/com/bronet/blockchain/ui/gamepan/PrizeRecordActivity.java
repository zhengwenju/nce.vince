package com.bronet.blockchain.ui.gamepan;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.PrizeRecordModel;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.gamepan.Adapter.PrizeRecordAdapter;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PrizeRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private PrizeRecordAdapter prizeRecordAdapter;
    private int pageSize=20;
    private int pageNo=1;
    @Override
    protected void initData() {
        PrizeRecrod(pageNo,pageSize);
    }

    @Override
    protected void setEvent() {

    }
    private  List<PrizeRecordModel.ResultBean> list=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.prize_records;
    }

    @Override
    protected void initView() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_tv.setText(getString(R.string.prize_record));
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        prizeRecordAdapter = new PrizeRecordAdapter(R.layout.prize_record_item, list);
        recyclerView.setAdapter(prizeRecordAdapter);

        smartRefreshLayout.setEnableRefresh(true);//启用刷新
        smartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
                pageNo=1;
                PrizeRecrod(pageNo,pageSize);
                smartRefreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                pageNo++;
                PrizeRecrod(pageNo,pageSize);
                smartRefreshLayout.finishLoadMore();

            }
        });

    }
    //中奖记录
    private void PrizeRecrod(int pageNo,int pageSize) {
        ApiStore.createApi(ApiService.class)
                .LotteryList(ConstantUtil.ID, pageNo,pageSize,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrizeRecordModel>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull PrizeRecordModel myTradeListSale) {
                        if (myTradeListSale.getStatus() == 0) {
                            list.addAll(myTradeListSale.getResult());
                            List<PrizeRecordModel.ResultBean> resultBeans = Util.ridRepeat2(list);
                            prizeRecordAdapter.setNewData(resultBeans);
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
