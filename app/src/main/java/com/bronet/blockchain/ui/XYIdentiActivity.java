package com.bronet.blockchain.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.IdeResult;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**协议
 * Created by 18514 on 2019/7/17.
 */

public class XYIdentiActivity extends BaseActivity {
    @BindView(R.id.authorize_return)
    ImageView authorize_return;
    @BindView(R.id.content)
    TextView content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xyident;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        RenChouAgreement();
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

    //认筹协议
    private void RenChouAgreement() {
        ApiStore.createApi(ApiService.class)
                .RenChouAgreement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IdeResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull IdeResult ideResult) {
                        if (ideResult.getStatus() == 0) {
                            String result =ideResult.getResult();
                            content.setText(result);
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
