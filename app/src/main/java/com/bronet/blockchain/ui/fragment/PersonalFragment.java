package com.bronet.blockchain.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.ContactChooseAdapter;
import com.bronet.blockchain.adapter.ContractAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Attendance;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.data.ContractRules;
import com.bronet.blockchain.data.InvestLevelList;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.data.Model1;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.MutualModelList;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.ReInvests;
import com.bronet.blockchain.data.UserInfo;
import com.bronet.blockchain.data.Withdrawal;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.MyMiningListActivity;
import com.bronet.blockchain.ui.MyNodeListActivity;
import com.bronet.blockchain.ui.NativeAdMobActivity;
import com.bronet.blockchain.ui.SettingActivity;
import com.bronet.blockchain.ui.game.GameAccoutActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.ui.login.MutualInfoActivity;
import com.bronet.blockchain.ui.login.MyDeviceActivity;
import com.bronet.blockchain.ui.login.WithMoneyAddressActivity;
import com.bronet.blockchain.ui.my.CreditActivity;
import com.bronet.blockchain.ui.my.FeedbackActivity;
import com.bronet.blockchain.ui.my.InputAssureActivity;
import com.bronet.blockchain.ui.my.InvitationCodeActivity;
import com.bronet.blockchain.ui.my.MyAssetsActivity;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.util.Const;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.itheima.wheelpicker.WheelPicker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
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
 * 个人中心
 * Created by 18514 on 2019/6/27.
 */

public class PersonalFragment extends BaseFragment {

    @BindView(R.id.vip)
    ImageView vip;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.assets_tv)
    RelativeLayout mAssetsTv;
    @BindView(R.id.myNode)
    LinearLayout myNode;
    @BindView(R.id.ExistApp)
    RelativeLayout ExistApp;
    @BindView(R.id.unread_iv)
    TextView unread_iv;
    @BindView(R.id.unread_iv1) //留言反馈
    TextView unread_iv1;



    String inviteCode;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_personal;
    }

    public static Fragment getInstance() {
        return new PersonalFragment();
    }

    @Override
    protected void initEvent() {


    }

    @OnClick({R.id.code_ll, R.id.credit_ll, R.id.feedback_ll, R.id.mydevice_ll, R.id.withmoney_ll,R.id.setting,R.id.my_qiandao_tv,R.id.assets_tv,R.id.myNode,R.id.ExistApp,R.id.myMining})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.code_ll:
                Bundle bundle = new Bundle();
                bundle.putString("code",inviteCode);
                JumpUtil.overlay(getActivity(), InvitationCodeActivity.class,bundle);
                break;
            case R.id.credit_ll:
                //信用评级
                JumpUtil.overlay(getActivity(), CreditActivity.class);
                break;
            case R.id.feedback_ll:
                JumpUtil.overlay(getActivity(), FeedbackActivity.class);
                break;
            case R.id.mydevice_ll:
                JumpUtil.overlay(getActivity(), MyDeviceActivity.class);
                break;
            case R.id.withmoney_ll:
                JumpUtil.overlay(getActivity(), WithMoneyAddressActivity.class);
                break;
            case R.id.setting:
                JumpUtil.overlay(getActivity(), SettingActivity.class);
                break;
            case R.id.my_qiandao_tv:
                attendance();
                break;
            case R.id.assets_tv:
                JumpUtil.overlay(getActivity(), MyAssetsActivity.class);
                break;
            case R.id.myNode:
                JumpUtil.overlay(getActivity(), MyNodeListActivity.class);
                break;
            case R.id.ExistApp:
                JumpUtil.overlay(getActivity(), MutualInfoActivity.class);
                break;

            case R.id.myMining:
                JumpUtil.overlay(getActivity(), MyMiningListActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        info();
        super.onResume();
//        if(Const.mutualHelpCount>0){
//            unread_iv.setVisibility(View.VISIBLE);
//            unread_iv.setText(String.valueOf(Const.mutualHelpCount));
//        }else {
//            unread_iv.setVisibility(View.GONE);
//        }
//        if (Const.feedBackCount>0){
//            unread_iv1.setVisibility(View.VISIBLE);
//            unread_iv1.setText(String.valueOf(Const.feedBackCount));
//        }else {
//            unread_iv1.setVisibility(View.GONE);
//        }
        getExistAppCount();
    }


    public void setUnreadCount(int feedbackUnreadCount,int mutualInfoUnreadCount){
        if(unread_iv!=null) {
            if (feedbackUnreadCount > 0) {
                unread_iv.setVisibility(View.VISIBLE);
                unread_iv.setText(String.valueOf(feedbackUnreadCount));
            } else {
                unread_iv.setVisibility(View.GONE);
            }
            if (mutualInfoUnreadCount > 0) {
                unread_iv1.setVisibility(View.VISIBLE);
                unread_iv1.setText(String.valueOf(mutualInfoUnreadCount));
            } else {
                unread_iv1.setVisibility(View.GONE);
            }
        }
    }
    @Override
    protected void initData() {
//        ApiStore.createApi(ApiService.class)
//                .ExistApp(ConstantUtil.ID)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Model10>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//                    }
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull Model10 model) {
//                        try {
//                            if(model.getStatus()==0){
//                                Const.ExistApp=Integer.valueOf(model.getResult());
//                            }else {
//                                Const.ExistApp=-1;
//                            }
//                            if(Const.ExistApp>0){
//                                unread_iv.setVisibility(View.VISIBLE);
//                                unread_iv.setText(String.valueOf(Const.ExistApp));
//                            }else {
//                                unread_iv.setVisibility(View.GONE);
//                            }
//                            L.test("Const.ExistApp==========>>"+Const.ExistApp);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//        ApiStore.createApi(ApiService.class)
//                .ExistAnswer(ConstantUtil.ID)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Model10>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//                    }
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull Model10 model) {
//                        if (model.getStatus()==0){
//                            unread_iv1.setVisibility(View.VISIBLE);
//                            unread_iv1.setText(model.getResult());
//                    }else {
//                            unread_iv1.setVisibility(View.GONE);
//                        /*try {
//                            if(model.getStatus()==0){
//                                Const.ExistApp=Integer.valueOf(model.getResult());
//                            }else {
//                                Const.ExistApp=-1;
//                            }
//                            if(Const.ExistApp>0){
//                                unread_iv2.setVisibility(View.VISIBLE);
//                                unread_iv2.setText(String.valueOf(Const.ExistApp));
//                            }else {
//                                unread_iv2.setVisibility(View.GONE);
//                            }
//                            L.test("Const.ExistApp==========>>"+Const.ExistApp);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }*/
//                        }
//                    }
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                });

    }

    private void info() {
        if (!TextUtils.isEmpty(ConstantUtil.ID)) {
            ApiStore.createApi(ApiService.class)
                    .info(ConstantUtil.ID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserInfo>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull UserInfo userInfo) {
                            try {
                                if (userInfo.getStatus() == 0) {
                                    String newUserName = userInfo.getResult().getUsername();
                                    if (name != null) {
                                        name.setText(newUserName);//+userInfo.getResult().getId()
                                    }
                                    if (!TextUtils.isEmpty(userInfo.getResult().getAvatar())) {
                                        ConstantUtil.Avatar = userInfo.getResult().getAvatar();
                                        ConstantUtil.USERNAME = userInfo.getResult().getUsername();
                                        SharePreData sp = new SharePreData();
                                        Glide.with(getActivity()).load(userInfo.getResult().getAvatar())
                                                .apply(new RequestOptions().error(R.mipmap.dicon_37).circleCrop())
                                                .into(iv);
                                    }
                                    inviteCode = userInfo.getResult().getInviteCode();

                                    switch (userInfo.getResult().getStarLevel()) {
                                        case 0:
                                            vip.setBackgroundResource(R.mipmap.vip0);
                                            break;
                                        case 1:
                                            vip.setBackgroundResource(R.mipmap.vip1);
                                            break;
                                        case 2:
                                            vip.setBackgroundResource(R.mipmap.vip2);
                                            break;
                                        case 3:
                                            vip.setBackgroundResource(R.mipmap.vip3);
                                            break;
                                        case 4:
                                            vip.setBackgroundResource(R.mipmap.vip4);
                                            break;
                                        case 5:
                                            vip.setBackgroundResource(R.mipmap.vip5);
                                            break;
                                        case 6:
                                            vip.setBackgroundResource(R.mipmap.vip6);
                                            break;
                                    }
                                }
                            }catch (Exception ex){
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
    }

    private void attendance() {
        ApiStore.createApi(ApiService.class)
                .attendance(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Attendance>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Attendance attendance) {
                        try {
                            ToastUtil.show(getActivity(), attendance.getMsg());
                        }catch (Exception ex){
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
    @Override
    protected void initView() {

    }
    public int mutualHelpCount = 0;
    public int feedBackCount = 0;

    //获取留言反馈的未读数
    private void getExistAppCount() {
        ApiStore.createApi(ApiService.class)
                .ExistAnswer(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Model10 model) {
                        try {
                            if (model.getStatus() == 0) {
                                feedBackCount = Integer.valueOf(model.getResult());
                                unread_iv1.setVisibility(View.VISIBLE);
                                unread_iv1.setText(String.valueOf(feedBackCount));
                            } else {
                                feedBackCount = 0;
                                unread_iv1.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        getExistAnswerCount(feedBackCount);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    //获取互助申请的未读数
    private void getExistAnswerCount(int unReadCount) {
        ApiStore.createApi(ApiService.class)
                .ExistApp(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Model10>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Model10 model) {
                        try {
                            if (model.getStatus() == 0) {
                                mutualHelpCount = Integer.valueOf(model.getResult());
                                unread_iv.setVisibility(View.VISIBLE);
                                unread_iv.setText(String.valueOf(mutualHelpCount));
                            } else {
                                mutualHelpCount = 0;
                                unread_iv.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
