package com.bronet.blockchain.ui.game.flop.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bronet.blockchain.ui.game.flop.listner.FlopTextViewListner;
import com.bronet.blockchain.util.L;

/**
 * Created by lxp  on 2018/5/10.
 * 正面和反面的
 */
public class FlopImageView extends RelativeLayout {
    private ImageView mPositiveIv;//正面
    private ImageView mOositiveIv;//反面
    private FlopTextViewListner mTextViewListner;//文字回调
    private Context mContext;
    public FlopImageView(Context context) {
        this(context,null);
    }


    public FlopImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }

    public FlopImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext  = context;

    }

    public void initView() {
        mPositiveIv = new ImageView(mContext);
        RelativeLayout.LayoutParams mIvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mIvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        mIvParams.setMargins(15, 15, 15, 15);
        mPositiveIv.setLayoutParams(mIvParams);
        mPositiveIv.setVisibility(View.VISIBLE);

        mOositiveIv = new ImageView(mContext);
        mOositiveIv.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mOositiveIv.setVisibility(View.GONE);

        addView(mPositiveIv);
        addView(mOositiveIv);
    }

    //初始化view
    public void initDrableImageUrl(Drawable positiviUrl){
        mPositiveIv.setBackground(positiviUrl);
    }
    public void startAnima(final int position, int duration){
        ScaleAnimation anim = new ScaleAnimation(1,0,1,1
        , Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                L.test("pkp========>>>onAnimationStart==========>>>");
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                L.test("pkp========>>>onAnimationEnd==========>>>");
                if (mTextViewListner != null){
                    mTextViewListner.onDrawText(position);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                L.test("pkp========>>>onAnimationRepeat==========>>>");
            }
        });
        mPositiveIv.startAnimation(anim);
    }

    /**
     * 设置动画结束后绘制文字回调
     * @param listner
     */
    public void setOnFlopTextViewListner(FlopTextViewListner listner){
        mTextViewListner = listner;
    }

}






