package com.bronet.blockchain.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.HistoricalAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.base.BaseFragment;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.ui.historicalfragment.HalfYearFragment;
import com.bronet.blockchain.ui.historicalfragment.MonthFragment;
import com.bronet.blockchain.ui.historicalfragment.WeekFragment;
import com.bronet.blockchain.ui.historicalfragment.YearFragment;
import com.bronet.blockchain.util.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoricalRecordActivity extends BaseActivity {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.btn_01)
    RadioButton mBtn01;
    @BindView(R.id.view01)
    View view;
    @BindView(R.id.btn_02)
    RadioButton mBtn02;
    @BindView(R.id.view02)
    View view2;
    @BindView(R.id.btn_03)
    RadioButton mBtn03;
    @BindView(R.id.view03)
    View view3;
    @BindView(R.id.btn_04)
    RadioButton mBtn04;
    @BindView(R.id.view04)
    View view4;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private int buysellrecord;
    private TransCoins.ResultBean bean;
    private TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_historical_record;
    }

    @Override
    protected void initView() {

        title =findViewById(R.id.title_tv);
        List<Fragment> list = new ArrayList<>();
        list.add(new WeekFragment());
        list.add(new MonthFragment());
        list.add(new HalfYearFragment());
        list.add(new YearFragment());
        HistoricalAdapter adapter = new HistoricalAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);

        mBtn01.setSelected(true);
        Bundle extras = getIntent().getExtras();
        buysellrecord = extras.getInt(Const.BUYSELLRECORD);
        bean = (TransCoins.ResultBean)extras.getSerializable(Const.BUYSELLRECORDBEAN);
        if(buysellrecord ==0){
            title.setText(getString(R.string.transf11));
        }else if (buysellrecord==1){
            title.setText(getString(R.string.transf12));
        }
        mBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                mBtn01.setSelected(true);
                mBtn02.setSelected(false);
                mBtn03.setSelected(false);
                mBtn04.setSelected(false);
                view.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);

            }
        });
        mBtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                mBtn01.setSelected(false);
                mBtn02.setSelected(true);
                mBtn03.setSelected(false);
                mBtn04.setSelected(false);
                view.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
            }
        });
        mBtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
                mBtn01.setSelected(false);
                mBtn02.setSelected(false);
                mBtn03.setSelected(true);
                mBtn04.setSelected(false);
                view.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.VISIBLE);
                view4.setVisibility(View.GONE);
            }
        });
        mBtn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);
                mBtn01.setSelected(false);
                mBtn02.setSelected(false);
                mBtn03.setSelected(false);
                mBtn04.setSelected(true);
                view.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.VISIBLE);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    //设置选中
                    mBtn01.setSelected(true);
                    mBtn02.setSelected(false);
                    mBtn03.setSelected(false);
                    mBtn04.setSelected(false);
                    view.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);
                }else if (position==1){
                    mBtn01.setSelected(false);
                    mBtn02.setSelected(true);
                    mBtn03.setSelected(false);
                    mBtn04.setSelected(false);
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);
                }else if (position==2){
                    mBtn01.setSelected(false);
                    mBtn02.setSelected(false);
                    mBtn03.setSelected(true);
                    mBtn04.setSelected(false);
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.GONE);
                }else if (position==3){
                    mBtn01.setSelected(false);
                    mBtn02.setSelected(false);
                    mBtn03.setSelected(false);
                    mBtn04.setSelected(true);
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    view4.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setEvent() {

    }
    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
