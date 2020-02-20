package com.tkda.advert.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tkda.advert.R;
import com.tkda.advert.http.DownLoadResource;
import com.tkda.advert.interf.FinishInterfece;
import com.tkda.advert.interf.HttpCallBack;
import com.tkda.advert.tools.DensityUtils;

public class AdvertView extends FrameLayout {
    private ImageView imageView;
    private CountDownView countDownView;

    private final int defCircleBg = 0x583d3d3d;
    private final int defTextColor = 0xffefefef;
    private final int defTextSize = 12;//sp
    private final int defProgressColor = 0xffff0000;
    private final int defProgressWidth = 3;// dp
    private final int defTime = 3; //单位 s
    private final int defRadius = 25; //单位 dp

    private boolean show;
    private int circleBg;
    private int textColor;
    private int textSize;
    private int progressColor;
    private int progressWidth;
    private int radius;
    private int time;

    private FinishInterfece finishInterfece;

    public void setFinishInterfece(FinishInterfece finishInterfece) {
        this.finishInterfece = finishInterfece;
    }

    public AdvertView(Context context) {
        this(context, null);
    }

    public AdvertView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AdvertView, defStyleAttr, 0);
        circleBg = typedArray.getColor(R.styleable.AdvertView_time_bg, defCircleBg);

        progressColor = typedArray.getColor(R.styleable.AdvertView_time_pro_color, defProgressColor);
        progressWidth = typedArray.getDimensionPixelSize(R.styleable.AdvertView_time_pro_width, DensityUtils.dp2px(context, defProgressWidth));

        textColor = typedArray.getColor(R.styleable.AdvertView_time_text_color, defTextColor);
        textSize = typedArray.getDimensionPixelSize(R.styleable.AdvertView_time_text_size, DensityUtils.sp2px(context, defTextSize));

        time = typedArray.getInteger(R.styleable.AdvertView_time_num, defTime);

        radius = typedArray.getDimensionPixelSize(R.styleable.AdvertView_time_radius, DensityUtils.dp2px(context, defRadius));

        show = typedArray.getBoolean(R.styleable.AdvertView_show, true);

        typedArray.recycle();

        View view = View.inflate(context, R.layout.advert_layout, this);

        imageView = view.findViewById(R.id.image);
        countDownView = view.findViewById(R.id.cd_view);
        countDownView.setFinishInterfece(new FinishInterfece() {
            @Override
            public void onFinish() {
                if (finishInterfece != null) {
                    finishInterfece.onFinish();
                }
            }
        });
        if (show) {
            countDownView.setVisibility(View.VISIBLE);
            countDownView.setCircleBg(circleBg);
            countDownView.setProgressColor(progressColor);
            countDownView.setProgressWidth(progressWidth);
            countDownView.setTextColor(textColor);
            countDownView.setTextSize(textSize);
            countDownView.setTime(time);
            countDownView.setRadius(radius);
            countDownView.showStartTime();
        } else {
            countDownView.setVisibility(View.GONE);
            countDownView.unShowStart();
        }
    }

    public void setImage(int resourceId) {
        if (imageView != null) {
            imageView.setImageResource(resourceId);
        }
    }

    public void setImage(Bitmap bitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void setImage(String url) {
        if (url != null) {
            if (url.contains("http")) {
                DownLoadResource downLoadResource = new DownLoadResource();
                downLoadResource.setHttpCallBack(new HttpCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        setImage(bitmap);
                    }
                });
                downLoadResource.execute(url);
            }
        }
    }
}
