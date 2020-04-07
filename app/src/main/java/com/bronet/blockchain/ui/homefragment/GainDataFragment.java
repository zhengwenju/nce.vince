package com.bronet.blockchain.ui.homefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.HomeAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.GetH24List;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore2;
import com.bronet.blockchain.util.MyViewPager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GainDataFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<GetH24List.Data> h24List;
    private HomeAdapter homeAdapter;






    private void GetH24List() {
        ApiStore2.createApi(ApiService.class)
                .GetH24List()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetH24List>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GetH24List getH24List) {
                        if (getH24List.getCode() == 0) {
                            h24List = getH24List.getData();
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            homeAdapter = new HomeAdapter(R.layout.item_home, h24List);
                            rv.setAdapter(homeAdapter);
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
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        GetH24List();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_gaindata_list;
    }
}
