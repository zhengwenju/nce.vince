package com.bronet.blockchain.ui.help;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.HelpAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Type;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 帮助中心
 * Created by 18514 on 2019/7/25.
 */

public class HelpActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<Type.Data>list=new ArrayList<>();
    HelpAdapter helpAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        helpAdapter = new HelpAdapter(R.layout.item_help, list,activity);
        rv.setAdapter(helpAdapter);
    }

    @Override
    protected void initData() {
        ApiStore.createApi(ApiService.class)
                .Type(Const.VERSION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Type>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Type type) {
                        if (type.getStatus()==0){
                            ArrayList<Type.Data> result = type.getResult();
                            helpAdapter.setNewData(result);
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
        helpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int id = helpAdapter.getItem(i).getId();
                if (id==1){
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",helpAdapter.getItem(i).getId());
                    JumpUtil.overlay(activity,JournalismActivity.class,bundle);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",helpAdapter.getItem(i).getId());
                    JumpUtil.overlay(activity,HelpListActivity.class,bundle);
                }

            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
