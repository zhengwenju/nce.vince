package com.bronet.blockchain.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MyMinListAdapter;
import com.bronet.blockchain.adapter.MyNodeListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.MyMineList;
import com.bronet.blockchain.data.MyNodeList;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.JumpUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyMiningListActivity extends BaseActivity implements MyMinListAdapter.OnItemClickListener{

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MyMinListAdapter myMinListAdapter;
    private List<MyMineList.ResultBean> result;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_mining_list;
    }

    @Override
    protected void initView() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv.setText(getString(R.string.mine02));

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        myMinListAdapter = new MyMinListAdapter(R.layout.mymin_item, result);
        myMinListAdapter.OnItemClickListener(this);
        mRecyclerView.setAdapter(myMinListAdapter);

    }

    @Override
    protected void initData() {
        ApiStore.createApi(ApiService.class)
                .MyMineList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyMineList>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MyMineList myMineList) {
                        result = myMineList.getResult();
                        try {
                            if (myMineList.getStatus() == 0) {
                                myMinListAdapter.setNewData(MyMiningListActivity.this.result);
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
    protected void setEvent() {

    }
    @Override
    public void onItemClick(int position) {
        /*if (ContinuousUtil.isDoubleClick()){
            return;
        }*/
            MyMineList.ResultBean item = myMinListAdapter.getItem(position);
            int minId = item.getId();
            Bundle bundle = new Bundle();
            bundle.putInt("minId",minId);
            JumpUtil.overlay(this, MiningDetailsActivity.class,bundle);







    }

}
