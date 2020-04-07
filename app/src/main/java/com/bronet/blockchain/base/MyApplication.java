package com.bronet.blockchain.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.StrictMode;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bronet.blockchain.R;
import com.bronet.blockchain.fix.AES;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.Constants;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.Log.CrashHandler2;
import com.bronet.blockchain.util.Log.LogcatHelper;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;


/**
 * application
 *
 * @packageName: cn.white.ymc.wanandroidmaster.base
 * @fileName: MyApplication
 * @date: 2018/7/19  15:22
 * @author: ymc
 * @QQ:745612618
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;
    private boolean DEBUGGABLE = false;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        }catch (Exception e){
            //
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            PackageInfo info  = getPackageManager().getPackageInfo(getPackageName(), 0);
            int flags = info.applicationInfo.flags;
            DEBUGGABLE = (0 != (flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            //
        }

        myApplication = this;
        // 初始化 log 保存本地工具类
        LogcatHelper.getInstance(this).start();
        // 初始化 抓取 异常信息
        CrashHandler2.getInstance().init(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        CrashReport.initCrashReport(getApplicationContext(), "b9280c2a63", L.isDebug);

    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

   /* //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setDrawableSize(20);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }*/

}
