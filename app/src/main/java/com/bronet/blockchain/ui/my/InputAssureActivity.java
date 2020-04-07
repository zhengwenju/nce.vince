package com.bronet.blockchain.ui.my;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.AssureAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Assure;
import com.bronet.blockchain.data.Model;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.FileUtils;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 预约金额数据填充
 * Created by vince on 2019/7/31.
 */

public class InputAssureActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.db_edit)
    EditText dbEdit;
    @BindView(R.id.db_edit2)
    EditText dbEdit2;
    @BindView(R.id.btn_accept)
    TextView btnAcceptImage;
    @BindView(R.id.btn_reject)
    TextView btnRejectImage;
    AssureAdapter assureAdapter;
    ArrayList<Assure.Result> results;
    public int pid=0;
    public int temp=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_inputassure;
    }
    @Override
    protected void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        assureAdapter = new AssureAdapter(R.layout.item_assure, results,this);
        recyclerView.setAdapter(assureAdapter);
    }
    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        pid =bundle.getInt("pid");
        FileUtils.writeV10Mesage("担保额度请求pid:"+pid);
        ApiStore.createApi(ApiService.class)
                .GetAssureQuota()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Assure>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull Assure assure) {
                        FileUtils.writeV10Mesage("担保额度响应getStatus:"+assure.getStatus());
                        if (assure.getStatus() == 0) {
                            ArrayList<Assure.Result> result = assure.getResult();
                            assureAdapter.setNewData(result);
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        FileUtils.writeV10Mesage("担保额度响应Throwable:"+e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
    @Override
    protected void setEvent() {
        assureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                assureAdapter.setThisPosition(i);
                assureAdapter.notifyDataSetChanged();
                Assure.Result item = assureAdapter.getItem(i);
                dbEdit.setText(String.valueOf(item.getUsdt()));
            }
        });
        dbEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                temp=1;
                Usdt2Nce(dbEdit.getText().toString());

            }
        });
    }
    private void Usdt2Nce(String usdt){
        ApiStore.createApi(ApiService.class)
                .Usdt2Nce(usdt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull Model assure) {
                        if (assure.getStatus() == 0) {
                            double result = assure.getResult();
                            dbEdit2.setText(String.valueOf(result));
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
    @OnClick({R.id.btn_accept, R.id.btn_reject,R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                //生成二维码：
                String usdtCount =dbEdit.getText().toString();
                String nceCount =dbEdit2.getText().toString();
                if(TextUtils.isEmpty(usdtCount)&&temp==1){
                    ToastUtil.show(InputAssureActivity.this,getString(R.string.assure10));
                    return;
                }
                if(TextUtils.isEmpty(usdtCount)){
                    ToastUtil.show(InputAssureActivity.this,getString(R.string.assure10));
                    return;
                }
                Bundle bundle = new Bundle();
                double nceCountD = Double.valueOf(nceCount);
                bundle.putDouble("nceData",nceCountD);
                double usdtCountD = Double.valueOf(usdtCount);
                bundle.putDouble("usdtData",usdtCountD);
                bundle.putInt("pid",pid);

                JumpUtil.overlay(InputAssureActivity.this, AppointContractCodeActivity.class,bundle);
                break;
            case R.id.btn_reject:
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
