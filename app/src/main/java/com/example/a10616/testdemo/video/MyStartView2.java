package com.example.a10616.testdemo.video;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import com.shuyu.gsyvideoplayer.R;

import moe.codeest.enviews.ENPlayView;

/**
 * Created by codeest on 16/11/7.
 */

public class MyStartView2 extends ENPlayView {

    public static int STATE_PLAY = 0;
    public static int STATE_PAUSE = 1;

    private  Paint mPaint;

    private  Paint paintPause;

    private int mCurrentState = STATE_PAUSE;
    private int mWidth;
    private int mHeight;


    public MyStartView2(Context context) {
        super(context);
    }

    public MyStartView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ffff0000"));
        mPaint.setTextSize(80f);
        paintPause = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPause.setColor(Color.parseColor("#ff00ff00"));
        paintPause.setTextSize(80f);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w * 9 / 10;
        mHeight = h * 9 / 10;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCurrentState == STATE_PLAY){
            canvas.drawText("暂停暂停",mWidth/2,mHeight/2,mPaint);
        }else if(mCurrentState == STATE_PAUSE){
            canvas.drawText("开始开始",mWidth/2,mHeight/2,paintPause);
        }


    }
    @Override
    public void play() {

        mCurrentState = STATE_PLAY;
        invalidate();

    }
    @Override
    public void pause() {
        mCurrentState = STATE_PAUSE;
        invalidate();

    }

    public int getCurrentState() {
        return mCurrentState;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
