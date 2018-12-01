package com.allon.customerview.basekonwageview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomerView extends ViewGroup {

    private Scroller mScroller;


    public CustomerView(Context context) {
        this(context, null);
    }

    public CustomerView(Context context,  AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomerView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("CustomerView","onTouchEvent");
        return super.onTouchEvent(event);
    }

    public void start(){
        mScroller.startScroll(0,0,500, 0, 2000);
        invalidate();
    }


    @Override
    public void computeScroll() {
       if (mScroller.computeScrollOffset()) {
          scrollTo(mScroller.getCurrX(),0);
          postInvalidate();
       }
    }

}
