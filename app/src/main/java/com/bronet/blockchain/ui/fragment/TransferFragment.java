package com.bronet.blockchain.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.bronet.blockchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TransferFragment extends Fragment {
    @BindView(R.id.ldentifyC)
    RadioButton mLdentifyC;
    @BindView(R.id.myTransfer)
    RadioButton mMyTransfer;
    @BindView(R.id.fram_bill)
    FrameLayout mFramBill;
    private View view;
    private Unbinder unbinder;
    private FragmentTransaction fragmentTransaction;
    private IdentifyChipsFragment ldentifyFragment;
    private MyTransferFragment mytransferFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tran_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        initFragment();
        return view;
    }

    public void refreshData(){
        if(ldentifyFragment!=null) {
            ldentifyFragment.setData();
        }
    }

    private void initFragment() {
        mLdentifyC.setSelected(true);
        //获取布局管理器
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //Fragment
        ldentifyFragment = new IdentifyChipsFragment();
        mytransferFragment = new MyTransferFragment();

        //加入布局管理器
        fragmentTransaction.add(R.id.fram_bill, ldentifyFragment);
        fragmentTransaction.add(R.id.fram_bill, mytransferFragment);
        //默认展示
        fragmentTransaction.hide(mytransferFragment);
        //提交
        fragmentTransaction.commit();
    }

    @OnClick({R.id.ldentifyC, R.id.myTransfer})
    public void onClick(View v) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(ldentifyFragment);
        fragmentTransaction.hide(mytransferFragment);
        switch (v.getId()) {

            case R.id.ldentifyC:
                fragmentTransaction.show(ldentifyFragment);
                mLdentifyC.setSelected(true);
                mMyTransfer.setSelected(false);
                break;
            case R.id.myTransfer:
                fragmentTransaction.show(mytransferFragment);
                mLdentifyC.setSelected(false);
                mMyTransfer.setSelected(true);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}