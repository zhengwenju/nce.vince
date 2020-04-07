package com.bronet.blockchain.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.HomeAdapter;
import com.bronet.blockchain.adapter.HomeTitleAdapter;
import com.bronet.blockchain.adapter.MyFragmentPagerAdapter;
import com.bronet.blockchain.adapter.ShowFragmentPagerAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Article;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.Banner;
import com.bronet.blockchain.data.GetH24List;
import com.bronet.blockchain.data.InvestNum;
import com.bronet.blockchain.data.UserInfo;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.ApiStore2;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.BuildIngActivity;
import com.bronet.blockchain.ui.CenterActivity;
import com.bronet.blockchain.ui.MiningMachineActivity;
import com.bronet.blockchain.ui.MyNodeListActivity;
import com.bronet.blockchain.ui.NodeActivity;
import com.bronet.blockchain.ui.game.GameAccoutActivity;
import com.bronet.blockchain.ui.MainActivity;
import com.bronet.blockchain.ui.PrivateAccountActivity;
import com.bronet.blockchain.ui.help.HelpActivity;
import com.bronet.blockchain.ui.homefragment.CoinListFragment;
import com.bronet.blockchain.ui.homefragment.DealFragment;
import com.bronet.blockchain.ui.homefragment.GainDataFragment;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.ui.my.CreditActivity;
import com.bronet.blockchain.ui.scancode.CommonScanActivity;
import com.bronet.blockchain.ui.scancode.utils.Constant;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.MyViewPager;
import com.bronet.blockchain.view.FixedSpeedScroller;
import com.bronet.blockchain.view.MyRecyclerView;
import com.sunfusheng.marqueeview.MarqueeView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页
 * Created by 18514 on 2019/6/27.
 */

public class HomeFragment extends BaseFragment {
    static MainActivity mainActivity;
    @BindView(R.id.title_vp)
    ViewPager titleVp;
    @BindView(R.id.data)
    MarqueeView data;
    @BindView(R.id.btn_contract)
    LinearLayout btnContract;
    @BindView(R.id.btn_transaction)
    LinearLayout btnTransaction;
    @BindView(R.id.Purchase)
    LinearLayout Purchase;
    @BindView(R.id.btn_credit)
    LinearLayout btnCredit;
    @BindView(R.id.btn_game)
    LinearLayout btnGame;
    @BindView(R.id.btn_auction)
    LinearLayout btnAuction;
    @BindView(R.id.btn_more)
    LinearLayout btnMore;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ArrayList<GetH24List.Data> list = new ArrayList<>();
    Article.Result[] result;
    List<String> messages = new ArrayList<>();


    @BindView(R.id.btn_scan)
    ImageView btnScan;
  /*  //涨幅
    ArrayList<GetH24List.Data> h24List;
    //新币
    ArrayList<GetH24List.Data> coinList;
    //成交
    ArrayList<GetH24List.Data> dealList;*/
    @BindView(R.id.zf)
    TextView zf;
    @BindView(R.id.cj)
    TextView cj;
    @BindView(R.id.xb)
    TextView xb;
    Unbinder unbinder;

    Unbinder unbinder1;

    @BindView(R.id.tiao)
    ImageView tiao;
    @BindView(R.id.note)
    LinearLayout mNote;
    @BindView(R.id.mine)
    LinearLayout mMine;
    @BindView(R.id.view01)
    View view01;
    @BindView(R.id.view02)
    View view02;
    @BindView(R.id.view03)
    View view03;
    @BindView(R.id.total_assets01)
    TextView total_assets01;
    @BindView(R.id.total_assets02)
    TextView total_assets02;
    @BindView(R.id.total_assets03)
    TextView total_assets03;
    @BindView(R.id.dollar01_tv)
    TextView dollar01_tv;
    @BindView(R.id.dollar02_tv)
    TextView dollar02_tv;
    @BindView(R.id.dollar03_tv)
    TextView dollar03_tv;
    String code= "3";

    private List<Banner.Result> titleList = new ArrayList<>();
    private HomeTitleAdapter homeTitleAdapter;
    private boolean isStop = false;//线程是否停止
    @Override
    protected void initEvent() {

        data.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                //跑马灯参数
                int id = result[position].getId();
            }
        });
        mNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(getActivity(), NodeActivity.class);
            }
        });

        mMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.overlay(getActivity(), MiningMachineActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

        //跑马灯
        Article();
        //设置轮播
        setTitleVp();

        initFragment();

        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
        //合约数量
        InvestNum();
        //委托买入数量
        DelegateBuy();
        //委托卖出数量
        DelegateSale();




        tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PrivateAccountActivity.class);
                startActivity(intent);
            }
        });

    }







    private void initFragment() {
        List<Fragment> list = new ArrayList<>();

        list.add(new GainDataFragment());
        list.add(new DealFragment());
        list.add(new CoinListFragment());
        ShowFragmentPagerAdapter showFragmentPagerAdapter = new ShowFragmentPagerAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(showFragmentPagerAdapter);
        zf.setSelected(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position==0){
                    //涨幅榜

                    zf.setSelected(true);
                    view01.setVisibility(View.VISIBLE);
                    cj.setSelected(false);
                    cj.setTextColor(Color.WHITE);
                    view02.setVisibility(View.GONE);
                    xb.setSelected(false);
                    xb.setTextColor(Color.WHITE);
                    view03.setVisibility(View.GONE);
                    zf.setTextSize(15);
                    zf.setTextColor(getResources().getColor(R.color.blue));
                    cj.setTextSize(13);
                    cj.setTextColor(getResources().getColor(R.color.white6));
                    xb.setTextSize(13);
                    xb.setTextColor(getResources().getColor(R.color.white6));
                }else if (position==1){
                    //成交榜
                    zf.setSelected(false);
                    zf.setTextColor(Color.WHITE);
                    view01.setVisibility(View.GONE);
                    cj.setSelected(true);
                    view02.setVisibility(View.VISIBLE);
                    xb.setSelected(false);
                    xb.setTextColor(Color.WHITE);
                    view03.setVisibility(View.GONE);
                    zf.setTextSize(13);
                    zf.setTextColor(getResources().getColor(R.color.white6));
                    cj.setTextSize(15);
                    cj.setTextColor(getResources().getColor(R.color.blue));
                    xb.setTextSize(13);
                    xb.setTextColor(getResources().getColor(R.color.white6));
                }else if (position==2){
                    //新币榜
                    zf.setSelected(false);
                    zf.setTextColor(Color.WHITE);
                    view01.setVisibility(View.GONE);
                    cj.setSelected(false);
                    cj.setTextColor(Color.WHITE);
                    view02.setVisibility(View.GONE);
                    xb.setSelected(true);
                    view03.setVisibility(View.VISIBLE);
                    zf.setTextSize(13);
                    zf.setTextColor(getResources().getColor(R.color.white6));
                    cj.setTextSize(13);
                    cj.setTextColor(getResources().getColor(R.color.white6));
                    xb.setTextSize(15);
                    xb.setTextColor(getResources().getColor(R.color.blue));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

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
                                    if (!TextUtils.isEmpty(userInfo.getResult().getAvatar())) {
                                        ConstantUtil.Avatar = userInfo.getResult().getAvatar();
                                        SharePreData sp = new SharePreData();
                                    }
                                    if (!TextUtils.isEmpty(userInfo.getResult().getUsername())) {
                                        ConstantUtil.USERNAME = userInfo.getResult().getUsername();
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

    /**
     * 轮播图
     */
    private void setTitleVp() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(titleVp.getContext(),
                    new AccelerateInterpolator());
            field.set(titleVp, scroller);
            scroller.setmDuration(400);
        } catch (Exception e) {
        }
        Banner();
        autoPlayView();
    }

    private void autoPlayView() {
        //自动播放图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (titleVp != null) {
                                titleVp.setCurrentItem(titleVp.getCurrentItem() + 1);
                            }
                        }
                    });
                    SystemClock.sleep(5400);
                }
            }
        }).start();
    }


    private void Article() {
        ApiStore.createApi(ApiService.class)
                .Article()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Article article) {
                        if (article.getStatus() == 0) {
                            result = article.getResult();
                            for (int i = 0; i < result.length; i++) {
                                messages.add(result[i].getTitle());
                            }
                            data.startWithList(messages);
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

    private void Banner() {
        ApiStore.createApi(ApiService.class)
                .Banner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Banner>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Banner banner) {
                        try {
                            if (banner.getStatus() == 0) {
                                Banner.Result[] result = banner.getResult();
                                for (int i = 0; i < result.length; i++) {
                                    titleList.add(result[i]);
                                }
                                homeTitleAdapter = new HomeTitleAdapter(mainActivity, titleList, titleVp);
                                titleVp.setAdapter(homeTitleAdapter);
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



    //合约数量
    private void InvestNum() {
        ApiStore.createApi(ApiService.class)
                .InvestNum2(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestNum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull InvestNum investNum) {

                        if (investNum.getStatus() == 0) {
                            String qty = investNum.getResult().getQty();
                            String usd = investNum.getResult().getUsd();
                            total_assets01.setText(qty);
                            dollar01_tv.setText("$"+usd);
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
    //委托买入
    private void DelegateBuy() {
        ApiStore.createApi(ApiService.class)
                .DelegateBuy(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestNum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull InvestNum investNum) {

                        if (investNum.getStatus() == 0) {

                            String qty = investNum.getResult().getQty();
                            String usd = investNum.getResult().getUsd();
                            total_assets02.setText(qty);
                            dollar02_tv.setText("$"+usd);
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
    //委托卖出
    private void DelegateSale() {
        ApiStore.createApi(ApiService.class)
                .DelegateSale(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvestNum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull InvestNum investNum) {
                        if (investNum.getStatus() == 0) {
                            String qty = investNum.getResult().getQty();
                            String usd = investNum.getResult().getUsd();
                            total_assets03.setText(qty);
                            dollar03_tv.setText("$"+usd);
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
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlayClearTop(getActivity(), LoginActivity.class);
            return;
        }
       /* rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new HomeAdapter(R.layout.item_home, list);
        rv.setAdapter(homeAdapter);*/

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    public static Fragment getInstance(MainActivity mainActivity) {
        HomeFragment.mainActivity = mainActivity;
        return new HomeFragment();
    }


    @OnClick({R.id.btn_scan, R.id.btn_contract, R.id.btn_transaction, R.id.btn_credit, R.id.btn_game, R.id.btn_auction,
            R.id.btn_more, R.id.zf, R.id.cj, R.id.xb,R.id.Purchase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_contract:
                //合约
                mainActivity.navigation.setSelectedItemId(R.id.bt_contract);
                break;
            case R.id.btn_transaction:
                //卖出
                mainActivity.navigation.setSelectedItemId(R.id.bt_transaction);
                break;
            case R.id.Purchase:
                //买入
                mainActivity.navigation.setSelectedItemId(R.id.bt_transaction);
                break;

            case R.id.btn_credit:
                //信用评级
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(getActivity(), LoginActivity.class);
                } else {
                    JumpUtil.overlay(getActivity(), CreditActivity.class);
                }
                break;
            case R.id.btn_auction:
                //帮助中心
                JumpUtil.overlay(getActivity(), HelpActivity.class);
                break;
            case R.id.btn_game:
                //游戏
                JumpUtil.overlay(getActivity(), GameAccoutActivity.class);
                break;
            case R.id.btn_more:
                //更多
                JumpUtil.overlay(getActivity(), BuildIngActivity.class);
                break;

            case R.id.zf:
                //涨幅榜
                viewPager.setCurrentItem(0);
                zf.setSelected(true);
                view01.setVisibility(View.VISIBLE);
                cj.setSelected(false);
                cj.setTextColor(Color.WHITE);
                view02.setVisibility(View.GONE);
                xb.setSelected(false);
                xb.setTextColor(Color.WHITE);
                view03.setVisibility(View.GONE);
                zf.setTextSize(15);
                zf.setTextColor(getResources().getColor(R.color.blue));
                cj.setTextSize(13);
                cj.setTextColor(getResources().getColor(R.color.white6));
                xb.setTextSize(13);
                xb.setTextColor(getResources().getColor(R.color.white6));

                break;
            case R.id.cj:
                //成交榜
                viewPager.setCurrentItem(1);
                zf.setSelected(false);
                zf.setTextColor(Color.WHITE);
                view01.setVisibility(View.GONE);
                cj.setSelected(true);
                view02.setVisibility(View.VISIBLE);
                xb.setSelected(false);
                xb.setTextColor(Color.WHITE);
                view03.setVisibility(View.GONE);
                zf.setTextSize(13);
                zf.setTextColor(getResources().getColor(R.color.white6));
                cj.setTextSize(15);
                cj.setTextColor(getResources().getColor(R.color.blue));
                xb.setTextSize(13);
                xb.setTextColor(getResources().getColor(R.color.white6));
                break;
            case R.id.xb:
                //新币榜
                viewPager.setCurrentItem(2);
                zf.setSelected(false);
                zf.setTextColor(Color.WHITE);
                view01.setVisibility(View.GONE);
                cj.setSelected(false);
                cj.setTextColor(Color.WHITE);
                view02.setVisibility(View.GONE);
                xb.setSelected(true);
                view03.setVisibility(View.VISIBLE);
                zf.setTextSize(13);
                zf.setTextColor(getResources().getColor(R.color.white6));
                cj.setTextSize(13);
                cj.setTextColor(getResources().getColor(R.color.white6));
                xb.setTextSize(15);
                xb.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.btn_scan:
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(getActivity(), LoginActivity.class);
                    return;
                }
                //扫码

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent intent = new Intent(getActivity(), CommonScanActivity.class);
                    intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
                    getActivity().startActivityForResult(intent, 11);

                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == 0) {
            Intent intent = new Intent(getActivity(), CommonScanActivity.class);
            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
            getActivity().startActivityForResult(intent, 11);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
