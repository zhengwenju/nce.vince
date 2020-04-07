package com.bronet.blockchain.ui.my;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.CreditAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Credits;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.view.CircleProgress;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 信用评级
 * Created by 18514 on 2019/7/3.
 */

public class CreditActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<Credits.Items> list = new ArrayList<>();
    CreditAdapter creditAdapter;
    @BindView(R.id.circle_progress)
    TextView circleProgress;

    @BindView(R.id.status)
    TextView status;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit;
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        creditAdapter = new CreditAdapter(R.layout.item_credit, list);
        rv.setAdapter(creditAdapter);
        creditAdapter.setPreLoadNumber(10);
    }

    @Override
    protected void initData() {
        ApiStore.createApi(ApiService.class)
                .Credits(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Credits>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Credits credits) {
                        if (credits.getStatus() == 0) {
                            ArrayList<Credits.Items> items = credits.getResult().getItems();
                            creditAdapter.setNewData(items);
                            circleProgress.setText( String.valueOf(credits.getResult().getTotalScore()));
                            status.setText(credits.getResult().getStatus());

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
