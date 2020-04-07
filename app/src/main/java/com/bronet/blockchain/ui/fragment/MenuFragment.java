package com.bronet.blockchain.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Attendance;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.UserInfo;
import com.bronet.blockchain.fix.AES;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.ui.login.MutualInfoActivity;
import com.bronet.blockchain.ui.login.MyDeviceActivity;
import com.bronet.blockchain.ui.login.PwdManagerActivity;
import com.bronet.blockchain.ui.login.WithMoneyAddressActivity;
import com.bronet.blockchain.ui.my.CreditActivity;
import com.bronet.blockchain.ui.my.FeedbackActivity;
import com.bronet.blockchain.ui.my.InvitationCodeActivity;
import com.bronet.blockchain.ui.my.MyDataActivity;
import com.bronet.blockchain.ui.my.SetActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yc on 2018/11/26.
 */

public class MenuFragment extends BaseFragment {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.vip)
    ImageView vip;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.unread_iv)
    TextView unread_iv;


    String inviteCode;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        L.test("MenuFragment onResume===>>");
        super.onResume();

        SharePreData sharePreData = new SharePreData();
        ConstantUtil.ID= AES.decrypt(sharePreData.getString(Const.SP_ID));

        if(!TextUtils.isEmpty(ConstantUtil.ID)) {
            ConstantUtil.ID = AESUtil.encry(ConstantUtil.ID);
        }

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
                                                .apply(new RequestOptions()
                                                        .error(R.mipmap.dicon_37).circleCrop())
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



    @Override
    protected int setLayoutId() {
        return R.layout.activity_menu;
    }


    @OnClick({R.id.iv1, R.id.tv1, R.id.iv2, R.id.tv2, R.id.iv3, R.id.tv3, R.id.iv5, R.id.tv5, R.id.iv7, R.id.tv7,R.id.iv8, R.id.tv8,R.id.btn_sign_in,R.id.tv9,R.id.tv10,R.id.tv11,R.id.tv12})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv1:
            case R.id.tv1:
                //会员资料
                JumpUtil.overlay(getActivity(),MyDataActivity.class);
                break;
            case R.id.iv2:
            case R.id.tv2:
                //邀请码
                Bundle bundle = new Bundle();
                bundle.putString("code",inviteCode);
                JumpUtil.overlay(getActivity(),InvitationCodeActivity.class,bundle);
                break;
            case R.id.iv3:
            case R.id.tv3:
                //设置
                JumpUtil.overlay(getActivity(),SetActivity.class);
                break;
            case R.id.iv5:
            case R.id.tv5:
                //信用评级
                JumpUtil.overlay(getActivity(),CreditActivity.class);
                break;
            case R.id.iv7:
            case R.id.tv7:
                //退出登录
                ConstantUtil.ID="";
                JumpUtil.overlay(getActivity(), LoginActivity.class);
                MainActivity activity = (MainActivity) getActivity();
                ConstantUtil.TYPE=true;
                activity.finish();
                //帐号管理
                break;
            case R.id.tv8:
                //留言反馈
                JumpUtil.overlay(getActivity(), FeedbackActivity.class);
                break;
            case R.id.btn_sign_in:
                attendance();
                break;
            case R.id.tv9:
                //密码管理
                JumpUtil.overlay(getActivity(), PwdManagerActivity.class);
                break;
            case R.id.tv10:
                JumpUtil.overlay(getActivity(), WithMoneyAddressActivity.class);
                break;
            case R.id.tv11:
                JumpUtil.overlay(getActivity(), MyDeviceActivity.class);
                break;
            case R.id.tv12:
                JumpUtil.overlay(getActivity(), MutualInfoActivity.class);
                break;
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


}
