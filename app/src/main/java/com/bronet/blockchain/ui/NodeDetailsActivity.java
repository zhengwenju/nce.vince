package com.bronet.blockchain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.NodeLogListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.NodeInfo;
import com.bronet.blockchain.data.NodeLog;
import com.bronet.blockchain.data.TransferNodeReward;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NodeDetailsActivity extends BaseActivity {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.rate)
    TextView mRate;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.Multiple)
    TextView mMultiple;
    @BindView(R.id.note)
    TextView mNote;
    @BindView(R.id.todayProfit)
    TextView mTodayProfit;
    @BindView(R.id.total_revenue)
    TextView mTotalRevenue;
    @BindView(R.id.incomeReward_tv)
    TextView incomeRewardTv;
    @BindView(R.id.btn_money)
    TextView mBtnMoney;
    @BindView(R.id.btn_tra_record)
    Button mBtnTraRecord;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.nodeName)
    TextView mNodeName;
    private int pageNo=1;
    private int pageSize=10;
    private List<NodeLog.ResultBean> list=new ArrayList<>();
    private NodeLogListAdapter nodeLogListAdapter;
    private int nodeId;
    private PopupWindow popWindow;
    private EditText name;
    private EditText num;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_node_details;
    }

    private BigDecimal num100=new BigDecimal(100);
    private MathContext mc = new MathContext(2);
    @Override
    protected void initView() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv.setText(getString(R.string.note8));
        nodeId = getIntent().getExtras().getInt("nodeId");



        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        nodeLogListAdapter = new NodeLogListAdapter(R.layout.node_list_item, list);
        mRecyclerView.setAdapter(nodeLogListAdapter);



        mSmartRefreshLayout.setEnableRefresh(true);//启用刷新
        mSmartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        mSmartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            list.clear();
            pageNo=1;
            NodeLogList(nodeId,pageNo,pageSize);
            mSmartRefreshLayout.finishRefresh();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNo++;
            NodeLogList(nodeId,pageNo,pageSize);
            mSmartRefreshLayout.finishLoadMore();

        });
        linearLayout5.setOnClickListener(view -> {
            if(popWindow!=null&&popWindow.isShowing()){
                return;
            }
            showPop();
        });
        mBtnTraRecord.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("nodeId",nodeId);
            Intent mIntent = new Intent(NodeDetailsActivity.this, TransferRecordActivity.class);
            mIntent.putExtras(bundle);
            NodeDetailsActivity.this.startActivity(mIntent);
        });

    }

    @Override
    protected void initData() {
        //用户节点日志
        NodeLogList(nodeId,pageNo,pageSize);
        //获取单个节点信息
        NodeInfo(nodeId);

    }




    @Override
    protected void setEvent() {

    }


    //用户节点日志
    private void NodeLogList(int nodeId,int pageNo, int pageSize) {
        ApiStore.createApi(ApiService.class)
                .NodeLog(ConstantUtil.ID,nodeId, pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NodeLog>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull NodeLog nodeLog) {
                        Log.d("-------", "onNext: "+nodeLog.getMsg());
                        if (nodeLog.getStatus() == 0) {
                            List<NodeLog.ResultBean> resultBeans = Util.nodelog(nodeLog.getResult());
                            list.addAll(resultBeans);
                            nodeLogListAdapter.setNewData(list);
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



    //获取单个节点信息
    private void NodeInfo(int nodeId) {
        ApiStore.createApi(ApiService.class)
                .NodeInfo(nodeId,ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NodeInfo>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull NodeInfo nodeInfo) {
                        NodeInfo.ResultBean result = nodeInfo.getResult();
                        String msg = nodeInfo.getMsg();
                        if (nodeInfo.getStatus()==0){
                            BigDecimal ratio = new BigDecimal(result.getRatio());
                            mRate.setText(num100.multiply(ratio,mc)+"%");
                            mMoney.setText(result.getQty()==null?"0.00":String.valueOf(result.getQty()));
                            mMultiple.setText(result.getMultiple()==null?"0.00":String.valueOf(result.getMultiple()));
                            mNodeName.setText(result.getName()==null?"0.00":result.getName());
                            mTodayProfit.setText(result.getTodayReward()==null?"0.00":result.getTodayReward());
                            mTotalRevenue.setText(result.getTotalReward()==null?"0.00":result.getTotalReward());

                            String str = getString(R.string.node23);
                            String text = str+(result.getIncomeReward()==null?"0.00":result.getIncomeReward());
                            incomeRewardTv.setText(text);

                        }else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(NodeDetailsActivity.this, R.style.FullScreenDialog);
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getWindow().clearFlags(
                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                            | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                            dialog.getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            Window window = dialog.getWindow();
                            window.setContentView(R.layout.alter);
                            View btnYes = window.findViewById(R.id.btn_yes);
                            TextView content = (TextView)window.findViewById(R.id.content);
                            content.setText(msg);
                            // 自定义图片的资源
                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
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
    private void showPop() {
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop24, null);
        TransferNode(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);
        changeWindowAlfa(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }


        });
    }




    private void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }

    private void TransferNode(View inflate) {
        name = inflate.findViewById(R.id.name);
        num = inflate.findViewById(R.id.num);

        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(num.getText().toString())){
                    ToastUtil.show(NodeDetailsActivity.this,NodeDetailsActivity.this.getString(R.string.transf_Profit_tip));
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString())){
                    ToastUtil.show(NodeDetailsActivity.this,NodeDetailsActivity.this.getString(R.string.d20));
                    return;
                }else{
                    if(!ClickUtils.isFastClick()){
                        return;
                    }

                }
                TransferNodeReward(name.getText().toString(), num.getText().toString());

            }

        });
    }

    private void TransferNodeReward(String name,String num) {
        Data data = new Data();
        data.userId=ConstantUtil.ID;
        data.password= AESUtil.encry(name);
        data.qty= num;
        data.id=nodeId;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .TransferNodeReward(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransferNodeReward>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull TransferNodeReward appNode) {
                        if(!TextUtils.isEmpty(appNode.getMsg())) {
                            ToastUtil.show(NodeDetailsActivity.this, appNode.getMsg());
                        }
                        if(appNode.getStatus()==0){
                            list.clear();
                            //用户节点日志
                            NodeLogList(nodeId,pageNo,pageSize);
                            //获取单个节点信息
                            NodeInfo(nodeId);
                            popWindow.dismiss();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        ToastUtil.show(NodeDetailsActivity.this, getString(R.string.server_exception));
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    public class Data {
        String userId;
        String password;
        String qty;
        int id;
    }



}
