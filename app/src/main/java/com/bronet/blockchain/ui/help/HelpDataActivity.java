package com.bronet.blockchain.ui.help;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.BaseResp;
import com.bronet.blockchain.data.Info;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bumptech.glide.Glide;
import com.zhy.android.percent.support.PercentRelativeLayout;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 帮助详情
 * Created by 18514 on 2019/7/26.
 */

public class HelpDataActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.datas)
    TextView datas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_d;
    }

    @Override
    protected void initView() {
        RichText.initCacheDir(activity);
        RichText.debugMode = true;
    }

    @Override
    protected void initData() {
        int id = getIntent().getExtras().getInt("id");
        ApiStore.createApi(ApiService.class)
                .Info(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Info>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Info infoBaseResp) {
                        try {
                            if (infoBaseResp.getStatus() == 0) {
                                Info.Data data = infoBaseResp.getResult();
                                title.setText(data.getTitle());
                                RichText.from(data.getContent()).singleLoad(false).into(datas);
                            }
                        }catch (Exception ex){
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


    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
