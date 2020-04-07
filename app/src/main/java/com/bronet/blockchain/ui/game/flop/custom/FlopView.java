package com.bronet.blockchain.ui.game.flop.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bronet.blockchain.R;

/**
 * Created by lxp  on 2018/4/26.
 */
public class FlopView extends View {

    private int mIsCustom;// -1是自定义模式  0必须指定图片，不指定会报错   1.自定义模式
    private int mPositiveRes;//正面的图片
    private int mOppositeRes;//反面的图片
    private Paint mPaint;//画笔

    public FlopView(Context context) {
        this(context,null);
    }

    public FlopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlopView);
        try {
            mIsCustom = typedArray.getInteger(R.styleable.FlopView_flopIsCustom,0);


            if (mIsCustom == 0){
                mPositiveRes = typedArray.getResourceId(R.styleable.FlopView_flopOppositeRes,0);
                mOppositeRes = typedArray.getResourceId(R.styleable.FlopView_flopOppositeRes,0);
                if (mPositiveRes == 0 || mOppositeRes == 0)
                    throw new RuntimeException("当isCustom为0的时候，必须指定正面和反面的图片");
            }


        }catch (Exception e){

        }finally {
            typedArray.recycle();
        }
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//描边
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = 200;
        int heightSize = 300;

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (modeWidth == MeasureSpec.AT_MOST){ //warp_content
            widthSize = Math.min(sizeWidth,widthSize);
        }else if (modeWidth == MeasureSpec.EXACTLY){ //指定大小
            widthSize = sizeWidth;
        }else {

        }

        if (modeHeight == MeasureSpec.AT_MOST){
            heightSize = Math.min(sizeHeight,heightSize);
        }else if (modeHeight == MeasureSpec.EXACTLY){
            heightSize = sizeHeight;
        }
        //不减动画会和边界交叉
        setMeasuredDimension(widthSize,heightSize);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}























