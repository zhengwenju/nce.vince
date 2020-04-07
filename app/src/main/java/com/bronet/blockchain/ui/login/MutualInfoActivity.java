package com.bronet.blockchain.ui.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MutualAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.MutualModelList;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 互助信息
 * Created by 18514 on 2019/6/25.
 */

public class MutualInfoActivity extends BaseActivity {

    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.title_tv)
    TextView titleTv;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private int pageSize=10;
    private int pageNo=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mutual_info;
    }
    private List<MutualModelList.ResultBean> result = new ArrayList<>();
    private MutualAdapter mutualAdapter;

    @Override
    protected void initView() {
        titleTv.setText(R.string.mutual50);
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
        LinearLayoutManager gridLayoutManager  = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView2.setLayoutManager(gridLayoutManager);
        mutualAdapter = new MutualAdapter(R.layout.item_mutual, result);
        recyclerView2.setAdapter(mutualAdapter);



        smartRefreshLayout.setEnableRefresh(true);//启用刷新
        smartRefreshLayout.setEnableLoadMore(true);//启用加载更多
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                result.clear();
                pageNo=1;
                MutualList(pageNo,pageSize);
                smartRefreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                pageNo++;
                MutualList(pageNo,pageSize);
                smartRefreshLayout.finishLoadMore();

            }
        });
    }
    @Override
    protected void initData() {
        MutualList(pageNo,pageSize);
    }

    @Override
    protected void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mutualAdapter.OnItemEntrustClickListener(new MutualAdapter.OnItemSetStatusClickListener() {
            @Override
            public void onItemSetStatusClick(View view, int position) {
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(activity, LoginActivity.class);
                    return;
                }
                MutualModelList.ResultBean item = mutualAdapter.getItem(position);
                if(item.getStatus()==0) {
                    int id = item.getId();
                    showPopConfirm(id);
                }

            }
        });
    }
    //审核列表
    private void MutualList(int pageNo,int pageSize) {
        ApiStore.createApi(ApiService.class)
                .MutualList(ConstantUtil.ID,pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MutualModelList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull MutualModelList tranEntrusts) {
                        try {
                            if (tranEntrusts.getStatus() == 0) {
                                result.addAll(tranEntrusts.getResult());
                                List<MutualModelList.ResultBean> resultBeans = Util.ridRepeat6(result);
                                mutualAdapter.setNewData(resultBeans);
                            }else {
                                //ToastUtil.show(MutualInfoActivity.this,tranEntrusts.getMsg());
                            }
                            if (TextUtils.isEmpty(ConstantUtil.ID)) {
                                JumpUtil.overlay(activity, LoginActivity.class);
                                return;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(MutualInfoActivity.this,143,e.getMessage(),true);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }


    //审核操作
    private void MutualAdd(int id ,String pwd,String status) {
        Data data = new Data();
        data.userId=ConstantUtil.ID;
        data.payPwd= AESUtil.encry(pwd);
        data.id=id;
        data.status=status;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .MutualAudit(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {
                        ToastUtil.show(MutualInfoActivity.this, tradeNce.getMsg());
                        if(tradeNce.getStatus()==0) {
//                            finish();
                        }
                        result.clear();
                        initData();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    public void onBackPressed() {
        if (ConstantUtil.TYPE) {
            JumpUtil.overlay(activity, MainActivity.class);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private PopupWindow popWindow;
    @SuppressLint("WrongConstant")
    private void showPop(int id,String status) {
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
        initB(inflate,id,status);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);

        changeWindowAlfa(0.5f);
        popWindow.showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void showPopConfirm( int id) {
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop16, null);
        TextView textView =inflate.findViewById(R.id.pop_quote_tv);
        TextView btn_yes =inflate.findViewById(R.id.btn_yes);
        TextView btn_no =inflate.findViewById(R.id.btn_no);
        textView.setText("您确定通过该条信息?");
        btn_yes.setText("通过");
        btn_no.setText("取消");
        initBConfirm(inflate,id);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);

        changeWindowAlfa(0.5f);
        popWindow.showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }
    private void initBConfirm(View inflate,final int id) {
        final EditText name = inflate.findViewById(R.id.name);
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
//                showPop(id,"1");
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow!=null) {
                    popWindow.dismiss();
                }
                //通过审核,需要再次输入密码
                showPop(id,"2");
            }
        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }
    private void initB(View inflate,final int id,final String status) {
        final EditText name = inflate.findViewById(R.id.name);
        final ImageView hidden_pwd = inflate.findViewById(R.id.hidden_pwd);
        final ImageView display_pwd = inflate.findViewById(R.id.display_pwd);
        hidden_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                display_pwd.setVisibility(View.VISIBLE);
                hidden_pwd.setVisibility(View.GONE);
                name.setSelection(name.getText().length());
            }
        });
        display_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setTransformationMethod(PasswordTransformationMethod.getInstance());
                display_pwd.setVisibility(View.GONE);
                hidden_pwd.setVisibility(View.VISIBLE);
                name.setSelection(name.getText().length());
            }
        });
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutualAdd(id,name.getText().toString(),status);
                popWindow.dismiss();
            }
        });
    }


    public class Data {
        int id;
        String userId;
        String payPwd;
        String status;
    }

    public interface ISetDeviceType {
        int deletetype=1;
        int settype=2;
    }
}
