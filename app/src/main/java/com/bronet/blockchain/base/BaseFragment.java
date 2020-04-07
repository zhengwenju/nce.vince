package com.bronet.blockchain.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.ui.Constants;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.L;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 18514 on 2019/1/7.
 */

public abstract class BaseFragment extends Fragment {
    public View mContentView;
    private Context mContext;
    private  Unbinder unbinder;
    protected Activity mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharePreData sharePreData = new SharePreData();
        Constants.langae=sharePreData.getString(Const.LANGUAGE_KEY,"zh");
        L.test("Constants.langae==========>>>"+Constants.langae);
        Locale locale = new Locale(Constants.langae);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());


        mContentView = inflater.inflate(setLayoutId(),container,false);
        unbinder = ButterKnife.bind(this, mContentView);
        mActivity = getActivity();
        initView();
        initData();
        initEvent();
        return mContentView;
    }

    protected abstract void initEvent();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int setLayoutId();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
