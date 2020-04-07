package com.bronet.blockchain.ui.my;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.Feedback;
import com.bronet.blockchain.adapter.FeedbackListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
 * 留言列表
 * Created by 18514 on 2019/7/26.
 */

public class FeedbackListActivity extends BaseActivity  implements FeedbackListAdapter.OnItemChildClickListener{
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    ArrayList<Feedback.Data> list = new ArrayList<>();
    int page,totalPage;
    FeedbackListAdapter feedbackListAdapter;
    LinearLayoutManager linearLayoutManager;
    boolean loding;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_list;
    }
    @Override
    protected void initView() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        feedbackListAdapter = new FeedbackListAdapter(this,list,activity);
        rv.setAdapter(feedbackListAdapter);
    }

    @Override
    protected void initData() {
        Feedback(page);
        SetRead();
    }

    @Override
    protected void setEvent() {
        smartRefreshLayout.setEnableRefresh(true);
        //是否在加载完成时滚动列表显示新的内容
        //smartRefreshLayout.setEnableHeaderTranslationContent(false);//是否下拉Header的时候向下平移列表或者内容
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Feedback(page);
                smartRefreshLayout.finishRefresh();
            }
        });
    }

    public void Feedback(int i){
        i++;
        ApiStore.createApi(ApiService.class)
                .Feedback(ConstantUtil.ID,i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Feedback>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Feedback feedback) {
                        L.test("feedback========>>>"+feedback);
                        if (feedback.getStatus()==0){
                            page=feedback.getPageNo();
                            totalPage=feedback.getTotalPage();
                            ArrayList<Feedback.Data> data = feedback.getData();
                            if (data!=null&&data.size()>0) {
                                loding=true;
                                feedbackListAdapter.setNewData(data,rv);
                            }

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
    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
    private void SetRead() {
        Data data = new Data();
        data.userId=ConstantUtil.ID;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .SetRead(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {
                        L.test("setread======>"+tradeNce.getStatus());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public class Data {
        String userId;
    }
    @Override
    public void onItemShowClick(int position) {
        Feedback.Data data = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("imageurl",data.getFujian());
        JumpUtil.overlay(this, ImageViewActivity.class,bundle);
    }
}
