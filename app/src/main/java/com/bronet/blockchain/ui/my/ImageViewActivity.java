package com.bronet.blockchain.ui.my;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * 留言列表
 * Created by 18514 on 2019/7/26.
 */

public class ImageViewActivity extends BaseActivity{
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.imageview)
    ImageView imageview;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_imageview;
    }

    @Override
    protected void initView() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_tv.setText("图片");
    }

    @Override
    protected void initData() {
       String imageUrl =  getIntent().getStringExtra("imageurl");
       if(!TextUtils.isEmpty(imageUrl)) {
           Glide.with(activity).load(imageUrl).into(imageview);
       }
    }

    @Override
    protected void setEvent() {
    }


}
