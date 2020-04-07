package com.bronet.blockchain.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MyNodeListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.MyNodeList;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.JumpUtil;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//我的节点列表
public class MyNodeListActivity extends BaseActivity implements MyNodeListAdapter.OnItemClickListener {


    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MyNodeListAdapter myNodeListAdapter;
    private List<MyNodeList.ResultBean> result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_node;
    }

    @Override
    protected void initView() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv.setText(getString(R.string.node19));

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        myNodeListAdapter = new MyNodeListAdapter(R.layout.mynode_item, result);
        myNodeListAdapter.OnItemClickListener(this);
        mRecyclerView.setAdapter(myNodeListAdapter);

    }

    @Override
    protected void initData() {
        MyNodeList();
    }

    @Override
    protected void setEvent() {

    }
    //我的节点列表
    private void MyNodeList() {
        ApiStore.createApi(ApiService.class)
                .MyNodeList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyNodeList>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MyNodeList nodeInfo) {
                        result = nodeInfo.getResult();
                        try {
                            if (nodeInfo.getStatus() == 0) {
                                myNodeListAdapter.setNewData(result);
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
    public void onItemClick(int position) {
        /*if (ContinuousUtil.isDoubleClick()){
            return;
        }*/
            MyNodeList.ResultBean item = myNodeListAdapter.getItem(position);
            int nodeId = item.getId();
            Bundle bundle = new Bundle();
            bundle.putInt("nodeId", nodeId);
            JumpUtil.overlay(this, NodeDetailsActivity.class, bundle);




    }

}
