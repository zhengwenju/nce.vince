package com.bronet.blockchain.ui.game.flop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.data.PkpModel;
import com.bronet.blockchain.data.PkpModel2;
import com.bronet.blockchain.data.WinningList;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.ui.game.flop.custom.FlopTextView;
import com.bronet.blockchain.ui.game.flop.listner.FlopOnAnimationEndListner;
import com.bronet.blockchain.ui.game.flop.model.FlopPopupModel;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.MarqueeView;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//扑克牌游戏
public class Pkp_MainActivity extends Activity implements  View.OnClickListener, FlopOnAnimationEndListner {

    private static MarqueeView marqueeView2 ;
    private boolean isClickAnim;
    private int mClickPosition;
    private ArrayList<FlopTextView> mFlopTextView=null;
    private List<FlopPopupModel> mData = null;
    private TextView tv1;
    private  TextView tv2;
    private  TextView tv3;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout btn_back;
    private TextView title_tv;
    private TextView reset;
    private TextView rightTv;
    private int qtyId=1;
    private String tv1Data,tv2Data,tv3Data;
    private List<String> winningLists= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pkp_activity_main);
        initView();
        initEvent();
        initData();
    }
    private void initView(){
        btn_back = findViewById(R.id.btn_back);
        title_tv = findViewById(R.id.title_tv);
        rightTv = findViewById(R.id.right_tv);
        marqueeView2 = findViewById(R.id.marqueeView2);
        rl1 = findViewById(R.id.relativeLayout1);
        rl2 = findViewById(R.id.relativeLayout2);
        rl3 = findViewById(R.id.relativeLayout3);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
    }
    private void initEvent(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.overlay(Pkp_MainActivity.this, PrizeActivity.class);
            }
        });
    }
    private void initData(){
        mClickPosition=0;
        mFlopTextView  = new ArrayList<>();
        mData = new ArrayList<>();
        title_tv.setText(getString(R.string.pkp_game));
        if (qtyId == 1) {
            rl1.setBackgroundResource(R.drawable.pkp_choose);
            rl2.setBackgroundResource(R.drawable.pkp_choose_sel);
            rl3.setBackgroundResource(R.drawable.pkp_choose_sel);
        } else if (qtyId == 2) {
            rl1.setBackgroundResource(R.drawable.pkp_choose_sel);
            rl2.setBackgroundResource(R.drawable.pkp_choose);
            rl3.setBackgroundResource(R.drawable.pkp_choose_sel);
        } else if (qtyId == 3) {
            rl1.setBackgroundResource(R.drawable.pkp_choose_sel);
            rl2.setBackgroundResource(R.drawable.pkp_choose_sel);
            rl3.setBackgroundResource(R.drawable.pkp_choose);
        }
        try {
            marqueeView2.startWithList(winningLists);
            marqueeView2.startWithList(winningLists, R.anim.anim_right_in, R.anim.anim_left_out);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        tv1.setText(tv1Data);
        tv2.setText(tv2Data);
        tv3.setText(tv3Data);
        getPkpData(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WinningList();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popupFlop01:
                clickIndex(0);
                break;
            case R.id.popupFlop02:
                clickIndex(1);
                break;
            case R.id.popupFlop03:
                clickIndex(2);
                break;
            case R.id.popupFlop04:
                clickIndex(3);
                break;
            case R.id.relativeLayout1:
                qtyId=1;
                isClickAnim=false;
                onCreate(null);
                break;
            case R.id.relativeLayout2:
                qtyId=2;
                isClickAnim=false;
                onCreate(null);
                break;
            case R.id.relativeLayout3:
                qtyId=3;
                isClickAnim=false;
                onCreate(null);
                break;
            case R.id.reset:
                //重置
                isClickAnim=false;
                Pkp_MainActivity.this.onCreate(null);
                break;
        }
    }
    private void clickIndex(int position){
        if (!isClickAnim) {
            isClickAnim = true;
            ClickCardDraw(position);
        }
    }

    //消耗NCE数量列表
    private void getPkpData(final boolean isReset) {
        ApiStore.createApi(ApiService.class)
                .QtyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PkpModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull PkpModel pmodel) {

                        tv1Data=pmodel.getResult().get(0).getQty()+"NCE";
                        tv2Data=pmodel.getResult().get(1).getQty()+"NCE";
                        tv3Data=pmodel.getResult().get(2).getQty()+"NCE";
                        tv1.setText(tv1Data);
                        tv2.setText(tv2Data);
                        tv3.setText(tv3Data);

                        mFlopTextView.add((FlopTextView) findViewById(R.id.popupFlop01));
                        mFlopTextView.add((FlopTextView) findViewById(R.id.popupFlop02));
                        mFlopTextView.add((FlopTextView) findViewById(R.id.popupFlop03));
                        mFlopTextView.add((FlopTextView) findViewById(R.id.popupFlop04));
                        for (int i = 0; i < mFlopTextView.size(); i++) {
                            Drawable drawable = getResources().getDrawable(R.mipmap.pkp_hide_bg);
                            mFlopTextView.get(i).initDrableImageUrl(drawable);
                            mFlopTextView.get(i).setOnClickListener(Pkp_MainActivity.this);

                            FlopPopupModel model = new FlopPopupModel();
                            FlopTextView.Builder builder = new FlopTextView.Builder();
                            builder.setmIsCustom(1).setmIsText(1).setmTextColor(Color.YELLOW)
                                    .setmTextSize(10).setmIsAnimType(1)
                                    .setmText("00NCE");
                            model.setBuilder(builder);
                            mData.add(model);
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
    private int prizeNceNum=0;
    //翻牌
    private void ClickCardDraw(final int position) {
        Data data = new Data();
        data.gameId=2;
        data.userId= ConstantUtil.ID;
        data.index=position;
        data.qtyId=qtyId;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test("CardDraw s==========>>>"+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .CardDraw(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PkpModel2>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull PkpModel2 pkpModel2) {
                        mData.clear();
                        if(pkpModel2.getStatus()==0) {
                            if (pkpModel2.getResult() != null) {
                                for (int j = 0; j < pkpModel2.getResult().size(); j++) {
                                    if (pkpModel2.getResult().get(j).getStatus() == 1) {
                                        prizeNceNum = pkpModel2.getResult().get(j).getQty();
                                    }
                                    FlopPopupModel model = new FlopPopupModel();
                                    FlopTextView.Builder builder = new FlopTextView.Builder();
                                    builder.setmIsCustom(1).setmIsText(1).setmTextColor(Color.YELLOW)
                                            .setmTextSize(10).setmIsAnimType(1)
                                            .setmText(String.valueOf(pkpModel2.getResult().get(j).getQty()));
                                    model.setBuilder(builder);

                                    L.test("ClickCardDraw response======>>>result:" + pkpModel2.getResult().get(j).getQty());
                                    mData.add(model);
                                }
                                //点击事件// 初始化没做好
                                mClickPosition = position;
                                mFlopTextView.get(position).setConfig(mData.get(position).getBuilder());
                                mFlopTextView.get(position).setFlopOnAnimationEndListner( Pkp_MainActivity.this);
                                mFlopTextView.get(position).startAnimation(position);
                            }
                        }else {
                            ToastUtil.show(Pkp_MainActivity.this,pkpModel2.getMsg());
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
    public void onAnimationEnd() {
        L.test("pkp========>>>onAnimationEnd22==========>>>");
        for (int i = 0; i < mFlopTextView.size(); i++) {
            if (mClickPosition != i ){
                FlopTextView.Builder builder = mData.get(i).getBuilder();
                mFlopTextView.get(i).setConfig(builder);
                mFlopTextView.get(i).startAnimation(i);
            }
        }
        Message msg = new Message();
        handler.sendMessageDelayed(msg,1500);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(prizeNceNum>0) {
                showPop(true,"恭喜您,中"+prizeNceNum+"NCE");
            }else {
                showPop1(true,"抱歉,没有中奖");
            }


        }
    };

    public class Data {
        int gameId;
        String userId;
        int index;
        int qtyId;
    }
    private void WinningList() {
        ApiStore.createApi(ApiService.class)
                .WinningList(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WinningList>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WinningList winningList) {
                        winningLists = winningList.getResult();
                        marqueeView2.startWithList(winningLists);
                        marqueeView2.startWithList(winningLists, R.anim.anim_right_in, R.anim.anim_left_out);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private PopupWindow popWindow;
    @SuppressLint("WrongConstant")
    private void showPop(boolean isWin, String content) {
        try {
            View inflate = null;
            inflate = LayoutInflater.from(this).inflate(R.layout.pop20, null);
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
            inflate = LayoutInflater.from(this).inflate(R.layout.pop19, null);
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

        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                if (!isWin) {
//                    start();
                }
                isClickAnim=false;
                onCreate(null);
            }

        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }
}