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
import com.bronet.blockchain.adapter.MineLogListAdapter;
import com.bronet.blockchain.adapter.NodeLogListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.MyMineInfo;
import com.bronet.blockchain.data.NodeInfo;
import com.bronet.blockchain.data.NodeLog;
import com.bronet.blockchain.data.TransferMineReward;
import com.bronet.blockchain.data.TransferNodeReward;
import com.bronet.blockchain.data.UserMineLog;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

public class MiningDetailsActivity extends BaseActivity {


    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.b)
    TextView mB;
    @BindView(R.id.rate)
    TextView mRate;
    @BindView(R.id.RelativeLayout1)
    RelativeLayout mRelativeLayout1;
    @BindView(R.id.f)
    TextView mF;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.c1)
    TextView mC1;
    @BindView(R.id.incomeReward_tv5)
    TextView mIncomeRewardTv5;
    @BindView(R.id.RelativeLayout2)
    RelativeLayout mRelativeLayout2;
    @BindView(R.id.a)
    TextView mA;
    @BindView(R.id.Multiple)
    TextView mMultiple;
    @BindView(R.id.nodeName)
    TextView mNodeName;
    @BindView(R.id.todayProfit)
    TextView mTodayProfit;
    @BindView(R.id.total_revenue)
    TextView mTotalRevenue;
    @BindView(R.id.btn_money)
    TextView mBtnMoney;
    @BindView(R.id.incomeReward_tv)
    TextView mIncomeRewardTv;
    @BindView(R.id.linearLayout5)
    LinearLayout mLinearLayout5;
    @BindView(R.id.btn_tra_record)
    Button mBtnTraRecord;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.image)
    ImageView mImage;
    private int minId;
    private int pageNo=1;
    private int pageSize=10;
    private BigDecimal num100=new BigDecimal(100);
    private MathContext mc = new MathContext(2);
    private List<UserMineLog.ResultBean> list=new ArrayList<>();
    private MineLogListAdapter mineLogListAdapter;
    private PopupWindow popWindow;
    private EditText name;
    private EditText num;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mining_details;
    }

    @Override
    protected void initView() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv.setText(getString(R.string.time20));
        minId = getIntent().getExtras().getInt("minId");

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mineLogListAdapter = new MineLogListAdapter(R.layout.node_list_item, list);
        mRecyclerView.setAdapter(mineLogListAdapter);



        mSmartRefreshLayout.setEnableRefresh(true);//启用刷新
        mSmartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        mSmartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            list.clear();
            pageNo=1;
            UserMineLog(minId,pageNo,pageSize);
            mSmartRefreshLayout.finishRefresh();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNo++;
            UserMineLog(minId,pageNo,pageSize);
            mSmartRefreshLayout.finishLoadMore();

        });

        mLinearLayout5.setOnClickListener(view -> {
            if(popWindow!=null&&popWindow.isShowing()){
                return;
            }
            showPop();
        });

        mBtnTraRecord.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("minId",minId);
            Intent mIntent = new Intent(MiningDetailsActivity.this, MineRecordActivity.class);
            mIntent.putExtras(bundle);
            MiningDetailsActivity.this.startActivity(mIntent);
        });

    }



    @Override
    protected void initData() {
        //矿机详情
        MyMineInfo(minId);
        //用户矿机日志
        UserMineLog(minId,pageNo,pageSize);

    }




    @Override
    protected void setEvent() {

    }

    private void showPop() {
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop24, null);
        TransferMineReward(inflate);
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

    //矿机划转收益
    private void TransferMineReward(View inflate) {
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
                    ToastUtil.show(MiningDetailsActivity.this,MiningDetailsActivity.this.getString(R.string.transf_Profit_tip));
                    return;
                }
                if (TextUtils.isEmpty(name.getText().toString())){
                    ToastUtil.show(MiningDetailsActivity.this,MiningDetailsActivity.this.getString(R.string.d20));
                    return;
                }else{
                    if(!ClickUtils.isFastClick()){
                        return;
                    }

                }
                TransferMineReward1(name.getText().toString(), num.getText().toString());

            }



        });
    }
    private void TransferMineReward1(String name,String num) {
        Data data = new Data();
        data.userId=ConstantUtil.ID;
        data.password= AESUtil.encry(name);
        data.qty= num;
        data.id=minId;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .TransferMineReward(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransferMineReward>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull TransferMineReward appmin) {
                        if(!TextUtils.isEmpty(appmin.getMsg())) {
                            ToastUtil.show(MiningDetailsActivity.this, appmin.getMsg());
                        }
                        if(appmin.getStatus()==0){
                            list.clear();
                            //用户节点日志
                            UserMineLog(minId,pageNo,pageSize);
                            //获取单个节点信息
                            MyMineInfo(minId);
                            popWindow.dismiss();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        ToastUtil.show(MiningDetailsActivity.this, getString(R.string.server_exception));
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void MyMineInfo(int minId) {
        ApiStore.createApi(ApiService.class)
                .MyMineInfo(minId, ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyMineInfo>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MyMineInfo myMineInfo) {
                        MyMineInfo.ResultBean result = myMineInfo.getResult();
                        String msg = myMineInfo.getMsg();
                        if (myMineInfo.getStatus()==0){
                            BigDecimal ratio = new BigDecimal(result.getHashrate());
                            mRate.setText(num100.multiply(ratio,mc)+"%");
                            mMultiple.setText(result.getMultiple()==null?"0.00":String.valueOf(result.getMultiple()));
                            mTime.setText(result.getBalanceDays()==null?"0.00":result.getBalanceDays());
                            mNodeName.setText(result.getName()==null?"0.00":result.getName());
                            mTodayProfit.setText(result.getTodayReward()==null?"0.00":result.getTodayReward());
                            mTotalRevenue.setText(result.getTotalReward()==null?"0.00":result.getTotalReward());
                            Glide.with(activity).load(result.getImgUrl()).into(mImage);

                            String str = getString(R.string.node23);
                            String text = str+(result.getIncomeQty()==null?"0.00":result.getIncomeQty());
                            mIncomeRewardTv.setText(text);

                        }else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(MiningDetailsActivity.this, R.style.FullScreenDialog);
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
    private void UserMineLog(int minId,int pageNo, int pageSize) {
        ApiStore.createApi(ApiService.class)
                .UserMineLog(ConstantUtil.ID,minId, pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserMineLog>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull UserMineLog userMineLog) {
                        if (userMineLog.getStatus() == 0) {
                            List<UserMineLog.ResultBean> resultBeans = Util.minelog(userMineLog.getResult());
                            list.addAll(resultBeans);
                            mineLogListAdapter.setNewData(list);
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

    public class Data {
        String userId;
        String password;
        String qty;
        int id;
    }

}
