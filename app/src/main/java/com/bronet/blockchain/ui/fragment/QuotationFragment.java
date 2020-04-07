package com.bronet.blockchain.ui.fragment;

import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.QuotationAdapter;
import com.bronet.blockchain.adapter.TitleAdapter;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.Candles1000;
import com.bronet.blockchain.data.CoinTypeResp;
import com.bronet.blockchain.data.CustomCoin;
import com.bronet.blockchain.data.Tickers;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.ApiStore2;
import com.bronet.blockchain.util.StringUtil;
import com.github.mikephil.charting.stockChart.FiveDayChart;
import com.github.mikephil.charting.stockChart.KLineChart;
import com.github.mikephil.charting.stockChart.OneDayChart;
import com.github.mikephil.charting.stockChart.dataManage.KLineDataManage;
import com.github.mikephil.charting.stockChart.dataManage.TimeDataManage;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 行情
 * Created by 18514 on 2019/6/27.
 */

public class QuotationFragment extends BaseFragment {
    /* @BindView(R.id.tl_1)
     SegmentTabLayout tl1;*/
    @BindView(R.id.tit_rv)
    RecyclerView titRv;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.day_select_tv)
    TextView daySelectTv;
    @BindView(R.id.low_price_tv)
    TextView lowPriceTv;
    @BindView(R.id.high_price_tv)
    TextView highPriceTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.rate_tv)
    TextView rateTv;
//    @BindView(R.id.kchart_view)
//    KChartView mKChartView;

    @BindView(R.id.ll_fenzhong)
    LinearLayout ll_fenzhong;
    @BindView(R.id.ll_fenzhong_eth)
    LinearLayout ll_fenzhong_eth;
    @BindView(R.id.ll_fenzhong_nce)
    LinearLayout ll_fenzhong_nce;
    @BindView(R.id.ll_fiveday)
    LinearLayout ll_fiveday;
    @BindView(R.id.ll_combined)
    LinearLayout ll_combined;

    @BindView(R.id.chart_fengzhong)
    OneDayChart chart_fengzhong;
    @BindView(R.id.chart_fengzhong_eth)
    OneDayChart chart_fengzhong_eth;
    @BindView(R.id.chart_fengzhong_nce)
    OneDayChart chart_fengzhong_nce;
    @BindView(R.id.chart5)
    FiveDayChart chart5;
    @BindView(R.id.combinedchart)
    KLineChart combinedchart;
    ArrayList<Tickers.Data> list = new ArrayList<>();
    ArrayList<String> titlelist = new ArrayList<>();
    QuotationAdapter homeAdapter;
    TitleAdapter titleAdapter;
    ArrayList<Tickers.Data> data;
    ArrayList<String> coins = new ArrayList<>();
    @BindView(R.id.loding)
    ProgressBar loding;
    Unbinder unbinder;
    private PopupWindow popWindow;
    private String coinName;

    private int dataRange;

    private JSONObject object;

    private KLineDataManage kLineData;

    @Override
    protected void initEvent() {

        titleAdapter.setOnItemClickListener((baseQuickAdapter, view, i) -> {
            titleAdapter.setType(i, titRv);
            coinName = titleAdapter.getItem(i);
            getQuoListForCoinType(coinName);
            getCoinDetailQuo(coinName);
            titleAdapter.setThisPosition(i);
            titleAdapter.notifyDataSetChanged();
            Candles(coinName, dataRange);
        });

        daySelectTv.setOnClickListener(view -> {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.spinner_pop, null);
            TextView onedayTv = inflate.findViewById(R.id.oneday_tv);
            TextView servendayTv = inflate.findViewById(R.id.servenday_tv);
            TextView thirtydayTv = inflate.findViewById(R.id.thirtyday_tv);
            TextView yeardayTv = inflate.findViewById(R.id.yearday_tv);
            onedayTv.setOnClickListener(view1 -> {
                daySelectTv.setText(R.string.time01);
                popWindow.dismiss();
                Candles(coinName, 0);
                dataRange = 0;
            });
            servendayTv.setOnClickListener(view12 -> {
                daySelectTv.setText(R.string.time02);
                popWindow.dismiss();
                Candles(coinName, 1);
                ll_fenzhong.setVisibility(View.GONE);
                ll_fenzhong_eth.setVisibility(View.GONE);
                ll_fenzhong_nce.setVisibility(View.GONE);
                ll_combined.setVisibility(View.VISIBLE);

                dataRange = 1;
            });
            thirtydayTv.setOnClickListener(view13 -> {
                daySelectTv.setText(R.string.time03);
                popWindow.dismiss();
                Candles(coinName, 2);
                ll_fenzhong.setVisibility(View.GONE);
                ll_fenzhong_eth.setVisibility(View.GONE);
                ll_fenzhong_nce.setVisibility(View.GONE);
                ll_combined.setVisibility(View.VISIBLE);

                dataRange = 2;
            });
            yeardayTv.setOnClickListener(view13 -> {
                daySelectTv.setText(R.string.time031);
                popWindow.dismiss();
                Candles(coinName, 3);
                ll_fenzhong.setVisibility(View.GONE);
                ll_fenzhong_eth.setVisibility(View.GONE);
                ll_fenzhong_nce.setVisibility(View.GONE);
                ll_combined.setVisibility(View.VISIBLE);
                dataRange = 3;

            });
            popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int[] location = new int[2];
            popWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
            popWindow.setFocusable(true);
            if (Build.VERSION.SDK_INT < 24) {
                popWindow.showAsDropDown(view);
            } else {
                // 适配 android 7.0
                view.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                popWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y + view.getHeight() + 10);
            }
            popWindow.setOnDismissListener(() -> {

            });
        });
    }

    @Override
    protected void initData() {
        getCoinList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取币种列表
     */
    private void getCoinList() {
        ApiStore.createApi(ApiService.class)
                .CustomCoin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CustomCoin>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CustomCoin customCoin) {
                        if (customCoin.getStatus() == 0) {
                            coins = customCoin.getResult();
                            titleAdapter.setNewData(coins);

                            //刚进去界面，请求第一个币种数据（包括详情和列表）
                            coinName = coins.get(0);
                            getQuoListForCoinType(coinName);
                            getCoinDetailQuo(coinName);


                            daySelectTv.setText(R.string.time01);
                            Candles(coinName, 0);
                            ll_fenzhong.setVisibility(View.VISIBLE);
                            titleAdapter.setThisPosition(0);
                            titleAdapter.notifyDataSetChanged();
                            dataRange = 0;
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
     * //获取某币种的详细行情
     *
     * @param coinName
     */
    private void getCoinDetailQuo(String coinName) {
        ApiStore2.createApi(ApiService.class)
                .CoinDetail(coinName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinTypeResp>() {


                    @Override
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@androidx.annotation.NonNull CoinTypeResp coinTypeResp) {
                        Log.d("-------", "onNext: " + coinTypeResp.getMsg());
                        if (coinTypeResp.getCode() == 0) {

                            String last = coinTypeResp.getData().getLast();
                            String highDay = coinTypeResp.getData().getDayHigh();
                            String lowDay = coinTypeResp.getData().getDayLow();
                            String changePercentage = coinTypeResp.getData().getChangePercentage();
                            priceTv.setText(last);
                            highPriceTv.setText(highDay);
                            lowPriceTv.setText(lowDay);
                            rateTv.setText(changePercentage);
                        }

                    }

                    @Override
                    public void onError(@androidx.annotation.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * //获取某币种行情列表
     *
     * @param type
     */
    private void getQuoListForCoinType(String type) {
        ApiStore2.createApi(ApiService.class)
                .Tickers(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Tickers>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        loding.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull Tickers tickers) {
                        if (tickers.getCode() == 0) {
                            data = tickers.getData();
                            homeAdapter.setNewData(data);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (loding != null) {
                            loding.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        titRv.setLayoutManager(linearLayoutManager);
        titleAdapter = new TitleAdapter(R.layout.item_title, titlelist);
        titRv.setAdapter(titleAdapter);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new QuotationAdapter(R.layout.item_quotaion, list);
        homeAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.item_empty, null));
        rv.setAdapter(homeAdapter);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_quotation;
    }

    public static Fragment getInstance() {
        return new QuotationFragment();
    }


    //根据某阶段，获取走势

    /**
     * @param instrumentId BTC,EHT
     * @param dateRange    0:分钟,1：小时：2，日,3：周
     */
    private void Candles1000(String instrumentId, int dateRange) {
        Data data = new Data();
        data.instrumentId = instrumentId;
        data.dateRange = dateRange;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore2.createApi(ApiService.class)
                .Candles1000(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Candles1000>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Candles1000 candles) {
                        String[][] arrays = candles.getData();
                        if (dateRange == 0) {  //分时
                            try {
                                ll_combined.setVisibility(View.GONE);
                                if(instrumentId.equals("BTC")) {
                                    ll_fenzhong_eth.setVisibility(View.GONE);
                                    ll_fenzhong_nce.setVisibility(View.GONE);
                                    ll_fenzhong.setVisibility(View.VISIBLE);
//                                    String[][] newArrays = new String[arrays.length][6];
//                                    for (int i = arrays.length - 1; i >= 0; i--) {
//                                        for (int j = arrays[i].length - 1; j >= 0; j--) {
//                                            newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
//                                        }
//                                    }

                                    String str = new JSONArray(arrays).toString();
                                    String allStr = "{\"data\": " + str + ",\"preClose\": \"7600\"}";
                                    object = new JSONObject(allStr);
                                    chart_fengzhong.initChart(false);
                                    TimeDataManage kTimeData = new TimeDataManage();
                                    kTimeData.parseTimeData(object, "000001.IDX.SH", 0);
                                    chart_fengzhong.setDataToChart(kTimeData, object);
                                }else if(instrumentId.equals("ETH")) {
                                    ll_fenzhong_eth.setVisibility(View.VISIBLE);
                                    ll_fenzhong.setVisibility(View.GONE);
                                    ll_fenzhong_nce.setVisibility(View.VISIBLE);
//                                    String[][] newArrays = new String[arrays.length][6];
//                                    for (int i = arrays.length - 1; i >= 0; i--) {
//                                        for (int j = arrays[i].length - 1; j >= 0; j--) {
//                                            newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
//                                        }
//                                    }
                                    String str = new JSONArray(arrays).toString();
                                    String allStr = "{\"data\": " + str + ",\"preClose\": \"145\"}";
                                    object = new JSONObject(allStr);
                                    chart_fengzhong_eth.initChart(false);
                                    TimeDataManage kTimeData = new TimeDataManage();
                                    kTimeData.parseTimeData(object, "000001.IDX.SH", 0);
                                    chart_fengzhong_eth.setDataToChart(kTimeData, object);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dateRange == 1) { //1小时
                            kLineData = new KLineDataManage(getActivity());
                            combinedchart.initChart(false);
                            try {
//                                String[][] newArrays = new String[arrays.length][6];
//                                for (int i = arrays.length - 1; i >= 0; i--) {
//                                    for (int j = arrays[i].length - 1; j >= 0; j--) {
//                                        newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
//                                    }
//                                }

                                String str = new JSONArray(arrays).toString();
                                String allStr = "{\"data\": " + str + "}";
                                object = new JSONObject(allStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            kLineData.parseKlineData(object, "000001.IDX.SH", false);
                            combinedchart.setDataToChart(kLineData);
                        } else if (dateRange == 2) { //1日
                            kLineData = new KLineDataManage(getActivity());
                            combinedchart.initChart(false);
                            try {

//                                String[][] newArrays = new String[arrays.length][6];
//                                for (int i = arrays.length - 1; i >= 0; i--) {
//                                    for (int j = arrays[i].length - 1; j >= 0; j--) {
//                                        newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
//                                    }
//                                }

                                String str = new JSONArray(arrays).toString();
                                String allStr = "{\"data\": " + str + "}";
                                object = new JSONObject(allStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            kLineData.parseKlineData(object, "000001.IDX.SH", false);
                            combinedchart.setDataToChart(kLineData);
                        }else if(dateRange==3){ //1周
                            kLineData = new KLineDataManage(getActivity());
                            combinedchart.initChart(false);
                            try {

//                                String[][] newArrays = new String[arrays.length][6];
//                                for (int i = arrays.length - 1; i >= 0; i--) {
//                                    for (int j = arrays[i].length - 1; j >= 0; j--) {
//                                        newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
//                                    }
//                                }

                                String str = new JSONArray(arrays).toString();
                                String allStr = "{\"data\": " + str + "}";
                                object = new JSONObject(allStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            kLineData.parseKlineData(object, "000001.IDX.SH", false);
                            combinedchart.setDataToChart(kLineData);
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

    //根据某阶段，获取走势

    /**
     * @param instrumentId BTC,EHT
     * @param dateRange    0:分钟,1：小时：2，日,3：周
     */
    private void Candles(String instrumentId, int dateRange) {
        Data data = new Data();
        data.instrumentId = instrumentId;
        data.dateRange = dateRange;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore2.createApi(ApiService.class)
                .Candles(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String[][]>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String[][] arrays) {
                        if (dateRange == 0) {  //分时
                            try {
                                ll_combined.setVisibility(View.GONE);
                                if(instrumentId.equals("BTC")) {
                                    ll_fenzhong_eth.setVisibility(View.GONE);
                                    ll_fenzhong_nce.setVisibility(View.GONE);
                                    ll_fenzhong.setVisibility(View.VISIBLE);
                                    String[][] newArrays = new String[arrays.length][6];
                                    for (int i = arrays.length - 1; i >= 0; i--) {
                                        for (int j = arrays[i].length - 1; j >= 0; j--) {
                                            newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
                                        }
                                    }
                                    String str = new JSONArray(newArrays).toString();
                                    String allStr = "{\"data\": " + str + ",\"preClose\": \"7600\"}";
                                    object = new JSONObject(allStr);
                                    chart_fengzhong.initChart(false);
                                    TimeDataManage kTimeData = new TimeDataManage();
                                    kTimeData.parseTimeData(object, "000001.IDX.SH", 0);
                                    chart_fengzhong.setDataToChart(kTimeData, object);
                                }else if(instrumentId.equals("ETH")) {
                                    ll_fenzhong_eth.setVisibility(View.VISIBLE);
                                    ll_fenzhong_nce.setVisibility(View.GONE);
                                    ll_fenzhong.setVisibility(View.GONE);
                                    String[][] newArrays = new String[arrays.length][6];
                                    for (int i = arrays.length - 1; i >= 0; i--) {
                                        for (int j = arrays[i].length - 1; j >= 0; j--) {
                                            newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
                                        }
                                    }
                                    String str = new JSONArray(newArrays).toString();
                                    String allStr = "{\"data\": " + str + ",\"preClose\": \"145\"}";
                                    object = new JSONObject(allStr);
                                    chart_fengzhong_eth.initChart(false);
                                    TimeDataManage kTimeData = new TimeDataManage();
                                    kTimeData.parseTimeData(object, "000001.IDX.SH", 0);
                                    chart_fengzhong_eth.setDataToChart(kTimeData, object);
                                }else if(instrumentId.equals("NCE")) {
                                    ll_fenzhong_nce.setVisibility(View.VISIBLE);
                                    ll_fenzhong_eth.setVisibility(View.GONE);
                                    ll_fenzhong.setVisibility(View.GONE);
                                    String[][] newArrays = new String[arrays.length][6];
                                    for (int i = arrays.length - 1; i >= 0; i--) {
                                        for (int j = arrays[i].length - 1; j >= 0; j--) {
                                            newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
                                        }
                                    }
                                    String str = new JSONArray(newArrays).toString();
                                    String allStr = "{\"data\": " + str + ",\"preClose\": \"0.46\"}";
                                    object = new JSONObject(allStr);
                                    chart_fengzhong_nce.initChart(false);
                                    TimeDataManage kTimeData = new TimeDataManage();
                                    kTimeData.parseTimeData(object, "000001.IDX.SH", 0);
                                    chart_fengzhong_nce.setDataToChart(kTimeData, object);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (dateRange == 1) { //1小时
                            kLineData = new KLineDataManage(getActivity());
                            combinedchart.initChart(false);
                            try {
                                String[][] newArrays = new String[arrays.length][6];
                                for (int i = arrays.length - 1; i >= 0; i--) {
                                    for (int j = arrays[i].length - 1; j >= 0; j--) {
                                        newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
                                    }
                                }

                                String str = new JSONArray(newArrays).toString();
                                String allStr = "{\"data\": " + str + "}";
                                object = new JSONObject(allStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            kLineData.parseKlineData(object, "000001.IDX.SH", false);
                            combinedchart.setDataToChart(kLineData);
                        } else if (dateRange == 2) { //1日
                            kLineData = new KLineDataManage(getActivity());
                            combinedchart.initChart(false);
                            try {

                                String[][] newArrays = new String[arrays.length][6];
                                for (int i = arrays.length - 1; i >= 0; i--) {
                                    for (int j = arrays[i].length - 1; j >= 0; j--) {
                                        newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
                                    }
                                }

                                String str = new JSONArray(newArrays).toString();
                                String allStr = "{\"data\": " + str + "}";
                                object = new JSONObject(allStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            kLineData.parseKlineData(object, "000001.IDX.SH", false);
                            combinedchart.setDataToChart(kLineData);
                        }else if(dateRange==3){ //1周
                            kLineData = new KLineDataManage(getActivity());
                            combinedchart.initChart(false);
                            try {

                                String[][] newArrays = new String[arrays.length][6];
                                for (int i = arrays.length - 1; i >= 0; i--) {
                                    for (int j = arrays[i].length - 1; j >= 0; j--) {
                                        newArrays[arrays.length - 1 - i][arrays[i].length - 1 - j] = arrays[i][j];
                                    }
                                }

                                String str = new JSONArray(newArrays).toString();
                                String allStr = "{\"data\": " + str + "}";
                                object = new JSONObject(allStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            kLineData.parseKlineData(object, "000001.IDX.SH", false);
                            combinedchart.setDataToChart(kLineData);
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

    public class Data {
        String instrumentId;
        int dateRange;
    }
}
