package com.example.drawabledemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import static android.graphics.PixelFormat.TRANSLUCENT;

public class CustomerDrawable extends Drawable {

    private final Paint mPaint;

    public CustomerDrawable(int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);

    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        float centerX = rect.exactCenterX();
        float centerY = rect.exactCenterY();
        canvas.drawCircle(centerX, centerY, Math.min(centerX, centerY), mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter( ColorFilter colorFilter) {
       mPaint.setColorFilter(colorFilter);
       invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return TRANSLUCENT;
    }
}
