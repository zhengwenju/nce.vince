package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.ContactChooseAdapter;
import com.bronet.blockchain.adapter.ContractAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.AppNode;
import com.bronet.blockchain.data.AssetsFromCoin;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.data.ContractRules;
import com.bronet.blockchain.data.InvestLevelList;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.data.Model1;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.ReInvests;
import com.bronet.blockchain.data.Withdrawal;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.OpenContractActivity;
import com.bronet.blockchain.ui.TransferActivity;
import com.bronet.blockchain.ui.TransferRewardRecordActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.ui.my.InputAssureActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.ConstKeys;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.bronet.blockchain.view.FloorCountDownLib.Center;
import com.bronet.blockchain.view.FloorCountDownLib.ICountDownCenter;
import com.bronet.blockchain.view.MyScrollView;
import com.bronet.blockchain.view.RangeSeekbar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.itheima.wheelpicker.WheelPicker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 合约
 * Created by 18514 on 2019/6/27.
 */

public class ContractFragment extends BaseFragment implements ContractAdapter.OnItemChildClickListener, RangeSeekbar.OnCursorChangeListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    ArrayList<InvestList.Result> tempList1 = new ArrayList<>();
    ArrayList<InvestList.Result> tempList2 = new ArrayList<>();
    ContractAdapter contractAdapter;
    View inflate;
    //合约规则,风险
    String guize, fenxian;
    TextView number, number1, profit;
    PopupWindow popWindow;
    TextView et_assets, et_number;
    public static String TAG = "ContractFragment";
    //合约明细
    TextView btn_hy;
    //收益明细
    TextView btn_mx;
    //合约明细
    TextView btn_yy;
    //收益明细
    TextView btn_db;
    Button eyeBtn;
    int isReInverst;
    //滑动
    MyScrollView scr_text2;
    TextView data;
    TextView text1, text2, text3, btn_ok;
    ImageView iv2, iv1;
    PercentRelativeLayout layout2, layout3;
    ArrayList<InvestList.Result> invest = new ArrayList<>();   //合约明细
    ArrayList<InvestList.Result> rewardList = new ArrayList<>();//收益明细
    ArrayList<InvestList.Result> appointment = new ArrayList<>(); //预约明细
    ArrayList<InvestList.Result> guarantee = new ArrayList<>();//担保明细
    private ICountDownCenter countDownCenter;
    int i = 1;
    EditText transferProfitView;
    TextView transferBtn;
    TextView profitQutoeTv;
    TextView recyclerViewTitleTv;
    private int pageSize = 10;
    private int pageNo = 1;
    private CharSequence[] mTextArray;
    private List<InvestLevelList.ResultBean> result1;

    private LinearLayout zjhz_ll, hzjl_ll,kthy_ll;

    private boolean isShowNum = false;

    private String s2;
    private String s1;
    private String s3;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_contract;
    }

    @Override
    protected void initEvent() {
        if (transferBtn != null) {
            transferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Const.IS_FROZEN) {
                        DialogUtil.showFrozen(getActivity(), Const.FROZEN_CONTENT);
                        return;
                    }
                    if (TextUtils.isEmpty(transferProfitView.getText().toString())) {
                        DialogUtil.show(getActivity(), getString(R.string.transf_Profit_tip));
                        return;
                    }
                    if (KitingCointype == 0) {
                        DialogUtil.show(getActivity(), getString(R.string.invest20));
                        return;
                    }
                    showPop(2, 3, -1);

                }
            });
        }

    }

    //合约数量
    private void InvestNum() {
        ApiStore.createApi(ApiService.class)
                .InvestNum(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContractNum contractRules) {
                        if (contractRules.getStatus() == 0) {
                            try {
                                BigDecimal eth = new BigDecimal(contractRules.getResult().getEth());
                                BigDecimal nce = new BigDecimal(contractRules.getResult().getNce());

                                if (eth.doubleValue() > 0 && nce.doubleValue() > 0) {
                                    s1 = "ETH：" + eth.doubleValue() + "\n" + "NCE：" + nce.doubleValue();
                                } else if (eth.doubleValue() > 0 && nce.doubleValue() <= 0) {
                                     s1 = "ETH：" + eth.doubleValue();
                                } else if (eth.doubleValue() <= 0 && nce.doubleValue() > 0) {
                                    number1.setText("NCE：" + nce.doubleValue());
                                     s1 = "NCE：" + nce.doubleValue();
                                }

                                number1.setText(s1);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }


                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 116, e.getMessage(), false);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //复投合约收益
    private void ReInvestReward() {
        ApiStore.createApi(ApiService.class)
                .ReInvestReward(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {

                    private String s3;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContractNum contractRules) {
                        if (contractRules.getStatus() == 0) {
                            try {
                                BigDecimal eth = new BigDecimal(contractRules.getResult().getEth());
                                BigDecimal nce = new BigDecimal(contractRules.getResult().getNce());

                                if (eth.doubleValue() > 0 && nce.doubleValue() > 0) {
                                    s3=("ETH：" + eth.doubleValue() + "\n" + "NCE：" + nce.doubleValue());
                                } else if (eth.doubleValue() > 0 && nce.doubleValue() <= 0) {
                                    s3 = ("ETH：" + eth.doubleValue());
                                } else if (eth.doubleValue() <= 0 && nce.doubleValue() > 0) {
                                    s3=("NCE：" + nce.doubleValue());
                                }
                                profit.setText(s3);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 117, e.getMessage(), false);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //合约帐户总收益
    private void InvestReward() {
        ApiStore.createApi(ApiService.class)
                .InvestReward(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContractNum contractRules) {
                        if (contractRules.getStatus() == 0) {
                            try {
                                BigDecimal eth = new BigDecimal(contractRules.getResult().getEth());
                                BigDecimal nce = new BigDecimal(contractRules.getResult().getNce());

                                if (eth.doubleValue() > 0 && nce.doubleValue() > 0) {
                                    s2 = ("ETH：" + eth.doubleValue() + "\n" + "NCE：" + nce.doubleValue());
                                } else if (eth.doubleValue() > 0 && nce.doubleValue() <= 0) {
                                    s2=("ETH：" + eth.doubleValue());
                                } else if (eth.doubleValue() <= 0 && nce.doubleValue() > 0) {
                                    s2=("NCE：" + nce.doubleValue());
                                }
                                number.setText(s2);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 118, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

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
                        JumpUtil.errorHandler(getActivity(), 119, e.getMessage(), false);
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
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 120, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void initData() {
    }


    private BigDecimal buyQuota;

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        countDownCenter = new Center(1000, false);
        contractAdapter = new ContractAdapter(this, tempList1, countDownCenter);
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.contract_title, null);
        contractAdapter.addHeaderView(inflate);
        rv.setAdapter(contractAdapter);
        countDownCenter.bindRecyclerView(rv);
        initHeader();

        smartRefreshLayout.setEnableRefresh(true);//启用刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefreshLayout.setEnableLoadMore(true); //启用加载更多
//                list.clear();
                tempList1.clear();
                tempList2.clear();
                tag = 1;
                pageNo = 1;
                RewardLog(pageNo, pageSize);
                InvestList(pageNo, pageSize);
                ContractRules();
                RiskWarning();
                InvestNum();
                ReInvestReward();
                InvestReward();
//                Appointment();
//                Guarantee();
                smartRefreshLayout.finishRefresh();
                btn_hy.setSelected(true);
                btn_mx.setSelected(false);
                btn_yy.setSelected(false);
                btn_db.setSelected(false);

                btn_hy.setSelected(true);
                btn_mx.setSelected(false);
                btn_yy.setSelected(false);
                btn_db.setSelected(false);

                RecommendReward();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                if (tag == 2) { //加载更多收益明细
                    pageNo++;
                    RewardLog(pageNo, pageSize);
                } else {  //加载更多合约明细
                    pageNo++;
                    InvestList(pageNo, pageSize);
                }
                smartRefreshLayout.finishLoadMore();

            }
        });


    }

    //收余额
    private void RecommendReward() {
        ApiStore.createApi(ApiService.class)
                .RecommendReward(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContractNum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ContractNum contractRules) {
                        try {
                            if (profitQutoeTv != null) {
                                if (contractRules.getStatus() == 0) {
                                    BigDecimal eth = new BigDecimal(contractRules.getResult().getEth());
                                    BigDecimal nce = new BigDecimal(contractRules.getResult().getNce());
                                    if (eth.compareTo(BigDecimal.ZERO) > 0 && nce.compareTo(BigDecimal.ZERO) > 0) {
                                        profitQutoeTv.setText(getString(R.string.Profit_Banlance) + "：" + "ETH:" + eth + "  NCE:" + nce);
                                    } else if (eth.compareTo(BigDecimal.ZERO) > 0 && nce.compareTo(BigDecimal.ZERO) <= 0) {
                                        profitQutoeTv.setText(getString(R.string.Profit_Banlance) + "：" + "ETH:" + eth);
                                    } else if (eth.compareTo(BigDecimal.ZERO) <= 0 && nce.compareTo(BigDecimal.ZERO) > 0) {
                                        profitQutoeTv.setText(getString(R.string.Profit_Banlance) + "：" + "NCE:" + nce);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        JumpUtil.errorHandler(getActivity(),121,e.getMessage(),false);
                        L.test(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private int minNceNum;
    private int maxNceNum;

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
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.FullScreenDialog);
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                            dialog.getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            Window window = dialog.getWindow();
                            window.setContentView(R.layout.pop23);


                            for (int i = 0; i < investLevels.size(); i++) {
                                InvestLevelList.ResultBean resultBean = investLevels.get(i);
                                int j = i + 1;
                                resultBean.setLevel("C" + j);
                            }


                            LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager(getActivity());
                            gridLayoutManager1.setOrientation(GridLayout.VERTICAL);
                            final RecyclerView recyclerView = window.findViewById(R.id.recyclerView);
                            final TextView tip_tv = window.findViewById(R.id.tip_tv);
                            final TextView btn_no = window.findViewById(R.id.btn_no);
                            final TextView btn_ok = window.findViewById(R.id.btn_ok);
                            final com.bronet.blockchain.ui.MyEditText input_num_et = window.findViewById(R.id.input_num_et);

                            recyclerView.setLayoutManager(gridLayoutManager1);
                            final ContactChooseAdapter adapter = new ContactChooseAdapter(R.layout.contact_choose_item, investLevels);
                            //初始化数据
                            adapter.setThisPosition(0);
                            InvestLevelList.ResultBean item0 = adapter.getItem(0);
                            minNceNum = item0.getMinQty();
                            maxNceNum = item0.getMaxQty();
                            tip_tv.setText("提示：\n当前级别投资范围" + minNceNum + "-" + maxNceNum + "NCE");

                            recyclerView.setAdapter(adapter);
                            btn_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        if (range != null) {
                                            String inputStr = input_num_et.getText().toString();
                                            if (!TextUtils.isEmpty(inputStr)) {
                                                Double inputNum = Double.valueOf(inputStr);
                                                if (buyQuota != null) {
                                                    if (inputNum.floatValue() > buyQuota.floatValue()) {//不能超过资产
                                                        if (Const.tipMap.containsKey(ConstKeys.CONTACT_INPUT_LACK_NCE)) {
                                                            DialogUtil.show(getActivity(), Const.tipMap.get(ConstKeys.CONTACT_INPUT_LACK_NCE));
                                                        }
                                                        return;
                                                    }
                                                }
                                                if (inputNum < minNceNum || inputNum > maxNceNum) {
                                                    DialogUtil.show(getActivity(), "输入的投资数量不在指定范围内");
                                                    return;
                                                } else {
                                                    et_number.setText(String.valueOf(inputNum));
                                                    dialog.dismiss();
                                                }
                                            } else {
                                                DialogUtil.show(getActivity(), "请输入投资数量");
                                            }
                                        } else {
                                            ToastUtil.show(getActivity(), getString(R.string.server_exception));
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            });
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter2, View view, int position) {
                                    adapter.setThisPosition(position);
                                    adapter.notifyDataSetChanged();
                                    InvestLevelList.ResultBean item = adapter.getItem(position);
                                    minNceNum = item.getMinQty();
                                    maxNceNum = item.getMaxQty();
                                    tip_tv.setText("提示：\n当前级别投资范围" + minNceNum + "-" + maxNceNum + "NCE");
                                    input_num_et.setText("");
                                }
                            });


//
//                        View btnYes = window.findViewById(R.id.btn_yes);
//                        View image = window.findViewById(R.id.image);
//                        View close = window.findViewById(R.id.close);


                            if (banlance.getStatus() == 0) {
                                buyQuota = new BigDecimal(banlance.getResult());
                            }
                            TextView inputNumTv = window.findViewById(R.id.input_num_tv);
                            if (buyQuota != null) {
                                inputNumTv.setText(getString(R.string.nce_assert) + "：" + buyQuota);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 122, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private BigDecimal range;

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
                        JumpUtil.errorHandler(getActivity(), 123, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //划转收益
    private void KitingRecommendReward(int cointype) {
        TransfData data = new TransfData();
        data.userId = ConstantUtil.ID;
        data.paymentPassword = AESUtil.encry(pwd);
        data.coinType = cointype;
        data.qty = transferProfitView.getText().toString();
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test("KitingRecommendReward s=====>>" + s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .KitingRecommendReward(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model1>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model1 reInvests) {
                        popWindow.dismiss();
                        if (!TextUtils.isEmpty(reInvests.getMsg())) {
                            DialogUtil.show(getActivity(), reInvests.getMsg());
                        }
                        if (reInvests.getStatus() == 0) {
                            RecommendReward(); //更新收益余额
                            transferProfitView.setText("");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 124, e.getMessage(), false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //合约明细
    private void InvestList(int pageNo, int pageSize) {
        L.test("数据==>请求：合约明细请求");
        ApiStore.createApi(ApiService.class)
                .InvestList(ConstantUtil.ID, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull InvestList investList) {
                        L.test("数据==>响应：合约明细响应getStatus" + investList.getStatus());
//                        if (investList.getStatus() == 0) {
                        try {
                            invest = investList.getResult();

                            for (int i = 0; i < invest.size(); i++) {
                                invest.get(i).setItemType(1);
                            }
                            tempList1.addAll(invest);
                            List<InvestList.Result> resultBeans = Util.ridRepeat5(tempList1);
                            contractAdapter.setNewData(resultBeans);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：合约明细异常" + e.getMessage());
                        JumpUtil.errorHandler(getActivity(), 125, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //收益明细
    private void RewardLog(int pageNo, int pageSize) {
        L.test("数据==>请求：收益明细请求");
        ApiStore.createApi(ApiService.class)
                .RewardLog(ConstantUtil.ID, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull InvestList investList) {
                        L.test("数据==>响应：收益明细响应getStatus" + investList.getStatus());
                        try {
                            rewardList = investList.getResult();

                            for (int i = 0; i < rewardList.size(); i++) {
                                rewardList.get(i).setItemType(2);
                            }
                            tempList2.addAll(rewardList);
                            List<InvestList.Result> resultBeans = Util.ridRepeat5(tempList2);
                            contractAdapter.setNewData(resultBeans);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：收益明细异常" + e.getMessage());
                        JumpUtil.errorHandler(getActivity(), 126, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //预约明细
    private void Appointment() {
        appointment.clear();
        L.test("数据==>请求：预约明细请求ConstantUtil.ID：" + ConstantUtil.ID);
        ApiStore.createApi(ApiService.class)
                .Appointment(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(InvestList investList) {

                        L.test("数据==>响应：预约明细响应getStatus" + investList.getStatus());
//                        if (investList.getStatus() == 0) {
                        try {
                            appointment = investList.getResult();
                            for (int i = 0; i < appointment.size(); i++) {
                                appointment.get(i).setItemType(3);

                            }
                            contractAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                }
                            });
                            contractAdapter.setNewData(appointment);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：预约明细异常" + e.getMessage());
                        JumpUtil.errorHandler(getActivity(), 127, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //担保明细
    private void Guarantee() {
        guarantee.clear();
        L.test("数据==>请求：担保明细");
        ApiStore.createApi(ApiService.class)
                .Guarantee(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(InvestList investList) {
                        L.test("数据==>响应：担保明细响应getStatus" + investList.getStatus());

//                        if (investList.getStatus() == 0) {
                        try {
                            guarantee = investList.getResult();
                            Log.d("ContractFragment", "guarantee:" + guarantee);
                            for (int i = 0; i < guarantee.size(); i++) {
                                guarantee.get(i).setItemType(4);
                            }
                            contractAdapter.setNewData(guarantee);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：担保明细异常" + e.getMessage());
                        JumpUtil.errorHandler(getActivity(), 128, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * @param i
     * @param password
     */
    private void AutoRepetition(int i, String password, int status) {
        InvestList.Result item = contractAdapter.getItem(i);
        Data data = new Data();
        data.id = item.getId();
        data.userId = ConstantUtil.ID;
        data.password = AESUtil.encry(password);
        switch (status) {
            case 1:
                data.status = 0;
                break;
            case 2:
                data.status = 1;
                break;
        }
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test(TAG + "复投接口AutoRepetition==>请求数据：" + s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .AutoRepetition(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReInvests>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReInvests reInvests) {
                        L.test("复投接口AutoRepetition==>返回数据：" + reInvests);
                        popWindow.dismiss();
                        if (!TextUtils.isEmpty(reInvests.getMsg())) {
                            ToastUtil.show(getActivity(), reInvests.getMsg());
                        }
                        if (reInvests.getStatus() == 0) {
                            tempList1.clear();
                            tempList2.clear();
                            pageNo = 1;
                            RewardLog(pageNo, pageSize);
                            InvestList(pageNo, pageSize);
                            ContractRules();
                            RiskWarning();
                            InvestNum();
                            ReInvestReward();
                            InvestReward();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 129, e.getMessage(), false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * @param i
     * @param password
     */
    private void ReturnReInvest(int i, String password) {
        InvestList.Result item = contractAdapter.getItem(i);
        Data data = new Data();
        data.id = item.getId();
        data.userId = ConstantUtil.ID;
        data.password = AESUtil.encry(password);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test(TAG + "复投接口AutoRepetition==>请求数据：" + s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .ReturnReInvest(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReInvests>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReInvests reInvests) {
                        L.test("复投接口AutoRepetition==>返回数据：" + reInvests);
                        popWindow.dismiss();
                        if (!TextUtils.isEmpty(reInvests.getMsg())) {
                            ToastUtil.show(getActivity(), reInvests.getMsg());
                        }
                        if (reInvests.getStatus() == 0) {
                            RewardLog(pageNo, pageSize);
                            InvestList(pageNo, pageSize);
                            ContractRules();
                            RiskWarning();
                            InvestNum();
                            ReInvestReward();
                            InvestReward();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 130, e.getMessage(), false);
                        popWindow.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private int tag = -1;

    private void initHeader() {
        //合约总收益
        number = inflate.findViewById(R.id.number);
        //合约数量
        number1 = inflate.findViewById(R.id.number1);
        //合约收益
        profit = inflate.findViewById(R.id.profit);
        //资产
        et_assets = inflate.findViewById(R.id.et_assets);
        //数量
        et_number = inflate.findViewById(R.id.et_number);
        //布局1
        layout2 = inflate.findViewById(R.id.layout2);
        layout3 = inflate.findViewById(R.id.layout3);
        scr_text2 = inflate.findViewById(R.id.scr_text2);
        //内容
        data = inflate.findViewById(R.id.data);
        //确定
        btn_ok = inflate.findViewById(R.id.btn_ok);
        //合约明细
        btn_hy = inflate.findViewById(R.id.btn_hy);
        //收益明细
        btn_mx = inflate.findViewById(R.id.btn_mx);
        eyeBtn = inflate.findViewById(R.id.eye_openclose_btn);
        //合约明细
        btn_yy = inflate.findViewById(R.id.btn_yy);
        //收益明细
        btn_db = inflate.findViewById(R.id.btn_db);
        transferProfitView = inflate.findViewById(R.id.transfer_profit_ev);
        transferBtn = inflate.findViewById(R.id.transfer_btn);
        profitQutoeTv = inflate.findViewById(R.id.profit_qutoe_tv);

        recyclerViewTitleTv = inflate.findViewById(R.id.recyclerView_title);
        zjhz_ll = inflate.findViewById(R.id.zjhz_ll);
        hzjl_ll = inflate.findViewById(R.id.hzjl_ll);
        kthy_ll = inflate.findViewById(R.id.kthy_ll);


        zjhz_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(getActivity(), TransferActivity.class);
            }
        });
        hzjl_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(getActivity(), TransferRewardRecordActivity.class);
            }
        });

        kthy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(getActivity(), OpenContractActivity.class);
            }
        });

        btn_hy.setSelected(true);



        btn_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewTitleTv.setText(getString(R.string.hy8));
                btn_hy.setSelected(true);
                btn_mx.setSelected(false);
                btn_yy.setSelected(false);
                btn_db.setSelected(false);
//                contractAdapter.setNewData(invest);
                smartRefreshLayout.setEnableLoadMore(true);

                tag = 1;
                pageNo = 1;
                tempList1.clear();
                InvestList(pageNo, pageSize);

                btn_hy.setTextSize(15);
                btn_mx.setTextSize(14);
                btn_yy.setTextSize(14);
                btn_db.setTextSize(14);
            }
        });
        btn_mx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewTitleTv.setText(getString(R.string.hy8));
                btn_hy.setSelected(false);
                btn_mx.setSelected(true);
                btn_yy.setSelected(false);
                btn_db.setSelected(false);
//                contractAdapter.setNewData(rewardList);
                smartRefreshLayout.setEnableLoadMore(true);
                tag = 2;

                pageNo = 1;
                tempList2.clear();
                RewardLog(pageNo, pageSize);
                btn_hy.setTextSize(14);
                btn_mx.setTextSize(15);
                btn_yy.setTextSize(14);
                btn_db.setTextSize(14);
            }
        });
        btn_yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewTitleTv.setText(getString(R.string.hy8));
                btn_hy.setSelected(false);
                btn_mx.setSelected(false);
                btn_yy.setSelected(true);
                btn_db.setSelected(false);
//                contractAdapter.setNewData(appointment);
                smartRefreshLayout.setEnableLoadMore(false);
                tag = 3;
                Appointment();
                btn_hy.setTextSize(14);
                btn_mx.setTextSize(14);
                btn_yy.setTextSize(15);
                btn_db.setTextSize(14);
            }
        });
        btn_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewTitleTv.setText(getString(R.string.hy18));
                btn_hy.setSelected(false);
                btn_mx.setSelected(false);
                btn_yy.setSelected(false);
                btn_db.setSelected(true);
//                contractAdapter.setNewData(guarantee);
                smartRefreshLayout.setEnableLoadMore(false);
                tag = 4;
                Guarantee();
                btn_hy.setTextSize(14);
                btn_mx.setTextSize(14);
                btn_yy.setTextSize(14);
                btn_db.setTextSize(15);
            }
        });

    }

    private void showPop(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.FullScreenDialog);
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



    private void Investment(String password, int vouchApp) {
        Data data = new Data();
        data.userId = ConstantUtil.ID;
        data.qty = et_number.getText().toString();
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
                        flag = 0;
                        popWindow.dismiss();

                        try {
                            btn_ok.setText(R.string.hy5);
                            layout3.setVisibility(View.VISIBLE);
                            scr_text2.setVisibility(View.GONE);
                            text2.setVisibility(View.GONE);
                            text3.setVisibility(View.GONE);
                            text1.setVisibility(View.VISIBLE);
                            text1.setText(getString(R.string.hy2));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        if (withdrawal.getStatus() == 2) {//合约满了，走预约
                            showPop(withdrawal.getMsg());
                        } else if (withdrawal.getStatus() == 3) { //合约满了后，点击是否预约弹框，点击是再调用Investment接口，传vouchApp＝1，服务端status返回3，跳到输入额度界面。
                            Bundle bundle = new Bundle();
                            bundle.putInt("pid", withdrawal.getResult());
                            JumpUtil.overlay(getActivity(), InputAssureActivity.class, bundle);
                            et_number.setText("");

                        } else {
                            DialogUtil.show(getActivity(), withdrawal.getMsg());
                            scr_text2.scrollTo(0, 0);
                            text2.setVisibility(View.GONE);
                            text3.setVisibility(View.GONE);
                            scr_text2.setVisibility(View.GONE);
//                            layout2.setVisibility(View.VISIBLE);
                            layout3.setVisibility(View.VISIBLE);
                            text1.setVisibility(View.VISIBLE);
                            btn_ok.setText(R.string.hy5);
                            et_number.setText("");
                            if (withdrawal.getStatus() == 0) {
                                tempList1.clear();
                                tempList2.clear();
                                pageNo = 1;
                                RewardLog(pageNo, pageSize);
                                InvestList(pageNo, pageSize);
                                ContractRules();
                                RiskWarning();
                                InvestNum();
                                ReInvestReward();
                                InvestReward();

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(getActivity(), 131, e.getMessage(), false);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public static Fragment getInstance() {
        return new ContractFragment();
    }


    public void pause() {
        try {
            transferProfitView.setText("");
            et_number.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int temp = 0;

    @Override
    public void onResume() {
        super.onResume();
        if (Const.REFRESHFLAG == 1) {
            return;
        }
//        tempList1.clear();
//        tempList2.clear();
//        pageNo=1;
//        RewardLog(pageNo,pageSize);
//        InvestList(pageNo,pageSize);

        if (btn_hy != null) {
            btn_hy.performClick();
            temp = 1;
        }

        ContractRules();
        RiskWarning();
        InvestNum();
        ReInvestReward();
        InvestReward();
//        Appointment();
//        Guarantee();
        RecommendReward();
        GetRange(1, 3);

    }

    public void newList() {
        if (contractAdapter != null) {
            if (Const.REFRESHFLAG == 1) {
                return;
            }
//            tag=1;
//            tempList1.clear();
//            tempList2.clear();
//            pageNo=1;
//            InvestList(pageNo,pageSize);
//            RewardLog(pageNo,pageSize);

            if (temp == 0) {
                tag = 1;
                pageNo = 1;
                tempList1.clear();
                InvestList(pageNo, pageSize);
            }


            ContractRules();
            RiskWarning();
            InvestNum();
            ReInvestReward();
            InvestReward();


            btn_hy.setSelected(true);
            btn_mx.setSelected(false);
            btn_yy.setSelected(false);
            btn_db.setSelected(false);
        }
    }

    @SuppressLint("WrongConstant")
    private void showPop(int type, int type2, int position) {
        View inflate = null;
        switch (type) {
            case 1:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop3, null);
                bz(inflate);
                break;
            case 2:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
                initB(inflate, type2, position);
                break;
            case 3:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop3, null);
                num(inflate);
                break;
            case 4:
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop23, null);
                if (!ClickUtils.isFastClick()) {
                    return;
                }

                break;

        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
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

    private void num(View inflate) {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i < 51; i++) {
            list.add(String.valueOf(i));
        }
        final WheelPicker wp = (WheelPicker) inflate.findViewById(R.id.wp);
        wp.setData(list);
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                int currentItemPosition = wp.getCurrentItemPosition();
                et_number.setText(list.get(currentItemPosition));
            }
        });
    }

    private List<InvestLevelList.ResultBean> investLevels;

    @Override
    public void onRightCursorChanged(int location, String a) {
//        L.test("3mRightCursorIndex============>>>" + location);
//        long  eth = 0;
//        if (location == 1) {
//            eth = result1.get(0).getQty();
//        } else if (location == 2) {
//            eth = result1.get(1).getQty();;
//        } else if (location == 3) {
//            eth = result1.get(2).getQty();;
//        } else if (location == 4) {
//            eth = result1.get(3).getQty();;
//        } else if (location == 5) {
//            eth = result1.get(4).getQty();;
//        } else if (location == 6) {
//            eth = result1.get(5).getQty();;
//        }
//        BigDecimal ethDec = new BigDecimal(eth);
//        inputNumEv.setText(String.valueOf(ethDec));

    }

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
                        JumpUtil.errorHandler(getActivity(), 123, e.getMessage(), false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private int flag = 0;
    private String pwd;

    /**
     * 支付密码
     *
     * @param inflate
     * @param type
     */
    private void initB(View inflate, final int type, final int position) {
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
        name.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
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
                    ToastUtil.show(getActivity(), getActivity().getString(R.string.d20));
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
                        case 2:
                            if (!ClickUtils.isFastClick()) {
                                return;
                            }
                            InvestList.Result item = contractAdapter.getItem(position);
                            if (item.getStatus() == 1 || item.getStatus() == 2) {
                                //复投,取消复投
                                AutoRepetition(position, name.getText().toString(), item.getStatus());
                            } else {//已回款
                                ReturnReInvest(position, name.getText().toString());
                            }

                            break;
                        case 3:
                            if (!ClickUtils.isFastClick()) {
                                return;
                            }
                            pwd = name.getText().toString();
                            //划转
                            KitingRecommendReward(KitingCointype);
                            break;
                    }

                }
            }
        });
    }


    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgcolor;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }

    private int KitingCointype = 0;//划转币种

    /**
     * 币种
     *
     * @param inflate
     */
    private void bz(View inflate) {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("ETH");
        list.add("NCE");
        final WheelPicker wp = (WheelPicker) inflate.findViewById(R.id.wp);
        wp.setCyclic(false);
        wp.setData(list);
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                int currentItemPosition = wp.getCurrentItemPosition();
//                bz_tv.setText(list.get(currentItemPosition));
                if (currentItemPosition == 0) {
                    KitingCointype = 1;
                } else {
                    KitingCointype = 3;
                }
                transferProfitView.setText("");

            }
        });
    }


    @Override
    public void onAppointItemChildClick(int i) {
        InvestList.Result item = contractAdapter.getItem(i);
        if (item.getStatusMsg().equals("进行中")) {
            Bundle bundle = new Bundle();
            bundle.putInt("pid", item.getId());
            JumpUtil.overlay(getActivity(), InputAssureActivity.class, bundle);
            Const.REFRESHFLAG = 1;
        }
    }

    @Override
    public void onItemReinvestClick(View view, int position) {
        switch (view.getId()) {
            case R.id.btn_ft:
                if (Const.IS_FROZEN) {
                    DialogUtil.showFrozen(getActivity(), Const.FROZEN_CONTENT);
                    return;
                }

                showPop(2, 2, position);
                break;

        }
    }

    @Override
    public void onItemShowClick(int position) {
        if (appointment.get(position).isOpen()) {

            appointment.get(position).setOpen(false);

        } else {

            appointment.get(position).setOpen(true);


        }
        contractAdapter.setNewData(appointment);
    }
    public class Data1 {
        String userid;
        String password;
    }


    public class Data {
        String userId;
        String password;
        String qty;
        int isReInverst;
        int id;
        int status;
        int vouchApp;
        int coinType;

    }

    public class TransfData {
        String userId;
        String paymentPassword;
        String qty;
        int coinType;

    }

    @Override
    public void onPause() {
        super.onPause();
        L.test("========>>>onPause");
    }

    @Override
    public void onDestroyView() {
        if (countDownCenter != null) {
            countDownCenter.deleteObservers();
        }
        super.onDestroyView();
    }
}
