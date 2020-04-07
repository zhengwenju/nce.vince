package com.bronet.blockchain.ui.games;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.TrigerRequestModel;
import com.bronet.blockchain.data.TrigerResultModel;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.data.WinningList;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.bronet.blockchain.util.MarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Tiger_MainActivity extends BaseActivity {
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    @BindView(R.id.p1_iv)
    TextView mP1Iv;
    @BindView(R.id.p2_iv)
    TextView mP2Iv;
    @BindView(R.id.p3_iv)
    TextView mP3Iv;
    @BindView(R.id.p4_iv)
    TextView mP4Iv;
    @BindView(R.id.p12_iv)
    TextView mP12Iv;
    @BindView(R.id.p11_iv)
    TextView mP11Iv;
    @BindView(R.id.start_iv)
    ImageView mStartIv;
    @BindView(R.id.p5_iv)
    TextView mP5Iv;
    @BindView(R.id.p6_iv)
    TextView mP6Iv;
    @BindView(R.id.p10_iv)
    TextView mP10Iv;
    @BindView(R.id.p9_iv)
    TextView mP9Iv;
    @BindView(R.id.p8_iv)
    TextView mP8Iv;
    @BindView(R.id.p7_iv)
    TextView mP7Iv;
    @BindView(R.id.bet_tv1)
    TextView mBetTv1;
    @BindView(R.id.bet_nce_tv1)
    TextView mBetNceTv1;
    @BindView(R.id.bet_btn1)
    Button mBetBtn1;
    @BindView(R.id.bet_tv2)
    TextView mBetTv2;
    @BindView(R.id.bet_nce_tv2)
    TextView mBetNceTv2;
    @BindView(R.id.bet_btn2)
    Button mBetBtn2;
    @BindView(R.id.bet_tv3)
    TextView mBetTv3;
    @BindView(R.id.bet_nce_tv3)
    TextView mBetNceTv3;
    @BindView(R.id.bet_btn3)
    Button mBetBtn3;
    @BindView(R.id.bet_tv4)
    TextView mBetTv4;
    @BindView(R.id.bet_nce_tv4)
    TextView mBetNceTv4;
    @BindView(R.id.bet_btn4)
    Button mBetBtn4;
    @BindView(R.id.bet_tv5)
    TextView mBetTv5;
    @BindView(R.id.bet_nce_tv5)
    TextView mBetNceTv5;
    @BindView(R.id.bet_btn5)
    Button mBetBtn5;
    @BindView(R.id.marqueeView2)
    MarqueeView marqueeView;

    private TextView[] mImgArr = new TextView[12];//使用数据存储这12张图片

    private String mBetName = "";

    //下注的金币和剩余总金币
    private int mBetMoney = 0;
    private int mBetTotalMoney = 10000;

    //当前选中图片的id
    private int currentId = 0;

    //使用数据存储数据名
    private String[] mNameArr = {"10NCE", "1NCE", "100NCE", "50NCE", "2NCE", "26NCE", "25NCE", "24NCE", "23NCE", "22NCE", "21NCE", "20NCE"};



    private MyHandler mHandler = new MyHandler();

    //创建定时器
    private AnimThread animThread = new AnimThread();
    private TimeThread timeThread = new TimeThread();
    private int btnId=0;

    private int value1;
    private int value5;
    private int value10;
    private int value20;
    private int value100;


    private boolean isStart=false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tiger__main;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mTitleTv.setText(getString(R.string.zp_prize));
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        checkMoney();//获取存储的金币
        //将图片添加到ImageView[]中
        mImgArr[0] = mP1Iv;
        mImgArr[1] = mP2Iv;
        mImgArr[2] = mP3Iv;
        mImgArr[3] = mP4Iv;
        mImgArr[4] = mP5Iv;
        mImgArr[5] = mP6Iv;
        mImgArr[6] = mP7Iv;
        mImgArr[7] = mP8Iv;
        mImgArr[8] = mP9Iv;
        mImgArr[9] = mP10Iv;
        mImgArr[10] = mP11Iv;
        mImgArr[11] = mP12Iv;
        mStartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value1>0||value5>0||value10>0||value20>0||value100>0) {
                    if(!isStart) {
                        isStart=true;
                        StartFruit();
                    }
                }else {
                    ToastUtil.show(Tiger_MainActivity.this,"请下注");
                }
            }
        });
        clearData();

    }


    private int index=0;
    private void StartFruit() {
        TrigerRequestModel data = new TrigerRequestModel();
        data.setUserId(ConstantUtil.ID);

        List<TrigerRequestModel.BetlistBean> list = new ArrayList<>();
        TrigerRequestModel.BetlistBean betlistBean1 = new TrigerRequestModel.BetlistBean();
        betlistBean1.setNum(1);
        betlistBean1.setQty(value1);

        TrigerRequestModel.BetlistBean betlistBean2 = new TrigerRequestModel.BetlistBean();
        betlistBean2.setNum(5);
        betlistBean2.setQty(value5);

        TrigerRequestModel.BetlistBean betlistBean3 = new TrigerRequestModel.BetlistBean();
        betlistBean3.setNum(10);
        betlistBean3.setQty(value10);

        TrigerRequestModel.BetlistBean betlistBean4 = new TrigerRequestModel.BetlistBean();
        betlistBean4.setNum(20);
        betlistBean4.setQty(value20);

        TrigerRequestModel.BetlistBean betlistBean5 = new TrigerRequestModel.BetlistBean();
        betlistBean5.setNum(100);
        betlistBean5.setQty(value100);

        list.add(betlistBean1);
        list.add(betlistBean2);
        list.add(betlistBean3);
        list.add(betlistBean4);
        list.add(betlistBean5);

        data.setBetlist(list);

        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        L.test("StartFruit s=====>>>reqeuest:"+s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .StartFruit(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TrigerResultModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TrigerResultModel tradeNce) {
                        L.test("setread======>"+tradeNce.getStatus());

                        if(tradeNce.getStatus()==0) {
                            index = tradeNce.getResult().getIndex();
                            L.test("StartFruit s=====>>>index:"+index);
                            timeThread = new TimeThread();
                            timeThread.start();//转动持续时间
                            animThread = new AnimThread();
                            animThread.start();//计算当前选中图片的序号
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

    @OnClick({R.id.bet_btn1, R.id.bet_btn2, R.id.bet_btn3, R.id.bet_btn4, R.id.bet_btn5})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.bet_btn1:
                btnId=1;
                break;
            case R.id.bet_btn2:
                btnId=2;
                break;
            case R.id.bet_btn3:
                btnId=3;
                break;
            case R.id.bet_btn4:
                btnId=4;
                break;
            case R.id.bet_btn5:
                btnId=5;
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.FullScreenDialog);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.pop22);
        final EditText editText = window.findViewById(R.id.qty_etv);

        View btnYes = window.findViewById(R.id.btn_yes);
        ImageView image = window.findViewById(R.id.nce_image_iv);
        TextView textView = window.findViewById(R.id.tv_content);

        View close = window.findViewById(R.id.close);
        if(btnId==1){
            image.setImageResource(R.mipmap.nce_1_bg);
            textView.setText("1NCE");
        }else  if(btnId==2){
            image.setImageResource(R.mipmap.nce_5_bg);
            textView.setText("5NCE");
        }else  if(btnId==3){
            image.setImageResource(R.mipmap.nce_10_bg);
            textView.setText("10NCE");
        }else  if(btnId==4){
            image.setImageResource(R.mipmap.nce_20_bg);
            textView.setText("20NCE");
        }else  if(btnId==5){
            image.setImageResource(R.mipmap.nce_100_bg);
            textView.setText("100NCE");
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    if (btnId == 1) {
                        String numStr =editText.getText().toString();
                        if(!TextUtils.isEmpty(numStr)) {
                            mBetTv1.setText("已下注");
                            mBetNceTv1.setVisibility(View.VISIBLE);
                            mBetNceTv1.setText(editText.getText().toString() + Const.TRIGER_GAME_STR);
                            value1 = Integer.valueOf(mBetNceTv1.getText().toString().replace(Const.TRIGER_GAME_STR,""));
                        }else {
                            mBetTv1.setText("未下注");
                            mBetNceTv1.setVisibility(View.INVISIBLE);
                        }
                    } else if (btnId == 2) {
                        String numStr =editText.getText().toString();
                        if(!TextUtils.isEmpty(numStr)) {
                            mBetTv2.setText("已下注");
                            mBetNceTv2.setVisibility(View.VISIBLE);
                            mBetNceTv2.setText(editText.getText().toString() + Const.TRIGER_GAME_STR);
                            value5 = Integer.valueOf(mBetNceTv2.getText().toString().replace(Const.TRIGER_GAME_STR,""));
                        }else {
                            mBetTv2.setText("未下注");
                            mBetNceTv2.setVisibility(View.INVISIBLE);
                        }
                    }else if (btnId == 3) {
                        String numStr =editText.getText().toString();
                        if(!TextUtils.isEmpty(numStr)) {
                            mBetTv3.setText("已下注");
                            mBetNceTv3.setVisibility(View.VISIBLE);
                            mBetNceTv3.setText(editText.getText().toString() + Const.TRIGER_GAME_STR);
                            value10 = Integer.valueOf(mBetNceTv3.getText().toString().replace(Const.TRIGER_GAME_STR,""));
                        }else {
                            mBetTv3.setText("未下注");
                            mBetNceTv3.setVisibility(View.INVISIBLE);
                        }
                    } else if (btnId == 4) {
                        String numStr =editText.getText().toString();
                        if(!TextUtils.isEmpty(numStr)) {
                            mBetTv4.setText("已下注");
                            mBetNceTv4.setVisibility(View.VISIBLE);
                            mBetNceTv4.setText(editText.getText().toString() + Const.TRIGER_GAME_STR);
                            value20 = Integer.valueOf(mBetNceTv4.getText().toString().replace(Const.TRIGER_GAME_STR,""));
                        }else {
                            mBetTv4.setText("未下注");
                            mBetNceTv4.setVisibility(View.INVISIBLE);
                        }
                    } else if (btnId == 5) {
                        String numStr =editText.getText().toString();
                        if(!TextUtils.isEmpty(numStr)) {
                            mBetTv5.setText("已下注");
                            mBetNceTv5.setVisibility(View.VISIBLE);
                            mBetNceTv5.setText(editText.getText().toString() + Const.TRIGER_GAME_STR);
                            value100 = Integer.valueOf(mBetNceTv5.getText().toString().replace(Const.TRIGER_GAME_STR,""));
                        }else {
                            mBetTv5.setText("未下注");
                            mBetNceTv5.setVisibility(View.INVISIBLE);
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                dialog.dismiss();

            }
        });

    }
    @Override
    protected void initData() {
        WinningList();
    }



    @Override
    protected void setEvent() {
        mRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.overlay(Tiger_MainActivity.this, TigerRecordActivity.class);
            }
        });

    }

    /**
     * 获取存储的金币，如果获取到，则将获取的金币作为总金币数，否则弹出提示第一次游戏、欢迎光临
     */
    private void checkMoney() {
        try {
            SharedPreferences pref = getSharedPreferences("money", MODE_PRIVATE);
            int mTotalMoney = pref.getInt("money", 10000);
            mBetTotalMoney = mTotalMoney;
//            moneyTv.setText(mBetTotalMoney + "");
        } catch (Exception e) {
            Toast.makeText(Tiger_MainActivity.this, "第一次游戏，欢迎你的光临", Toast.LENGTH_SHORT);
        }
    }

    /*
     *转盘转动开始，在此期间TimeThread保持sleep，AnimThread则一直计算序号
     */
    private class TimeThread extends Thread {
        @Override
        public void run() {
            Random random = new Random();
            int x = random.nextInt(3);
            L.test("run x==========>>>"+x);
            try {
                Thread.sleep(150*index+(12*150));//转动持续的时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //随机时间到后，AnimThread停止计算序号
            animThread.stopAnim();

            //发送结果给MyHandler进行处理
            Message msg = Message.obtain();
            msg.what = 2;
            mHandler.sendMessage(msg);
        }
    }


    /*
     * 在转动期间（即TimeThread保持sleep期间），一直计算序号
     */
    private class AnimThread extends Thread {

        private boolean isStopped = false;

        //停止计算序号
        public void stopAnim() {
            isStopped = true;
            animThread.interrupt();
        }

        @Override
        public void run() {
            while (!isStopped) {
                //计算当前选中图片的序号
                currentId++;
                L.test("currentId=====>>>"+currentId);
                if (currentId > 11) {
                    currentId = 0;
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //发送结果给MyHandler进行处理
                Message msg = Message.obtain();
                msg.arg2 = currentId;
                msg.what = 1;
                mHandler.sendMessage(msg);

            }
        }
    }


    /**
     * 处理转盘转动过程中的界面变化
     */
    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //序号计算器发送过来的消息
            if (msg.what == 1) {
                //清空所有图片背景
                for (int i = 0; i < mNameArr.length; i++) {
                    TextView iv = mImgArr[i];
                    iv.setBackgroundResource(R.mipmap.triger_nce_bg);
                    iv.setTextColor(Color.parseColor("#7f3e3e"));
                }
                //将选中图片背景设置成黄色
                int index = msg.arg2;
                TextView selectIv = mImgArr[index];
                selectIv.setBackgroundResource(R.mipmap.triger_nce_bg_sel);
                selectIv.setTextColor(Color.parseColor("#ffffff"));
            }
            //随机计时器发过来的消息
            else if (msg.what == 2) {
                //压中
//                if (getResult(currentId)) {
//                    if (mBetName.startsWith("猕猴桃")) {
//                        mBetTotalMoney = mBetTotalMoney + mBetMoney * 1;
//                        Toast.makeText(Tiger_MainActivity.this, "恭喜您中彩金 " + mBetMoney * 1, Toast.LENGTH_SHORT).show();
//                    } else if (mBetName.equals("苹果")) {
//                        mBetTotalMoney = mBetTotalMoney + mBetMoney * 2;
//                        Toast.makeText(Tiger_MainActivity.this, "恭喜您中彩金 " + mBetMoney * 2, Toast.LENGTH_SHORT).show();
//                    } else if (mBetName.equals("西瓜")) {
//                        mBetTotalMoney = mBetTotalMoney + mBetMoney * 3;
//                        Toast.makeText(Tiger_MainActivity.this, "恭喜您中彩金 " + mBetMoney * 3, Toast.LENGTH_SHORT).show();
//                    } else if (mBetName.equals("草莓")) {
//                        mBetTotalMoney = mBetTotalMoney + mBetMoney * 4;
//                        Toast.makeText(Tiger_MainActivity.this, "恭喜您中彩金 " + mBetMoney * 4, Toast.LENGTH_SHORT).show();
//                    }
////                    moneyTv.setText(mBetTotalMoney + "");
//                }
//                //押错了
//                else {
//                    Toast.makeText(Tiger_MainActivity.this, "没中，再来一次?", Toast.LENGTH_SHORT).show();
//                }
                //计算完成之后，需要将押注人名和押注金额清空
                mBetName = "";
                mBetMoney = 0;
                timeThread.interrupt();

                clearData();
            }
        }
    }
    private void clearData(){
        mBetTv1.setText(getString(R.string.mutual59));
        mBetTv2.setText(getString(R.string.mutual59));
        mBetTv3.setText(getString(R.string.mutual59));
        mBetTv4.setText(getString(R.string.mutual59));
        mBetTv5.setText(getString(R.string.mutual59));

        mBetNceTv1.setText("");
        mBetNceTv2.setText("");
        mBetNceTv3.setText("");
        mBetNceTv4.setText("");
        mBetNceTv5.setText("");

        currentId = 0;
        index=0;

        value1=0;
        value5=0;
        value10=0;
        value20=0;
        value100=0;

        isStart=false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 500) {
            mBetName = data.getStringExtra("name");
            mBetMoney = data.getIntExtra("money", 0);
            mBetTotalMoney = mBetTotalMoney - mBetMoney;
            if (mBetTotalMoney >= 0) {
//                moneyTv.setText(mBetTotalMoney + "");
            } else {
//                moneyTv.setText(0 + "");
            }
        }
    }


    /**
     * 比较下注的水果名和当前选择器选中的水果名是否相同，返回一个boolean型数值
     *
     * @param index
     * @return
     */
    private boolean getResult(int index) {
        String selectName = mNameArr[index];
        return selectName.startsWith(mBetName);
    }

    //重写onPause方法，游戏被kill掉时，保存金币数量
    @Override
    protected void onPause() {
        currentId=0;
//        for (int i = 0; i < mNameArr.length; i++) {
//            TextView iv = mImgArr[i];
//            iv.setBackgroundColor(Color.TRANSPARENT);
//        }
//        SharedPreferences.Editor editor = getSharedPreferences("money", MODE_PRIVATE).edit();
//        editor.putInt("money", mBetTotalMoney);
//        editor.commit();
        super.onPause();
    }



    private void WinningList() {
        ApiStore.createApi(ApiService.class)
                .WinningList(3)
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


}
