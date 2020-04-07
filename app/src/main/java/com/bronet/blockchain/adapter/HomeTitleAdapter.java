package com.bronet.blockchain.adapter;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Banner;
import com.bronet.blockchain.ui.CenterActivity;
import com.bronet.blockchain.ui.MyNodeListActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18514 on 2019/5/16.
 */

public class HomeTitleAdapter extends PagerAdapter {
    BaseActivity activity;
    List<Banner.Result> titleList;
    ViewPager titleVp;
    List<View> list=new ArrayList<>();
    private ImageView view;

    public HomeTitleAdapter(final BaseActivity activity, final List<Banner.Result> titleList, ViewPager titleVp) {
        this.activity=activity;
        this.titleList=titleList;
        this.titleVp=titleVp;

        for (int i = 0; i < titleList.size(); i++) {
            ImageView view = (ImageView) LayoutInflater.from(activity).inflate(R.layout.item_title_iv, null);
            list.add(view);

        }
    }

    @Override
    public int getCount() {
        return list.size()!=0?Integer.MAX_VALUE:0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view = null;
        try {
            int temp = position % list.size();
            view = (ImageView) LayoutInflater.from(activity).inflate(R.layout.item_title_iv, null);
            Glide.with(activity).load(titleList.get(temp).getCn_image()).into(view);
            container.addView(view);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.overlay(activity, CenterActivity.class);
            }
        });
        return view;


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
