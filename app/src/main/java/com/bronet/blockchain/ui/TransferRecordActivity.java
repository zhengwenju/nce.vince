package com.bronet.blockchain.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.NodeLogListAdapter;
import com.bronet.blockchain.adapter.TransferRecordAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.NodeLog;
import com.bronet.blockchain.data.TransferNodeLog;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransferRecordActivity extends BaseActivity {


    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private int pageNo = 1;
    private int pageSize = 10;
    int nodeId;
    private List<TransferNodeLog.ResultBean> list = new ArrayList<>();
    private TransferRecordAdapter transferRecordAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer_record;
    }

    @Override
    protected void initView() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv.setText("转账记录");

        nodeId = getIntent().getExtras().getInt("nodeId");


        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        transferRecordAdapter = new TransferRecordAdapter(R.layout.transfer_record_item, list);
        mRecyclerView.setAdapter(transferRecordAdapter);


        mSmartRefreshLayout.setEnableRefresh(true);//启用刷新
        mSmartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
                pageNo = 1;
                TransferRecord(nodeId,pageNo, pageSize);
                mSmartRefreshLayout.finishRefresh();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                TransferRecord(nodeId,pageNo, pageSize);
                mSmartRefreshLayout.finishLoadMore();

            }
        });

    }

    @Override
    protected void initData() {
        TransferRecord(nodeId,pageNo, pageSize);
    }


    @Override
    protected void setEvent() {

    }

    private void TransferRecord(int nodeId,int pageNo, int pageSize) {
        ApiStore.createApi(ApiService.class)
                .TransferNodeLog(ConstantUtil.ID,nodeId, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransferNodeLog>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull TransferNodeLog transferNodeLog) {
                        Log.d("-------", "onNext: " + transferNodeLog.getMsg());
                        if (transferNodeLog.getStatus() == 0) {
                            list.addAll(transferNodeLog.getResult());
                            List<TransferNodeLog.ResultBean> resultBeans = Util.TransferNodeLog(list);
                            transferRecordAdapter.setNewData(resultBeans);
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
