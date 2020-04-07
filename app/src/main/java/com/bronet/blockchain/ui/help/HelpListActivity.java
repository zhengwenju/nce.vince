package com.bronet.blockchain.ui.help;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.HelpListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.TypeList;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 列表
 * Created by 18514 on 2019/7/26.
 */

public class HelpListActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    int id;
    int page,totalPage;
    ArrayList<TypeList.Data>list=new ArrayList<>();
    HelpListAdapter helpListAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_list;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        helpListAdapter = new HelpListAdapter(R.layout.item_helpl, list);
        rv.setAdapter(helpListAdapter);
    }

    @Override
    protected void initData() {
        id = getIntent().getExtras().getInt("id");
        TypeList(page);
    }

    private void TypeList(int i) {
        i++;
        Data data = new Data();
        data.typeId=id;
        data.keyword="";
        data.pageNo=i;

        Gson gson = StringUtil.getGson(true);
        String s1 = gson.toJson(data);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s1);
        ApiStore.createApi(ApiService.class)
                .TypeList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TypeList typeList) {
                        if (typeList.getStatus()==0){
                            page=typeList.getPageNo();
                            totalPage=typeList.getTotalPage();
                            ArrayList<TypeList.Data> result = typeList.getData();
                            if (page==1){
                                helpListAdapter.setNewData(result);
                            }else{
                                helpListAdapter.addData(result);
                            }
                            helpListAdapter.loadMoreComplete();
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
        helpListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",helpListAdapter.getItem(i).getId());
                JumpUtil.startForResult(activity,HelpDataActivity.class,1,bundle);
            }
        });
        helpListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (page < totalPage) {
                    TypeList(page++);
                } else {
                    helpListAdapter.loadMoreEnd();
                }
            }
        }, rv);
    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
    public class Data{
        int typeId;
        String keyword;
        int pageNo;
        String pageSize;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==10){
            finish();
        }
    }
}
