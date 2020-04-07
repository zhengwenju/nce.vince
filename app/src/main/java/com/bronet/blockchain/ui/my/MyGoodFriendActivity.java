package com.bronet.blockchain.ui.my;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MyGoodFriendAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.InvitedList;
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
 * 我的好友
 * Created by 18514 on 2019/7/3.
 */

public class MyGoodFriendActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<InvitedList.Result> list = new ArrayList<>();
    MyGoodFriendAdapter adapter;
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_g;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyGoodFriendAdapter(R.layout.item_my, list, activity);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        ApiStore.createApi(ApiService.class)
                .InvitedList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvitedList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull InvitedList invitedList) {
                        if (invitedList.getStatus() == 0) {
                            InvitedList.Result[] result = invitedList.getResult();
                            for (int i = 0; i < result.length; i++) {
                                list.add(result[i]);
                            }
                            adapter.setNewData(list);
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
}
