package com.bronet.blockchain.ui.login;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.util.FileUtils;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 18514 on 2019/7/15.
 */

public class AuxiliariesActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.tagFlowLayout1)
    TagFlowLayout tagFlowLayout1;
    @BindView(R.id.tagFlowLayout2)
    TagFlowLayout tagFlowLayout2;
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    String datas;
    //正确排列
    ArrayList<String> dataList = new ArrayList<>();
    //打乱排列
    ArrayList<String> dataList2 = new ArrayList<>();
    //多次使用打乱的排列
    ArrayList<String> dataList3 = new ArrayList<>();
    //验证排列
    ArrayList<String> dataList4 = new ArrayList<>();
    PopupWindow popWindow;
    @BindView(R.id.sc1)
    ScrollView sc1;
    @BindView(R.id.sc2)
    ScrollView sc2;
    private long time;
    Boolean TYPE;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aa;
    }

    @Override
    protected void initView() {


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    @Override
    protected void initData() {
        datas = getIntent().getExtras().getString("data");
        if (L.isDebug) {
//            datas = "ostrich ethics zero glare version bind wise modify source undo decorate round";
        }
        String[] words = datas.split(" ");
        for (String s : words) {
            dataList.add(s);
        }

        ArrayList<String> newList = new ArrayList<>();
        newList.addAll(dataList);
        for (int i = 0; i < newList.size(); i++) {
            Random ra = new Random();
            int i1 = ra.nextInt(newList.size());
            dataList2.add(newList.get(i1));
            newList.remove(i1);
            i--;
        }
        dataList3.addAll(dataList2);
        TagAdapter<String> tagAdapter1 = new TagAdapter<String>(dataList3) {
            @Override
            public View getView(FlowLayout parent, int position, String seachItem) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview_item_hot, null);
                String name = seachItem;
                text.setText(name);
                return text;
            }
        };
        TagAdapter<String> tagAdapter2 = new TagAdapter<String>(dataList4) {
            @Override
            public View getView(FlowLayout parent, int position, String seachItem) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview_item_hot, null);
                String name = seachItem;
                text.setText(name);
                return text;
            }
        };
        tagFlowLayout1.setMaxSelectCount(dataList3.size());
        tagFlowLayout1.setAdapter(tagAdapter1);
        tagFlowLayout2.setMaxSelectCount(dataList4.size());
        tagFlowLayout2.setAdapter(tagAdapter2);

        try {
            FileUtils.writePublishMesage("dataList3====>>>" + Arrays.asList(dataList3.subList(0, 5)));
            FileUtils.writePublishMesage("dataList4====>>>" + Arrays.asList(dataList4.subList(0, 5)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void setEvent() {
        tagFlowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String s = dataList3.get(position);
                dataList4.add(s);
                dataList3.remove(position);
                tagFlowLayout1.getAdapter().notifyDataChanged();
                tagFlowLayout2.getAdapter().notifyDataChanged();
                return true;
            }
        });
        tagFlowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                dataList4.remove(position);
                dataList3.clear();
                dataList3.addAll(dataList2);
                for (int i = 0; i < dataList3.size(); i++) {
                    boolean type = false;
                    for (int j = 0; j < dataList4.size(); j++) {
                        if (dataList4.get(j).equals(dataList3.get(i))) {
                            type = true;
                        }
                    }
                    if (type) {
                        dataList3.remove(i);
                        i--;
                    }
                }
                tagFlowLayout1.getAdapter().notifyDataChanged();
                tagFlowLayout2.getAdapter().notifyDataChanged();
                return true;
            }
        });

    }

    @OnClick({R.id.btn_back, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_ok:
                String btnStr = btnOk.getText().toString();
                if (btnStr.equals(getString(R.string.aa3))) {
                    btnOk.setText(getString(R.string.invest39));
                    data.setText(datas);
                    data.setVisibility(View.VISIBLE);
                } else if (btnStr.equals(getString(R.string.invest39))) {
                    data.setText("");
                    data.setVisibility(View.GONE);
                    btnOk.setText(getString(R.string.ok));
                    sc1.setVisibility(View.VISIBLE);
                    sc2.setVisibility(View.VISIBLE);
                } else if (btnStr.equals(getString(R.string.ok))) {
                    StringBuffer stringBuffer1 = new StringBuffer();
                    StringBuffer stringBuffer2 = new StringBuffer();
                    for (int i = 0; i < dataList.size(); i++) {
                        stringBuffer1.append(dataList.get(i));
                    }
                    for (int i = 0; i < dataList4.size(); i++) {
                        stringBuffer2.append(dataList4.get(i));
                    }
                    if (stringBuffer1.toString().equals(stringBuffer2.toString())) {
                        TYPE = true;
                        showPop();
                    } else {
                        TYPE = false;
                        ToastUtil.show(activity, getString(R.string.d16));
                    }
                }
                break;
        }
    }

    private void showPop() {
        View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop2, null);
        initB(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        changeWindowAlfa(0.5f);
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.showAtLocation(parent,
                Gravity.CENTER, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }

    private void initB(View inflate) {
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                setResult(11);
                finish();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                setResult(11);
                finish();
            }
        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        if(TYPE==null){
            finish();
            return;
        }
        if (TYPE) {
            setResult(11);
            finish();
        } else {
            return;
        }
    }
}
