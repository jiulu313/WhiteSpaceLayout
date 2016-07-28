package com.zhj.view;

/**
 * Created by zhanghongjun on 16/7/25.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.zhj.R;

/**
 * Created by zhanghongjun on 16/7/24.
 */
public class WhiteSpaceLayout extends ViewGroup {
    public static final int DEFAULT_H_PADING = 0;
    public static final int DEFAULT_V_PADING = 24;

    //离左右的距离
    int H_pading = DEFAULT_H_PADING;//水平
    int V_pading = DEFAULT_V_PADING;//垂直

    public WhiteSpaceLayout(Context context) {
        this(context, null);
    }

    public WhiteSpaceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
	//TODO:open it for your own attrs
	/*
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        H_pading = (int) ta.getDimension(R.styleable.FlowLayout_H_pading,DEFAULT_H_PADING);
        V_pading = (int) ta.getDimension(R.styleable.FlowLayout_V_pading,DEFAULT_V_PADING);
        ta.recycle();*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modelWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        //测量自己的宽高
        int maxHeight = 0;
        int maxWidth = 0;
        int nCount = getChildCount();
        for (int i = 0; i < nCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            maxWidth += childWidth;

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int tempHeight = childHeight + lp.topMargin + lp.bottomMargin;
            maxHeight = Math.max(maxHeight, tempHeight);
        }

        if(modelWidth == MeasureSpec.AT_MOST){
            maxWidth = maxWidth + DEFAULT_H_PADING * (nCount + 1);
            width = maxWidth;
        }else if(modelWidth == MeasureSpec.EXACTLY){
            width = sizeWidth;
        }

        if(modeHeight == MeasureSpec.AT_MOST){
            height = maxHeight + V_pading * 2;
        }else if(modeHeight == MeasureSpec.EXACTLY){
            height = sizeHeight;
        }

        //Log.d("xxyy","height="+height);

        //设置自己的宽和高
        setMeasuredDimension(width, height);

        int allChildWidth = 0;
        //重新设置子view的宽度
        for (int i = 0; i < nCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            allChildWidth += childWidth;
        }

        //算出控件之间的空隙
        H_pading = (width - allChildWidth) / (nCount + 1);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int tempWidth = left + H_pading;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int lc = tempWidth;
            int tc = (height - childHeight) / 2;
            int rc = lc + childWidth;
            int bc = tc + childHeight;

            child.layout(lc, tc, rc, bc);
            tempWidth += (childWidth + H_pading);
        }
    }


    /**
     * 与当前ViewGroup对应的LayotParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}




















