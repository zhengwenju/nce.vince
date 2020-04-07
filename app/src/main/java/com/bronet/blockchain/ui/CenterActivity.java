package com.bronet.blockchain.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.ActivityCenterAdapter;
import com.bronet.blockchain.adapter.HelpAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.CenterActivit;
import com.bronet.blockchain.data.Type;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.help.HelpDataActivity;
import com.bronet.blockchain.ui.help.JournalismActivity;
import com.bronet.blockchain.util.JumpUtil;

import java.util.ArrayList;
import java.util.List;

public class CenterActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.btn_back)
    ImageView btn_back;


    private HelpAdapter helpAdapter;
    private ActivityCenterAdapter activityCenterAdapter;

    private List<CenterActivit.ResultBean> result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_center;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        activityCenterAdapter = new ActivityCenterAdapter(R.layout.activity_center_list, result,activity);
        rv.setAdapter(activityCenterAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        ApiStore.createApi(ApiService.class)
                .Activity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CenterActivit>() {




                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CenterActivit centerActivit) {
                        if (centerActivit.getStatus()==0){
                            result = centerActivit.getResult();
                            activityCenterAdapter.setNewData(result);
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
}
