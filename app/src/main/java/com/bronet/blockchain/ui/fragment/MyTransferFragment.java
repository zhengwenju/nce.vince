package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.TranAdapterRecord;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Confirm;
import com.bronet.blockchain.data.MyTransfer;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.DialogUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MyTransferFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<MyTransfer.ResultBean> result;
    private TranAdapterRecord identiAdapter;
    private PopupWindow popWindow;
    private MyTransfer.ResultBean bean;

    private int id;
    private double nceQty;
    private double ethQty;
    public double nceEthRange;

    @BindView(R.id.loding)
    ProgressBar loding;
    private TransfData data;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        TransferList();
    }


    @Override
    protected void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        identiAdapter = new TranAdapterRecord(R.layout.item_tran_record, result);
        mRecyclerView.setAdapter(identiAdapter);
        identiAdapter.OnItemChildClickListener(new TranAdapterRecord.OnItemChildClickListener() {
            @Override
            public void onItemAcceptClick(View view, int position) { //接受
                bean = identiAdapter.getItem(position);
                if(bean!=null) {
                    id = bean.getId();
                    ethQty = bean.getFinalPrice();
                    nceQty = bean.getNceQty();
                }
                showPop(3);
            }

            @Override
            public void onItemRefuseClick(View view, int position) {//拒绝
                bean = identiAdapter.getItem(position);
                if(bean!=null) {
                    id = bean.getId();
                    ethQty = bean.getFinalPrice();
                    nceQty = bean.getNceQty();
                }
                if(Const.IS_FROZEN){
                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
                    return;
                }
                showPop(1);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mytransfer;
    }


    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate = null;
        switch (type) {
            case 1:
                //支付密码
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
                fail(inflate);
                break;
            case 2:
                //支付密码
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop4, null);
                initB(inflate);
                break;
            case 3:
                //弹框提示
                inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop11, null);
                priceTip(inflate);
                break;
        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
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


    private void initB(View inflate) {
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
                Confirm(name.getText().toString(),2);
                popWindow.dismiss();
            }
        });
    }
    private void Confirm(String password,int status) {
        final TransfData data = new TransfData();
        data.id = id;
        data.userid = ConstantUtil.ID;
        data.ethQty = ethQty;
        data.nceQty = nceQty;
        data.nceEhtRange = nceEthRange;
        data.password = AESUtil.encry(password);
        data.status = status;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .Confirm(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Confirm>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Confirm confirm) {
                        L.test("confirm==============>>>"+confirm);
                        if(!TextUtils.isEmpty(confirm.getMsg())) {
                            ToastUtil.show(getActivity(), confirm.getMsg());
                        }
                        if (confirm.getStatus()==0) {
                            TransferList();
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
    private void fail(View inflate) {
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
                Confirm(name.getText().toString(),1);
                popWindow.dismiss();
            }
        });
    }

    private void priceTip(View inflate) {
        TextView textView = inflate.findViewById(R.id.content);
        textView.setText("您确定接受" + nceQty + "NCE额度?");
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                if(Const.IS_FROZEN){
                    DialogUtil.showFrozen(getActivity(),Const.FROZEN_CONTENT);
                    return;
                }
                showPop(2);
            }

        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgcolor;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }
    public class TransfData {
        int id;
        String password;
        String userid;
        double nceQty;
        double ethQty;
        double nceEhtRange;
        int status;

    }

    private void TransferList() {
        ApiStore.createApi(ApiService.class )
                .TransferList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyTransfer>() {
                    @Override

                    public void onSubscribe(@NonNull Disposable d) {
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull MyTransfer myTransfer) {
                        try {
                            nceEthRange = Double.valueOf(myTransfer.getMsg());
                            result = myTransfer.getResult();
                            if (myTransfer.getStatus() == 0) {
                                identiAdapter.setNewData(result);
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        L.test("数据==>响应：合约明细异常" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loding.setVisibility(View.GONE);
                    }
                });
    }

}
