package com.bronet.blockchain.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.ERAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.NCEList;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.fix.ConstantUtil;
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
 * 提币记录
 * Created by 18514 on 2019/7/26.
 */

public class ExchangeRecordActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<NCEList.Result>list=new ArrayList<>();
    ERAdapter erAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_er;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        erAdapter = new ERAdapter(R.layout.item_er, list);
        rv.setAdapter(erAdapter);
    }

    @Override
    protected void initData() {
        ApiStore.createApi(ApiService.class)
                .NCEList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NCEList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NCEList nceList) {
                        if (nceList.getStatus() == 0) {
                            ArrayList<NCEList.Result> result = nceList.getResult();
                            erAdapter.setNewData(result);
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


    @OnClick({R.id.btn_back})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
