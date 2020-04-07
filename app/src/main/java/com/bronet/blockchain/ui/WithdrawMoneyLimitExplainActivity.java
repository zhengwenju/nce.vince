package com.bronet.blockchain.ui;

import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/**
 * 提币限制说明
 * Created by 18514 on 2019/7/16.
 */

public class WithdrawMoneyLimitExplainActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView content_tv;
    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_withmoney_limitexplain;
    }

    @Override
    protected void initView() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_tv.setText(getString(R.string.withmoeng1));
    }

    @Override
    protected void initData() {
        Freeze();
    }

    @Override
    protected void setEvent() {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    //可用提币额度限制
    private void Freeze() {
        ApiStore.createApi(ApiService.class)
                .Freeze(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ContractNum model) {
                        try {
                            content_tv.setText(Html.fromHtml(model.getMsg()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
