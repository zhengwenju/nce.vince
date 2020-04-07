package com.bronet.blockchain.ui;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.ContactChooseAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.ContractRules;
import com.bronet.blockchain.data.InvestLevelList;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.data.Withdrawal;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.ExchangeRecordListActivity;
import com.bronet.blockchain.ui.MyEditText;
import com.bronet.blockchain.ui.fragment.ContractFragment;
import com.bronet.blockchain.ui.my.InputAssureActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.ConstKeys;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.ZXingUtils;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 开通合约
 * Created by 18514 on 2019/7/26.
 */

public class OpenContractActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.input_num_et)
    MyEditText input_num_et;
    @BindView(R.id.tip_tv)
    TextView tip_tv;
    @BindView(R.id.data)
    TextView data;
    private int minNceNum;
    private int maxNceNum;
    private BigDecimal buyQuota;
    private BigDecimal range;
    private ContactChooseAdapter adapter;
    int isReInverst;
    private String pwd;
    //合约规则,风险
    String guize, fenxian;
    PopupWindow popWindow;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_contract;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        adapter = new ContactChooseAdapter(R.layout.contact_choose_item, investLevels);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OpenContractActivity.this, 3);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<InvestLevelList.ResultBean> investLevels;
    @Override
    protected void initData() {
        GetRange(1,3);
        ContractRules();
        InvestLevelList();
    }
    //投资范围
    private void InvestLevelList() {
        ApiStore.createApi(ApiService.class)
                .InvestLevelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestLevelList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull InvestLevelList investLevelList) {
                        if (investLevelList.getStatus() == 0) {
                            investLevels = investLevelList.getResult();
                        }
                        Banlance(3);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(OpenContractActivity.this, 123, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void Banlance(int code) {
        ApiStore.createApi(ApiService.class)
                .Banlance(ConstantUtil.ID, String.valueOf(code))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Banlance>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @SuppressLint("WrongConstant")
                    @Override
                    public void onNext(@NonNull Banlance banlance) {
                        try {
                            for (int i = 0; i < investLevels.size(); i++) {
                                InvestLevelList.ResultBean resultBean = investLevels.get(i);
                                int j = i + 1;
                                resultBean.setLevel("C" + j);
                            }



                            adapter.setNewData(investLevels);

                            //初始化数据
                            adapter.setThisPosition(0);
                            InvestLevelList.ResultBean item0 = adapter.getItem(0);
                            minNceNum = item0.getMinQty();
                            maxNceNum = item0.getMaxQty();
                            tip_tv.setText("(提示：当前级别投资范围" + minNceNum + "-" + maxNceNum + "NCE)");

                            if (banlance.getStatus() == 0) {
                                buyQuota = new BigDecimal(banlance.getResult());
                            }
                            TextView inputNumTv = findViewById(R.id.input_num_tv);
                            if (buyQuota != null) {
                                inputNumTv.setText(buyQuota.toString());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(OpenContractActivity.this, 122, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void GetRange(int inCoreType, int outCoreType) {
        ApiStore.createApi(ApiService.class)
                .Range(inCoreType, outCoreType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NceEthRange>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NceEthRange nceEthRange) {
                        if (nceEthRange.getStatus() == 0) {
                            range = nceEthRange.getResult();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(OpenContractActivity.this, 123, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    protected void setEvent() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter2, View view, int position) {
                adapter.setThisPosition(position);
                adapter.notifyDataSetChanged();
                InvestLevelList.ResultBean item = adapter.getItem(position);
                minNceNum = item.getMinQty();
                maxNceNum = item.getMaxQty();
                tip_tv.setText("(提示：当前级别投资范围" + minNceNum + "-" + maxNceNum + "NCE)");
                input_num_et.setText("");
            }
        });
        final PercentLinearLayout btn_is = findViewById(R.id.btn_is); //复投
        btn_is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReInverst == 0) {
                    isReInverst = 1;
                    btn_is.getChildAt(0).setSelected(true);
                    btn_is.getChildAt(1).setSelected(true);
                } else {
                    isReInverst = 0;
                    btn_is.getChildAt(0).setSelected(false);
                    btn_is.getChildAt(1).setSelected(false);
                }
            }
        });
        final PercentLinearLayout btn_dan = findViewById(R.id.btn_dan); //复投
        btn_dan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(OpenContractActivity.this,ExplainActivity.class);
            }
        });
    }


    @SuppressLint("WrongConstant")
    @OnClick({R.id.btn_back,R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case  R.id.btn_ok:
                try {
                    if (range != null) {
                        String inputStr = input_num_et.getText().toString();
                        if (!TextUtils.isEmpty(inputStr)) {
                            Double inputNum = Double.valueOf(inputStr);
                            if (buyQuota != null) {
                                if (inputNum.floatValue() > buyQuota.floatValue()) {//不能超过资产
                                    if (Const.tipMap.containsKey(ConstKeys.CONTACT_INPUT_LACK_NCE)) {
                                        DialogUtil.show(OpenContractActivity.this, Const.tipMap.get(ConstKeys.CONTACT_INPUT_LACK_NCE));
                                    }
                                    return;
                                }
                            }
                            if (inputNum < minNceNum || inputNum > maxNceNum) {
                                DialogUtil.show(OpenContractActivity.this, "输入的投资数量不在指定范围内");
                                return;
                            } else {

                                showPop(2, 1, 0);
//                                View inflate = LayoutInflater.from(this).inflate(R.layout.pop_tip, null);
//                                TextView content =inflate.findViewById(R.id.content);
//                                content.setText(fenxian);
//                                inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        popWindow.dismiss();
//                                        changeWindowAlfa(1f);
//                                    }
//                                });
//                                inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        popWindow.dismiss();
//                                        changeWindowAlfa(1f);
//                                        showPop(2, 1, 0);
//                                    }
//                                });
//                                popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
//                                        WindowManager.LayoutParams.WRAP_CONTENT);
//                                View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
//                                popWindow.setFocusable(true);
//                                popWindow.setAnimationStyle(R.style.popwindow_anim_style);
//                                popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
//                                popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//                                popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                                popWindow.showAtLocation(parent,
//                                        Gravity.CENTER, 0, 0);
//                                changeWindowAlfa(0.5f);


                            }
                        } else {
                            DialogUtil.show(OpenContractActivity.this, "请输入投资数量");
                        }
                    } else {
                        ToastUtil.show(OpenContractActivity.this, getString(R.string.server_exception));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public class Data {
        String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }

        String coinId;
    }

    public class Data1 {
        String userId;
        String password;
        String qty;
        int isReInverst;
        int id;
        int status;
        int vouchApp;
        int coinType;

    }

    private void Investment(String password, int vouchApp) {
        Data1 data = new Data1();
        data.userId = ConstantUtil.ID;
        data.qty = input_num_et.getText().toString();
        data.isReInverst = isReInverst;
        data.password = AESUtil.encry(password);
        data.vouchApp = vouchApp;//是否申请担保0 不申请(默认0)， 1 申请
        data.coinType = 3; //只能投资NCE
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .Investment(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Withdrawal>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Withdrawal withdrawal) {
                        popWindow.dismiss();

                        if (withdrawal.getStatus() == 2) {//合约满了，走预约
                            showPop(withdrawal.getMsg());
                        } else if (withdrawal.getStatus() == 3) { //合约满了后，点击是否预约弹框，点击是再调用Investment接口，传vouchApp＝1，服务端status返回3，跳到输入额度界面。
                            Bundle bundle = new Bundle();
                            bundle.putInt("pid", withdrawal.getResult());
                            JumpUtil.overlay(OpenContractActivity.this, InputAssureActivity.class, bundle);
//                            et_number.setText("");

                        } else {

                            InvestLevelList();

                            AlertDialog.Builder builder = new AlertDialog.Builder(OpenContractActivity.this,R.style.FullScreenDialog);
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getWindow().clearFlags(
                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                            | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                            dialog.getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            Window window = dialog.getWindow();
                            window.setContentView(R.layout.alter4);
                            View btnYes = window.findViewById(R.id.btn_yes);
                            View btnBack = window.findViewById(R.id.btn_back);
                            TextView content = (TextView)window.findViewById(R.id.content);
                            content.setText(withdrawal.getMsg());
                            // 自定义图片的资源
                            btnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                   finish();
                                    dialog.dismiss();
                                }
                            });
                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    dialog.dismiss();
                                    input_num_et.setText("");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(OpenContractActivity.this, 131, e.getMessage(), false);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void showPop(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.FullScreenDialog);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.alter2);
        View btnYes = window.findViewById(R.id.btn_yes);
        View btnNo = window.findViewById(R.id.btn_no);
        TextView content = (TextView) window.findViewById(R.id.content);
        content.setText(msg);
        // 自定义图片的资源
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Investment(pwd, 1);
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });


    }

    @SuppressLint("WrongConstant")
    private void showPop(int type, int type2, int position) {
        View inflate = null;
        switch (type) {
            case 2:
                inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
                initB(inflate, type2, position);
                break;

        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        if (type == 1) {
            popWindow.setOutsideTouchable(true);
            popWindow.setTouchable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.showAtLocation(parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            popWindow.setFocusable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWindow.showAtLocation(parent,
                    Gravity.CENTER, 0, 0);
        }
        changeWindowAlfa(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }
    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }

    /**
     * 支付密码
     *
     * @param inflate
     * @param type
     */
    private void initB(View inflate, final int type, final int position) {
        final EditText name = inflate.findViewById(R.id.name);
        name.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
                InputMethodManager imm = (InputMethodManager) OpenContractActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) OpenContractActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
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
                    ToastUtil.show(OpenContractActivity.this,OpenContractActivity.this.getString(R.string.d20));
                } else {
                    //支付密码
                    switch (type) {
                        case 1:
                            //开通合约
                            pwd = name.getText().toString();
                            if (!ClickUtils.isFastClick()) {
                                return;
                            }
                            Investment(pwd, 0);
//                            et_number.setText("");
                            break;
                    }

                }
            }
        });
    }

    //风险
    private void RiskWarning() {
        ApiStore.createApi(ApiService.class)
                .RiskWarning()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractRules>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContractRules contractRules) {
                        if (contractRules.getStatus() == 0) {
                            fenxian = contractRules.getResult();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(OpenContractActivity.this, 119, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //合约规则
    private void ContractRules() {
        ApiStore.createApi(ApiService.class)
                .ContractRules()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractRules>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContractRules contractRules) {

                        if (contractRules.getStatus() == 0) {
                            guize = contractRules.getResult();
                            data.setText(guize);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(OpenContractActivity.this, 120, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
