package com.bronet.blockchain.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bronet.blockchain.data.AppNode;
import com.bronet.blockchain.data.CenterActivit;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.VerifyPwd;
import com.bronet.blockchain.ui.fragment.HomeFragment;
import com.bronet.blockchain.ui.fragment.PersonalFragment;
import com.bronet.blockchain.ui.my.MyAssetsActivity;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.github.mikephil.charting.utils.CommonUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.AppModel;
import com.bronet.blockchain.data.RemindList;
import com.bronet.blockchain.fix.AESUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.fragment.ContractFragment;
import com.bronet.blockchain.ui.fragment.NewTransactionFragment;
import com.bronet.blockchain.ui.fragment.QuotationFragment;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.fix.AES;
import com.bronet.blockchain.util.Action;
import com.bronet.blockchain.util.BottomNavigationViewHelper;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.MonitorUtil;
import com.bronet.blockchain.util.MyDatabaseHelper;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.view.MyDialog;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdLoader;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentFrameLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {

    @BindView(R.id.frame_layout)
    PercentFrameLayout frameLayout;
    @BindView(R.id.navigation)
    public BottomNavigationView navigation;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.drawer_layout)
    LinearLayout drawerLayout;
    private ArrayList<Fragment> fragmentList;
    private int lastIndex;
    private long time;
    ContractFragment contractFragment;
    NewTransactionFragment newTransactionFragment;
    PersonalFragment personalFragment;
    PopupWindow popWindow;
    private int updateflag = 0;
    private InterstitialAd interstitialAd;
    private static final long GAME_LENGTH_MILLISECONDS = 1000;
    private static final String TAG = "CustomSQLiteOpenHelper";
    //            private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";  //demo unit id
    private static final String AD_UNIT_ID = "ca-app-pub-8791296902505750/2704461253"; //my unit id
    public boolean isStatusBar = true;
    private TextView unread_iv;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Const.REFRESHFLAG = 0;
            switch (item.getItemId()) {
                case R.id.bt_home:
                    selectFragment(0);
                    if (contractFragment != null) {
                        contractFragment.pause();
                    }
                    startGame();
                    return true;
                case R.id.bt_quotation:
                    selectFragment(1);
                    if (contractFragment != null) {
                        contractFragment.pause();
                    }
                    return true;
                case R.id.bt_transaction:
                    if (TextUtils.isEmpty(ConstantUtil.ID)) {
                        JumpUtil.overlay(activity, LoginActivity.class);
                        return true;
                    }

                    selectFragment(2);
                    if (newTransactionFragment != null) {
//                        newTransactionFragment.EntrustChange();
                        newTransactionFragment.monitorBuyClick();
                    }
                    if (contractFragment != null) {
                        contractFragment.pause();
                    }
                    return true;
                case R.id.bt_contract:
                    if (TextUtils.isEmpty(ConstantUtil.ID)) {
                        JumpUtil.overlay(activity, LoginActivity.class);
                        return true;
                    }
                    selectFragment(3);
                    if (contractFragment != null) {
                        contractFragment.newList();
                    }
                    return true;
                case R.id.bt_assets:
                    if (TextUtils.isEmpty(ConstantUtil.ID)) {
                        JumpUtil.overlay(activity, LoginActivity.class);
                        return true;
                    }
                    selectFragment(4);
                    if (contractFragment != null) {
                        contractFragment.pause();
                    }
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    private InterstitialAd mInterstitialAd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private static final int REQUEST_PERMISSION = 0;

    @Override
    protected void initView() {
        int i = CommonUtil.px2dip(this, 88);
        // Create the InterstitialAd and set the adUnitId.
        //dbName数据库名
        //MyDatabaseHelper
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        dbHelper.getWritableDatabase();

        Constants.activityList.add(this);
//
//        String s1 = Util.isMonitor(this);
        boolean s1 = MonitorUtil.isFeatures();
//        boolean s2 = MonitorUtil.checkIsRunningInEmulator();
//        boolean s3 = MonitorUtil.isEmulator(this);
        L.test("monitor============>>s1:" + s1);
//        L.test("monitor============>>s2:"+s2);
//        L.test("monitor============>>s3:"+s3);


        boolean b = hasLightSensor(this);
        L.test("b=================" + b);
        setBarBiack(true);
        ConstantUtil.TYPE = false;
        navigation.setItemIconTintList(null);
        initFragment();
        selectFragment(0);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        View tab = menuView.getChildAt(4);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        View badge = LayoutInflater.from(this).inflate(R.layout.unread_count, menuView, false);
        itemView.addView(badge);
        unread_iv = badge.findViewById(R.id.unread_iv);
        unread_iv.setVisibility(View.GONE);
//        getSupportActionBar().setTitle("");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }
//        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 10);
//        }
//        Util.imei = telephonyManager.getDeviceId();
//        Util.phone  = telephonyManager.getLine1Number();


//        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-8791296902505750~5781771493")
//                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//                    @Override
//                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                        // Show the ad.
//                    }
//                })
//                .withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(int errorCode) {
//                        // Handle the failure by logging, altering the UI, and so on.
//                    }
//                })
//                .withNativeAdOptions(new NativeAdOptions.Builder()
//                        // Methods in the NativeAdOptions.Builder class can be
//                        // used here to specify individual options settings.
//                        .build())
//                .build();
//        adLoader.loadAd(new AdRequest.Builder().build());
    }


    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            startGame();
        }
    }

    private void startGame() {
        //vince huawei :89C03B854F4C68ACE78D1C874BA53E22
        //vince mi: 8952DD76684DE403AD12FB407B0EE650
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest = null;
            if(L.isDebug){
                adRequest = new AdRequest.Builder().addTestDevice("89C03B854F4C68ACE78D1C874BA53E22").build();
            }else {
                adRequest = new AdRequest.Builder().build();
            }
//            if(interstitialAd==null){
            interstitialAd = new InterstitialAd(this);
            // Defined in res/values/strings.xml
            interstitialAd.setAdUnitId(AD_UNIT_ID);

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
//                Toast.makeText(InterstitialAdActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                    showInterstitial();
                    L.test("google ad success");
                    GoogleAdSuccess();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(MainActivity.this, "Google onAdFailedToLoad", Toast.LENGTH_SHORT).show();
//                JumpUtil.overlay(InterstitialAdActivity.this,MainActivity.class);
                    L.test("google ad errorCode:" + errorCode);
                    GoogleAdFail();
                }

                @Override
                public void onAdClosed() {
//                startGame();
//                finish();
//                JumpUtil.overlay(InterstitialAdActivity.this,MainActivity.class);
                }
            });
//            }
            interstitialAd.loadAd(adRequest);
        }
        resumeGame(GAME_LENGTH_MILLISECONDS);
    }

    private boolean gameIsInProgress;
    private long timerMilliseconds;
    private CountDownTimer countDownTimer;

    private void resumeGame(long milliseconds) {
        // Create a new timer for the correct length and start it.
        gameIsInProgress = true;
        timerMilliseconds = milliseconds;
        createTimer(milliseconds);
        countDownTimer.start();
    }

    private void createTimer(final long milliseconds) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        final TextView textView = findViewById(R.id.timer);

        countDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                timerMilliseconds = millisUnitFinished;
//                textView.setText("seconds remaining: " + ((millisUnitFinished / 1000) + 1));
            }

            @Override
            public void onFinish() {
                gameIsInProgress = false;
//                textView.setText("done!");
//                retryButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void initFragment() {
        personalFragment = (PersonalFragment) PersonalFragment.getInstance();
        contractFragment = (ContractFragment) ContractFragment.getInstance();
        newTransactionFragment = (NewTransactionFragment) NewTransactionFragment.getInstance();
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.getInstance(this));
        fragmentList.add(QuotationFragment.getInstance());
//        if(L.isDebug){
//            fragmentList.add(newTransactionFragment);
//        }else {
//            fragmentList.add(TransactionFragment.getInstance());
//        }

        fragmentList.add(newTransactionFragment);

        //    fragmentList.add(TransactionFragment.getInstance());
        fragmentList.add(contractFragment);
        fragmentList.add(personalFragment);
    }

    /**
     * 设置默认选中fragment
     *
     * @param index 碎片fragment
     */
    private void selectFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragmentList.get(index);
        Fragment lastFragment = fragmentList.get(lastIndex);
        lastIndex = index;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.frame_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    private MyReceiver recevier;
    private IntentFilter intentFilter;
    private Thread t;

    @Override
    protected void initData() {
        SharePreData sharePreData = new SharePreData();
        ConstantUtil.ID = AES.decrypt(sharePreData.getString(Const.SP_ID));

        L.test("1非首次登录成功userid========>>>" + ConstantUtil.ID);

        if (!TextUtils.isEmpty(ConstantUtil.ID)) {
            ConstantUtil.ID = AESUtil.encry(ConstantUtil.ID);
        }

        L.test("2非首次登录成功userid========>>>" + ConstantUtil.ID);

        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            //            return;
        }

        recevier = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Action.WithMoneyAction);
        registerReceiver(recevier, intentFilter);

        if (Const.outIpAddressArray == null) {
            t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    Const.outIpAddressArray = Util.getOutNetIP(MainActivity.this, 1);
                }
            };
            t.start();
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        interstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        interstitialAd.setAdUnitId(AD_UNIT_ID);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                Toast.makeText(InterstitialAdActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                showInterstitial();
                L.test("google ad success");
                GoogleAdSuccess();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(MainActivity.this, "Google onAdFailedToLoad", Toast.LENGTH_SHORT).show();
//                JumpUtil.overlay(InterstitialAdActivity.this,MainActivity.class);
                L.test("google ad errorCode:" + errorCode);
                GoogleAdFail();
            }

            @Override
            public void onAdClosed() {
//                startGame();
//                finish();
//                JumpUtil.overlay(InterstitialAdActivity.this,MainActivity.class);
            }
        });
        startGame();
    }

    @Override
    protected void setEvent() {

    }

    @Override
    protected void onPause() {
        L.test("MineClearanceMainActivity==========>>onPause");
        super.onPause();
        try {
            if (recevier != null) {
                unregisterReceiver(recevier);
            }
            if (t != null) {
                t.interrupt();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startGame();
        if (navigation != null) {
            if (TextUtils.isEmpty(ConstantUtil.ID)) {
                navigation.setSelectedItemId(R.id.bt_home);
            } else {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment currentFragment = fragmentList.get(lastIndex);
                ft.replace(R.id.frame_layout, currentFragment);
                ft.show(currentFragment);
                ft.commitAllowingStateLoss();
                navigation.getMenu().getItem(lastIndex).setChecked(true);


                if (contractFragment != null) {
                    contractFragment.newList();
                }
                if (personalFragment != null) {
//                    personalFragment.newList();
                }
            }
        }

        updateflag = 0;
        // onScroll();

        checkVersion();
        getTipValue();
//        OperationFreeze();
        getExistAppCount();
    }

//    private void OperationFreeze(){
//        ApiStore.createApi(ApiService.class)
//                .OperationFreeze(ConstantUtil.ID)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Model1>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//                    }
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull Model1 assure) {
//                        if (assure.getStatus() == 0) {//不冻结
//                            Const.IS_FROZEN=false;
//                        }else if(assure.getStatus() == 1){//冻结
//                            Const.IS_FROZEN=true;
//                            Const.FROZEN_CONTENT=assure.getMsg();
//                        }
//
////                        Const.IS_FROZEN=true;
////                        Const.FROZEN_CONTENT="账号已被冻结";//assure.getMsg();
//                    }
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }


    private void getTipValue() {
        ApiStore.createApi(ApiService.class)
                .RemindList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RemindList>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull RemindList remindList) {
                        if (remindList.getStatus() == 0) {
                            try {
                                List<RemindList.ResultBean> result = remindList.getResult();
                                if (result != null) {
                                    for (RemindList.ResultBean resultBean : result) {
                                        if (resultBean != null) {
                                            if (Const.tipMap == null) {
                                                Const.tipMap = new HashMap<>();
                                            }
                                            Const.tipMap.put(resultBean.getId(), resultBean.getMsg());
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
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

    private void checkVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Data data = new Data();
        data.type = 2;
        data.version = packInfo.versionName;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        L.test("checkVersion s1====>>>" + s);
        ApiStore.createApi(ApiService.class)
                .CheckNewVersion(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppModel>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull AppModel appModel) {
                        try {
                            L.test("checkVersion resp appModel.getStatus()====>>>" + appModel.getStatus());
                            if (appModel.getStatus() == 0) {
                                if (appModel.getResult().getStatus() == 1) {//强制升级
                                    if (updateflag == 0) {
                                        updateflag = 1;
                                        shopPop2(appModel.getResult().getDescription(), appModel.getResult().getDownloadUrl(), appModel.getResult().getStatus(), appModel.getResult().getVersion());
                                    }
                                } else if (appModel.getResult().getStatus() == 2) {//非强制升级
                                    if (updateflag == 0) {
                                        updateflag = 1;
                                        shopPop2(appModel.getResult().getDescription(), appModel.getResult().getDownloadUrl(), appModel.getResult().getStatus(), appModel.getResult().getVersion());
                                    }
                                }
                            }
                            L.test("checkVersion resp appModel.getStatus()====>>>" + appModel.getResult().getDescription());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        L.test(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void shopPop2(String description, final String downloadurl, int status, String version) {
        View view = getLayoutInflater().inflate(R.layout.pop8, null);
        final MyDialog mMyDialog = new MyDialog(MainActivity.this, 0, 0, view, R.style.DialogTheme);
        if (status == 1) {
            if (!L.isDebug) {
                mMyDialog.setCancelable(false);
            }
        }
        TextView textView = view.findViewById(R.id.content);
        TextView titleTv = view.findViewById(R.id.title);
        TextView btn_yes = view.findViewById(R.id.btn_yes);
        if (!TextUtils.isEmpty(description)) {
            textView.setText(description);
        }
        if (!TextUtils.isEmpty(version)) {
            titleTv.setText(getString(R.string.transf44) + "\n" + version);
        }

        btn_yes.setClickable(true);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser(MainActivity.this, downloadurl);
            }
        });

        mMyDialog.show();
    }


    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment currentFragment = fragmentList.get(4);
//            Fragment lastFragment = fragmentList.get(4);
            lastIndex = 4;
            ft.replace(R.id.frame_layout, currentFragment);
            ft.show(currentFragment);
            ft.commitAllowingStateLoss();

            navigation.getMenu().getItem(4).setChecked(true);

        }
    }


    private void showPop(String description, final String downloadurl) {
        View inflate = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.pop8, null);
        TextView textView = inflate.findViewById(R.id.content);
        TextView btn_yes = inflate.findViewById(R.id.btn_yes);
        textView.setText(description);
//        initB(inflate,downloadurl);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        changeWindowAlfa(0.5f);
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
//        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
        btn_yes.setClickable(true);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser(MainActivity.this, downloadurl);
            }
        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }

    public void openBrowser(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            final Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
            // 官方解释 : Name of the component implementing an activity that can display the intent
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
                context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
            } else {
                Toast.makeText(MainActivity.this, "请下载浏览器", Toast.LENGTH_SHORT).show();
            }
        }
    }
   /* public void onScroll() {
        if (!TextUtils.isEmpty(ConstantUtil.ID)) {
            */

    /**
     * 监听抽屉的滑动事件
     *//*
            drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    //滑动动画
                    View mContent = drawerLayout.getChildAt(0);
                    View mMenu = drawerView;
                    float scale = 1 - slideOffset;
                    float rightScale = 0.8f + scale * 0.2f;
                    float leftScale = 0.5f + slideOffset * 0.5f;
                    if (navigation.getSelectedItemId() == R.id.bt_home) {
                        mMenu.setAlpha(leftScale);
                        mMenu.setScaleX(leftScale);
                        mMenu.setScaleY(leftScale);
                        mContent.setPivotX(0);
                        mContent.setPivotY(mContent.getHeight() * 1 / 2);
                        mContent.setScaleX(rightScale);
                        mContent.setScaleY(rightScale);
                        mContent.setTranslationX(mMenu.getWidth() * slideOffset);
                    } else {
                        mMenu.setAlpha(1);
                        mMenu.setScaleX(1);
                        mMenu.setScaleY(1);
                        mContent.setPivotX(0);
                        mContent.setPivotY(1);
                        mContent.setScaleX(1);
                        mContent.setScaleY(1);
                        mContent.setTranslationX(0);
                    }
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    cardView.setRadius(navigation.getSelectedItemId() == R.id.bt_home ? 20 : 0);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    cardView.setRadius(0);
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        }
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10) {
            String code = data.getStringExtra("code");
//            ToastUtil.show(this,code);
//            Toast.makeText(this,code,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - time > 1000) {
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            ConstantUtil.ID = "";
            finish();

            if (Const.tipMap != null) {
                Const.tipMap.clear();
                Const.tipMap = null;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 获取到Activity下的Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null) {
            return;
        }
        // 查找在Fragment中onRequestPermissionsResult方法并调用
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public class Data {
        int type;
        String appId;
        String version;
    }

    public static boolean hasLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (sensor == null)
            return true;
        else
            return false;
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
                            } else {
                                feedBackCount = 0;
                            }
                            if (feedBackCount > 0) {
                                unread_iv.setVisibility(View.VISIBLE);
                                unread_iv.setText(String.valueOf(feedBackCount));
                            } else {
                                unread_iv.setVisibility(View.GONE);
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
                            //互助信息隐藏图标后，如果留言反馈大于0也显示
                            if (unReadCount > 0) {
                                unread_iv.setVisibility(View.VISIBLE);
                                unread_iv.setText(String.valueOf(mutualHelpCount + unReadCount));
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

    private void GoogleAdSuccess() {
        ApiStore.createApi(ApiService.class)
                .GoogleAdSuccess(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppNode>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull AppNode centerActivit) {
                        if (centerActivit.getStatus() == 0) {
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

    private void GoogleAdFail() {
        ApiStore.createApi(ApiService.class)
                .GoogleAdFail(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppNode>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull AppNode centerActivit) {
                        if (centerActivit.getStatus() == 0) {
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
