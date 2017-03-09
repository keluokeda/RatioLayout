package com.ufind.library;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {
    private int widthWeight;
    private int heightWeight;
    private boolean isFixedWidth;

    public RatioLayout(@NonNull Context context) {
        super(context);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.RatioLayout);
        widthWeight = typedArray.getInt(R.styleable.RatioLayout_width_weight, 1);
        heightWeight = typedArray.getInt(R.styleable.RatioLayout_height_weight, 1);
        isFixedWidth = typedArray.getInt(R.styleable.RatioLayout_fixed, 1) == 1;

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        // Children are just made to fill our space.
        int childWidthSize;
        if (isFixedWidth) {
            childWidthSize = getMeasuredWidth();
            int width = childWidthSize;
            int height = width * heightWeight / widthWeight;
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        } else {
            childWidthSize = getMeasuredHeight();
            int height = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
            int width = height * widthWeight / heightWeight;
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        }

    }
}
