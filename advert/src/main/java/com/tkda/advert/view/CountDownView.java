package com.tkda.advert.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.tkda.advert.tools.DensityUtils;
import com.tkda.advert.interf.FinishInterfece;
import com.tkda.advert.R;

public class CountDownView extends View {

    private final int defCircleBg = 0x583d3d3d;
    private final int defTextColor = 0xffefefef;
    private final int defTextSize = 12;//sp
    private final int defProgressColor = 0xffff0000;
    private final int defProgressWidth = 3;// dp
    private final int defTime = 3; //单位 s
    private final int defRadius = 25; //单位 dp

    private int circleBg;
    private int textColor;
    private int textSize;
    private int progressColor;
    private int progressWidth;
    private int radius;
    private int time;

    private float totalTime;
    private float curTime;

    private Paint bgPaint;
    private Paint textPaint;
    private Paint proPaint;

    private Rect mBounds;//绘制文字的范围
    private RectF mRectF;//圆弧的外接矩形

    private FinishInterfece finishInterfece;

    private Handler handler = new Handler();

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView, defStyleAttr, 0);
        circleBg = typedArray.getColor(R.styleable.CountDownView_circleBg, defCircleBg);

        progressColor = typedArray.getColor(R.styleable.CountDownView_progressColor, defProgressColor);
        progressWidth = typedArray.getDimensionPixelSize(R.styleable.CountDownView_progressWidth, DensityUtils.dp2px(context, defProgressWidth));

        textColor = typedArray.getColor(R.styleable.CountDownView_textColor, defTextColor);
        textSize = typedArray.getDimensionPixelSize(R.styleable.CountDownView_textSize, DensityUtils.sp2px(context, defTextSize));

        time = typedArray.getInteger(R.styleable.CountDownView_time, defTime);

        radius = typedArray.getDimensionPixelSize(R.styleable.CountDownView_radius, DensityUtils.dp2px(context, defRadius));

        typedArray.recycle();

        // 初始化 画笔
        initPaint();
    }

    private void initPaint() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(circleBg);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        proPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        proPaint.setColor(progressColor);
        proPaint.setStyle(Paint.Style.STROKE);
        proPaint.setStrokeWidth(progressWidth);

        mBounds = new Rect();
        mRectF = new RectF();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finishInterfece != null) {
                    finishInterfece.onFinish();
                    if (handler != null)
                        handler.removeCallbacks(showTimeRunnable);
                }
            }
        });

        //扩大10倍
        totalTime = time * 100;
        curTime = totalTime;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//指定宽高了
            result = specSize;
            if (result / 2 < radius) {
                radius = result / 2;
            }
        } else {// 一般为WARP_CONTENT
            result = 2 * radius;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, bgPaint);
        //画进度条
        mRectF.set(getWidth() / 2 - radius + progressWidth / 2, getHeight() / 2 - radius + progressWidth / 2, getWidth() / 2 + radius - progressWidth / 2, getHeight() / 2 + radius - progressWidth / 2);
        canvas.drawArc(mRectF, 270, 360 * curTime / totalTime, false, proPaint);
        //画文本
        String text = "跳过";
        textPaint.getTextBounds(text, 0, text.length(), mBounds);
        canvas.drawText(text, getWidth() / 2 - mBounds.width() / 2, getHeight() / 2 + mBounds.height() * 2 / 5, textPaint);

    }

    public void setCircleBg(int circleBg) {
        this.circleBg = circleBg;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setFinishInterfece(FinishInterfece finishInterfece) {
        this.finishInterfece = finishInterfece;
    }

    /**
     * 显示view  开始
     */
    public void showStartTime() {
        //开始之前 若有外部方法设置参数，就重新初始化一下画笔参数
        initPaint();
        if (handler != null) {
            handler.postDelayed(showTimeRunnable, 100);
        }
    }

    /**
     * 不显示view  开始
     */
    public void unShowStart() {
        if (handler != null) {
            handler.postDelayed(unShowTimeRunnable, 100);
        }
    }

    private Runnable unShowTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (time == 0) {
                if (finishInterfece != null)
                    finishInterfece.onFinish();
                handler = null;
                return;
            }
            time--;
            handler.postDelayed(unShowTimeRunnable, 1000);
        }
    };
    private Runnable showTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (curTime == 0) {
                if (finishInterfece != null)
                    finishInterfece.onFinish();
                handler = null;
                return;
            }
            curTime--;
            invalidate();
            handler.postDelayed(showTimeRunnable, 10);//100毫秒刷新一次
        }
    };
}
