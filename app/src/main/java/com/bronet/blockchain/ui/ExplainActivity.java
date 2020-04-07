package com.bronet.blockchain.ui;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.data.ContractRules;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.JumpUtil;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 提币限制说明
 * Created by 18514 on 2019/7/16.
 */

public class ExplainActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView content_tv;
    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @Override
    protected int getLayoutId() {
        return R.layout.explain;
    }

    @Override
    protected void initView() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        content_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        title_tv.setText(getString(R.string.dangr_info));
    }

    @Override
    protected void initData() {
        RiskWarning();
    }

    @Override
    protected void setEvent() {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    //风险
    private void RiskWarning() {
        ApiStore.createApi(ApiService.class)
                .RiskWarning()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractRules>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContractRules contractRules) {
                        if (contractRules.getStatus() == 0) {
                            String fenxian = contractRules.getResult();
                            content_tv.setText(fenxian);
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
