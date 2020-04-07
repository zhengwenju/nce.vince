package com.bronet.blockchain.ui.homefragment;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.HomeAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.GetH24List;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore2;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DealFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<GetH24List.Data> dealList;
    private HomeAdapter homeAdapter;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        GetH24Volume();
    }



    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new HomeAdapter(R.layout.item_home, dealList);
        rv.setAdapter(homeAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_deal;
    }
    private void GetH24Volume() {
        ApiStore2.createApi(ApiService.class)
                .GetH24Volume()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetH24List>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GetH24List getH24List) {
                        if (getH24List.getCode() == 0) {
                            dealList = getH24List.getData();
                            homeAdapter.setNewData(dealList);
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
