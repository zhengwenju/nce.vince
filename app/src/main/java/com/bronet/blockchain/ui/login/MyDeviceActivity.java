package com.bronet.blockchain.ui.login;

import android.annotation.SuppressLint;
import android.os.Build;
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
import com.bronet.blockchain.adapter.MyDeviceAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.DeviceModel;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

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
 * 我的设备
 * Created by 18514 on 2019/6/25.
 */

public class MyDeviceActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;


    @BindView(R.id.btn_register)
    TextView btnRegister;


    @BindView(R.id.title_tv)
    TextView titleTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_device;
    }

    private List<DeviceModel.ResultBean> result;
    private MyDeviceAdapter mydeviceAdapter;

    private int isSelfDevice=0;
    @Override
    protected void initView() {
        titleTv.setText(R.string.invest37);
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
        LinearLayoutManager gridLayoutManager  = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        mydeviceAdapter = new MyDeviceAdapter(R.layout.item_my_device, result);
        recyclerView.setAdapter(mydeviceAdapter);
    }
    @Override
    protected void initData() {
        getDeviceList();
    }

    @Override
    protected void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mydeviceAdapter.OnItemEntrustClickListener(new MyDeviceAdapter.OnItemEntrustClickListener() {
            @Override
            public void onItemDeleteDeviceClick(View view, int position) {
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(activity, LoginActivity.class);
                    return;
                }
                DeviceModel.ResultBean item = mydeviceAdapter.getItem(position);
                int id = item.getId();

                StringBuffer deviceSb = new StringBuffer();
                deviceSb.append(Build.MANUFACTURER+" ");
                deviceSb.append(Build.MODEL);

                if(item.getOsModel().contains(deviceSb.toString())){ //删除自己设备,加提醒
                    showPopConfirm(id);
                    isSelfDevice=1;
                }else {
                    showPop(ISetDeviceType.deletetype,id);
                    isSelfDevice=0;
                }


            }

            @Override
            public void onItemSetDeviceClick(View view, int position) {
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(activity, LoginActivity.class);
                    return;
                }
                DeviceModel.ResultBean item = mydeviceAdapter.getItem(position);
                int id = item.getId();
                showPop(ISetDeviceType.settype,id);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(ISetDeviceType.settype,0);
            }
        });

    }

    private void getDeviceList() {
        ApiStore.createApi(ApiService.class)
                .deviceList(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeviceModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull DeviceModel tranEntrusts) {
                        try {
                            mydeviceAdapter.setNewData(tranEntrusts.getResult());
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
                        JumpUtil.errorHandler(MyDeviceActivity.this,143,e.getMessage(),true);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }


    //登记设备
    private void setDefault(String pwd) {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
        String deviceid = Util.getAndroidId(this);
        String osModel = Util.getOsModel();
        Data1 data = new Data1();
        data.userid=ConstantUtil.ID;
        data.password= AESUtil.encry(pwd);
        data.deviceId=deviceid;
        data.osModel=osModel;
        data.appVersion =Util.getAppVersion(this);

        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data.ipAddress = Const.outIpAddressArray[1];
        }else {
            data.ipAddress = " ";
        }
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data.outIp = Const.outIpAddressArray[0];
        }else {
            data.outIp = " ";
        }
        String netType = Util.getNetType(this);
        data.netType=netType;


        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .setDefault(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {
                        ToastUtil.show(MyDeviceActivity.this, tradeNce.getMsg());
                        if(tradeNce.getStatus()==0) {
                            getDeviceList();
                        }
                        if(popWindow!=null){
                            popWindow.dismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(MyDeviceActivity.this,144,e.getMessage(),true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //删除设备
    private void deviceDelete(int id,String pwd) {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
        Data2 data = new Data2();
        data.id=id;
        data.userid=ConstantUtil.ID;
        data.password=AESUtil.encry(pwd);
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .deviceDelete(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Model10 tradeNce) {
                        if(!TextUtils.isEmpty(tradeNce.getMsg())) {
                            ToastUtil.show(MyDeviceActivity.this, tradeNce.getMsg());
                        }
//                        if(tradeNce.getStatus()==0) {
//                            getDeviceList();
//                        }
                        if (isSelfDevice==1) {
                            finish();
                            JumpUtil.overlay(activity, LoginActivity.class);
                            return;
                        }
                        getDeviceList();



                        if(popWindow!=null){
                            popWindow.dismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(MyDeviceActivity.this,145,e.getMessage(),true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class Data {
        int userid;
        String oldPwd;
        String newPwd;
        String rePwd;

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
    private void showPop(int type, int id) {

//        if(Const.IS_FROZEN){
//            DialogUtil.showFrozen(this,Const.FROZEN_CONTENT);
//            return;
//        }

        View inflate = null;
        inflate = LayoutInflater.from(this).inflate(R.layout.pop4, null);
        initB(inflate,type,id);
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
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow!=null) {
                    popWindow.dismiss();
                }
                showPop(ISetDeviceType.deletetype,id);
            }

        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }



    private void initB(View inflate,final int type,final int id) {
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

                if (TextUtils.isEmpty(name.getText().toString())){
                    ToastUtil.show(MyDeviceActivity.this,getString(R.string.d20));
                }else{
                    if(!ClickUtils.isFastClick()){
                        return;
                    }
                    if(type== ISetDeviceType.deletetype){
                        deviceDelete(id,name.getText().toString());
                    }
                    if(type== ISetDeviceType.settype){
                        setDefault(name.getText().toString());
                    }
                }
            }


        });
    }


    public class Data2 {
        int id;
        String userid;
        String password;
    }
    public class Data1 {
        String userid;
        String password;
        String deviceId;
        String osModel;
        String appVersion;

        String outIp;
        String ipAddress;
        String netType;
    }

    public interface ISetDeviceType {
        int deletetype=1;
        int settype=2;
    }
}
