package com.bronet.blockchain.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.MiningListAdapter;
import com.bronet.blockchain.adapter.NodeListAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.AppNode;
import com.bronet.blockchain.data.BuyMine;
import com.bronet.blockchain.data.Mine;
import com.bronet.blockchain.data.NodeList;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MiningMachineActivity extends BaseActivity {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MiningListAdapter miningListAdapter;
    private List<Mine.ResultBean> result;
    private PopupWindow popWindow;
    private EditText name;
    private int nodeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mining_machine;
    }

    @Override
    protected void initView() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv.setText(getString(R.string.mine01));
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        miningListAdapter = new MiningListAdapter(R.layout.mining_item, result);
        mRecyclerView.setAdapter(miningListAdapter);

       miningListAdapter.OnItemChildClickListener(new NodeListAdapter.OnItemChildClickListener() {
           @Override
           public void onItemReinvestClick(Button purchase, int position) {
               Mine.ResultBean item = miningListAdapter.getItem(position);
               nodeId = item.getId();
               showPop();
           }

           @Override
           public void onItemShowClick(int position) {

           }
       });


    }

    @Override
    protected void initData() {
        MiningList();

    }



    @Override
    protected void setEvent() {

    }

    private void MiningList() {

        ApiStore.createApi(ApiService.class)
                .Mine()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Mine>() {



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Mine mine) {
                        result = mine.getResult();
                        try {
                            if (mine.getStatus() == 0) {
                                miningListAdapter.setNewData(result);
                            }
                        } catch (Exception ex) {
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

    private void showPop() {
        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
        Purchase(inflate);
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


    private void Purchase(View inflate) {

        name = inflate.findViewById(R.id.name);
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

                if (TextUtils.isEmpty(name.getText().toString())) {
                    ToastUtil.show(MiningMachineActivity.this, MiningMachineActivity.this.getString(R.string.d20));
                    return;
                } else {
                    if (!ClickUtils.isFastClick()) {
                        return;
                    }

                }
                NodePurchase(name.getText().toString(), nodeId);

            }

        });

    }

    private void NodePurchase(String password, int mineId) {
        Data data = new Data();
        data.mineId = mineId;
        data.userid = ConstantUtil.ID;
        data.password = AESUtil.encry(password);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .BuyMine(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BuyMine>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull BuyMine buyMine) {
                        ToastUtil.show(MiningMachineActivity.this, buyMine.getMsg());
                        Log.i("------->", "onNext: " + buyMine.getMsg());
                        popWindow.dismiss();
                        MiningList();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class Data {
        int mineId;
        String userid;
        String password;

    }


}

