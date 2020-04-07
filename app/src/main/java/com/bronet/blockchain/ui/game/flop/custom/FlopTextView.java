package com.bronet.blockchain.ui.game.flop.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.ui.game.flop.listner.FlopOnAnimationEndListner;
import com.bronet.blockchain.ui.game.flop.listner.FlopTextViewListner;
import com.bronet.blockchain.util.L;
import com.bumptech.glide.Glide;


/**
 * Created by lxp  on 2018/4/27.
 */
public class FlopTextView extends RelativeLayout implements FlopTextViewListner {
    private int mTextColor;//字体颜色
    private int mTextSize;//字体大小
    private int mIsText;//是否显示文字，1显示
    private String mText;//显示的文字
    private FlopImageView mFlopView;//翻牌动画
    private TextPaint mPaint;
    private Context context;
    private int marginLeft, marginTop, marginRight, marginBottom;//设置边距
    private FlopOnAnimationEndListner mFlopAnimEnd;//动画执行结束监听
    private String mIvUrl;//图片地址

    public FlopTextView(Context context) {
        this(context, null);
    }

    private AttributeSet attrs;
    public FlopTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlopTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mFlopView = new FlopImageView(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlopView);
        mIsText = typedArray.getInteger(R.styleable.FlopView_flopIsText, -1);
        if (mIsText == 0) {
            mText = typedArray.getString(R.styleable.FlopView_flopText);
            mTextColor = typedArray.getInteger(R.styleable.FlopView_flopTextColor, Color.BLACK);
            mTextSize = (int) typedArray.getDimension(R.styleable.FlopView_flopTextSize, 60f);
        }

        mPaint = new TextPaint();
        marginLeft = (int) typedArray.getDimension(R.styleable.FlopView_flopMarginLeft, 0f);
        marginRight = (int) typedArray.getDimension(R.styleable.FlopView_flopMarginRight, 0f);
        marginTop = (int) typedArray.getDimension(R.styleable.FlopView_flopMarginTop, 0f);
        marginBottom = (int) typedArray.getDimension(R.styleable.FlopView_flopMarginBottom, 0f);


        RelativeLayout.LayoutParams flopViewParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        flopViewParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        Log.e("flopWidth", "执行flopTextView");
        flopViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mFlopView.setLayoutParams(flopViewParams);
        addView(mFlopView);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //视图是个正方形的 所以有宽就足够了 默认值是500 也就是WRAP_CONTENT的时候
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
    }

    public void initDrableImageUrl(Drawable positiviUrl) {
        mFlopView.initDrableImageUrl(positiviUrl);
    }


    public void setFlopOnAnimationEndListner( FlopOnAnimationEndListner animationEndListner) {
        mFlopAnimEnd = animationEndListner;
    }

    public void startAnimation(int position) {
        if (mFlopView != null) {
            mFlopView.setOnFlopTextViewListner(this);
            mFlopView.startAnima(position,600);
        }
    }

    public void setConfig(Builder builder) {
        if (builder.mIsText != 0) {
            mIsText = builder.mIsText;
        }
        if (builder.mTextSize != 0) {
            mTextSize = builder.mTextSize;
        }
        if (builder.mTextColor != 0) {
            mTextColor = builder.mTextColor;
        }
        if (!TextUtils.isEmpty(builder.mText)) {
            mText = builder.mText;
        }
        if (!TextUtils.isEmpty(builder.mIvUrl)) {
            mIvUrl = builder.mIvUrl;
        }

    }

    @Override
    public void onDrawText(int type) { //动画播放完成后绘制界面
        L.test("pkp========>>>onDrawText==========>>>");
            if (mIsText == 1) {
                mPaint.setColor(mTextColor);
                mPaint.setTextSize(mTextSize);
                //设置图片
                ImageView mIv = new ImageView(context);
                RelativeLayout.LayoutParams mIvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                mIvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                mIvParams.setMargins(7, 7, 7, 7);
                mIv.setLayoutParams(mIvParams);
                mIv.setBackground(context.getResources().getDrawable(R.mipmap.pkp_show_bg));
                addView(mIv);

//                设置展示的textView
                TextView mTextView = new TextView(context);
                mTextView.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.BELOW, mIv.getId());
                mTextView.setLayoutParams(params);
                mTextView.setTextColor(mTextColor);

                TextPaint paint = mTextView.getPaint();
                paint.setFakeBoldText(true);

                params.setMargins(12, 150, 12, 12);
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                addView(mTextView);
                mTextView.setText(mText);



                TextView tvNce = new TextView(context);
                tvNce.setGravity(Gravity.CENTER);
//                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params1.addRule(RelativeLayout.BELOW, mIv.getId());
                tvNce.setLayoutParams(params1);
                tvNce.setTextColor(mTextColor);

                params1.setMargins(12, 290, 12, 12);
                tvNce.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                addView(tvNce);
                tvNce.setText("NCE");

                //完成后回调
                if (mFlopAnimEnd != null) {
                        mFlopAnimEnd.onAnimationEnd();
                }
            }

    }


    public static final class Builder {
        private int mIsText;//是否显示文字，0显示
        private String mText;//文字内容
        private int mTextColor;//字体颜色
        private int mTextSize;//字体大小
        private String mIvUrl;//显示小图片地址



        public Builder setmIsAnimType(int mIsAnimType) {
            return this;
        }

        public Builder setmTextColor(int mTextColor) {
            this.mTextColor = mTextColor;
            return this;
        }

        public Builder setmTextSize(int mTextSize) {
            this.mTextSize = mTextSize;
            return this;
        }

        public Builder setmIsText(int mIsText) {
            this.mIsText = mIsText;
            return this;
        }

        public Builder setmIsCustom(int mIsCustom) {
            return this;
        }

        public Builder setmText(String mText) {
            this.mText = mText;
            return this;
        }
    }
}
