package com.tkda.advert.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tkda.advert.interf.TimeListener;
import com.tkda.advert.tools.DensityUtils;
import com.tkda.advert.interf.AdvertListener;
import com.tkda.advert.R;

public class CountDownView extends View {

    private final int SHOW_TIME_VIEW = 1001;
    private final int UNSHOW_TIME_VIEW = 1002;
    private final int SHOW_TIME = 1003;

    private Context mContext;
    private final int defCircleBg = 0x583d3d3d;
    private final int defTextColor = 0xffefefef;
    private final int defTextSize = 12;//sp
    private final int defProgressColor = 0xffff0000;
    private final int defProgressWidth = 3;// dp
    private final int defTime = 3000; //单位 ms
    private final int defRadius = 25; //单位 dp

    private int circleBg;
    private int textColor;
    private int textSize;
    private int progressColor;
    private int progressWidth;
    private int radius;
    private int totalTime;
    private boolean showTime;

    private String content = "跳过"; // 中间的内容，默认是 跳过

    private int delayed = 30;
    private float sweepAngle = 360;

    private Paint bgPaint;
    private Paint textPaint;
    private Paint proPaint;

    private Rect mBounds;//绘制文字的范围
    private RectF mRectF;//圆弧的外接矩形

    private TimeListener timeListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SHOW_TIME_VIEW:
                    dealAngle();
                    break;
                case UNSHOW_TIME_VIEW:
                    dealTimer();
                    break;
                case SHOW_TIME:
                    dealTime();
                    break;
            }
        }
    };

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public void setCircleBg(int circleBg) {
        this.circleBg = circleBg;
        bgPaint.setColor(circleBg);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        proPaint.setColor(progressColor);
    }

    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
        proPaint.setStrokeWidth(progressWidth);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setTime(int totalTime) {
        this.totalTime = totalTime;
        initDelayed();
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }


    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView, defStyleAttr, 0);
        circleBg = typedArray.getColor(R.styleable.CountDownView_time_circle_bg, defCircleBg);

        progressColor = typedArray.getColor(R.styleable.CountDownView_time_pro_color, defProgressColor);
        progressWidth = typedArray.getDimensionPixelSize(R.styleable.CountDownView_time_pro_width, DensityUtils.dp2px(context, defProgressWidth));

        textColor = typedArray.getColor(R.styleable.CountDownView_time_text_color, defTextColor);
        textSize = typedArray.getDimensionPixelSize(R.styleable.CountDownView_time_text_size, DensityUtils.sp2px(context, defTextSize));

        totalTime = typedArray.getInteger(R.styleable.CountDownView_time_num, defTime);

        radius = typedArray.getDimensionPixelSize(R.styleable.CountDownView_time_radius, DensityUtils.dp2px(context, defRadius));

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
                if (timeListener != null) {
                    timeListener.onDismiss();
                    stop();
                }
            }
        });
        initDelayed();
    }

    private void initDelayed() {
        delayed = totalTime / 100;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, bgPaint);
        //画进度条
        mRectF.set(getWidth() / 2 - radius + progressWidth / 2, getHeight() / 2 - radius + progressWidth / 2, getWidth() / 2 + radius - progressWidth / 2, getHeight() / 2 + radius - progressWidth / 2);
        canvas.drawArc(mRectF, 270, sweepAngle, false, proPaint);
        //画文本
        textPaint.getTextBounds(content, 0, content.length(), mBounds);
        canvas.drawText(content, getWidth() / 2 - mBounds.width() / 2, getHeight() / 2 + mBounds.height() * 2 / 5, textPaint);
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

    /**
     * 显示view  开始
     */
    public void showStartTime() {
        if (handler != null) {
            if (showTime) {
                handler.sendEmptyMessage(SHOW_TIME);
            }
            handler.sendEmptyMessage(SHOW_TIME_VIEW);
        }
    }

    /**
     * 不显示view  开始
     */
    public void unShowStart() {
        if (handler != null) {
            handler.sendEmptyMessage(UNSHOW_TIME_VIEW);
        }
    }

    public void stop() {
        if (handler != null) {
            handler.removeMessages(SHOW_TIME_VIEW);
            handler.removeMessages(UNSHOW_TIME_VIEW);
            handler.removeMessages(SHOW_TIME);
            handler = null;
        }
    }

    private void dealAngle() {
        if (sweepAngle <= 0) {
            if (timeListener != null) {
                timeListener.onDismiss();
                stop();
            }
            return;
        }
        /**
         * 每次绘制3.6°，都是平均到100次绘制，根据设置的总时间来决定每次绘制的间隔时间
         */
        sweepAngle -= 3.6f;
        invalidate();
        handler.sendEmptyMessageDelayed(SHOW_TIME_VIEW, delayed);
    }

    private void dealTimer() {
        if (totalTime <= 0) {
            if (timeListener != null) {
                timeListener.onDismiss();
                stop();
            }
            return;
        }
        totalTime -= 1000;
        handler.sendEmptyMessageDelayed(UNSHOW_TIME_VIEW, 1000);
    }

    private void dealTime() {
        content = (totalTime / 1000) + "s";
        if (totalTime == 0) {
            return;
        }
        totalTime -= 1000;
        handler.sendEmptyMessageDelayed(SHOW_TIME, 1000);
    }
}
