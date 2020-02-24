package com.tkda.advert.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tkda.advert.R;
import com.tkda.advert.tools.DensityUtils;

public class LoadingView extends View {
    private Context mContext;
    private final int defColor = 0xffff0000;
    private final int defRadis = 25;
    private int defLoadingWidth = 2;

    private int startAngle = 0; // 开始角度
    private int color;
    private int radius;
    private int width;

    private Paint mPaint;
    private RectF mRectF;//圆弧的外接矩形

    private Handler handler = new Handler();

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0);
        color = typedArray.getColor(R.styleable.LoadingView_loading_color, defColor);
        radius = typedArray.getDimensionPixelSize(R.styleable.LoadingView_loading_radius, DensityUtils.dp2px(context, defRadis));
        width = typedArray.getDimensionPixelSize(R.styleable.LoadingView_loading_width, DensityUtils.dp2px(context, defLoadingWidth));
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        initPaint();
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
        super.onDraw(canvas);
        mRectF.set(getWidth() / 2 - radius + width / 2, getHeight() / 2 - radius + width / 2, getWidth() / 2 + radius - width / 2, getHeight() / 2 + radius - width / 2);
        //固定圆弧为270度，每次更新起点角度
        canvas.drawArc(mRectF, startAngle, 270, false, mPaint);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startAngle += 10f;//每次更新10度，一周360，需要36次更新完
            invalidate();
            handler.postDelayed(runnable, 15);//这个延迟数字*36，就是一周的毫秒值；
        }
    };

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != View.VISIBLE) {
            if (handler != null)
                handler.removeCallbacks(runnable);
            handler = null;
        }
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setWidth(int width) {
        this.width = DensityUtils.dp2px(mContext, width);
        mPaint.setStrokeWidth(this.width);
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(width);
    }

    public void start() {
        if (handler == null) {
            handler = new Handler();
        }
        handler.post(runnable);
    }
}
