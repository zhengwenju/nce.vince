package com.bronet.blockchain.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.IdeResult;
import com.bronet.blockchain.data.TransferAgreement;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ZRIdentifyActivity extends BaseActivity {

    @BindView(R.id.authorize_return)
    ImageView authorize_return;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.common_title_TV_center)
    TextView common_title_TV_center;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_xyident;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {
        common_title_TV_center.setText(getString(R.string.invest28));
        TransferAgreement();
    }

    @Override
    protected void setEvent() {

    }
    @OnClick({R.id.authorize_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.authorize_return:
                finish();
                break;
        }
    }

    //转让协议
    private void TransferAgreement() {
        ApiStore.createApi(ApiService.class)
                .TransferAgreement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransferAgreement>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransferAgreement transferAgreement) {
                        if (transferAgreement.getStatus() == 0) {
                            content.setText(transferAgreement.getResult());
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
