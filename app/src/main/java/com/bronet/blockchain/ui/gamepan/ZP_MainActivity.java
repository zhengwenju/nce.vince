package com.bronet.blockchain.ui.gamepan;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Article;
import com.bronet.blockchain.data.Mode13;
import com.bronet.blockchain.data.TransferAgreement;
import com.bronet.blockchain.data.WinningList;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.AnimationUtil;
import com.bronet.blockchain.util.ClickUtils;
import com.bronet.blockchain.view.LuckyPanView;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ZP_MainActivity extends BaseActivity {
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.game_content)
    TextView game_content;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.id_luckypan)
    LuckyPanView mLuckyPanView;
    @BindView(R.id.id_start_btn)
    ImageView mStartBtn;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.marqueeView2)
    com.bronet.blockchain.util.MarqueeView marqueeView;
    /*@BindView(R.id.marqueeView2)
    com.bronet.blockchain.util.MarqueeView marqueeView2;*/
    @BindView(R.id.text1)
    TextView text;

    private int lukyIndex = -1;
    private int temp = 0;
    List<String> messages = new ArrayList<>();
    private String msg;
    @Override
    protected int getLayoutId() {
        return R.layout.pan_activity_main;
    }

    @Override
    protected void initView() {
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_tv.setText(getString(R.string.zp_prize));
        mStartBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {
                    if(!ClickUtils.isFastClick()){
                        return;
                    }

                    start();
//                    Runnable runnable = new Runnable() {
//                        public void run() {
//                            start();
//                            System.out.println("aaaaaaaaaaaaaaa============>>"+new Date());
//                        }
//
//                    };
//                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//                    service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
                }
//                else {
//                    if (!mLuckyPanView.isShouldEnd()) {
//                        mStartBtn.setImageResource(R.drawable.start);
//                        mLuckyPanView.luckyEnd();
//                    }
//                }
            }
        });
        rightTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.overlay(ZP_MainActivity.this, PrizeRecordActivity.class);
            }
        });

    }

    @Override
    protected void initData() {
        GameLotteryRules();
        WinningList();
    }

    @Override
    protected void setEvent() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            temp = 0;
            switch (msg.arg1) {
                case 1:
                    showPop1(false, "");
                    break;
                case 2:
                    String Str = getString(R.string.prize_win);
                    String content = String.format(Str, msg.what);
                    showPop(true, content);
                    break;

            }
        }
    };
    private PopupWindow popWindow;

    @SuppressLint("WrongConstant")
    private void showPop(boolean isWin, String content) {
        try {
            View inflate = null;
            inflate = LayoutInflater.from(this).inflate(R.layout.pop18, null);
            initB(inflate, isWin, content);
            popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            if (popWindow != null) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    @SuppressLint("WrongConstant")
    private void showPop1(boolean isWin, String content) {
        try {
            View inflate = null;
            inflate = LayoutInflater.from(this).inflate(R.layout.pop21, null);
            initB(inflate, isWin, content);
            popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            if (popWindow != null) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void initB(View inflate, final boolean isWin, String content) {
        final TextView tv_content = inflate.findViewById(R.id.tv_content);
        final TextView btn_yes = inflate.findViewById(R.id.btn_yes);
        if (isWin) {
            tv_content.setText(content);
            btn_yes.setText(getString(R.string.ok));
        } else {
            tv_content.setText(getString(R.string.prize_fail));
            btn_yes.setText(getString(R.string.prize_again));
        }
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
                if (!isWin) {
                    start();
                }
            }

        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }

    @Override
    protected void onDestroy() {
        popWindow = null;
        handler = null;
        super.onDestroy();
    }

    //开始抽奖
    private void start() {
        Data data = new Data();
        data.userId = ConstantUtil.ID;
        data.gameId = 1;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .Lottery(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Mode13>() {



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Mode13 mode) {
                        try {
                            if(!TextUtils.isEmpty(mode.getMsg())){
                                msg = mode.getMsg();
                                Log.d("", "======"+msg);

                                if (!TextUtils.isEmpty(msg)&&!msg.equals("余额不足")){
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    AnimationSet animationSet = new AnimationSet(true);
                                    TranslateAnimation translateAnimation = new TranslateAnimation(
                                            //X轴初始位置
                                            Animation.RELATIVE_TO_SELF, 2.3f,
                                            //X轴移动的结束位置
                                            Animation.RELATIVE_TO_SELF,0.0f,
                                            //y轴开始位置
                                            Animation.RELATIVE_TO_SELF,0.0f,
                                            //y轴移动后的结束位置
                                            Animation.RELATIVE_TO_SELF,0.0f);

                                    //3秒完成动画
                                    translateAnimation.setDuration(6000);
                                    //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
                                    animationSet.setFillAfter(true);
                                    //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
                                    animationSet.addAnimation(translateAnimation);
                                    //启动动画
                                    relativeLayout.startAnimation(animationSet);
                                    //relativeLayout.setAnimation(AnimationUtils.makeOutAnimation(ZP_MainActivity.this, true));
                                    text.setText(msg);
                                    //控件隐藏的动画

                                }
                            }
                            //	private String[] mStrs = new String[] { "  0  " ,"1" ," 2" ,"  3 " ,"  4  ","   5         6",     7",    "8",       "9", };
                            //	private String[] mStrs = new String[] { "2NCE","5NCE","20NCE","80NCE","100NCE","50NCE", "10NCE", "3NCE", "1NCE", "未中奖" };
                            int status = mode.getStatus();
                            if (status == 0) {
                                int nceNum = mode.getResult();
                                L.test("nceNum==========>>>" + nceNum);
                                if (nceNum == 100) { //1   100NCE
                                    lukyIndex = 0;
                                } else if (nceNum == 80) { //1   80NCE
                                    lukyIndex = 1;
                                } else if (nceNum == 50) { //1   50NCE
                                    lukyIndex = 2;
                                } else if (nceNum == 20) { //1    20NCE
                                    lukyIndex = 3;
                                } else if (nceNum == 10) { //1    10NCE
                                    lukyIndex = 4;
                                } else if (nceNum == 5) { //3   5NCE
                                    lukyIndex = 5;
                                } else if (nceNum == 3) { //15   3NCE
                                    lukyIndex = 6;
                                } else if (nceNum == 2) { //120    2NCE
                                    lukyIndex = 7;
                                } else if (nceNum == 1) { //430    1NCE
                                    lukyIndex = 8;
                                } else if (nceNum == 0) { //400     未中奖
                                    lukyIndex = 9;
                                }
                                mStartBtn.setImageResource(R.drawable.start);
                                mLuckyPanView.luckyStart(lukyIndex);

                                if (!mLuckyPanView.isShouldEnd()) {
                                    mStartBtn.setImageResource(R.drawable.start);
                                    mLuckyPanView.luckyEnd();
                                }

                                if (lukyIndex == 9) {
                                    Message message = new Message();
                                    message.arg1 = 1;
                                    handler.sendMessageDelayed(message, 3500);
                                } else {
                                    Message message = new Message();
                                    message.arg1 = 2;
                                    message.what = nceNum;
                                    handler.sendMessageDelayed(message, 3500);
                                }
                                //WinningList();
                            } else {
                                ToastUtil.show(ZP_MainActivity.this, mode.getMsg());
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

    //协议
    private void GameLotteryRules() {
        ApiStore.createApi(ApiService.class)
                .GameLotteryRules()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransferAgreement>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TransferAgreement transferAgreement) {
                        if (transferAgreement.getStatus() == 0) {
                            game_content.setText(transferAgreement.getResult());
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

    private void WinningList() {
        ApiStore.createApi(ApiService.class)
                .WinningList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WinningList>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WinningList winningList) {
                        List<String> result = winningList.getResult();


                        marqueeView.startWithList(result);
                        marqueeView.startWithList(result, R.anim.anim_right_in, R.anim.anim_left_out);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public class Data {
        String userId;
        int gameId;
    }

    public class Data2 {
        int id;
        String userId;
        int rewardQty;
    }
}
