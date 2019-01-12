package com.allon.customerview.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.allon.customerview.R;

public class CircleView extends View {
    private int defaultWidth = 200;
    private int defaultHeight = 200;
    private Paint mPaint;
    private int mColor = Color.RED;
    private int mRadius = 0;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context,  AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = a.getColor(R.styleable.CircleView_circle_color, mColor);
        mRadius = a.getColor(R.styleable.CircleView_circle_radius, 0);
        a.recycle();
        init();
    }

    private void init() {
       mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
       mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        System.out.println("width = " + width);
        System.out.println("widthMode = " + widthMode);
        System.out.println("height = " + height);
        System.out.println("heightMode = " + heightMode);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

    }


    private int measureWidth(int widthMeasureSpec) {
        int resultSize = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                resultSize = defaultWidth;
        }
        return resultSize;
    }

    private int measureHeight(int heightMeasureSpec) {
        int resultSize = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                resultSize = defaultHeight;
        }
        return resultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = mRadius == 0 ?  Math.min(width, height) / 2 : mRadius;
        canvas.drawCircle(paddingLeft + width/2, paddingTop + height/2, radius, mPaint);

    }
}
