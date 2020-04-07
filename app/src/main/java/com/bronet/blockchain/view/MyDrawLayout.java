package com.bronet.blockchain.view;


import android.content.Context;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.AttributeSet;

public class MyDrawLayout extends DrawerLayout {
    public MyDrawLayout(Context context) {
        super(context);
    }

    public MyDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDrawLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
